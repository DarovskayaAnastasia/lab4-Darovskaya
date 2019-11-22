import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import java.io.IOException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

import static akka.http.javadsl.server.Directives.*;

public class JSTesterApp {

    public JSTesterApp(ActorSystem system) {
        //
    }

    static void startHttpServer() throws IOException {

        ActorSystem classicSystem = ActorSystem.create("local_server");
        final Http http = Http.get(classicSystem);
        final ActorMaterializer materializer = ActorMaterializer.create(classicSystem);

        JSTesterApp jsTesterApp = new JSTesterApp(classicSystem);

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = jsTesterApp.createRoute().flow(classicSystem, materializer);

        final CompletionStage<ServerBinding> binding =
                http.bindAndHandle(routeFlow,
                        ConnectHttp.toHost("localhost", 8080),
                        materializer
                );

        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");

        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> classicSystem.terminate());
    }

    public static void main(String[] args) {
        
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
