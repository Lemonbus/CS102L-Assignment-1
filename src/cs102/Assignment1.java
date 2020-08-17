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

/**
 * CS-102 Assignment 1 - Tennis Database project
 * Kettering University - Summer 2020
 * Under instruction from Professor Giuseppe Turini
 * 
 * @author Jeremy Gooch / Freshman I
 *
 */
public class Assignment1 {
	
	/**
	 * Entry to program, contains database object which instructs other objects to gather/modify data and also scans for user input
	 * @param args - startup flags
	 */
	public static void main(String[] args) {
		TennisDatabase database = new TennisDatabase(); // single database object
		
		if (args.length != 0) { // try to load file that might exist in startup parameters
		
			try {
				database.loadFromFile(args[0]);
			} catch (TennisDatabaseRuntimeException | TennisDatabaseException e) {
				System.out.println("No files were loaded successfully on startup, please load a file"); // let user know there is no data in database
			}
		
		}
		
		sendHelpMessage();
		
		Scanner scanner = new Scanner(System.in); // scanner object for input
		
		while (scanner.hasNextLine()) { // begin reading user input
			
			String input = scanner.next();
			
			if (!isNumber(input)) { // since all of our commands are number based, we don't really care about any other form of input other than integers
				sendHelpMessage();
				continue;
			}
			
			switch (Integer.parseInt(input)) { // switch statement to determine what to do when certain values are entered
			case 1:
				
				if (database.getAllPlayers().length <= 0) { // no players are in the database, so we can't list them
					System.out.println("There are no players to list in the database");
					break; // break back to main loop
				}
				
				for (TennisPlayer player : database.getAllPlayers()) {
					System.out.println(player); // TennisPlayer has a custom toString() method which makes it easy to print here
				}
				
				break; // break back to main loop after listing players
			case 2:
				
				if (database.getAllPlayers().length <= 0) { // no players are in the database, so we can't get the records of any
					System.out.println("There are no players to lookup in the database");
					break; // break back to main loop
				}
				
				System.out.println("Please insert the ID of the player you want the records of");
				
				String playerIdInput = scanner.next();
				
				try {
					for (TennisMatch match : database.getMatchesOfPlayer(playerIdInput)) {
						
						if (match == null) continue;
						
						System.out.println(database.getFormattedMatchString(match));
					}
				} catch (TennisDatabaseRuntimeException e) {
					System.out.println("No matches found for player: " + playerIdInput);
				} catch (TennisDatabaseException e) {
					System.out.println("Invalid player ID: " + playerIdInput);
				}
				
				break;
			case 3:
				
				try {
				
					for (TennisMatch match : database.getAllMatches()) {
						
						if (match == null) break;
						
						System.out.println(database.getFormattedMatchString(match));
						
					}
				
				} catch (TennisDatabaseRuntimeException e) {
					System.out.println("There are no matches in the database");
				}
				
				break;
			case 4:
				
				// request player data from user
				
				System.out.println("Please enter the ID of the player you'd like to insert");
				String id = scanner.next();
				System.out.println("Please enter the first name of the player you'd like to insert");
				String firstName = scanner.next();
				System.out.println("Please enter the last name of the player you'd like to insert");
				String lastName = scanner.next();
				System.out.println("Please enter the birth year of the player you'd like to insert");
				int birthYear;
				
				String birthYearInput = scanner.next();
				
				if (!isNumber(birthYearInput)) { // verify that the birth year entered is an integer
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
				
				if (!database.isValidPlayerId(id1)) { // check that id1 is an existing player in the database
					System.out.println("Player ID '" + id1 + "' is not a valid player ID");
					break;
				}
				
				System.out.println("Please enter the ID of the second player you'd like to insert");
				String id2 = scanner.next();
				
				if (!database.isValidPlayerId(id2)) { // check that id2 is an existing player in the database
					System.out.println("Player ID '" + id2 + "' is not a valid player ID");
					break;
				}
				
				System.out.println("Please enter the date of the match in the following format: YYYYMMDD");
				String date = scanner.next();
				int[] separatedDate = getDate(date);
				
				if (separatedDate == null) {
					System.out.println("The date you entered is not in a valid format");
					break;
				}
				
				System.out.println("Please enter the location of the match");
				String tourneyCountry = scanner.next();
				
				System.out.println("Please enter the score of the match");
				String score = scanner.next();
				
				try {
					database.insertMatch(id1, id2, separatedDate[0], separatedDate[1], separatedDate[2], tourneyCountry, score);
					System.out.println("Successfully inserted match!");
				} catch (TennisDatabaseException e1) {
					System.out.println("Unable to insert the requested match");
				}
				break;
			case 6:
				System.out.println("Please enter the full name of the file you'd like to load"); // this requires the user to also include the extension (i.e. .txt)
				String fileName = scanner.next();
				try {
					database.loadFromFile(fileName);
					System.out.println(fileName + " loaded");
				} catch (TennisDatabaseRuntimeException | TennisDatabaseException e) {
					System.out.println("An error occured while loading your file, please ensure that it is in a valid format");
				}
				
				break;
			case 7:
				scanner.close(); // close scanner to prevent memory leaks
				System.exit(0); // close program
				break;
			default:
				sendHelpMessage(); // any random input should just return help message
				break;
			}
			
		}
	}
	
	/*
	 * Simple method to send the user a list of commands
	 */
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
	
	/**
	 * Generic method to take a date represented in a string format and split it into day, month and year
	 * 
	 * @param string - string of date which should be represented in the following format: yyyyMMdd
	 * @return - null if string cannot be parsed OR array of integers where int[0] = year, int[1] = month, int[2] = day
	 */
	private static int[] getDate(String string) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
		
		Date date = null;
		try {
			date = dateFormatter.parse(string);
		} catch (ParseException e) {
			return null;
		}
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		return new int[] { year, month, day };
	}
	
	/**
	 * Simple check to determine whether a string is an integer
	 * 
	 * @param string - string to check
	 * @return true if string can be converted into an integer, otherwise false
	 */
	private static boolean isNumber(String string) {
		
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
