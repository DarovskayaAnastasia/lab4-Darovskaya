import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class MsgTest {

    private String packageID;
    private String jsScript;
    private String funcName;
    private TestData[] tests;

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

    public String getFunctionName() {
        return funcName;
    }

    
}
