public class ItemStatusAvailable implements ItemStatus {
    private String iStatus = "Available";
    private static ItemStatus inst = new ItemStatusAvailable();

    @Override
    public String toString() {
        return iStatus;
    }

    public static ItemStatus getInstance() {
        return inst;
    }
}
