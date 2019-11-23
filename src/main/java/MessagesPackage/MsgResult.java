package MessagesPackage;

public class MsgResult {

    private String packageID;

    public MsgResult() {}

    MsgResult(String packageID) {
        this.packageID = packageID;
    }

    String getPackageID() {
        return packageID;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }
}
