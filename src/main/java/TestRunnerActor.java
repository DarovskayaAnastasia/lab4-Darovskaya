import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.*;

public class TestRunnerActor extends AbstractActor {
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
    }
