import java.util.Scanner;
public class NimGame {
	private int stoneCount,uppBound;
	private NimPlayer aiPlayer;
	//private NimHumanPlayer humanPlayer;
	private int zero=0;
	
	
	//identify whose turn in the game
	public void whoseTurn(NimPlayer player){
		System.out.printf("%s's turn", player.getGivenName());
		System.out.println(" - remove how many?");
		System.out.print("\n");
	}
	
	//set the current number of stones (initial number of stones as well)
	public void setCurrentStone(int stones){
		stoneCount = stones;
	}
	
	//set the upper limit is allowed to be removed the game
	public void setUpperBound(int bound){
		uppBound = bound;
	}
	
	//get the current number of stones (initial number of stones as well)
	public int getCurrentStone(){
		return stoneCount;
	}
	
	//get the upper limit is allowed to be removed the game
	public int getUpperBound(){
		return uppBound;
	}
	
	//throw zeroInput and invalidInput Exception
	public void validInput(int numRemove) throws StoneInputException,
		ZeroInputException{
			if (numRemove>this.uppBound)
				throw new StoneInputException();
			else if (numRemove == zero)
				throw new ZeroInputException();
	}
	
	//identify if the number of stone remove of the player is valid 
	//(exceed upper limit or 0 remove)
	public boolean uppBoundRemoval(int numRemove){
		try {
			validInput(numRemove);
		} catch (StoneInputException e) {
			if (this.stoneCount < this.uppBound)
				System.out.println("Invalid move. You must remove between 1 and " 
						+ this.stoneCount + " stones.");
			else {
				System.out.println("Invalid move. You must remove between 1 and " 
						+ this.uppBound + " stones.");
			}
			System.out.print("\n");
		} catch (ZeroInputException e) {
			if (this.stoneCount > this.uppBound)
				System.out.println("Invalid move. You must remove between 1 and " 
					+ this.uppBound + " stones.");
			else
				System.out.println("Invalid move. You must remove between 1 and " 
						+ this.stoneCount + " stones.");
			System.out.print("\n");
		}
		if ((numRemove>this.uppBound)||(numRemove==zero)){
			return false;
		}
		else {
			return true;
		}
	}
	
	//check if the move is a valid move
	public void isValidMove(Scanner removeStone,int numOfStone,boolean play,
			NimPlayer userA,NimPlayer userB){
		boolean validMove=false;
		int remove;
		while (validMove !=true){
			stoneCount();
			System.out.print("\n");	
			if (play == true){
				whoseTurn(userA);
				remove = removeStone(userA,removeStone);
			}
			else {
				whoseTurn(userB);
				remove = removeStone(userB,removeStone);
			}
			validMove = uppBoundRemoval(remove);
			if (validMove == true){
				numOfStone = numOfStone - remove;
				setCurrentStone(numOfStone);
				break;
			}
		}
	}
	
	//get number of Stone to be remove
	public int removeStone(NimPlayer player,Scanner removeStone){
		int remove;
		if(player.isAI.equals("true")){
			player.setAiRemoveStone(this.stoneCount, this.uppBound);
			remove =(player.getRemoveStone());
		}
		else {
			player.setRemoveStone(removeStone.nextInt());
			removeStone.nextLine(); //helps to get rip of error
			remove =(player.getRemoveStone());
		}
		return remove;
	}
	
	//method to print stone left
	public void stoneCount() {
		//printing stones
		System.out.printf("%d stones left:", this.stoneCount);
		int stoneRemaing=zero;
		for (stoneRemaing=this.stoneCount;stoneRemaing>0;stoneRemaing--){
			System.out.print(" *");
		}
	}
	
	//display the initial states of the game
	public void initStates(int numStone,int bound,
			NimPlayer playerA, NimPlayer playerB){
		System.out.println();
		System.out.print("Initial stone count: " + numStone + "\n");
		System.out.print("Maximum stone removal: " + bound + "\n");
		System.out.print("Player 1: " + playerA.getGivenName() + 
				" " + playerA.getFamilyName()+"\n");
		System.out.print("Player 2: " + playerB.getGivenName() + 
				" " + playerB.getFamilyName()+"\n\n");
	}
	
	//method for game play mechanics
	public void gamePlayMechanics(int setInit,int setUpp,
			NimPlayer userA,NimPlayer userB,Scanner removeStone){
		int numOfStone,uppB;
		int gameWinA,gamePlayedA,gameWinB,gamePlayedB;
		boolean play = true;
		setCurrentStone(setInit); //stoneCount = setInt
		setUpperBound(setUpp);	  //uppBound = setUpp
		numOfStone = getCurrentStone();
		uppB = getUpperBound();
		//load the data of playerA and playerB 
		//(about their numWin and numPlayed)
		gameWinA = userA.getNumGameWon(); 
		gamePlayedA = userA.getNumGamePlayed();
		gameWinB = userB.getNumGameWon();
		gamePlayedB = userB.getNumGamePlayed();
		/*=================================*/
		initStates(numOfStone,uppB,userA,userB);//display initial states
		
		while (numOfStone!=zero){
			isValidMove(removeStone,numOfStone,play,userA,userB);
			numOfStone = getCurrentStone();
			if ((numOfStone==zero)||(numOfStone<zero)){
				System.out.println("Game Over");
				if (play == true){
					// if playerA's turn finish picking the stoneLeft
					// then playerB wins the game
					System.out.print(userB.getGivenName()
							+ " " + userB.getFamilyName()+ " wins!\n");
					gameWinB++;
					userB.setNumWon(gameWinB); //renew numWin for playerB
				}
				else {
					System.out.print(userA.getGivenName()
							+ " "+ userA.getFamilyName()+ " wins!\n");	
					gameWinA++;
					userA.setNumWon(gameWinA);//renew numWin for playerA
				}
				gamePlayedA++;
				userA.setNumPlayed(gamePlayedA);//renew numPlayed for playerA
				gamePlayedB++;
				userB.setNumPlayed(gamePlayedB);//renew numPlayed for playerB
				break;
			}
			setCurrentStone(numOfStone);
			play = !play;
		}
		
	}
}
