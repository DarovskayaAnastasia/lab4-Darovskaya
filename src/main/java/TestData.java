import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class TestData {

    private String testName;
    private String jsScript;
    private Object[] params;

    public String getTestName() {
        return testName;
    }

    

}
