import java.util.ArrayList;

public class Member implements Comparable<Member> {
    private String id;
    private String name;
    private Day joinDate;
    private int brw;
    private int req;

    public Member(String id, String name) {

        this.id = id;
        this.name = name;
        this.brw = 0;
        this.req = 0;
        this.joinDate = SystemDate.getInstance().clone();

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        // Learn: "-" means left-aligned
        return String.format("%-5s%-9s%11s%7d%13d", id, name, joinDate, brw, req);
    }

    public static String getListingHeader() {
        return String.format("%-5s%-9s%11s%11s%13s", "ID", "Name", "Join Date ", "#Borrowed", "#Requested");
    }

    @Override
    public int compareTo(Member anth) {
        return this.id.compareTo(anth.id);
    }

    public static Member findDup(ArrayList<Member> allMembers, String id) throws ExMemberNotFound {
        Member result=null;
        for (Member mem : allMembers) {
            if (mem.getId().equals(id))
                result = mem;
        }
        if (result == null)
            throw new ExMemberNotFound();
        return result;
    }

    public void addBorrowed() {
        brw++;
    }

    public int getBorrowed() {
        return brw;
    }

    public void returnBorrowed() {
        brw--;
    }

    public void retReq() {
        req--;
    }

    public void addReq() {
        req++;
    }

    public int getReq() {
        return req;
    }

    public static boolean isMemberExist(ArrayList<Member> allMembers, String id) {
        for(Member member : allMembers){
            if (member.getId().equals(id))
                return true;
        }
        return false;
    }
}