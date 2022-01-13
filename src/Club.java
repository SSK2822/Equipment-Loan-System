import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Club {
	private ArrayList<Member> allMembers;
	private ArrayList<Item> allItems;

	private static Club instance = new Club();

	private Club() {
		allMembers = new ArrayList<>();
		allItems = new ArrayList<>();
	}

	public static Club getInstance() {
		return instance;
	}

	public void addMember(Member m) throws ExMemberIDInUse {// See how it is called in Member constructor (Step 3)

		allMembers.add(m);
		Collections.sort(allMembers); // allMembers

	}

	public void listClubMembers() {
		System.out.println(Member.getListingHeader()); // Member

		for (Member m : allMembers)
			System.out.println(m); // m or m.toString()
	}

	public void removeMember(Member m) {

		allMembers.remove(m);
		Collections.sort(allMembers);
	}

	public void addItems(Item i) throws ExItemInUse {

		allItems.add(i);
		Collections.sort(allItems);

	}

	public void listClubItems() {
		System.out.println(Item.getListingHeader());

		for (Item i : allItems) {
			System.out.print(i.toString());
			if (!i.getBQueue().isEmpty()) {
				System.out.printf(" + %d request(s): ", i.getBQueue().size());

				ArrayDeque<Member> temp = i.getBQueue();
				Iterator<Member> itr = temp.iterator();

				while (itr.hasNext()) {
					System.out.print(itr.next().getId() + " ");
				}

			}
			System.out.println("");
		}

	}

	public void removeItems(Item i) {
		allItems.remove(i);
		Collections.sort(allItems);
	}

	public Member getMember(String id) throws ExMemberNotFound {
		for (Member m : allMembers) {
			String tempId = m.getId();
			if (tempId.equals(id))
				return m;
			else
				throw new ExMemberNotFound();

		}
		return null;

	}

	public Item getItem(String id) throws ExItemNotFound {
		for (Item it : allItems) {
			String tempId = it.getId();
			if (tempId.equals(id))
				return it;
			else
				throw new ExItemNotFound();

		}
		return null;
	}

	public ArrayList<Member> getAllMembers() {
		return allMembers;
	}

	public ArrayList<Item> getAllItems() {
		return allItems;
	}

}
