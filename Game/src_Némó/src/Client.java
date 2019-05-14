// A Java program for a Client 
import java.net.*; 
import java.io.*; 
  
public class Client 
{ 
    private Socket socket            = null; 
    private BufferedReader in   = null; 
    private PrintWriter out     = null; 
  
	public Client(String address, int port) 
    { 
    	 
        // establish a connection 
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
	public void normal_datatransfer_to_server(int [] array)
	{
		 try {
		 System.out.println("Normál mûködés elkezdõdött kliens oldalon!");
		 ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			// if (kör vége) a szerver következik
			 outputStream.writeObject(array);
		}
		 catch (IOException i) 
		 {
			   System.out.println(i);
		 }
	}
	public void normal_datatransfer_from_server()
	 {
		try {
		ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
		int [] hello  = (int[]) inputStream.readObject();
		for (int i=0; i<hello.length; i++)
		{
			System.out.println(hello[i]); 
		}
		}
		 catch(IOException i) 
	     { 
	         System.out.println(i); 
	     } catch (ClassNotFoundException e) {
			e.printStackTrace();
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
    public void closing_all()
    {
    	try {
    	if (!socket.isClosed())
    	{
    	socket.close();
    	} 
    }
    	 catch(IOException i) 
        { 
            System.out.println(i); 
        }
       } 
    public boolean isClosed() {
    	  return socket.isClosed();
    	 }
    	 public boolean isBound() {
    	  return socket.isBound();
    	 }
    public String getLocalAddress() {
    	  return socket.getInetAddress().getHostAddress();
    	 }
} 