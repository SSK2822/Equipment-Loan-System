
public class CmdCheckOut extends RecordedCommand {
    Club club;
    private Item it;
    private Member brw;
    private ItemStatus iStat;

    @Override
    public void execute(String[] cmdLine) {
        // TODO Auto-generated method stub
        try {
            if(cmdLine.length != 3)
                throw new ExInsufficientCommand();
            club = Club.getInstance();

            brw = Member.findDup(club.getAllMembers(), cmdLine[1]);
            it = Item.findDup(club.getAllItems(), cmdLine[2]);
      
            iStat = it.getStatus();
            it.checkOut(brw, it);
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch (ExMemberNotFound eM) {
            System.out.println(eM.getMessage());
        } catch (ExItemNotFound eI) {
            System.out.println(eI.getMessage());
        } catch (ExItemNotAvailable eI) {
            System.out.println(eI.getMessage());
        } catch (ExLoanQuotaExceed qE) {
            System.out.println(qE.getMessage());
        } catch (ExInsufficientCommand e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void undoMe() {
        // TODO Auto-generated method stub
        // club.removeItems(it); - Used earlier for removing items
        addRedoCommand(this);
        it.setStatus(iStat);
        brw.returnBorrowed();

    }

    @Override
    public void redoMe() {
        // TODO Auto-generated method stub
        try {
            // club.addItems(it); - Used Earlier for adding values
            it.checkOut(brw, it);
            addUndoCommand(this);
        } catch (ExItemNotFound eI) {
            System.out.println(eI.getMessage());
        } catch (ExItemNotAvailable eI) {
            System.out.println(eI.getMessage());
        } catch (ExLoanQuotaExceed qE) {
            System.out.println(qE.getMessage());
        } catch (ExMemberNotFound eM) {
            System.out.println(eM.getMessage());
        }

    }

}
