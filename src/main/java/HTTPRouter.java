import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Scheduler;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;

import java.time.Duration;

import static akka.http.javadsl.server.Directives.*;

public class HTTPRouter {

    final private ActorRef router;
    private final Duration askTimeout;
    private final Scheduler scheduler;

    public HTTPRouter(ActorSystem system, ActorRef router) {
        scheduler = system.scheduler();
        askTimeout = system.settings().config().getDuration("my-app.routes.ask-timeout");
        this.router = router;
    }

    public Route result() {
        return concat(getResult());
    }

    private Route getResult() {

    }

    public Route userRoutes() {
        return pathPrefix("users", () ->
                concat(
                        //#users-get-delete
                        pathEnd(() ->
                                concat(
                                        get(() ->
                                                onSuccess(getUsers(),
                                                        users -> complete(StatusCodes.OK, users, Jackson.marshaller())
                                                )
                                        ),
                                        post(() ->
                                                entity(
                                                        Jackson.unmarshaller(User.class),
                                                        user ->
                                                                onSuccess(createUser(user), performed -> {
                                                                    log.info("Create result: {}", performed.description);
                                                                    return complete(StatusCodes.CREATED, performed, Jackson.marshaller());
                                                                })
                                                )
                                        )
                                )
                        ),
                        //#users-get-delete
                        //#users-get-post
                        path(PathMatchers.segment(), (String name) ->
                                concat(
                                        get(() ->
                                                        //#retrieve-user-info
                                                        rejectEmptyResponse(() ->
                                                                onSuccess(getUser(name), performed ->
                                                                        complete(StatusCodes.OK, performed.maybeUser, Jackson.marshaller())
                                                                )
                                                        )
                                                //#retrieve-user-info
                                        ),
                                        delete(() ->
                                                        //#users-delete-logic
                                                        onSuccess(deleteUser(name), performed -> {
                                                                    log.info("Delete result: {}", performed.description);
                                                                    return complete(StatusCodes.OK, performed, Jackson.marshaller());
                                                                }
                                                        )
                                                //#users-delete-logic
                                        )
                                )
                        )
                        //#users-get-post
                )
        );
    }
    //#all-routes
}

