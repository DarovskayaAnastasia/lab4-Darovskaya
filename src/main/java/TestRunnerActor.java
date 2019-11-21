import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;

public class TestRunnerActor extends AbstractActor {
        private static final int MAX_RETRIES = 10;
...
        private static SupervisorStrategy strategy =
                new OneForOneStrategy(MAX_RETRIES,
        @Override
        public SupervisorStrategy supervisorStrategy() {
            return strategy;
        }
    }
