import java.io.Serializable;
import java.util.Arrays;
public class NetworkData implements Serializable {
	private static final long serialVersionUID = 1L;
	private int [] units;
	private int [] xpos;
	private int [] ypos;
	private int dimension;
	transient private Thread myThread;
	public NetworkData (int [] units,int[] xpos, int [] ypos,int dimension)
	{
		this.setUnits(units);
		this.setXpos(xpos);
		this.setYpos(ypos);
		this.setDimension(dimension);
		this.setMyThread(new Thread());
	}

	
	public void PrintState ()
	{
		System.out.println("Printing out the state of NetworkData(x,y,units)"); 
		System.out.println(Arrays.toString(xpos));
		System.out.println(Arrays.toString(ypos));
		System.out.println(Arrays.toString(units));
		
	}
	
	public int [] getUnits() {
		return units;
	}

	public void setUnits(int [] units) {
		this.units = units;
	}

	public int [] getXpos() {
		return xpos;
	}

	public void setXpos(int [] xpos) {
		this.xpos = xpos;
	}

	public int [] getYpos() {
		return ypos;
	}

	public void setYpos(int [] ypos) {
		this.ypos = ypos;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public Thread getMyThread() {
		return myThread;
	}

	public void setMyThread(Thread myThread) {
		this.myThread = myThread;
	}
	
	
}
