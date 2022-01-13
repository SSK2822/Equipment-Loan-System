public class ItemStatusOnHold implements ItemStatus {

    private Member mem;
    private Day due;

    private static ItemStatus inst = new ItemStatusAvailable();

    public static ItemStatus getInstance() {
        return inst;
    }

    public ItemStatusOnHold(Member mem) {

        this.mem = mem;
        this.due = SystemDate.getInstance().clone();
        due.addDay(3);
    }

    public Day getDueDate() {
        return due;
    }

    public Member getMember() {
        return mem;
    }

    @Override
    public String toString() {
        return String.format("On holdshelf for %s %s until %s", this.mem.getId(), this.mem.getName(), due.toString());
    }

}
