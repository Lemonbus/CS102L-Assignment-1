package cs102;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import TennisDatabase.TennisDatabase;
import TennisDatabase.TennisDatabaseException;
import TennisDatabase.TennisDatabaseRuntimeException;
import TennisDatabase.TennisMatch;
import TennisDatabase.TennisPlayer;

public class Assignment1 {
	
	public static void main(String[] args) {
		TennisDatabase database = new TennisDatabase();
		
		if (args.length != 0) {
		
			try {
				database.loadFromFile(args[0]);
			} catch (TennisDatabaseRuntimeException | TennisDatabaseException e) {
				System.out.println("No files were loaded successfully on startup, please load a file");
			}
		
		}
		
		sendHelpMessage();
		
		Scanner scanner = new Scanner(System.in);
		
		while (scanner.hasNextLine()) {
			
			String input = scanner.next();
			
			if (!isNumber(input)) {
				sendHelpMessage();
				continue;
			}
			
			switch (Integer.parseInt(input)) {
			case 1:
				
				if (database.getAllPlayers().length <= 0) {
					System.out.println("There are no players to list in the database");
					break;
				}
				
				for (TennisPlayer player : database.getAllPlayers()) {
					System.out.println(player);
				}
				
				break;
			case 2:
				
				System.out.println("Please insert the ID of the player you want the records of");
				
				String playerIdInput = scanner.next();
				
				try {
					for (TennisMatch match : database.getMatchesOfPlayer(playerIdInput)) {
						
						if (match == null) continue;
						
						System.out.println(database.getFormattedMatchString(match));
					}
				} catch (TennisDatabaseRuntimeException | TennisDatabaseException e1) {
					e1.printStackTrace();
				}
				
				break;
			case 3:
				
				for (TennisMatch match : database.getAllMatches()) {
					
					if (match == null) break;
					
					System.out.println(database.getFormattedMatchString(match));
					
				}
				
				break;
			case 4:
				
				System.out.println("Please enter the ID of the player you'd like to insert");
				String id = scanner.next();
				System.out.println("Please enter the first name of the player you'd like to insert");
				String firstName = scanner.next();
				System.out.println("Please enter the last name of the player you'd like to insert");
				String lastName = scanner.next();
				System.out.println("Please enter the birth year of the player you'd like to insert");
				int birthYear;
				
				String birthYearInput = scanner.next();
				
				if (!isNumber(birthYearInput)) {
					System.out.println("Invalid number");
					break;
				}
				
				birthYear = Integer.valueOf(birthYearInput);
				
				System.out.println("Please enter the home country of the player you'd like to insert");
				String country = scanner.next();
				
				try {
					database.insertPlayer(id.toUpperCase(), firstName.toUpperCase(), lastName.toUpperCase(), birthYear, country.toUpperCase());
					System.out.println("Successfully inserted player: " + database.getPlayer(id.toUpperCase()));
				} catch (TennisDatabaseException e1) {
					System.out.println("Unable to insert player");
				}
				
				break;
			case 5:
				System.out.println("Please enter the ID of the first player you'd like to insert");
				String id1 = scanner.next();
				
				if (!database.isValidPlayerId(id1)) {
					System.out.println("Player ID '" + id1 + "' is not a valid player ID");
					break;
				}
				
				System.out.println("Please enter the ID of the second player you'd like to insert");
				String id2 = scanner.next();
				
				if (!database.isValidPlayerId(id2)) {
					System.out.println("Player ID '" + id2 + "' is not a valid player ID");
					break;
				}
				
				System.out.println("Please enter the date of the match in the following format: YYYYMMDD");
				String date = scanner.next();
				int[] separatedDate = getDate(date);
				
				System.out.println("Please enter the location of the match");
				String tourneyCountry = scanner.next();
				
				System.out.println("Please enter the score of the match");
				String score = scanner.next();
				
				try {
					database.insertMatch(id1, id2, separatedDate[0], separatedDate[1], separatedDate[2], tourneyCountry, score); // TODO: Check for valid player ID
					System.out.println("Successfully inserted match!");
				} catch (TennisDatabaseException e1) {
					System.out.println("Unable to insert the requested match");
				}
				break;
			case 6:
				System.out.println("Please enter the full name of the file you'd like to load");
				String fileName = scanner.next();
				try {
					database.loadFromFile(fileName);
					System.out.println(fileName + " loaded");
				} catch (TennisDatabaseRuntimeException | TennisDatabaseException e) {
					System.out.println("An error occured while loading your file, please ensure that it is in a valid format");
				}
				
				break;
			case 7:
				scanner.close();
				System.exit(0);
				break;
			default:
				sendHelpMessage();
				break;
			}
			
		}
	}
	
	private static void sendHelpMessage() {
		System.out.println("*********************");
		System.out.println("CS-102 Tennis Manager / Available Commands");
		System.out.println("1 - List all players");
		System.out.println("2 - List all matches of a player");
		System.out.println("3 - List all matches");
		System.out.println("4 - Insert new player");
		System.out.println("5 - Insert new match");
		System.out.println("6 - Load file");
		System.out.println("7 - Exit");
		System.out.println("*********************");
	}
	
	private static int[] getDate(String string) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
		
		Date date = null;
		try {
			date = dateFormatter.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("Unable to parse date for match");
		}
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		return new int[] { year, month, day };
	}
	
	private static boolean isNumber(String string) {
		
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
