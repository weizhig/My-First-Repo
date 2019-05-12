
public abstract class NimPlayer{
	String isAI;
	private String userName, givenName, familyName;
	private int numGamePlayed,numGameWon;
	protected int removal;
	private double gameWinRatio;
	
	//constructors//
	//setting initial condition for each player
	public NimPlayer(){
		userName = "";
		givenName = "";
		familyName = "";
		numGamePlayed = 00;
		numGameWon = 00;
	}
	public NimPlayer(String uName,String gName,String fName,
			int numPlayed, int numWon){
		userName = uName;
		givenName = gName;
		familyName = fName;
		numGamePlayed = numPlayed;
		numGameWon = numWon;
	}
	
	//method to create a player
	public void setPlayerInfo(String uName,String gName,String fName){
		userName = uName;
		givenName = gName;
		familyName = fName;
	}
	
	//method to update the number of game the player played
	public void setNumPlayed(int numPlayed){
		numGamePlayed = numPlayed;
	}
	
	//method to update the number of game the player won
	public void setNumWon(int numWon){
		numGameWon = numWon;
	}
	
	//obtain the username of the player
	public String getUsername(){
		return userName;
	}
	
	//obtain the given name of the player
	public String getGivenName(){
		return givenName;
	}
	
	//obtain the family name of the player
	public String getFamilyName(){
		return familyName;
	}
	
	//method to print full name of the player
	public String printFullName(){
		return (givenName + " " + familyName);
	}
	
	//obtain the number of game the player played
	public int getNumGamePlayed(){
		return numGamePlayed;
	}
	
	//obtain the number of game the player won
	public int getNumGameWon(){
		return numGameWon;
	}
	
	//get each player number of stone will move from the game
	public abstract int getRemoveStone();
	
	//set each player number of stone will move from the game
	public abstract void setRemoveStone(int remove);
	
	//set aiplayer number of stone to remove
	public abstract void setAiRemoveStone(int stoneCount,int uppBound);
	
	//set the game winning rate for each player
	public void setGameWinRatio(double gameWinRatio){
		this.gameWinRatio = gameWinRatio;
	}//display each player's statistic
	public String displayGameInfo(){
		return (userName + "," + givenName
				+ "," + familyName + "," +
				numGamePlayed + " games," + 
				numGameWon + " wins");
	}
	
	//display each player's winning rate
	public void displayRanking(int winningRate){
		String wN = Integer.toString(winningRate) + "%";
        System.out.printf("%-5s",wN);
        System.out.printf("| %02d games ",this.numGamePlayed);
        System.out.print("| " + this.givenName + " " + this.familyName);
        System.out.print("\n");
	}
	
	//get the game winning rate for each player
	//This method is used to get each player's winning rate
	//to compare with each other, and decide who will have 
	//a higher winning rate
	public double getGameWinRatio(){
		double numGameWon = this.numGameWon;
		double numGamePlayed = this.numGamePlayed;
		return this.gameWinRatio =  numGameWon*100/numGamePlayed;
		   
	}
	
	public String toString(){
		return (userName + "," + givenName
				+ "," + familyName + "," +
				numGamePlayed + "," + 
				numGameWon + "," + isAI);
	}
	
}
