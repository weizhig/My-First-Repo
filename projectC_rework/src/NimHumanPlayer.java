
public class NimHumanPlayer extends NimPlayer{
	
	public NimHumanPlayer(){
		super();
	}
	
	public NimHumanPlayer(String uName,String gName,String fName,
			int numPlayed, int numWon){
		super(uName,gName,fName,numPlayed,numWon);
		
	}
	//get each player number of stone will move from the game
	public int getRemoveStone(){
		return removal;
	}
		
		//set each player number of stone will move from the game
	public void setRemoveStone(int remove){
		removal = remove;
	}
	
	public void setAiRemoveStone(int stoneCount, int uppBound){}

	
}
