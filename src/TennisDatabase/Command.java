package TennisDatabase;

import java.util.Scanner;

public interface Command {
	
	public TennisDatabase getDatabase();
	
	public Scanner getScanner();
	
	public default Command attachScanner(Scanner scanner) { // only necessary if scanner is required
		return this;
	}
	
	public void execute();

}
