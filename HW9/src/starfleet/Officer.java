package starfleet;

/**
 * Officer - A crew man with rank
 * 
 */
public class Officer extends Crewman {

	private OfficerRank rank;

	public Officer(String name, int age, int yearsInService, OfficerRank rank) {
		super(name, age, yearsInService);
		this.rank = rank;
	}

	public OfficerRank getRank() {
		return rank;
	}

	@Override
	public String toString() {
		
		
		StringBuilder str = new StringBuilder(this.getClass().getSimpleName());
		str.append(super.toString());
		str.append("\n\rrank=").append(getRank());
		
		return this.getClass().getSimpleName() + str.toString();
		}
}
