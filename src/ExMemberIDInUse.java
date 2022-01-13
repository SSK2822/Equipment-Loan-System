
public class ExMemberIDInUse extends Exception {
    public ExMemberIDInUse() {
        super("Member ID already in use: ");
    }

    public ExMemberIDInUse(String Msg) {
        super(Msg);
    }
}
