import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import java.util.*;

import static akka.actor.SupervisorStrategy.*;
import static akka.actor.SupervisorStrategy.escalate;

public class TestResultsActor extends AbstractActor {
    private Map<String, List<TestResult>> store = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder().create()
                .match(TestResult.class, m -> {
                    store.computeIfAbsent(m.getPackageID(), func -> new ArrayList<>());
                    store.get(m.getPackageID()).add(m);
                })
                .match(TestResult.class, m -> {
                    List<TestResult> results = store.get(m.getPackageID());
                    if (!results.isEmpty()) {
                        results.sort(Comparator.comparing(TestResult::getTestName));

                        Response response = new Response(m.getPackageID(), results.toArray(new TestResult[0]))
                    }
                    else {
                        sender().tell("empty results statement", self());
                    }
                }).build();
    }

    static Props props() {
        return Props.create(TestResultsActor.class);
    }

}
