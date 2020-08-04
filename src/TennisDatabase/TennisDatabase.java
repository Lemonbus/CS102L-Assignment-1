package TennisDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class TennisDatabase implements TennisDatabaseInterface {
	
	private TennisMatchContainer matchContainer;
	private TennisPlayerContainer playerContainer;
	
	public TennisDatabase() {
		this.matchContainer = new TennisMatchContainer();
		this.playerContainer = new TennisPlayerContainer();
	}
	
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
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().toUpperCase();
			
			String[] data = line.split("/");
			
			if (data[0].equals("MATCH")) {
				
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
			
			if (data[0].equals("PLAYER")) {
				insertPlayer(data[1], data[2], data[3], Integer.parseInt(data[4]), data[5]);
			}
		}
		
	}
	
	private boolean checkFileValidity(File file) {
		
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			System.out.println("Could not get file input stream for requested file - please load valid file");
			return false;
		}
		
		if (!scanner.hasNextLine()) return false; // empty file
		
		boolean loadedPlayers = false;
		
		while (scanner.hasNextLine()) {
			
			String[] data = scanner.nextLine().toUpperCase().split("/");
			
			if (!(data[0].equalsIgnoreCase("PLAYER") || data[0].equalsIgnoreCase("MATCH"))) return false; // invalid input data
			
			if (data[0].equalsIgnoreCase("PLAYER")) {
				
				if (loadedPlayers) return false; // this is set when a match input is loaded, so if a player is loaded after it's set to true we know the file is invalid
				
			} else loadedPlayers = true;
		}
		
		return true;
	}

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
		TennisMatch match = new TennisMatch(idPlayer1, idPlayer2, year, month, day, tournament, score, 1);
		matchContainer.insertMatch(match);
		playerContainer.insertMatch(match);
	}

}
