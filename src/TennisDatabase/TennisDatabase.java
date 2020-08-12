package TennisDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * CS-102 Assignment 1 - Tennis Database project
 * Kettering University - Summer 2020
 * Under instruction from Professor Giuseppe Turini
 * 
 * Represents a controller class that commands the TennisMatchContainer and TennisPlayerContainer
 * 
 * @author Jeremy Gooch / Freshman I
 *
 */
public class TennisDatabase implements TennisDatabaseInterface {
	
	private TennisMatchContainer matchContainer;
	private TennisPlayerContainer playerContainer;
	
	public TennisDatabase() {
		this.matchContainer = new TennisMatchContainer();
		this.playerContainer = new TennisPlayerContainer();
	}
	
	/**
	 * Gets a file in the current directory and attempts to load it as a database file
	 * The file is first checked with {@link #checkFileValidity(File)}
	 * 
	 * @param fileName - the file to search for and load
	 */
	@Override
	public void loadFromFile(String fileName) throws TennisDatabaseException, TennisDatabaseRuntimeException {
		File file = new File(fileName);
		
		if (!checkFileValidity(file)) {
			throw new TennisDatabaseRuntimeException("Invalid format, players need to be loaded before matches!");
		}
		
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			System.out.println("Could not get file input stream for requested file, please load valid file");
			return;
		}
		
		while (scanner.hasNextLine()) { // begin iterating through each line of file
			String line = scanner.nextLine().toUpperCase();
			
			String[] data = line.split("/");
			
			if (data[0].equals("MATCH")) { // this should be valid match data
				
				SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
				
				Date date = null;
				try {
					date = dateFormatter.parse(data[3]);
				} catch (ParseException e) {
					e.printStackTrace();
					System.out.println("Unable to parse date for match");
				}
				
				Calendar calendar = Calendar.getInstance();
				
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				
				insertMatch(data[1], data[2], year, month, day, data[4], data[5]);
			}
			
			if (data[0].equals("PLAYER")) { // this should be valid player data
				insertPlayer(data[1], data[2], data[3], Integer.parseInt(data[4]), data[5]);
			}
		}
		
		scanner.close(); // close scanner to prevent memory leaks
		
	}
	
	/**
	 * Ran before loading a file to make sure that the file is not empty and that players are loaded before matches
	 * 
	 * @param file - the file to check the validity of
	 * @return - true if file is valid, false otherwise
	 */
	private boolean checkFileValidity(File file) {
		
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			System.out.println("Could not get file input stream for requested file - please load valid file");
			return false;
		}
		
		if (!scanner.hasNextLine()) {
			scanner.close();
			return false; // empty file
		}
		
		boolean loadedPlayers = false;
		
		while (scanner.hasNextLine()) {
			
			String[] data = scanner.nextLine().toUpperCase().split("/");
			
			if (!(data[0].equalsIgnoreCase("PLAYER") || data[0].equalsIgnoreCase("MATCH"))) {
				scanner.close();
				return false; // invalid input data
			}
			
			if (data[0].equalsIgnoreCase("PLAYER")) {
				
				if (loadedPlayers) {
					scanner.close();
					return false; // this is set when a match input is loaded, so if a player is loaded after it's set to true we know the file is invalid
				}
				
			} else loadedPlayers = true;
		}
		
		scanner.close();
		
		return true;
	}
	
	/**
	 * Gets a shortened name of a player to minimize screen space
	 * 
	 * @param id - the playerId of the player to shorten
	 * @return - the player's name in the following format: first initial. last name (i.e. Jeremy Gooch -> J. GOOCH)
	 */
	public String getShortenedName(String id) {
		if (id == null) return null;
		
		TennisPlayer player = getPlayer(id);
		
		if (player == null) return null;
		
		return player.getFirstName().substring(0, 1) + ". " + player.getLastName();
	}
	
	/**
	 * Gets the name of the player who won a match
	 * 
	 * @param match - the match to check the winner of
	 * @return - name of winner or "No winner" if there is no clear winner (in the event of invalid score or a draw)
	 */
	public String getWinnerPlayer(TennisMatch match) {
		try {
			
			if (match.getWinner() == 1) {
				return getShortenedName(match.getIdPlayer1());
			} else if (match.getWinner() == 2) {
				return getShortenedName(match.getIdPlayer2());
			} else return "No winner";
			
		} catch (TennisDatabaseRuntimeException e) {
			return "No winner";
		}
	}
	
	/**
	 * Simple check to determine whether a player id is a valid id in the database
	 * 
	 * @param id - the id to check
	 * @return true if player id is valid, false otherwise
	 */
	public boolean isValidPlayerId(String id) {
		return getPlayer(id) != null;
	}
	
	// Methods inherited from interface
	
	@Override
	public TennisPlayer getPlayer(String id) throws TennisDatabaseRuntimeException {
		return playerContainer.getPlayer(id);
	}

	@Override
	public TennisPlayer[] getAllPlayers() throws TennisDatabaseRuntimeException {
		return playerContainer.getAllPlayers();
	}

	@Override
	public TennisMatch[] getMatchesOfPlayer(String playerId)
			throws TennisDatabaseException, TennisDatabaseRuntimeException {
		return matchContainer.getMatchesOfPlayer(playerId);
	}

	@Override
	public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException {
		return matchContainer.getAllMatches();
	}

	@Override
	public void insertPlayer(String id, String firstName, String lastName, int year, String country)
			throws TennisDatabaseException {
		playerContainer.insertPlayer(new TennisPlayer(id, firstName, lastName, year, country));
	}

	@Override
	public void insertMatch(String idPlayer1, String idPlayer2, int year, int month, int day, String tournament, String score) throws TennisDatabaseException {
		TennisMatch match = new TennisMatch(idPlayer1, idPlayer2, year, month, day, tournament, score);
		matchContainer.insertMatch(match);
		playerContainer.insertMatch(match);
	}
	
	public String getFormattedMatchString(TennisMatch match) {
		
		if (match == null) return null;
		
		return match.getDateDay() + "/" + match.getDateMonth() + "/" + match.getDateYear() + ": " + 
				getShortenedName(match.getIdPlayer1()) + " vs. " + getShortenedName(match.getIdPlayer2()) + ", " 
					+ match.getTournament() + ", " + match.getMatchScore() + ", Winner: " + getWinnerPlayer(match);
	}
}
