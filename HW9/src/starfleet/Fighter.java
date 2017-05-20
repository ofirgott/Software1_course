package starfleet;

import java.util.List;
import java.util.Set;

public class Fighter extends AbstBattleship {

	public static final int ANNUAL_COST_FOR_FIGHTER = 2500;
	public static final int MAINTAIN_COST_FOR_ENGINE = 1000;

	public Fighter(String name, int commissionYear, float maximalSpeed,
			Set<CrewMember> crewMembers, List<Weapon> weaponArray) {
		super(name, commissionYear, maximalSpeed, crewMembers, weaponArray);
	}

	@Override
	public int getAnnualMaintenanceCost() {
		return ANNUAL_COST_FOR_FIGHTER + this.getTotalCostWeapons()
				+ Math.round(MAINTAIN_COST_FOR_ENGINE * getMaximalSpeed());
	}

}
