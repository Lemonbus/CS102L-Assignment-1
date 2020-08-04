package TennisDatabase;

public class TennisPlayer implements TennisPlayerInterface {
	
	private String id;
	private String firstName;
	private String lastName;
	private int birthYear;
	private String country;
	
	public TennisPlayer(String id, String firstName, String lastName, int birthYear, String country) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthYear = birthYear;
		this.country = country;
	}
	
	@Override
	public int compareTo(TennisPlayer otherPlayer) {
		
		if (otherPlayer == null) return 1;
		
		return otherPlayer.getId().compareTo(getId());
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public int getBirthYear() {
		return birthYear;
	}

	@Override
	public String getCountry() {
		return country;
	}

}
