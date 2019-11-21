import akka.actor.ActorRef;
import akka.http.javadsl.server.Route;

public class HTTPRouter {

    final private ActorRef router;

    public HTTPRouter(ActorRef router) {
        this.router = router;
    }

    private Route func() {
        route(
                path("semaphore", () ->
                        route(
                                get( () -> {
                                    Future<Object> result = Patterns.ask(testPackageActor,
                                            SemaphoreActor.makeRequest(), 5000);
                                    return completeOKWithFuture(result, Jackson.marshaller());
                                }))),
                path("test", () ->
                        route(
                                post(() ->
                                        entity(Jackson.unmarshaller(TestPackageMsg.class), msg -> {
                                            testPackageActor.tell(msg, ActorRef.noSender());
                                            return complete("Test started!");
                                        })))),
                path("put", () ->
                        get(() ->
                                parameter("key", (key) ->
                                        parameter("value", (value) ->
                                        {
                                            storeActor.tell(new StoreActor.StoreMessage(key, value), ActorRef.noSender());
                                            return complete("value saved to store ! key=" + key + " value=" + value);
                                        })))),
    }
}

