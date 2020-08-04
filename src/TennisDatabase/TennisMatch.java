package TennisDatabase;

import java.util.Calendar;

public class TennisMatch implements TennisMatchInterface {

	private String idPlayer1;
	private String idPlayer2;
	private int dateYear;
	private int dateMonth;
	private int dateDay;
	private String tourney;
	private String matchScore;
	private int winner;
	
	public TennisMatch(String idPlayer1, String idPlayer2, int dateYear, int dateMonth, int dateDay, String tourney, String matchScore, int winner) {
		this.idPlayer1 = idPlayer1;
		this.idPlayer2 = idPlayer2;
		this.dateYear = dateYear;
		this.dateMonth = dateMonth;
		this.dateDay = dateDay;
		this.tourney = tourney;
		this.matchScore = matchScore;
		this.winner = winner;
	}
	
	@Override
	public int compareTo(TennisMatch o) {
		
		if (o == null) return 1;
		
		Calendar thisDate = Calendar.getInstance();
		thisDate.set(dateYear, dateMonth, dateDay);
		
		Calendar otherDate = Calendar.getInstance();
		otherDate.set(o.dateYear, o.dateMonth, o.dateDay);
		
		return thisDate.compareTo(otherDate);
		
	}

	@Override
	public String getIdPlayer1() {
		return idPlayer1;
	}

	@Override
	public String getIdPlayer2() {
		return idPlayer2;
	}

	@Override
	public int getDateYear() {
		return dateYear;
	}

	@Override
	public int getDateMonth() {
		return dateMonth;
	}

	@Override
	public int getDateDay() {
		return dateDay;
	}

	@Override
	public String getTournament() {
		return tourney;
	}

	@Override
	public String getMatchScore() {
		return matchScore;
	}

	@Override
	public int getWinner() {
		return winner;
	}

}
