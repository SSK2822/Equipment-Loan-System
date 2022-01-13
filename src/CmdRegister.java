
public class CmdRegister extends RecordedCommand {
    Club club;
    private Member mem;

    @Override
    public void execute(String[] cmdLine) {
        club = Club.getInstance();

        try {
            if(cmdLine.length != 3)
                throw new ExInsufficientCommand();

            if (Member.isMemberExist(club.getAllMembers(), cmdLine[1])) {
                Member dup = Member.findDup(club.getAllMembers(), cmdLine[1]);

                String tempS = String.format("Member ID already in use: %s %s", dup.getId(), dup.getName());
                throw new ExMemberIDInUse(tempS);
            }
            mem = new Member(cmdLine[1], cmdLine[2]);
            club.addMember(mem);
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch (ExMemberIDInUse eMID) {
            System.out.println(eMID.getMessage());
        } catch (ExInsufficientCommand e) {
            System.out.println(e.getMessage());
        } catch (ExMemberNotFound e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void undoMe() {
        club.removeMember(mem);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        try {
            club.addMember(mem);
            addUndoCommand(this);
        } catch (ExMemberIDInUse eMID) {
            System.out.println(eMID.getMessage());
        } 

    }
}
