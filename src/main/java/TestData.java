import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class TestData {

    private String testName;
    private String expectedResult;
    private Object[] params;

    public TestData(String testName, String expectedResult, Object[] params) {
        this.testName = testName;
        this.expectedResult = expectedResult;
        this.params = params;
    }

//    getters
    public String getTestName() {
        return testName;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public Object[] getParams() {
        return params;
    }

//  setters
    public void setParams(Object[] params) {
        this.params = params;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }


}

