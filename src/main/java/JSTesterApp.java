import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class JSTesterApp {

    private static final String ROOT_ACTOR_NAME = "initializing_actor";

    public JSTesterApp() {}

    public static void main(String[] args) throws IOException {
        startHttpServer();
    }

    private static void startHttpServer() throws IOException {

        ActorSystem classicSystem = ActorSystem.create("local_server");
        ActorRef rootActor = classicSystem.actorOf(Props.create(RouterActor.class), ROOT_ACTOR_NAME);

        final Http http = Http.get(classicSystem);
        final ActorMaterializer materializer = ActorMaterializer.create(classicSystem);

        HTTPRouter httpRouter = new HTTPRouter();

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = httpRouter.createRoute(rootActor).flow(classicSystem, materializer);

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
    public static String getRootActorName() {
        return ROOT_ACTOR_NAME;
    }
}
