package starfleet;

/**
 * A regular crew man with name, age and yearsInService
 * 
 */
public class Crewman implements CrewMember {

	private String name;
	private int age;
	private int yearsInService;

	public Crewman(String name, int age, int yearsInService) {
		this.name = name;
		this.age = age;
		this.yearsInService = yearsInService;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public int getYearsInService() {
		return yearsInService;
	}

	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder();
		str.append("\n\rname=").append(getName());
		str.append("\n\rage=").append(getAge());
		str.append("\n\ryearsInService=").append(getYearsInService());
		return this.getClass().getSimpleName() + str.toString();
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
		Crewman other = (Crewman) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


}
