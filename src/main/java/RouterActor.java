import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.*;
import static akka.actor.SupervisorStrategy.escalate;

public class RouterActor {
    private static final int MAX_RETRIES = 10;

    ActorSystem system = ActorSystem.create("test");
    ActorRef routerActor = system.actorOf(
            Props.create(routerActor.class)
    );
    storeActor.tell(
        new StoreActor.StoreMessage("test", "test"),
        ActorRef.noSender()
        );

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
