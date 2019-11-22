import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.duration.Duration;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import static akka.actor.SupervisorStrategy.*;

public class TestRunnerActor extends AbstractActor {

    private static final int MAX_RETRIES = 10;
    LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public TestRunnerActor() {}

//    private String runTest(MsgTest m) {
//        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
//        TestData test = m.getTests()[0];
//        String testResult;
//
//        try {
//            engine.eval(m.getJsScript());
//            Invocable invocable = (Invocable) engine;
//            Object[] params = test.getParams();
//            return invocable.invokeFunction(m.getFunctionName(), params).toString();
//        }
//        catch (Exception exp) {
//            return testResult = "ERROR";
//        }
//    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(MsgTest.class, m -> {
                    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
                    TestData test = m.getTests()[0];
                    String testResult;

                    try {
                        engine.eval(m.getJsScript());
                        Invocable invocable = (Invocable) engine;
                        Object[] params = test.getParams();
                        testResult = invocable.invokeFunction(m.getFunctionName(), params).toString();
                    }
                    catch (Exception exp) {
                        testResult = "ERROR";
                    }
                    getContext().actorSelection("/user/local_server/store_actor")
                            .tell(new TestResult(
                                    m.getPackageID(),
                                    test.getTestName(),
                                    test.getExpectedResult(),
                                    testResult,
                                    testResult.equalsIgnoreCase(test.getExpectedResult()),
                                    test.getParams()
                            ), self());
                })
                .build();
    }

    static Props props() {
        return Props.create(TestRunnerActor.class);
    }
}
