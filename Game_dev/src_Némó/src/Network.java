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
	protected boolean YourTurn=true;;
	protected boolean Won;
	
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
		  return socket.getLocalPort();
	 }
	 public BufferedReader getInputStream() {
		  return in;
		 }
	 public PrintWriter getOutputStream() {
		    return out;
		 }
	 public void setYourTurn(boolean YourTurn)
	 {
		 this.YourTurn = YourTurn;
	 }
	 public boolean isYourTurn() {
		 return YourTurn;
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
	 if (YourTurn == true) {
	 ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
	 outputStream.writeObject(sendData);
		
	 }
	 else {
		 System.out.println("Its not the Client turn");
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
			 if (YourTurn == true) {
				ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
				incomData =((NetworkData) inputStream.readObject());
				
			 }
			 else {
				 System.out.println("Its the Client turn , data  isnt coming");
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
	    	if (!socket.isClosed())
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
