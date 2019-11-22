import akka.actor.ActorRef;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;

import java.util.concurrent.Future;


public class HTTPRouter extends AllDirectives {

    public HTTPRouter() {}

    private Route createRoute(ActorRef rootActor) {
        return route(
                path("test", () ->
                        post(() ->
                                entity(Jackson.unmarshaller(.class), msg -> {
                                    rootActor.tell(msg, ActorRef.noSender());
                                    return complete("test started");
                                })
                        )
                ),
                path("result", () ->
                        get(() ->
                                parameter("packageID", packageID -> {
                                    Future<Object> result = Patterns.ask(rootActor, new (packageID), 2500);
                                    return completeOKWithFuture(result, Jackson.marshaller());
                                })
                        )
                )
        );
    }
}

