package TennisDatabase;

import java.util.Scanner;

public class ViewPlayersCommand implements Command {
	
	private TennisDatabase tennisDatabase;
	
	public ViewPlayersCommand(TennisDatabase tennisDatabase) {
		this.tennisDatabase = tennisDatabase;
	}
	
	@Override
	public TennisDatabase getDatabase() {
		return this.tennisDatabase;
	}
	
	@Override
	public Scanner getScanner() {
		return null;
	}
	
	@Override
	public void execute() {
		if (getDatabase().getAllPlayers().length <= 0) {
			System.out.println("There are no players to list in the database");
			return;
		}
		
		for (TennisPlayer player : getDatabase().getAllPlayers()) {
			System.out.println(player);
		}
	}

}
