
public class WrongCommandException extends Exception{
	 public WrongCommandException( )
	    {
	        super("WrongCommandException"); 
	    }

	    public WrongCommandException(String message)
	    {
	        super(message); 
	    }
	    /*if((command!="startgame")||(command!="addplayer")||(command!="removeplayer")
						||(command!="editplayer")||(command!="resetstats")||(command!="ranking")
						||(command!="displayplayer")||(command!="exit"))
					throw new WrongCommandException(command);*/
}
