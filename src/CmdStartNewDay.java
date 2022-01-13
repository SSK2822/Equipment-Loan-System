import java.util.ArrayList;

public class CmdStartNewDay extends RecordedCommand {

    private String oldDaly;
    private String newDay;

    @Override
    public void execute(String[] cmdLine) {
        // TODO Auto-generated method stub
        
        SystemDate sd = SystemDate.getInstance();
        oldDaly = sd.toString();
        newDay = cmdLine[1];
        sd.set(newDay);

        ArrayList<Item> allItems = Club.getInstance().getAllItems();
        for (Item it : allItems) {
            if (it.getStatus() instanceof ItemStatusOnHold) {
                Day sDate = ((ItemStatusOnHold) it.getStatus()).getDueDate();
                if (sDate.lessThan(sd)) {
                    System.out.printf("On hold period is over for %s %s.%n", it.getId(), it.getName());
                    it.returning();
                }
            }
        }

        clearRedoList();
        addUndoCommand(this);
        System.out.println("Done.");

    }

    @Override
    public void undoMe() {
        // TODO Auto-generated method stub
        SystemDate.getInstance().set(oldDaly);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        // TODO Auto-generated method stub
        SystemDate.getInstance().set(newDay);
        addUndoCommand(this);
    }

}
