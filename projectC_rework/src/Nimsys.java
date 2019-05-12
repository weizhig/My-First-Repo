
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Nimsys {
	public static Scanner gamePlay;
	public NimGame game;
	private int zero=0;
	public NimPlayer aiPlayer;
	public NimHumanPlayer humanPlayer;
	TreeMap<String, NimPlayer> players = new TreeMap<String, NimPlayer>();
	
	public static void main(String[] args){
		
		Scanner gamePlay = new Scanner(System.in);
		String inputParse,command,parameter;
		Nimsys gameSys = new Nimsys();
		gameSys.readDataFile();
		System.out.println("Welcome to Nim\n");
		while (true){
			
			System.out.print("$");
			inputParse = gamePlay.nextLine();
			String[] input = inputParse.split("\\s");
			command = input[0];
			if(input.length==1)
				parameter = "";
			else
				parameter = input[1];
				gameSys.commands(command,parameter,gamePlay);
			System.out.print("\n");
			
		}
	}
	
	//commands
	public void commands(String command,String parameter,
			Scanner gamePlay){
		try{
			switch (command){
			case "startgame":
				startGame(parameter,gamePlay);
				break;
			case "addplayer":
				addPlayer(parameter);
				break;
			case "removeplayer":
				removePlayer(parameter,gamePlay);
				break;
			case "editplayer":
				editPlayer(parameter);
				break;
			case "resetstats":
				resetPlayer(parameter,gamePlay);
				break;
			case "displayplayer":
				displayPlayer(parameter);
				break;
			case "rankings":
				rankings(parameter,gamePlay);
				break;
			case "addaiplayer":
				addAIPlayer(parameter);
				
				break;
			case "exit":
				createDataFile();
				System.out.print("\n");
				System.exit(0);
				break;
			default:
				throw new WrongCommandException(command);
			}
		}
		catch(WrongCommandException e)
		{
			System.out.println("'"+e.getMessage()+"'"+" is not a valid command.");
		}
	}
	
	//create data file
	public void createDataFile(){
		int index =0;
		NimPlayer[] playerInfo=new NimPlayer[players.size()];
		NimPlayer player = new NimHumanPlayer();
		try
	      {
	         PrintWriter outputStream =
	             new PrintWriter(new FileOutputStream("players.dat"));
	         for (Map.Entry<String,NimPlayer> m:players.entrySet()){
	        	 playerInfo[index] = m.getValue();
	        	 index++;
	         }
	         for (int i = 0; i<index; i++){	
				playerInfo[i]=playerInfo[i];
				player = playerInfo[i];
				String[] words = new String[index];
				words[i]=player.toString();
				outputStream.println(words[i]);
	         }
	         outputStream.close( );
	      }
	      catch(IOException e)
	      {
	      }
		
	}
	
	//read exist Data file
	public void readDataFile(){
		 int maxNumPlayers=100;
		 String[] words = new String[maxNumPlayers];
		 String input;
		 try
		 {
			 BufferedReader inputStream = 
					 new BufferedReader(new FileReader("players.dat"));
			 for (int i=0;i<maxNumPlayers;i++){
				words[i] = inputStream.readLine();
				if(words[i] == null){
					break;
				}
			 	input = words[i];
			 	String[] inputs = input.split(",");
			 	String uName = inputs[0];
			 	String gName = inputs[1];
			 	String fName = inputs[2];
			 	int numPlayed = Integer.parseInt(inputs[3]);
			 	int numWon = Integer.parseInt(inputs[4]);
			 	if (inputs[5].equals("true")){
			 		
			 		NimPlayer aiPlayer = 
			 				new NimAIPlayer(uName,gName,fName,numPlayed,numWon);
			 		players.put(uName, aiPlayer);
			 	}
			 	if (inputs[5].equals("false")){
			 		NimPlayer humanPlayer = 
			 				new NimHumanPlayer(uName,gName,fName,numPlayed,numWon);
			 		players.put(uName, humanPlayer);
			 	}
			 	
			 }
			 inputStream.close( );
	    }
	    catch(FileNotFoundException e){}
	    catch(IOException e){}	
	}
	
	//throw StringIndexOutOfBoundsException
	public void invalidInputException(String parameter,int size)
			throws StringIndexOutOfBoundsException{
		String[] input = parameter.split(",");
		if (input.length!=size)
			throw new StringIndexOutOfBoundsException("Incorrect "
					+ "number of arguments supplied to command.");
	}
	
	//method for command: startgame
	public void startGame(String parameter, Scanner gamePlay){
		String setInitStone,setUppBound;
		int getInitStone,getUppBound;
		String usernameA,usernameB;
		NimPlayer playerA,playerB;
		int size = 4;
		try{
			String[] input = parameter.split(",");
			invalidInputException(parameter,size);
			setInitStone = input[0];
			//obtain initial number of Stone
			getInitStone = Integer.parseInt(setInitStone); 	
			setUppBound = input[1];
			//obtain upper bound removal
			getUppBound = Integer.parseInt(setUppBound); 			
			usernameA = input[2];
			//String: 1st User			
			usernameB = input[3];
			//String: 2nd User
			if ((isValidName(usernameA) == false) || 
					(isValidName(usernameB) == false)){
				System.out.println("One of the players "
						+ "does not exist.");
				return;
			}
			aiPlayer = new NimAIPlayer();
			if(players.get(usernameA).getClass().equals(aiPlayer)){
				playerA = new NimAIPlayer();
			}
			else if(players.get(usernameB).getClass().equals(aiPlayer)){
				playerB = new NimAIPlayer();
			}
			else {
				playerA = new NimHumanPlayer();
				playerB = new NimHumanPlayer();
			}
			playerA = players.get(usernameA);
			playerB = players.get(usernameB);
			game = new NimGame();
			game.gamePlayMechanics(getInitStone,getUppBound,playerA, 
									playerB,gamePlay);
		}catch(StringIndexOutOfBoundsException e)
		{
			String message = e.getMessage();
			System.out.println(message);
		}

	}
	
	//method for addAIplayer
	public void addAIPlayer(String parameter){
		String uname,fname,gname;
		int size = 3;
		try{
			String[] input = parameter.split(",");
			invalidInputException(parameter,size);
			uname = input[0];
			//obtain username of the player
			if(isValidName(uname) == true){
				System.out.println("The player already exists.");
				return;
			}
			
			fname = input[1];
			//obtain family name of the player
			gname = input[2];
			//obtain given name of the player
			NimPlayer player = new NimAIPlayer();
			player.setPlayerInfo(uname, gname, fname);
			player.isAI = "true";
			players.put(uname,player);
			//add player into the game
			
		}catch(StringIndexOutOfBoundsException e)
		{
			String message = e.getMessage();
			System.out.println(message);
		}
	}
	
	//method for command: addplayer
	public void addPlayer(String parameter) 
	{
		String uname,fname,gname;
		int size = 3;
		try{
			String[] input = parameter.split(",");
			invalidInputException(parameter,size);
			uname = input[0];
			//obtain username of the player
			if(isValidName(uname) == true){
				System.out.println("The player already exists.");
				return;
			}
			
			fname = input[1];
			//obtain family name of the player
			gname = input[2];
			//obtain given name of the player
			NimPlayer player = new NimHumanPlayer();
			player.setPlayerInfo(uname, gname, fname);
			player.isAI = "false";
			players.put(uname,player);
			//add player into the game
			
		}catch(StringIndexOutOfBoundsException e)
		{
			String message = e.getMessage();
			System.out.println(message);
		}
	}
	
	//method for command: removePlayer
	public void removePlayer(String parameters,Scanner gamePlay){ 
		//remove player from the game{

		String uname;
		boolean yes;
		if (parameters.length()!=zero){
			uname = parameters;
			if (isValidName(uname) == false){
				System.out.println("The player does not exist.");
				return;
			}
			
			players.remove(uname);
			//remove the chosen player from the game
		}
		else {
			System.out.println("Are you sure you want"
						+" to remove all players? (y/n)");
			yes = gamePlay.nextLine().charAt(0) == 'y'?true:false;
			if (yes){
			players.clear();
			//remove all the player from the game
			}
		}
	}
	//method for command: editPlayer; 
	//only able to change family name and given name
	public void editPlayer(String parameter){
		//edit player
		String uname,newFname,newGname; 
		
		int size = 3;
		try{
			String[] input = parameter.split(",");
			invalidInputException(parameter,size);
			uname = input[0];
			if (isValidName(uname) == false){
				System.out.println("The player does not exist.");
				return;
			}
			newFname = input[1];
			newGname = input[2];
			NimPlayer player = new NimHumanPlayer();
			player.setPlayerInfo(uname,newGname, newFname);
			players.put(uname,player);
		}catch(StringIndexOutOfBoundsException e)
		{
			String message = e.getMessage();
			System.out.println(message);
		}	
	}
	
	//method for command: resetPlayer
	public void resetPlayer(String parameters,Scanner gamePlay){
		int flag;
		String uname;
		boolean yes;
		
		if (parameters.length()!=zero){
			for(flag = zero;flag< parameters.length();flag++){
				if(parameters.charAt(flag) == ' '){
					break;
				}
			}
			uname = parameters.substring(zero, flag);
			if (isValidName(uname) == false){
				System.out.println("The player does not exist.");
				return;
			}
			NimPlayer player = new NimHumanPlayer();
			player = players.get(uname);
			player.setNumPlayed(zero);
			player.setNumWon(zero);
			//only zero the chosen one
		}
		else {
			System.out.println("Are you sure you want to reset "
					+ "all player statistics? (y/n)");
			
			yes = gamePlay.nextLine().charAt(0) == 'y'?true:false;
			
			if (yes){
				for(Map.Entry<String,NimPlayer> entry:players.entrySet()){
					entry.getValue().setNumPlayed(zero);
					entry.getValue().setNumWon(zero);
					//zeroing all int parameters inside each player in the map
				}
			}
		}
	}
	
	//method for command: displayplayer
	public void displayPlayer(String parameters){
		String uname;//username of the player
		if (parameters.length()!=zero){
			
			uname = parameters;
			if (isValidName(uname) != true){
				System.out.println("The player does not exist.");
				return;
			
			}
			System.out.println(players.get(uname).displayGameInfo());
		}
		else if (parameters==""){
			 for(Map.Entry<String,NimPlayer> m:players.entrySet()){
				
				 System.out.print(m.getValue().displayGameInfo());
				 System.out.print("\n");
			 }
		}
	}
	
	//method for command: rankings
	public void rankings(String parameters,Scanner gamePlay){
		int index=0;
		NimPlayer player = new NimHumanPlayer();
		NimPlayer[] playerInfo=new NimPlayer[players.size()];
		int[] w = new int[10];
		if ((parameters.length()==4)||(parameters=="")){
			//assumed that only 4 characters 'desc' will be inserted
			//rank descendingly
			for (Map.Entry<String,NimPlayer> m:players.entrySet()){
				
				playerInfo[index] = m.getValue();
                
                index++;
				
			}
			for (int i = 0; i<index; i++){
				w[i]=playerInfo[i].getNumGameWon();
				playerInfo[i]=playerInfo[i];
				
			}
			
			sortDescedingly(playerInfo);
			for (int i = 0; i<index; i++){
				int won = playerInfo[i].getNumGameWon();
				player = playerInfo[i];
				int rate = (int) Math.round(winRate(player,won));
				player.displayRanking(rate);
			}
		}
			
		else if(parameters.length()==3){
			//assumed that the insert value will only be 'asc' 
			//3 characters
			//ranking ascedingly mode
			for (Map.Entry<String,NimPlayer> m:players.entrySet()){
				
				playerInfo[index] = m.getValue();
                
                index++;
				
			}
			for (int i = 0; i<index; i++){
				w[i]=playerInfo[i].getNumGameWon();
				playerInfo[i]=playerInfo[i];
				
			}
			
			sortAsecdingly(playerInfo);
			for (int i = 0; i<index; i++){
				int won = playerInfo[i].getNumGameWon();
				player = playerInfo[i];
				int rate = (int) Math.round(winRate(player,won));
				player.displayRanking(rate);
			}
		}
		
	}
	
	//check if the name is valid
	public boolean isValidName(String uName){
		if(players.get(uName) == null )
		{
			return false;
		}
		else
			return true;
	}
	
	//get the winning rate in %
   	public double winRate(NimPlayer winRatio,int numWin){
   		double numGamePlayed = winRatio.getNumGamePlayed();
   		return ((numWin*100)/numGamePlayed); 
	}
   	
   	//method to sort the winning rate descendingly
   	public void sortDescedingly(NimPlayer[] player){
   		double[] playerWin = new double[players.size()];
   		for(int i = 0; i<player.length;i++){
   			int mostWin = i;
   			
   			for (int j=mostWin+1; j<player.length;j++){
   				
   				playerWin[j] = player[j].getGameWinRatio();
   				if(playerWin[j]> playerWin[mostWin]){
   					mostWin = j;
   				}
   			}
   			NimPlayer tmp = player[i];
   			player[i] = player[mostWin];
   			player[mostWin] = tmp;
   		}
   	}
   	//method to sort the winning rate asecendingly
   	public void sortAsecdingly(NimPlayer[] player){
   		double[] playerWin = new double[players.size()];
   		for(int i = 0; i<player.length;i++){
   			int leastWin = i;
   			
   			for (int j=leastWin+1; j<player.length;j++){
   				
   				playerWin[j] = player[j].getGameWinRatio();
   				if(playerWin[j]< playerWin[leastWin]){
   					leastWin = j;
   				}
   			}
   			NimPlayer tmp = player[i];
   			player[i] = player[leastWin];
   			player[leastWin] = tmp;
   		}
   	}
   
	
}
