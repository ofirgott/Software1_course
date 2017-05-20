package starfleet;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an abstract spaceship implementing the Spaceship interface
 * 
 */
public abstract class AbstSpaceShip implements Spaceship {
	private String name;
	private int commissionYear;
	private float maximalSpeed;
	private int firePower;
	private Set<CrewMember> crewMembers;
	public final static int BASIC_FIRE_POWER = 10; // for non-Battleship

	public AbstSpaceShip(String name, int commissionYear, float maximalSpeed,
			Set<CrewMember> crewMembers) {
		this.name = name;
		this.commissionYear = commissionYear;
		this.maximalSpeed = maximalSpeed;
		this.firePower = BASIC_FIRE_POWER;
		this.crewMembers = new HashSet<CrewMember>();
		this.crewMembers.addAll(crewMembers);
	}

	public String getName() {
		return name;
	}

	public int getCommissionYear() {
		return commissionYear;
	}

	public float getMaximalSpeed() {
		return maximalSpeed;
	}

	public int getFirePower() {
		return firePower;
	}

	public Set<CrewMember> getCrewMembers() {
		return crewMembers;
	}

	public abstract int getAnnualMaintenanceCost();

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(this.getClass().getSimpleName());
		str.append("\n\tName=").append(getName());
		str.append("\n\tCommissionYear=").append(getCommissionYear());
		str.append("\n\tMaximalSpeed=").append(getMaximalSpeed());
		str.append("\n\tFirePower=").append(getFirePower());
		str.append("\n\tCrewMembers=").append(getCrewMembers().size());
		str.append("\n\tAnnualMaintenanceCost=").append(getAnnualMaintenanceCost());
		return str.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstSpaceShip other = (AbstSpaceShip) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
}
