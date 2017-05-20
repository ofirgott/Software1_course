package starfleet;

public class Weapon {
	private String name;
	private int firePower;
	private int annualMaintenanceCost;
	
	public Weapon(String name, int firePower, int annualmainenanceCost){
		this.name = name;
		this.firePower = firePower;
		this.annualMaintenanceCost = annualmainenanceCost;
	}
	public String getName() {
		return name;
	}
	public int getFirePower() {
		return firePower;
	}
	public int getAnnualMaintenanceCost() {
		return annualMaintenanceCost;
	}
	
	public String toString() {
		
		String class_Name = this.getClass().getSimpleName();
		StringBuilder str = new StringBuilder();
		str.append(class_Name);
		str.append(" [name=").append(getName() + ", ");
		str.append("firePower=").append(getFirePower() + ", ");
		str.append("annualMaintenanceCost=").append(getAnnualMaintenanceCost() + "]");
		return str.toString();
		}
}
