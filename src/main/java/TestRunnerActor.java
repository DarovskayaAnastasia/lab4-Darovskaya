import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.*;

public class TestRunnerActor extends AbstractActor extends AbstractActor {

    private static final int MAX_RETRIES = 10;
    LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

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
