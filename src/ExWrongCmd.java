public class ExWrongCmd extends Exception {
    public ExWrongCmd() {
        super("Unknown command - ignored.");

    }

    public ExWrongCmd(String Msg) {
        super(Msg);
    }
}
