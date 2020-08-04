package TennisDatabase;

import java.util.Arrays;
import java.util.Comparator;

class TennisMatchContainer implements TennisMatchContainerInterface {

	private TennisMatch[] matches;
	
	public TennisMatchContainer() {
		matches = new TennisMatch[25]; // hardcoded space for 25 matches by default
	}
	
	@Override
	public void insertMatch(TennisMatch m) throws TennisDatabaseException {
		
		int slot = findFirstAvailableSlot(getAllMatches());
		
		if (slot == -1) {
			matches = resizeArray(getAllMatches());
		}
		
		slot = findFirstAvailableSlot(getAllMatches());
		
		matches[slot] = m;
		
		sortMatches(matches);
		
	}
	
	@Override
	public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException {
		return matches;
	}
	
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
		
		return personalMatches;
	}
	
	private TennisMatch[] resizeArray(TennisMatch[] array) {
		TennisMatch[] b = new TennisMatch[(int) Math.round(array.length * 1.5)];
		
		for (int i = 0; i < array.length; i++) {
			b[i] = array[i];
		}
		
		return b;
	}
	
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
	
	private TennisMatch[] sortMatches(TennisMatch[] array) {
		Arrays.sort(array, Comparator.nullsLast(Comparator.naturalOrder()));
		return array;
	}
	
//	private void sort(TennisMatch[] matches, TennisMatch currentMatch) {
//		
//		for (int i = 0; i < matches.length; i++) {
//			if (currentMatch.compareTo(matches[i]) > 0) {
//				rightShift(matches, i);
//				matches[i] = currentMatch;
//				break;
//			}
//		}
//		
//	}
	
//	private TennisMatch[] rightShift(TennisMatch[] array, int slot) {
//	
//	for (int i = array.length - 1; i > slot; i--) {
//		TennisMatch previousItem = array[i - 1];
//		array[i] = previousItem;
//	}
//	
//	array[slot] = null;
//	
//	return array;
//	
//}

}
