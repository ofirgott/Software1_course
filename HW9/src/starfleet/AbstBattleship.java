package starfleet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AbstBattleship extends AbstSpaceShip {

	private List<Weapon> weaponArray;

	public AbstBattleship(String name, int commissionYear, float maximalSpeed,
			Set<CrewMember> crewMembers, List<Weapon> weaponArray) {
		super(name, commissionYear, maximalSpeed, crewMembers);
		this.weaponArray = new ArrayList<Weapon>();
		this.weaponArray.addAll(weaponArray);

	}

	@Override
	public int getFirePower() {
		return 10 + getFirePowerOfAllWeapons();

	}

	public List<Weapon> getWeaponArray() {
		return this.weaponArray;
	}

	protected int getFirePowerOfAllWeapons() {
		int sum = 0;
		for (Weapon weapon : weaponArray)
			sum += weapon.getFirePower();
		return sum;
	}

	protected int getTotalCostWeapons() {
		int sum = 0;
		for (Weapon weapon : this.getWeaponArray()) {
			sum += weapon.getAnnualMaintenanceCost();
		}
		return sum;
	}

	@Override
	public String toString() {

		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("\n\tWeaponArray=[");
		for (Weapon weapon : this.getWeaponArray())
			str.append(weapon.toString() + ", ");
		str.replace(str.length() - 2, str.length(), "]");
		return str.toString();
	}

}
