package starfleet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StarfleetManager {

	/**
	 * Returns a list containing string representation of all fleet ships,
	 * sorted in descending order by fire power, and then in ascending order by
	 * commission year
	 */
	public static List<String> getShipDescriptionsSortedByFirePowerAndCommissionYear(
			List<Spaceship> fleet) {

		Collections.sort(fleet, new Comparator<Spaceship>() {
			public int compare(Spaceship s1, Spaceship s2) {
				if (s2.getFirePower() - s1.getFirePower() == 0)
					return s1.getCommissionYear() - s2.getCommissionYear();
				else return (s2.getFirePower() - s1.getFirePower());
			}
		});

		List<String> output = new ArrayList<String>();
		for (Spaceship spaceship : fleet)
			output.add(spaceship.toString());

		return output;
	}

	/**
	 * Returns a map containing ship type names as keys (the class name) and the
	 * number of instances created for each type as values
	 */
	public static Map<String, Integer> getInstanceNumberPerClass(
			List<Spaceship> fleet) {
		Map<String, Integer> output = new HashMap<String, Integer>();
		Integer tmp_cnt;
		String tmp_class_name;
		for (Spaceship spaceship : fleet) {
			tmp_class_name = spaceship.getClass().getSimpleName();
			if (output.containsKey(tmp_class_name)) {
				tmp_cnt = output.get(tmp_class_name);
				output.put(tmp_class_name, tmp_cnt+1);
			} else
				// map does not contain specific class name
				output.put(tmp_class_name, 1);
		}

		return output;

	}

	/**
	 * Returns the total annual maintenance cost of the fleet (which is the sum
	 * of maintenance costs of all the fleet's ships)
	 */
	public static int getTotalMaintenanceCost(List<Spaceship> fleet) {
		int sum = 0;
		for (Spaceship spaceship : fleet)
			sum += spaceship.getAnnualMaintenanceCost();
		return sum;
	}

	/**
	 * Returns the total fire power of the fleet (which is the sum of fire power
	 * of all the fleet's ships)
	 */
	public static int getTotalFleetFirePower(List<Spaceship> fleet) {
		int sum = 0;
		for (Spaceship spaceship : fleet)
			sum += spaceship.getFirePower();
		return sum;
	}

	/**
	 * Returns a set containing the names of all the fleet's weapons installed
	 * on any ship, without repetitions
	 */
	public static Set<String> getFleetWeaponNames(List<Spaceship> fleet) {
		Set<String> weaponsName = new HashSet<String>();
		List<Weapon> tmp_weaponArray;
		for (Spaceship spaceship : fleet) {
			if (spaceship instanceof AbstBattleship) {
				tmp_weaponArray = ((AbstBattleship) spaceship).getWeaponArray();
				for (Weapon weapon : tmp_weaponArray)
					weaponsName.add(weapon.getName());
			}
		}
		return weaponsName;
	}

	/*
	 * Returns the total number of crew-members serving on board of the given
	 * fleet's ships.
	 */
	public static int getTotalNumberOfFleetCrewMembers(List<Spaceship> fleet) {
		int sum = 0;
		for (Spaceship spaceship : fleet)
			sum += spaceship.getCrewMembers().size();
		return sum;
	}

	/*
	 * Returns the average age of all officers serving on board of the given
	 * fleet's ships.
	 */
	public static float getAverageAgeOfFleetOfficers(List<Spaceship> fleet) {
		int officers_cnt = 0;
		int ages_sum = 0;
		for (Spaceship spaceship : fleet) {
			for (CrewMember crewmember : spaceship.getCrewMembers()) {
				if (crewmember instanceof Officer) {
					officers_cnt++;
					ages_sum += crewmember.getAge();
				}
			}
		}
		return (float) ages_sum / officers_cnt;
	}

	/*
	 * Returns a map mapping the highest ranking officer on each ship (as keys),
	 * to his ship (as values).
	 */
	public static Map<Officer, Spaceship> getHighestRankingOfficerPerShip(
			List<Spaceship> fleet) {
		Map<Officer, Spaceship> map = new HashMap<Officer, Spaceship>();
		Officer officer; // temp Officer
		Officer highest_Rank_Officer = null;
		OfficerRank curr_Max_Rank = OfficerRank.Ensign; // lowest rank
		for (Spaceship spaceship : fleet) {
			for (CrewMember crewmember : spaceship.getCrewMembers())
				if (crewmember instanceof Officer) {
					officer = (Officer) crewmember;
					if (officer.getRank().compareTo(curr_Max_Rank) >= 0) {
						curr_Max_Rank = officer.getRank();
						highest_Rank_Officer = officer;
					}
				}
			if (highest_Rank_Officer != null)
				map.put(highest_Rank_Officer, spaceship);
		}
		return map;
	}

	/*
	 * Returns a List of entries representing ranks and their occurrences. Each
	 * entry represents a pair composed of an officer rank, and the number of
	 * its occurrences among starfleet personnel. The returned list is sorted
	 * descendingly based on the number of occurrences.
	 */
	public static List<Map.Entry<OfficerRank, Integer>> getOfficerRanksSortedByPopularity(
			List<Spaceship> fleet) {
		Map<OfficerRank, Integer> rank_Popularity_map = new HashMap<OfficerRank, Integer>();
		OfficerRank tmp_Rank;
		Integer tmp_cnt;
		List<Map.Entry<OfficerRank, Integer>> output = new ArrayList<Map.Entry<OfficerRank, Integer>>();

		for (Spaceship spaceship : fleet) {
			for (CrewMember member : spaceship.getCrewMembers()) {
				if (member instanceof Officer) {
					tmp_Rank = ((Officer) member).getRank();
					if (rank_Popularity_map.containsKey(tmp_Rank)) {
						tmp_cnt = rank_Popularity_map.get(tmp_Rank);
						rank_Popularity_map.put(tmp_Rank, tmp_cnt+1);
					} else
						rank_Popularity_map.put(tmp_Rank, 1);
				}
			}
		}
		output.addAll(rank_Popularity_map.entrySet());

		Collections.sort(output,
				new Comparator<Map.Entry<OfficerRank, Integer>>() {
					public int compare(Map.Entry<OfficerRank, Integer> e1,
							Map.Entry<OfficerRank, Integer> e2) {// Map entries
																	// comparator-
																	// Descending
																	// order
						return e1.getKey().compareTo(e2.getKey());
					}
				});

		return output;

	}

}
