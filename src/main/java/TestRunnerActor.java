import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.*;

public class TestRunnerActor extends AbstractActor {

    private static final int MAX_RETRIES = 10;
    LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private static TestRunnerActor

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(SomeName.class, m -> {})
                .build();
    }
}
