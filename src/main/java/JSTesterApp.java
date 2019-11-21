import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.actor.ActorSystem;

public class JSTesterApp extends AbstractActor {
    public static void main(String[] args) throws Exception {
        //#server-bootstrapping
        Behavior<NotUsed> rootBehavior = Behaviors.setup(context -> {
            ActorRef<UserRegistry.Command> userRegistryActor =
                    context.spawn(UserRegistry.create(), "UserRegistry");

            UserRoutes userRoutes = new UserRoutes(context.getSystem(), userRegistryActor);
            startHttpServer(userRoutes.userRoutes(), context.getSystem());

            return Behaviors.empty();
        });

        // boot up server using the route as defined below
        ActorSystem.create(rootBehavior, "HelloAkkaHttpServer");
        //#server-bootstrapping
    }
}
