import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class TestData {

    private String testName;
    private String expectedResult;
    private Object[] params;

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

    


}

