


// Giuseppe Turini
// CS-102, Summer 2020
// Assignment 1

package TennisDatabase;

// Custom (unchecked) exception for the TennisDatabase package, representing non critical runtime errors (handling is optional).
public class TennisDatabaseRuntimeException extends java.lang.RuntimeException {
   
	private static final long serialVersionUID = 1L; // added so compiler would stop complaining

	// Desc.: Constructor.
	// Input: Description of the runtime error.
	public TennisDatabaseRuntimeException( String s ) { super(s); }
   
}


