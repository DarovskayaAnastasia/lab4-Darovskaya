import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class MsgTest {

    private String packageID;
    private String jsScript;
    private String funcName;
    private TestData[] tests;
}
