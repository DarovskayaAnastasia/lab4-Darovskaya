import akka.actor.AbstractActor;

public class TestResultsActor extends AbstractActor {
    public static enum Msg {
        oneTestResult, oneTestResultRequest
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(StoreMessage.class, m -> {
                    sender().tell(Msg.oneTestResultRequest, self());
                }).build();
    }

}
