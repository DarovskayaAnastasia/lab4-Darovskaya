import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class TestData {

    private String testName;
    private String expectedResult;
    private Object[] params;

    public String getTestName() {
        return testName;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public Object[] getParams() {
        return params;
    }

}

