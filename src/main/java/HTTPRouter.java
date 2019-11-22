import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Scheduler;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;

import java.time.Duration;
import java.util.concurrent.Future;

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

    private Route createRoute(ActorSystem system) {
        return route(
                path("test", () ->
                        post(() ->
                                entity(Jackson.unmarshaller(.class), msg -> {
            RouterActor.tell(msg, ActorRef.noSender());
            return complete("test started");
        })
                        )
                ),
        path("result", () ->
                get(() ->
                        parameter("packageID", packageID -> {
                            Future<Object> result = Patterns.ask(RouterActor, new (packageID), 2500);
                            return completeOKWithFuture(result, Jackson.marshaller());
                        })
                )
        )
        );
    }
}

