import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonAutoDetect
@JsonPropertyOrder({"testName", "success", "expectedResult", "params", "result"})

public class TestResult {
    @JsonIgnore
    private String packageID;

    @JsonProperty
    private String testName;
    private String expectedResult;
    private boolean success;
    private Object[] params;
    private String result;

    public TestResult() {}

    public TestResult(String packageID, String testName,
                      String expectedResult, String result,
                      boolean success, Object[] params) {

        this.packageID = packageID;
        this.testName = testName;
        this.expectedResult = expectedResult;
        this.result = result;
        this.success = success;
        this.params = params;
    }

    public String getPackageID() {
        return packageID;
    }

    public String getTestName() {
        return testName;
    }

    public boolean isSuccessful() {
        return success;
    }

    public void setSuccessful(boolean successful) {
        success = successful;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
