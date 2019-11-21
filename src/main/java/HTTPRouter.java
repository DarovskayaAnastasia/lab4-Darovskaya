import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Scheduler;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;

import java.time.Duration;

import static akka.http.javadsl.server.Directives.*;

public class HTTPRouter {

    final private ActorRef router;
    private final Duration askTimeout;
    private final Scheduler scheduler;

    public HTTPRouter(ActorSystem system, ActorRef router) {
        scheduler = system.scheduler();
        askTimeout = system.settings().config().getDuration("my-app.routes.ask-timeout");
        this.router = router;
    }

    public Route result() {
        return concat(getResult());
    }

    private Route getResult() {
        return ;
    }

}

