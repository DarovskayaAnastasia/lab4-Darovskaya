package ActorsPackage;

import MessagesPackage.MsgResponse;
import MessagesPackage.MsgResult;
import TestPackage.TestResult;
import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

import java.util.*;


public class StoreActor extends AbstractActor {
    private Map<String, List<TestResult>> store = new HashMap<>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestResult.class, m -> {
                    store.computeIfAbsent(m.getPackageID(), func -> new ArrayList<>());
                    store.get(m.getPackageID()).add(m);
                })
                .match(MsgResult.class, m -> {
                    List<TestResult> results = store.get(m.getPackageID());
                    if (!results.isEmpty()) {
                        results.sort(Comparator.comparing(TestResult::getTestName));

                        MsgResponse response = new MsgResponse(m.getPackageID(), results.toArray(new TestResult[0]));

                        sender().tell(response, self());
                    }
                    else {
                        sender().tell("empty results statement", self());
                    }
                }).build();
    }

    static Props props() {
        return Props.create(StoreActor.class);
    }

}
