package src;
import java.net.*;
import java.io.*;
enum ConnectionType 
{
 Password_Fail,
 Password_Ok,
 Connected,
 Connected_Win,
 Not_Connected
}
public class Network {
	protected Socket socket = null;
	protected ObjectOutputStream outputStream = null;
	protected ObjectInputStream inputStream =null;
	protected int port;
	protected Inet4Address ip;
	protected BufferedReader in = null;
	protected PrintWriter out = null;
	protected NetworkData sendData;
	protected NetworkData incomData;
	protected boolean Won;
	protected int timeout;
	
	public boolean isClosed() {
		  return socket.isClosed();
		 }
 	public boolean isBound() {
		  return socket.isBound();
		 }
	public NetworkData getSending() {
		return sendData;
	}
	public void setSending(NetworkData sending) {
		this.sendData = sending;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public NetworkData getIncoming() {
		return incomData;
	}
	public void setIncoming(NetworkData incoming) {
		this.incomData = incoming;
	}
	 public String getLocalAddress() {
		  return socket.getInetAddress().getHostAddress();
		 }
	 public int getPort() {
		  System.out.println("Sending port info->");
		  return port;
	 }
	 public void setPort(int port) {
			this.port = port;
		}
	 public BufferedReader getInputStream() {
		  return in;
		 }
	 public PrintWriter getOutputStream() {
		    return out;
		 }
	 public void setWon(boolean Won)
	 {
		 this.Won = Won;
	 }
	 public boolean isWon() {
		 return Won;
	 }
	 public void sending (NetworkData sendData)
	 {
	 try {
	 System.out.println("Normál mûködés elkezdõdött szerver oldalon!");
	 ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
	 if(sendData != null)
	 {
	 if(this.Won != true)
	 {
	 outputStream.writeObject(sendData);
	 }
	 else
	 {
		 System.out.println("The game is already won");
	 }
	 }
	 else
	 {
		 System.out.println("A küldendõ csomag null");
	 }
		 
	 }
		 catch (IOException i) 
		 {
			   System.out.println(i);
		 }
		 

	 }
	 public NetworkData incoming ()
	 { 
		 try {
			 
			    NetworkData data = null;
				ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
				data =((NetworkData) inputStream.readObject());
				if(data != null)
				{
					incomData = data;
					this.Won = incomData.Won;
				}
				else
				{
					System.out.println("A kapott csomag null");
				}
		         }
				 catch(IOException i) 
			     { 
			         System.out.println(i); 
			     } catch (ClassNotFoundException e) {
					e.printStackTrace();
			     }
		 return  incomData;
	 }
	//Minden kezdeti kapcsolattfelépítéshez szükséges adat csatornát becsukja
	 public void closing_datachannels() {
	  try {
	   // close connection
	   if ( in != null || inputStream != null) {
	    System.out.println("Data input buffers are closing!"); 
	    in.close();
	   } else {
	    System.out.println("Data input buffers are already closed!");
	   }
	   if (out != null ||outputStream != null ) {
	    System.out.println("Data output buffers are closing!");
	    out.flush();
	    out.close();
	   } else {
	    System.out.println("Data output buffers are already closed!");
	   }


	  } catch (IOException i) {
	   System.out.println(i);
	  }
	 }
	 public void closing_all()
	    {
	    	try {
	    	if (socket !=null)
	    	{
	    		if(outputStream != null) {
	    	outputStream.flush();
		    outputStream.close();
	    		} 
	    		if(inputStream != null) {
		    inputStream.close();
	    		} 
	    	socket.close();
	    	} 
	    }
	    	 catch(IOException i) 
	        { 
	            System.out.println(i); 
	        }
	       }
}
