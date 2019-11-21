import akka.actor.ActorRef;
import akka.http.javadsl.server.Route;

import static akka.http.javadsl.server.Directives.path;
import static akka.http.javadsl.server.Directives.route;

public class HTTPRouter {

    final private ActorRef router;

    public HTTPRouter(ActorRef router) {
        this.router = router;
    }


}

