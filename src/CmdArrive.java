
public class CmdArrive extends RecordedCommand {
    Club club;
    private Item it;

    @Override
    public void execute(String[] cmdLine) {
        // TODO Auto-generated method stub
        club = Club.getInstance();

        try {
            if (Item.isItemExist(club.getAllItems(), cmdLine[1])) {
                Item dup = Item.findDup(club.getAllItems(), cmdLine[1]);
                String tempS = String.format("Item ID already in use: %s %s %n", dup.getId(), dup.getName());
                throw new ExItemInUse(tempS);
            }

            it = new Item(cmdLine[1], cmdLine[2], ItemStatusAvailable.getInstance());
            club.addItems(it);
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch (ExItemInUse eID) {
            System.out.println(eID.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Insufficient command arguments.");
        } catch (ExItemNotFound e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void undoMe() {
        // TODO Auto-generated method stub
        club.removeItems(it);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        // TODO Auto-generated method stub
        try {
            club.addItems(it);
            addUndoCommand(this);
        } catch (ExItemInUse eID) {
            System.out.println(eID.getMessage());
        }
    }

}
