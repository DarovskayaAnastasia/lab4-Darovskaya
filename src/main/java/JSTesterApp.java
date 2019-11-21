import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletionStage;

public class JSTesterApp extends AbstractActor {

    static void startHttpServer(Route route, ActorSystem system) {
        // Akka HTTP still needs a classic ActorSystem to start
        akka.actor.ActorSystem classicSystem = ActorSystem.create("local_server");
        final Http http = Http.get(classicSystem);
        final Materializer materializer = Materializer.matFromSystem(system);

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = route.flow(classicSystem, materializer);
        CompletionStage<ServerBinding> futureBinding =
                http.bindAndHandle(routeFlow, ConnectHttp.toHost("localhost", 8080), materializer);

        futureBinding.whenComplete((binding, exception) -> {
            if (binding != null) {
                InetSocketAddress address = binding.localAddress();
                system.log().info("Server online at http://{}:{}/",
                        address.getHostString(),
                        address.getPort());
            } else {
                system.log().error("Failed to bind HTTP endpoint, terminating system", exception);
                system.terminate();
            }
        });
    }
}
