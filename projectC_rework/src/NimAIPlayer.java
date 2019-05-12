/*
	NimAIPlayer.java
	
	This class is provided as a skeleton code for the tasks of 
	Sections 2.3, 2.4 and 2.5 in Project C. Add code (do NOT delete any) to it
	to finish the tasks. 
	
	Coded by: Jin Huang
	Modified by: Jianzhong Qi, 29/04/2015
*/

public class NimAIPlayer extends NimPlayer implements Testable {
	// you may further extend a class or implement an interface
	// to accomplish the task in Section 2.3	
	
	private int remove;
	
	public NimAIPlayer() {
		super();
	}
	
	public NimAIPlayer(String username,String givenName,
			String familyName, int numPlayed,int numWon){
		
		super(username,givenName,familyName,numPlayed,numWon);
	}
	
	
	
	public String advancedMove(boolean[] available, String lastMove) {
		// the implementation of the victory
		// guaranteed strategy designed by you
		String move = "";
		
		return move;
	}
	
	public void setAiRemoveStone(int stoneCount, int uppBound) {
		int N = stoneCount,M = uppBound;
		int i=-1,N1;//N1 represent numStones left for human player
		for (int k = 0;k*(M+1)+1<N;k++){
			//gives k's value
			i++;
		}
		N1 = i*(M+1)+1;
		
		remove = (stoneCount-N1); //numOfStoneRemove for AI player
	}
	public int getRemoveStone() {
		return remove;
	}

	public void setRemoveStone(int remove) {
	}

	
}