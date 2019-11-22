import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class MsgTest {

    private String packageID;
    private String jsScript;
    private String funcName;
    private TestData[] tests;

    public MsgTest() {}

    public MsgTest(String packageID, String jsScript,
                   String funcName, TestData[] tests) {
        this.packageID = packageID;
        this.jsScript = jsScript;
        this.funcName = funcName;
        this.tests = tests;
    }

    public String getPackageID() {
        return packageID;
    }

    public String getJsScript() {
        return jsScript;
    }

    public String getFunctionName() {
        return funcName;
    }

    public TestData[] getTests() {
        return tests;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public void setJsScript(String jsScript) {
        this.jsScript = jsScript;
    }

    public void setFunctionName(String funcName) {
        this.funcName = funcName;
    }

    public void setTests(TestData[] tests) {
        this.tests = tests;
    }
}
