public class ItemStatusBorrowed implements ItemStatus {

    private Member BrwMem;
    private Day lDate;

    private static ItemStatus inst = new ItemStatusAvailable();

    public static ItemStatus getInstance() {
        return inst;
    }

    public ItemStatusBorrowed(Day lDate, Member mem) {
        this.BrwMem = mem;
        this.lDate = lDate;
    }

    public Member getBorrowingMember() {
        return BrwMem;
    }

    @Override
    public String toString() {
        return String.format("Borrowed by %s %s on %s", BrwMem.getId(), BrwMem.getName(), lDate.toString());

    }

}
