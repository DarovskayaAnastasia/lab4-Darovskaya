import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static akka.actor.SupervisorStrategy.*;
import static akka.actor.SupervisorStrategy.escalate;

public class TestResultsActor extends AbstractActor {
    private Map<String, List<TestResult>> store = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder().create()
                .match(TestResult.class, m -> {
                    store.computeIfAbsent(m.getPackageID(), );
                }).build();
    }

    static Props props() {
        return Props.create(TestResultsActor.class);
    }

}
