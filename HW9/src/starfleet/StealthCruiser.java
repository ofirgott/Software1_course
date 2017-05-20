package starfleet;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class StealthCruiser extends Fighter {

	public static final int COST_PER_STEALTH_ENGINE_MAINTENANCE = 100;
	private static int instances_Counter = 0;

	public StealthCruiser(String name, int commissionYear, float maximalSpeed,
			Set<CrewMember> crewMembers, List<Weapon> weaponArray) {
		super(name, commissionYear, maximalSpeed, crewMembers, weaponArray);
		instances_Counter++;
	}

	public StealthCruiser(String name, int commissionYear, float maximalSpeed,
			Set<CrewMember> crewMembers) {
		this(name, commissionYear, maximalSpeed, crewMembers, Arrays
				.asList(new Weapon("Laser Cannons", 10, 100)));

	}

	public static int getInstances_Counter() {
		return instances_Counter;
	}

	@Override
	public int getAnnualMaintenanceCost() {
		int fighter_cost = super.getAnnualMaintenanceCost();
		return fighter_cost + COST_PER_STEALTH_ENGINE_MAINTENANCE
				* getInstances_Counter();
	}
}
