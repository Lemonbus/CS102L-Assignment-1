package TennisDatabase;

import java.util.Scanner;

public class ViewPlayerMatchesCommand implements Command {
	
	private TennisDatabase tennisDatabase;
	
	private Scanner scanner = null;
	
	public ViewPlayerMatchesCommand(TennisDatabase tennisDatabase) {
		this.tennisDatabase = tennisDatabase;
	}
	
	@Override
	public TennisDatabase getDatabase() {
		return this.tennisDatabase;
	}
	
	@Override
	public Scanner getScanner() {
		return this.scanner;
	}

	@Override
	public Command attachScanner(Scanner scanner) {
		this.scanner = scanner;
		return this;
	}
	
	@Override
	public void execute() {
		
		System.out.println("Please insert the ID of the player you want the records of");
		
		String playerIdInput = getScanner().next();
		
		try {
			for (TennisMatch match : getDatabase().getMatchesOfPlayer(playerIdInput)) {
				
				if (match == null) continue;
				
				System.out.println(getDatabase().getFormattedMatchString(match));
			}
		} catch (TennisDatabaseRuntimeException | TennisDatabaseException e1) {
			e1.printStackTrace();
		}
		
	}
}
