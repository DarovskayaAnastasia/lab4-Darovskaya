import akka.actor.AbstractActor;

public class TestResultsActor extends AbstractActor {
    private Map<String, >

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(StoreMessage.class, m -> {
                    sender().tell(Msg.oneTestResultRequest, self());
                }).build();
    }

}
