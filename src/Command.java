public interface Command {
    void execute(String[] cmdLine) throws ExControl;
}