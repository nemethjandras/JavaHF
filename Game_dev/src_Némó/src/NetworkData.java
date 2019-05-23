import java.io.Serializable;

public class NetworkData implements Serializable {
	private static final long serialVersionUID = 1L;
	Game Data;
	boolean EndTurn;
	boolean Won;
	transient private Thread myThread;
	
	public NetworkData(Game Data, boolean EndTurn, boolean Won)
	{
		this.Data =Data;
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
