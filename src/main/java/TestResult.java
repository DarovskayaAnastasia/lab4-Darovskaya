public class TestResult {
    private String packageID;
    private String testName;
    private String expectedResult;
    private Object[] params;
    private String result;
    private boolean success;

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

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public boolean suc() {
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
