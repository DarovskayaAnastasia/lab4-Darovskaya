package MessagesPackage;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class MsgResponse {

    private String packageID;
    private TestResult[] results;
    private boolean success;

    public MsgResponse() {}

    public MsgResponse(String packageID, TestResult[] results) {
        this.packageID = packageID;
        this.results = results;
        this.success = true;

        for (TestResult result : results) {
            if (!result.isSuccessful()) {
                this.success = false;
                break;
            }
        }
    }

    public String getPackageID() {
        return packageID;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public boolean isSuccessful() {
        return success;
    }

    public void setSuccessful(boolean successful) {
        success = successful;
    }

    public TestResult[] getResults() {
        return results;
    }

    public void setResults(TestResult[] results) {
        this.results = results;
    }
}
