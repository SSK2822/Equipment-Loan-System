public class CmdListItems implements Command {
    @Override
    public void execute(String[] cmdLine) {
        Club club = Club.getInstance();
        club.listClubItems();
    }
}

// Comments
// Code taken from CmdListMembers.java file
