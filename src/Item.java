import java.util.ArrayDeque;
import java.util.ArrayList;

public class Item implements Comparable<Item> {
    private String id;
    private String name;
    private Day aDate;
    private ItemStatus iStatus;
    private ArrayDeque<Member> bQueue;
    private String dte;

    // Constructors
    public Item(String id, String name, ItemStatus iStatus) {
        this.id = id;
        this.name = name;
        this.aDate = SystemDate.getInstance().clone();
        this.iStatus = iStatus;
        this.bQueue = new ArrayDeque<>();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Day getaDate() {
        return aDate;
    }

    public ItemStatus getStatus() {
        return iStatus;
    }

    public ArrayDeque<Member> getBQueue() {
        return bQueue;
    }

    // Getting Headers
    public static String getListingHeader() {
        return String.format("%-5s%-17s%11s   %s", "ID", "Name", "  Arrival  ", "Status");
    }

    // Comparable
    @Override
    public int compareTo(Item anth) {
        return this.id.compareTo(anth.id);
    }

    @Override
    public String toString() {
        return String.format("%-5s%-17s%11s   %s", id, name, aDate, iStatus.toString());
    }

    public static Item findDup(ArrayList<Item> allItems, String id) throws ExItemNotFound {
        Item result = null;
        for (Item it : allItems) {
            if (it.getId().equals(id))
                result = it;
        }
        if (result== null)
            throw new ExItemNotFound();
        return result;
    }

    // Setters
    public void setStatus(ItemStatus stat) {
        this.iStatus = stat;
    }

    public void setBQueue(ArrayDeque<Member> newBQueue) {
        bQueue = newBQueue.clone();
    }

    // methods
    public void returning() {
        if (bQueue.isEmpty()) {
            this.setStatus(new ItemStatusAvailable());
        } else {

            Member oMem = bQueue.pollFirst();
            this.setStatus(new ItemStatusOnHold(oMem));
            oMem.retReq();
            dte = ((ItemStatusOnHold) iStatus).getDueDate().toString();
            System.out.printf("Item [%s %s] is ready for pick up by [%s %s].  On hold due on %s.%n", id, name,
                    oMem.getId(), oMem.getName(), dte);

        }
    }

    public void createQueue(Member memTemp) {
        bQueue.addFirst(memTemp);
    }

    public int requestNo(Member mem) {
        bQueue.addLast(mem);
        return bQueue.size();
    }

    public void checkIn(Member mem, Item itm) throws ExItemNotBorrowed, ExMemberNotFound, ExItemNotFound {

        

        if (itm.getStatus() instanceof ItemStatusBorrowed) {

            if (((ItemStatusBorrowed) iStatus).getBorrowingMember().equals(mem)) {
                mem.returnBorrowed();
                itm.returning();
            } else {
                throw new ExItemNotBorrowed();
            }
        }
    }

    public void checkOut(Member mem, Item itm) throws ExLoanQuotaExceed, ExItemNotAvailable, ExMemberNotFound, ExItemNotFound {

      

        if (itm.getStatus() instanceof ItemStatusAvailable) {
            if (mem.getBorrowed() < 6) {
                mem.addBorrowed();
                itm.setStatus(new ItemStatusBorrowed(SystemDate.getInstance().clone(), mem));
            } else
                throw new ExLoanQuotaExceed();
        } else if (itm.getStatus() instanceof ItemStatusOnHold) {
            if (((ItemStatusOnHold) iStatus).getMember().equals(mem)) {
                if (mem.getBorrowed() < 6) {
                    itm.setStatus(new ItemStatusBorrowed(SystemDate.getInstance().clone(), mem));
                    mem.addBorrowed();
                } else
                    throw new ExLoanQuotaExceed();
            } else
                throw new ExItemNotAvailable();
        } else
            throw new ExItemNotAvailable();
    }

    public void cancReq(Member mem, Item itm) throws ExRequestNotFound, ExMemberNotFound, ExItemNotFound {

       
        if (bQueue.contains(mem)) {
            bQueue.remove(mem);
            mem.retReq();
        } else {
            throw new ExRequestNotFound();
        }

    }

    public int requested(Member mem, Item itm)
            throws ExItemAvailable, ExItemRequestExceed, ExItemAlreadyRequested, ExItemAlreadyBorrow, ExMemberNotFound, ExItemNotFound {

        if (itm.getStatus() instanceof ItemStatusAvailable) {
            throw new ExItemAvailable();

        }
        if (itm.getStatus() instanceof ItemStatusOnHold) {
            Member mTemp = ((ItemStatusOnHold) (iStatus)).getMember();
            if (mTemp.equals(mem))
                throw new ExItemAvailable();
        }
        if (itm.getBQueue().contains(mem)) {
            throw new ExItemAlreadyRequested();
        }
        if (itm.getStatus() instanceof ItemStatusBorrowed) {
            Member mTemp = ((ItemStatusBorrowed) (itm.getStatus())).getBorrowingMember();
            if (mTemp.equals(mem)) {
                throw new ExItemAlreadyBorrow();
            }
        }
        if (mem.getReq() >= 3) {
            throw new ExItemRequestExceed();
        }

        mem.addReq();
        return itm.requestNo(mem);

    }

    public static boolean isItemExist(ArrayList<Item> allItems, String id) {

        for(Item item : allItems){
            if(item.getId().equals(id))
                return true;
        }
        return false;
    }
}

// Comments:
// Class code mostly taken from Member.java file and some self getter
// implementations
// Using deque to help with a double ended array to remove and add elements from
// both ends making it easy to work with queue collection
