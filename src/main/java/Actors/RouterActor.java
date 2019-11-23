import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.RoundRobinPool;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.*;
import static akka.actor.SupervisorStrategy.escalate;

//initializing actor
public class RouterActor extends AbstractActor {

    private static final int MAX_RETRIES = 10;
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final ActorRef storeActor = getContext().actorOf(TestResultsActor.props(), ActorNames.STORE_ACTOR_NAME);
    private final ActorRef testRunner = getContext().actorOf(
            new RoundRobinPool(5)
                    .withSupervisorStrategy(strategy)
                    .props(TestRunnerActor.props()), ActorNames.TEST_RUNNER_ACTOR_NAME
    );


    private static SupervisorStrategy strategy =
            new OneForOneStrategy(MAX_RETRIES,
                    Duration.create("1 minute"),
                    DeciderBuilder.
                            match(NullPointerException.class, e -> restart()).
                            match(IllegalArgumentException.class, e -> stop()).
                            matchAny(o -> escalate()).build());


    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(MsgTest.class, m -> {
                    for (TestData test : m.getTests()) {
                        testRunner.tell(new OneTest(
                                m.getPackageID(),
                                m.getJsScript(),
                                m.getFuncName(),
                                new TestData(test.getTestName(),
                                        test.getExpectedResult(),
                                        test.getParams())), self());
                    }
                })
                .match(MsgResult.class, m -> {
                    storeActor.tell(m, sender());
                })
                .matchAny(o -> log.info(o.toString() + o.getClass()))
                .build();
    }
}
