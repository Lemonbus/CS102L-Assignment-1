package TennisDatabase;

/**
 * CS-102 Assignment 1 - Tennis Database project
 * Kettering University - Summer 2020
 * Under instruction from Professor Giuseppe Turini
 * 
 * Represents a reference-based storage container for TennisPlayer objects
 * 
 * @author Jeremy Gooch / Freshman I
 *
 */
class TennisPlayerContainer implements TennisPlayerContainerInterface {

	private TennisPlayerContainerNode entry;
	private int numOfPlayers;
	
	public TennisPlayerContainer() {
		this.entry = null;
		this.numOfPlayers = 0;
	}
	
	@Override
	public TennisPlayer getPlayer(String id) throws TennisDatabaseRuntimeException {
		
		TennisPlayerContainerNode playerNode;
		
		try {
			playerNode = getPlayerNode(id);
		} catch (TennisDatabaseRuntimeException e) {
			return null;
		}
		
		return playerNode.getPlayer();
		
	}
	
	/**
	 * Inserts a TennisPlayer object into the LinkedList in a sorted fashion
	 * 
	 * @param p - TennisPlayer object to insert
	 */
	@Override
	public void insertPlayer(TennisPlayer p) throws TennisDatabaseException {
		
		if (numOfPlayers == 0) { // LinkedList is empty so we should make one to store the player object // SPECIAL CASE
			entry = new TennisPlayerContainerNode(p);
			entry.setPrev(entry);
			entry.setNext(entry);
			numOfPlayers++;
			return;
		}
		
		TennisPlayerContainerNode currentNode = entry;
		TennisPlayerContainerNode prevNode = currentNode.getPrev();
		int currentIndex = 0;
		
		while (currentIndex < numOfPlayers && currentNode.getPlayer().compareTo(p) < 0) { // traverse through nodes and see where we can insert based on compareTo() results
			prevNode = currentNode;
			currentNode = currentNode.getNext();
			currentIndex++;
		}
		
		TennisPlayerContainerNode newNode = new TennisPlayerContainerNode(p);
		
		// insert and complete circular LinkedList
		
		newNode.setPrev(prevNode);
		newNode.setNext(currentNode);
		prevNode.setNext(newNode);
		currentNode.setPrev(newNode);
		
		if (currentIndex == 0) {
			entry = newNode;
		}
		
		numOfPlayers++;
		
	}
	
	/**
	 * Inserts the match into the TennisPlayer node of each player that particpated
	 * 
	 * @param m - TennisMatch object to insert
	 */
	@Override
	public void insertMatch(TennisMatch m) throws TennisDatabaseException {
		
		TennisPlayer player1 = getPlayer(m.getIdPlayer1());
		TennisPlayer player2 = getPlayer(m.getIdPlayer2());
		
		TennisPlayerContainerNode player1Node = null;
		TennisPlayerContainerNode player2Node = null;
		
		// search for each player by traversing the list
		// if either of the players can't be found we shouldn't insert the match and the user should update the database
		
		try {
			player1Node = getPlayerNode(player1.getId());
		} catch (TennisDatabaseRuntimeException e) {
			System.out.println("Unable to find player " + player1.getId());
			return;
		}
		
		try {
			player2Node = getPlayerNode(player2.getId());
		} catch (TennisDatabaseRuntimeException e) {
			System.out.println("Unable to find player " + player2.getId());
			return;
		}
		
		player1Node.insertMatch(m);
		player2Node.insertMatch(m);
		
	}
	
	/**
	 * Gets all players currently in the LinkedList
	 * 
	 * @return TennisPlayer[] of all players in database
	 */
	@Override
	public TennisPlayer[] getAllPlayers() throws TennisDatabaseRuntimeException {
		
		if (entry == null) return new TennisPlayer[0];
		
		TennisPlayer[] resultArray = new TennisPlayer[numOfPlayers];
		
		int i = 0;
		
		TennisPlayerContainerNode node = entry;
		
		while (i < numOfPlayers) {
			resultArray[i] = node.getPlayer();
			node = node.getNext();
			i++;
		}
		
		return resultArray;
		
	}

	/**
	 * 
	 * Gets all matches that a specified player participated in
	 * 
	 * @param playerId - the player id that we are searching for
	 * @return TennisMatch[] of all matches a player has participated in
	 */
	@Override
	public TennisMatch[] getMatchesOfPlayer(String playerId) throws TennisDatabaseException, TennisDatabaseRuntimeException {
		
		TennisPlayerContainerNode playerNode;
		
		try {
			playerNode = getPlayerNode(playerId);
		} catch (TennisDatabaseRuntimeException e) {
			return null;
		}
		
		return playerNode.getMatches();
	}
	
	/**
	 * Gets the node of a TennisPlayer with the specified id
	 * 
	 * @param playerId - the id we are searching for
	 * @return - TennisPlayerContainerNode which contains the same playerId that was inputted
	 * @throws TennisDatabaseRuntimeException - when the player by id playerId cannot be found
	 */
	private TennisPlayerContainerNode getPlayerNode(String playerId) throws TennisDatabaseRuntimeException {
		
		if (entry == null) throw new TennisDatabaseRuntimeException("No players are loaded");
		
		TennisPlayerContainerNode node = entry;
		
		int i = 0;
		
		while (i < numOfPlayers) { // simple iteration through all nodes in LinkedList
			
			if (node.getPlayer().getId().equals(playerId)) return node;
			
			node = node.getNext();
			i++;
			
		}
		
		throw new TennisDatabaseRuntimeException("Player by ID " + playerId + " not found");
		
	}

}
