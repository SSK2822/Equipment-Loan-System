public class ExItemInUse extends Exception {
    public ExItemInUse() {
        super("Item ID already in use: ");
    }

    public ExItemInUse(String Msg) {
        super(Msg);
    }
}
