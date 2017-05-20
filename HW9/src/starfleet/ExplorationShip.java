package starfleet;

import java.util.Set;

public class ExplorationShip extends AbstSpaceShip {
	private int numberOfResearchLabs;
	public static final int ANNUAL_COST_FOR_EXPLORAION_SHIP = 4000;
	public static final int ANNUAL_COST_FOR_LAB = 2500;

	public ExplorationShip(String name, int commissionYear, float maximalSpeed,
			Set<CrewMember> crewMembers, int numberOfResearchLabs) {
		super(name, commissionYear, maximalSpeed, crewMembers);
		this.numberOfResearchLabs = numberOfResearchLabs;
	}

	@Override
	public int getAnnualMaintenanceCost() {
		return ANNUAL_COST_FOR_EXPLORAION_SHIP
				+ (ANNUAL_COST_FOR_LAB * this.numberOfResearchLabs);

	}

	public int getNumberOfResearchLabs() {
		return numberOfResearchLabs;
	}

	@Override
	public String toString() {

		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("\n\tNumberOfResearchLabs=").append(
				getNumberOfResearchLabs());
		return str.toString();
	}

}