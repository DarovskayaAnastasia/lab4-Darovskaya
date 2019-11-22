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

    
}
