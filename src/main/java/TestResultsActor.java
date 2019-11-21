import akka.actor.AbstractActor;

public class TestResultsActor extends AbstractActor {
    public static enum Msg {
        oneTestResult, OneTestResultRequest
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .matchEquals(Msg.oneTestResult, m -> {

                }).build();
    }

}
