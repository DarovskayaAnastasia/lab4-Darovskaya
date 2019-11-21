import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.*;
import static akka.actor.SupervisorStrategy.escalate;

public class TestResultsActor extends AbstractActor {
    private static final int MAX_RETRIES = 10;

    private static SupervisorStrategy strategy =
            new OneForOneStrategy(MAX_RETRIES,
                    Duration.create("1 minute"),
                    DeciderBuilder.
                            match(ArithmeticException.class, e -> resume()).
                            match(NullPointerException.class, e -> restart()).
                            match(IllegalArgumentException.class, e -> stop()).
                            matchAny(o -> escalate()).build());
    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
    
    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(StoreMessage.class, m -> {
                    sender().tell(Msg.oneTestResultRequest, self());
                }).build();
    }

}
