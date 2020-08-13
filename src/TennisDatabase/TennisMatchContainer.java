package TennisDatabase;

import java.util.Arrays;
import java.util.Comparator;

/**
 * CS-102 Assignment 1 - Tennis Database project
 * Kettering University - Summer 2020
 * Under instruction from Professor Giuseppe Turini
 * 
 * Represents a array-based storage container for TennisMatch objects
 * 
 * @author Jeremy Gooch / Freshman I
 *
 */
class TennisMatchContainer implements TennisMatchContainerInterface {

	private TennisMatch[] matches;
	
	public TennisMatchContainer() {
		matches = new TennisMatch[25]; // hardcoded space for 25 matches by default
	}
	
	/**
	 * Adds the TennisMatch to the array, resizing it if necessary
	 * 
	 * @param m - the match to insert
	 */
	@Override
	public void insertMatch(TennisMatch m) throws TennisDatabaseException {
		
		int slot = findFirstAvailableSlot(getAllMatches());
		
		if (slot == -1) {
			matches = resizeArray(getAllMatches());
			slot = findFirstAvailableSlot(getAllMatches());
		}
		
		matches[slot] = m;
		
		sortMatches(matches);
		
	}
	
	/**
	 * Gets all matches in the database
	 * 
	 * @return - TennisMatch[] of all matches in the array
	 */
	@Override
	public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException {
		return matches;
	}
	
	/**
	 * Gets all matches of a specified player
	 * 
	 * @param playerId - the id of the player
	 * @return TennisMatch[] of all matches the player participated in
	 */
	@Override
	public TennisMatch[] getMatchesOfPlayer(String playerId) throws TennisDatabaseRuntimeException {
		
		TennisMatch[] personalMatches = new TennisMatch[10];
		
		int counter = 0;
		
		for (TennisMatch match : matches) {
			
			if (match == null) continue;
			
			if (match.getIdPlayer1().equals(playerId) || match.getIdPlayer2().equals(playerId)) {
				personalMatches[counter] = match;
				counter++;
			}
		}
		
		if (counter == 0) throw new TennisDatabaseRuntimeException("No matches found for player: " + playerId);
		
		return personalMatches;
	}
	
	/**
	 * Creates a new array 1.5x bigger than the inputted one and copies the contents of the inputted array into it
	 * 
	 * @param array - the array to resize
	 * @return TennisMatch[] - the new resized array
	 */
	private TennisMatch[] resizeArray(TennisMatch[] array) {
		TennisMatch[] b = new TennisMatch[(int) Math.round(array.length * 1.5)];
		
		for (int i = 0; i < array.length; i++) {
			b[i] = array[i];
		}
		
		return b;
	}
	
	/**
	 * Finds the first index where the value is null (which means open for use)
	 * 
	 * @param array - the array to search
	 * @return - the first index available
	 */
	private int findFirstAvailableSlot(TennisMatch[] array) {
		
		int result = -1;
		
		for (int i = 0; i < array.length; i++) {
			if (array[i] == null) {
				result = i;
				break;
			}
		}
		
		return result;
		
	}
	
	/**
	 * Sorts the matches based on the {@link TennisMatch#compareTo(TennisMatch)} method
	 * 
	 * @param array - the array to sort
	 * @return TennisMatch[] the sorted array
	 */
	private TennisMatch[] sortMatches(TennisMatch[] array) {
		Arrays.sort(array, Comparator.nullsLast(Comparator.naturalOrder()));
		return array;
	}

}
