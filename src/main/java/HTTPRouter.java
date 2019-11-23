import akka.actor.ActorRef;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;

import scala.concurrent.Future;


public class HTTPRouter extends AllDirectives {

    private final String TEST_PATH_SEGMENT = "test";
    private final String RESULT_PATH_SEGMENT = "result";
    private final int TIMEOUT_MILLIS = 5000;

    HTTPRouter() {}

    final Route createRoute(ActorRef rootActor) {
        return route(
                path(TEST_PATH_SEGMENT, () ->
                        post(() ->
                                entity(Jackson.unmarshaller(MsgTest.class), m -> {
                                    rootActor.tell(m, ActorRef.noSender());
                                    return complete("test started");
                                })
                        )
                ),
                path(RESULT_PATH_SEGMENT, () ->
                        get(() ->
                                parameter("packageID", packageID -> {
                                    Future<Object> result = Patterns.ask(rootActor, new MsgResult(packageID), TIMEOUT_MILLIS);
                                    return completeOKWithFuture(result, Jackson.marshaller());
                                })
                        )
                )
        );
    }

    public String getTEST_PATH_SEGMENT() {
        return TEST_PATH_SEGMENT;
    }

    public String getRESULT_PATH_SEGMENT() {
        return RESULT_PATH_SEGMENT;
    }
}

