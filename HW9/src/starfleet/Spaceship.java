package starfleet;

import java.util.Set;

/**	
 * Interface for accessing basic fields of a spaceship
 */
public interface Spaceship {

	public String getName();

	public int getCommissionYear();

	public float getMaximalSpeed();
	
	public int getFirePower();
	
	public Set<CrewMember> getCrewMembers();
	
	public int getAnnualMaintenanceCost();
	
	
	

}
