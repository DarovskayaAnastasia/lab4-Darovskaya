import akka.actor.ActorRef;

public class HTTPRouter {

    final private ActorRef router;

    public HTTPRouter(ActorRef router) {
        this.router = router;
    }

    
}

