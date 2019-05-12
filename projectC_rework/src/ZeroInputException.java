
public class ZeroInputException extends Exception{
	private int stoneNumber;
	public ZeroInputException( )
    {
        super("StoneInputExcetion"); 
    }

    public ZeroInputException(String message)
    {
        super(message); 
    }
    public ZeroInputException(int number){
    	stoneNumber = number;
    }
    public int getStoneNumber(){
    	return stoneNumber;
    }
    
}
