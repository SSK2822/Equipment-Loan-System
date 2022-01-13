
public class CmdItemRequested extends RecordedCommand {

    private Member mem;
    private Item it;
    private int pos;

    @Override
    public void execute(String[] cmdLine) {
        // TODO Auto-generated method stub
        Club club = Club.getInstance();

        try {
            if(cmdLine.length != 3)
                throw new ExInsufficientCommand();
            mem = Member.findDup(club.getAllMembers(), cmdLine[1]);
            it = Item.findDup(club.getAllItems(), cmdLine[2]);
            pos = it.requested(mem, it);
            System.out.printf("Done. This request is no. %d in the queue.%n", pos);
            addUndoCommand(this);
            clearRedoList();
            // System.out.println("Done."); - Testing purpose
        } catch (ExItemAvailable eA) {
            System.out.println(eA.getMessage());
        } catch (ExMemberNotFound eM) {
            System.out.println(eM.getMessage());
        } catch (ExItemNotFound eF) {
            System.out.println(eF.getMessage());
        } catch (ExItemAlreadyBorrow eAB) {
            System.out.println(eAB.getMessage());
        } catch (ExItemAlreadyRequested eAR) {
            System.out.println(eAR.getMessage());
        } catch (ExItemRequestExceed eIE) {
            System.out.println(eIE.getMessage());
        } catch (ExInsufficientCommand e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        // TODO Auto-generated method stub

        it.getBQueue().removeLast();
        mem.retReq();
        addRedoCommand(this);

    }

    @Override
    public void redoMe() {
        // TODO Auto-generated method stub
        try {
            it.requested(this.mem, it);
        } catch (ExItemAvailable eA) {
            System.out.println(eA.getMessage());
        } catch (ExMemberNotFound eM) {
            System.out.println(eM.getMessage());
        } catch (ExItemNotFound eF) {
            System.out.println(eF.getMessage());
        } catch (ExItemAlreadyBorrow eAB) {
            System.out.println(eAB.getMessage());
        } catch (ExItemAlreadyRequested eAR) {
            System.out.println(eAR.getMessage());
        } catch (ExItemRequestExceed eIE) {
            System.out.println(eIE.getMessage());
        } 

        addUndoCommand(this);
    }

}
