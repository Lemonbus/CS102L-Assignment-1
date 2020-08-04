


// Giuseppe Turini
// CS-102, Summer 2020
// Assignment 1

package TennisDatabase;

// Custom (checked) exception for the TennisDatabase package, representing critical runtime errors (that must be handled).
public class TennisDatabaseException extends java.lang.Exception {
   
	private static final long serialVersionUID = 1L; // added so compiler would stop complaining

	// Desc.: Constructor.
	// Input: Description of the runtime error.
	public TennisDatabaseException( String s ) { super(s); }
   
}


