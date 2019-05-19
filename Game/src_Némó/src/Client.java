// A Java program for a Client 
import java.net.*; 
import java.io.*; 
  
public class Client extends Network
{ 
	public Client(String address, int port) 
    { 
        try
        { 
        	Inet4Address ip = (Inet4Address) Inet4Address.getByName(address);
            socket = new Socket(ip, port);
            System.out.println("Connected"); 
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println(u); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        }
       
    }
    public void SendingPassword (int Password)
    { 
    	if (socket.isConnected())
    	{ 
        System.out.println("Sending password");  
        out.println(Integer.toString(Password));
    	}
    	else
    	{
    		System.out.println("Client isnt connected"); 
    	}
     
  
   }
public void AckCheck()
{ 
	try   {
	   if (in!= null)
	   {
	   in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	   String ack= in.readLine();
	   if (ack.compareTo("ACK") == 0)
	   {
		   System.out.println("Client side ACK arrived");
		   out.println(ack);
	   }
	   }
	}
	 catch(IOException i) 
     { 
         System.out.println(i); 
     }
} 
    @Override
    public int getPort()
    {
    	return socket.getLocalPort();
    	
    }
} 