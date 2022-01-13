import java.util.ArrayDeque;

public class CmdCheckIn extends RecordedCommand {
    private Member mem;
    private Item it;
    private ItemStatus iStatus;
    private ArrayDeque<Member> bQueue;
    private Day due;

    @Override
    public void execute(String[] cmdLine) {
        // TODO Auto-generated method stub
        Club club = Club.getInstance();

        try {
            if (cmdLine.length != 3)
                throw new ExInsufficientCommand();
            mem = Member.findDup(club.getAllMembers(), cmdLine[1]);
            it = Item.findDup(club.getAllItems(), cmdLine[2]);


            due = SystemDate.getInstance().clone();
            due.addDay(3);
            iStatus = it.getStatus();
            bQueue = it.getBQueue().clone();

            it.checkIn(mem, it);
            addUndoCommand(this);
            clearRedoList();

            System.out.println("Done.");
        } catch (ExMemberNotFound eM) {
            System.out.println(eM.getMessage());
        } catch (ExItemNotFound eF) {
            System.out.println(eF.getMessage());
        } catch (ExItemNotBorrowed eB) {
            System.out.println(eB.getMessage());
        } catch (ExInsufficientCommand e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void undoMe() {
        // TODO Auto-generated method stub
        this.mem.addBorrowed();
        ItemStatus itemp = it.getStatus();
        if (itemp instanceof ItemStatusOnHold) {
            it.setBQueue(bQueue);
            Member mtemp = it.getBQueue().peek();
            mtemp.addReq();

            System.out.printf("Sorry. %s %s please ignore the pick up notice for %s %s.%n", mtemp.getId(),
                    mtemp.getName(), it.getId(), it.getName());

        }
        it.setStatus(iStatus);
        addRedoCommand(this);

    }

    @Override
    public void redoMe() {
        // TODO Auto-generated method stub
        try {
            it.checkIn(this.mem, it);
        } catch (ExItemNotBorrowed eB) {
            System.out.println(eB.getMessage());
        } catch (ExMemberNotFound eM) {
            System.out.println(eM.getMessage());
        } catch (ExItemNotFound eF) {
            System.out.println(eF.getMessage());
        }

        addUndoCommand(this);
    }

}

// Comments:
// Codes taken by CmdCheckOut.java file