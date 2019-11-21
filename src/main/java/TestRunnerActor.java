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

    private static test(msg) {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(jscript);
        Invocable invocable = (Invocable) engine;
        return invocable.invokeFunction(functionName, params).toString();
    }
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(SomeName.class, m -> {
                    test(m);
                    getContext().actorSelection(path)
                            .tell(,self());
                })
                .build();
    }

    static Props props() {
        return Props.create(TestRunnerActor.class);
    }
}
