package src;
import java.io.Serializable;
import mygame.*;
public class NetworkData implements Serializable {
	private static final long serialVersionUID = 1L;
	//public Game Data;
	public Player playerOne;
	public Player playerTwo;
	public boolean EndTurn;
	public boolean Won;
	transient private Thread myThread;
	
	public NetworkData(Player playerOne,Player playerTwo,boolean EndTurn, boolean Won)
	{
		this.playerOne =playerOne;
		this.playerTwo =playerTwo;
		this.EndTurn=EndTurn;
		this.Won=Won;
		
	}
	
	
	
	public void PrintState ()
	{
		System.out.println("Printing out the state of NetworkData(Game Data, end turn , won)"); 
		
		
	}



	public Thread getMyThread() {
		return myThread;
	}



	public void setMyThread(Thread myThread) {
		this.myThread = myThread;
	}
	
	
	
}
