package TennisDatabase;

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

	@Override
	public void insertPlayer(TennisPlayer p) throws TennisDatabaseException {
		
		if (numOfPlayers == 0) {
			entry = new TennisPlayerContainerNode(p);
			entry.setPrev(entry);
			entry.setNext(entry);
			numOfPlayers++;
			return;
		}
		
		TennisPlayerContainerNode currentNode = entry;
		TennisPlayerContainerNode prevNode = currentNode.getPrev();
		int currentIndex = 0;
		
		while (currentIndex < numOfPlayers && currentNode.getPlayer().compareTo(p) < 0) {
			prevNode = currentNode;
			currentNode = currentNode.getNext();
			currentIndex++;
		}
		
		TennisPlayerContainerNode newNode = new TennisPlayerContainerNode(p);
		
		newNode.setPrev(prevNode);
		newNode.setNext(currentNode);
		prevNode.setNext(newNode);
		currentNode.setPrev(newNode);
		
		if (currentIndex == 0) {
			entry = newNode;
		}
		
		if (currentIndex == numOfPlayers) {
			
		}
		
		numOfPlayers++;
		
	}

	@Override
	public void insertMatch(TennisMatch m) throws TennisDatabaseException {
		
		TennisPlayer player1 = getPlayer(m.getIdPlayer1());
		TennisPlayer player2 = getPlayer(m.getIdPlayer2());
		
		TennisPlayerContainerNode player1Node = null;
		TennisPlayerContainerNode player2Node = null;
		
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
	
	private TennisPlayerContainerNode getPlayerNode(String playerId) throws TennisDatabaseRuntimeException {
		
		if (entry == null) throw new TennisDatabaseRuntimeException("No players are loaded");
		
		TennisPlayerContainerNode node = entry;
		
		while (node != null) {
			
			if (node.getPlayer().getId().equals(playerId)) return node;
			
			node = node.getNext();
			
		}
		
		throw new TennisDatabaseRuntimeException("Player by ID " + playerId + " not found");
		
	}

}
