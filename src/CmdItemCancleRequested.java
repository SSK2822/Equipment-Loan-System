import java.util.ArrayDeque;

public class CmdItemCancleRequested extends RecordedCommand {

    private Member mem;
    private Item it;

    private ArrayDeque<Member> bQueue;

    @Override
    public void execute(String[] cmdLine) {
        // TODO Auto-generated method stub

        Club club = Club.getInstance();

        try {
            if(cmdLine.length != 3)
                throw new ExInsufficientCommand();
            mem = Member.findDup(club.getAllMembers(), cmdLine[1]);
            it = Item.findDup(club.getAllItems(), cmdLine[2]);
            this.bQueue = it.getBQueue().clone(); // Used to help for the undo command
            it.cancReq(mem, it);
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch (ExMemberNotFound eM) {
            System.out.println(eM.getMessage());
        } catch (ExItemNotFound eF) {
            System.out.println(eF.getMessage());
        } catch (ExRequestNotFound eRN) {
            System.out.println(eRN.getMessage());
        } catch (ExInsufficientCommand e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        // TODO Auto-generated method stub
        mem.addReq();
        it.setBQueue(bQueue);
        
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        // TODO Auto-generated method stub
        try {
            it.cancReq(mem, it);
        } catch (ExMemberNotFound eM) {
            System.out.println(eM.getMessage());
        } catch (ExItemNotFound eF) {
            System.out.println(eF.getMessage());
        } catch (ExRequestNotFound eRN) {
            System.out.println(eRN.getMessage());
        } 
        addUndoCommand(this);
    }

}
