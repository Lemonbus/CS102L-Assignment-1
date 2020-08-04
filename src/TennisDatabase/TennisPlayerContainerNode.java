package TennisDatabase;

class TennisPlayerContainerNode implements TennisPlayerContainerNodeInterface {
	
	private SortedLinkedList<TennisMatch> sortedMatches;
	
	private TennisPlayerContainerNode next;
	private TennisPlayerContainerNode prev;
	
	private TennisPlayer player;
	
	public TennisPlayerContainerNode(TennisPlayer player) {
		this.player = player;
		this.sortedMatches = new SortedLinkedList<TennisMatch>();
		this.next = null;
		this.prev = null;
	}
	
	@Override
	public TennisPlayer getPlayer() {
		return this.player;
	}

	@Override
	public TennisPlayerContainerNode getPrev() {
		return this.prev;
	}

	@Override
	public TennisPlayerContainerNode getNext() {
		return this.next;
	}

	@Override
	public void setPrev(TennisPlayerContainerNode p) {
		this.prev = p;
	}

	@Override
	public void setNext(TennisPlayerContainerNode n) {
		this.next = n;
	}

	@Override
	public void insertMatch(TennisMatch m) throws TennisDatabaseException {
		try {
			sortedMatches.insert(m);
		} catch (Exception e) {
			throw new TennisDatabaseException("Match was unable to be inserted");
		}
	}

	@Override
	public TennisMatch[] getMatches() throws TennisDatabaseRuntimeException {
		
		if (sortedMatches.size() == 0) throw new TennisDatabaseRuntimeException("Player has no matches registered");
		
		TennisMatch[] resultArray = new TennisMatch[sortedMatches.size()];
		
		for (int i = 0; i < sortedMatches.size(); i++) {
			resultArray[i] = sortedMatches.get(i);
		}
		
		return resultArray;
		
	}

}
