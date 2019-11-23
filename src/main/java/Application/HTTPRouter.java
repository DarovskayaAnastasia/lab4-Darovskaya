package Application;

import MessagesPackage.MsgResult;
import MessagesPackage.MsgTest;
import akka.actor.ActorRef;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import scala.concurrent.Future;


class HTTPRouter extends AllDirectives {

    private final int TIMEOUT_MILLIS = 5000;
    private final String PARAMETER_PACKAGE_ID_NAME = "packageID";

    HTTPRouter() {}

    final Route createRoute(ActorRef rootActor) {
        String RESULT_PATH_SEGMENT = "result";
        String TEST_PATH_SEGMENT = "test";
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
                                parameter(PARAMETER_PACKAGE_ID_NAME, packageID -> {
                                    Future<Object> result = Patterns.ask(rootActor, new MsgResult(packageID), TIMEOUT_MILLIS);
                                    return completeOKWithFuture(result, Jackson.marshaller());
                                })
                        )
                )
        );
    }
}

