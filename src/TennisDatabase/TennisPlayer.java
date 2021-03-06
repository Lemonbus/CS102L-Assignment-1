package TennisDatabase;

/**
 * CS-102 Assignment 1 - Tennis Database project
 * Kettering University - Summer 2020
 * Under instruction from Professor Giuseppe Turini
 * 
 * Represents a tennis player including id, name, birth year and home country
 * 
 * @author Jeremy Gooch / Freshman I
 *
 */
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
	
	@Override
	public String toString() {
		return getId() + ": " + getFirstName() + " " + getLastName() + " - Birthyear: " + getBirthYear() + " - Country: " + getCountry();
	}

}
