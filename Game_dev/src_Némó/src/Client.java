// A Java program for a Client 
import java.net.*; 
import java.io.*; 
  
public class Client extends Network
{ 
	public Client(String address, int port) 
    { 
        	try {
				this.ip = (Inet4Address) Inet4Address.getByName(address);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	this.port =port;
            this.Won = false;
       
            
    }
    
	public boolean starting()
	 {
		
	 boolean result = false;
	 try
    { 
        socket = new Socket(this.ip, this.port);
        if(socket.isBound() == false)
   	 { 
   		 result = true;
   	 }
        if(socket == null)
        { 
        	result = true;
        }
        System.out.println("Connected"); 
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    } 
    catch(IOException i) 
    { 
        System.out.println(i); 
        result = true;
    }
	
	 return result;
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
    public int getClientPort()
    {
    	return socket.getLocalPort();
    	
    }
} 