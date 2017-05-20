package starfleet;

import java.util.Set;

public class TransportShip extends AbstSpaceShip {

	private int cargoCapacity;
	private int passengerCapacity;
	public static final int BASIC_ANNUAL_COST_FOR_TRANSPORT_SHIP = 3000;
	public static final int COST_PER_MEGATON_CARGO_CAPACITY = 5;
	public static final int COST_PER_PASSENGER_CAPACITY = 3;

	public TransportShip(String name, int commissionYear, float maximalSpeed,
			Set<CrewMember> crewMembers, int cargoCapacity,
			int passengerCapacity) {
		super(name, commissionYear, maximalSpeed, crewMembers);
		this.cargoCapacity = cargoCapacity;
		this.passengerCapacity = passengerCapacity;
	}

	@Override
	public int getAnnualMaintenanceCost() {
		return BASIC_ANNUAL_COST_FOR_TRANSPORT_SHIP
				+ COST_PER_MEGATON_CARGO_CAPACITY * this.getCargoCapacity()
				+ COST_PER_PASSENGER_CAPACITY * this.getPassengerCapacity();
	}

	public int getCargoCapacity() {
		return cargoCapacity;

	}

	public int getPassengerCapacity() {
		return passengerCapacity;
	}

	@Override
	public String toString() {

		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("\n\tCargoCapacity=").append(getCargoCapacity());
		str.append("\n\tPassengerCapacity=").append(getPassengerCapacity());
		return str.toString();
	}

}
