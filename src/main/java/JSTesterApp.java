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

    public JSTesterApp(ActorSystem system) {
        //
    }

    static void startHttpServer() {

        ActorSystem classicSystem = ActorSystem.create("local_server");
        final Http http = Http.get(classicSystem);
        final ActorMaterializer materializer = ActorMaterializer.create(classicSystem);

        JSTesterApp jsTesterApp = new JSTesterApp(classicSystem);

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = jsTesterApp.createRoute().flow(classicSystem, materializer);

        CompletionStage<ServerBinding> futureBinding =
                http.bindAndHandle(routeFlow,
                        ConnectHttp.toHost("localhost", 8080),
                        materializer);

        futureBinding.whenComplete((binding, exception) -> {
            if (binding != null) {
                InetSocketAddress address = binding.localAddress();
                classicSystem.log().info("Server online at http://{}:{}/",
                        address.getHostString(),
                        address.getPort());
            } else {
                classicSystem.log().error("Failed to bind HTTP endpoint, terminating system", exception);
                classicSystem.terminate();
            }
        });
    }
}
