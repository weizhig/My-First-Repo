
public class StoneInputException extends Exception{
	private int stoneNumber;
	public StoneInputException( )
    {
        super("StoneInputExcetion"); 
    }

    public StoneInputException(String message)
    {
        super(message); 
    }
    public StoneInputException(int number){
    	stoneNumber = number;
    }
    public int getStoneNumber(){
    	return stoneNumber;
    }
    
}
