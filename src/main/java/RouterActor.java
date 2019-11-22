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
    private final ActorRef storeActor;
    private final ActorRef testRunner;

    private static final int MAX_RETRIES = 10;
    LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);


    private static SupervisorStrategy strategy =
            new OneForOneStrategy(MAX_RETRIES,
                    Duration.create("1 minute"),
                    DeciderBuilder.
                            match(NullPointerException.class, e -> restart()).
                            match(IllegalArgumentException.class, e -> stop()).
                            matchAny(o -> escalate()).build());
    public RouterActor() {
        super();
        this.storeActor = getContext().actorOf(TestResultsActor.props(), "store_actor");
        this.testRunner = getContext().actorOf(
                new RoundRobinPool(5)
                        .withSupervisorStrategy(strategy)
                        .props(TestRunnerActor.props()), "testing_router"
        );
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(MsgTest.class, m -> {
                    
                })
                .match()
                .matchAny(o -> log.info(o.toString() + o.getClass()))
                .build();
    }
}
