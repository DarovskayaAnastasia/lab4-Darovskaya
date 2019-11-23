import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class OneTest {

    private String packageID;
    private String jsScript;
    private String funcName;
    private TestData test;

    public OneTest() {}

    public OneTest(@JsonProperty("packageID") String packageID,
                   @JsonProperty("jsScript") String jsScript,
                   @JsonProperty("funcName") String funcName,
                   @JsonProperty("tests") TestData test) {
        this.packageID = packageID;
        this.jsScript = jsScript;
        this.funcName = funcName;
        this.test = test;
        System.out.println("id = " + packageID + "\n" +  "script = " + jsScript + "\n" +  "function name = " + funcName);
    }

    public String getPackageID() {
        return packageID;
    }

    public String getJsScript() {
        return jsScript;
    }

    public String getFuncName() {
        return funcName;
    }

    public TestData getTest() {
        return test;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public void setJsScript(String jsScript) {
        this.jsScript = jsScript;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public void setTest(TestData test) {
        this.test = test;
    }
}
