
public class CmdListMembers implements Command {

    @Override
    public void execute(String[] cmdLine) {
        Club club = Club.getInstance();
        club.listClubMembers();
    }

}
