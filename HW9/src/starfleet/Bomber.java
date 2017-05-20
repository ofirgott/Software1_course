package starfleet;

import java.util.List;
import java.util.Set;

public class Bomber extends AbstBattleship {

	private int numberOfTechnicians;
	public static final int ANNUAL_COST_FOR_BOMBER = 5000;

	public Bomber(String name, int commissionYear, float maximalSpeed,
			Set<CrewMember> crewMembers, List<Weapon> weaponArray,
			int numberOfTechnicians) {
		super(name, commissionYear, maximalSpeed, crewMembers, weaponArray);
		this.numberOfTechnicians = numberOfTechnicians;
	}

	public int getNumberOfTechnicians() {
		return numberOfTechnicians;
	}

	@Override
	public int getAnnualMaintenanceCost() {

		int technicians_Discount = 100 - (this.getNumberOfTechnicians() * 10);
		int weapons_sum = Math.round(this.getTotalCostWeapons()
				* technicians_Discount / 100); // weapons discount

		return ANNUAL_COST_FOR_BOMBER + weapons_sum;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("\n\tNumberOfTechnicians=").append(getNumberOfTechnicians());
		return str.toString();
	}
}
