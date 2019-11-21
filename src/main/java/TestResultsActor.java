import akka.actor.AbstractActor;

public class TestResultsActor {
    public static enum Msg {
        oneTestResult, getOneTestResultRequest
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .matchEquals().build();
    }

}
