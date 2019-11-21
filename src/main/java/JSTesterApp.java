import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletionStage;

public class JSTesterApp {
    private final HTTPRoutes userRoutes;

    public JSTesterApp(ActorSystem system) {
        //
    }

    static void startHttpServer() {

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

    protected Route createRoute() {
        return userRoutes.routes();
    }
}
