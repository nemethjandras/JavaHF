import java.net.*;
import java.io.*;
//TODO nagy esélyel kelleni fog egy kis öröklés, hogy átláthatóbb legyen meg ne legyen sok duplikált függvény 
//TODO A timeoutokkal is kell még egy kicsit szórakozni 

//Not_Connected = egyértelmû
//Password_fail = rossz a jelszó, 3 probálkozást enged
//Password_ok = jó a jelszó de még megy egy ACK kör -> szerver küld egy ACK-t kliens megkapja és visszaküldi,
//ha ez sikeresen a szerverhez jut, akkor kerülünk ebbe az állapotba, ekkor lehetséges elindítani a tényleges adatküldést
//,ezáltal mind a kettõ fél tudja mikor tol jön a hasznos adat és nem lesz para
// frissiteni nagy esélyel nem kell csak körönként szóval arra kell majd nekem egy flag ha új kör 

//Connected = sikeres volt az ACK kör, jó  kapcsolat mehet a játék
//Connected_Win = valamelyik fél megnyerte a játékot és a kapcsolatot el kell dobni 

enum ConnectionType 
{
 Password_Fail,
 Password_Ok,
 Connected,
 Connected_Win,
 Not_Connected
}

public class Server {
 //initialize socket and input stream 
 private ConnectionType flag;
 @SuppressWarnings("unused")
private int port;
 //IPV4 address<-
 private Inet4Address ip;
 //requested maximum length of the queue of incoming connections
 private int backlog;
 private Socket acceptedSocket = null;
 private ServerSocket server = null;
 private BufferedReader in = null;
 private PrintWriter out = null;
 private int password;
 private int counter =0;
 
 
 public Server(String ip, int port, int password, int backlog) {
  this.flag = ConnectionType.Not_Connected;
  this.port =port;
  this.backlog=backlog;
  
  try {
   this.ip = (Inet4Address) Inet4Address.getByName(ip);
  } catch (UnknownHostException e) {
   e.printStackTrace();
  }
  this.password = password;
  try {
   server = new ServerSocket(port, backlog, this.ip);
   System.out.println("Server started");
  } catch (IOException i) {
   System.out.println(i);
  }


 }


 public NetworkData normal_datatransfer_from_client ()
 { 
	 NetworkData incoming = null;
	 if(flag == ConnectionType.Connected)
	 {
	 try {
			ObjectInputStream inputStream = new ObjectInputStream(acceptedSocket.getInputStream());
			incoming =((NetworkData) inputStream.readObject());
			
			
	     }
			 catch(IOException i) 
		     { 
		         System.out.println(i); 
		     } catch (ClassNotFoundException e) {
				e.printStackTrace();
		     }
	 }
	 return  incoming;
	 }
  
 public void normal_datatransfer_to_client (NetworkData send)
 {
 try {
 System.out.println("Normál mûködés elkezdõdött szerver oldalon!");
 ObjectOutputStream outputStream = new ObjectOutputStream(acceptedSocket.getOutputStream());
 if(flag == ConnectionType.Connected)
 {
	// if (kör vége) a kliens következik
	 outputStream.writeObject(send);
	
	 
 }
 }
	 catch (IOException i) 
	 {
		   System.out.println(i);
	 }
	 

 }
 //ACK kör lecsekkolása
 public void datatransfer_check() {
  try {
   if (flag == ConnectionType.Password_Ok || flag == ConnectionType.Connected) {
    if (!acceptedSocket.isClosed()) {
     String reading = in .readLine();
     if (reading.compareTo("ACK") == 0) {
      System.out.println("ACK arrived");
      System.out.println("Data exchange can be started");
      flag = ConnectionType.Connected;
     } 
     else {
      in.close();
      acceptedSocket.close();
      flag = ConnectionType.Not_Connected;
     }
    } else {
     flag = ConnectionType.Not_Connected;
     this.closing_all();

    }

   } else {
    flag = ConnectionType.Not_Connected;
    this.closing_datachannels();
    this.closing_all();

   }
  } catch (IOException i) {
   System.out.println(i);
  }
 }
//Jelszó lecsekkolása -> nincs kikötve egyelõre milyen hosszú lehet (sima int)
 public void password_check() {
  try {
   if (flag == ConnectionType.Not_Connected || flag == ConnectionType.Password_Fail ) 
   {
    System.out.println("Server is waiting for password");
    String reading = in.readLine();
    int client_password = Integer.parseInt(reading);
    if (password == client_password)
    {
     System.out.println("Password matched, connection established");
     flag = ConnectionType.Password_Ok;
     String ack = "ACK";
     out.println(ack.toString());
    } 
    else
    {
     counter = counter +1;
     int trying = 3 - counter;
     flag = ConnectionType.Password_Fail;
     System.out.println("Password doesnt match");
     System.out.println("You have " + trying + " remaining attempts!");
     if (counter == 3) 
     {
    	 flag = ConnectionType.Not_Connected;
    	 this.closing_all();
    	 counter = 0;
     }
    }

   }
   else
   {
    System.out.println("Server is closed!");
   }
  } catch (IOException i)
  {
   System.out.println(i);
  }
  
 }
 //Várja a kliens válaszát -> egyelõre egy klienst tud fogadni és kiszolgálni -> ha kell threadezhetek 
 public void listening() {

  try {
   if (!server.isClosed() && flag == ConnectionType.Not_Connected) {
    System.out.println("Server is runnning");
    System.out.println("Waiting for a client ...");
    System.out.println("Listening");
    acceptedSocket = server.accept();
    in = new BufferedReader(new InputStreamReader(acceptedSocket.getInputStream()));
    out = new PrintWriter(acceptedSocket.getOutputStream(), true);
   } else {
    System.out.println("Server is closed !");
    this.closing_datachannels();
   }
  } catch (IOException i) {
   System.out.println(i);
  }
 }
 //Minden kezdeti kapcsoaltfelépítéshez szükséges adat csatornát becsukja
 public void closing_datachannels() {
  try {
   // close connection
   if ( in != null) {
    System.out.println("Data input buffer is closing!"); 
    in .close();
   } else {
    System.out.println("Data input buffer is already closed!");
   }
   if (out != null) {
    System.out.println("Data output buffer is closing!");
    out.flush();
    out.close();

   } else {
    System.out.println("Data output buffer is already closed!");
   }


  } catch (IOException i) {
   System.out.println(i);
  }
 }
//mindent adatáramlást megszüntet
 public void closing_all() {
  try {
   if (!server.isClosed()) {
    System.out.println("Server is closing!");
    server.close();
    flag = ConnectionType.Not_Connected;
    this.closing_datachannels();
   } else {
    System.out.println("Server is already closed!");
   }
   if (acceptedSocket != null) {
    System.out.println("Accepted client is closing!");
    flag = ConnectionType.Not_Connected;
    acceptedSocket.close();
   } else {
    System.out.println("Accepted client is already closed!");
   }
  } catch (IOException i) {
   System.out.println(i);
  }
 }
 // hibaellenõrzés, ha Password_Fail van újra indítja a listeninget
 public void checkingFlag()
 {
	 System.out.println("Flag errors corrected:");
	 if (server.isClosed())
	 {
		if (flag != ConnectionType.Not_Connected)
	 {	
			System.out.println("1. Server was closed, flag state was"+flag.toString());
			flag = ConnectionType.Not_Connected;
			
	 }
	 }
	 if (acceptedSocket.isClosed())
	 {
			if (flag != ConnectionType.Not_Connected)
			 {	
				    System.out.println("2. Accepted socket was closed, flag state was" + flag.toString());
					flag = ConnectionType.Not_Connected;
					
			 }
	 if (counter > 0)
	 {
		if (flag != ConnectionType.Password_Fail)
		{
			System.out.println("3. Password failed, flag state was"+flag.toString());
			flag = ConnectionType.Password_Fail;
			
		}
		 if (counter > 3)
		 {
			if (flag != ConnectionType.Not_Connected)
			{
				System.out.println("4. Counter is overbufferd, flag state was"+flag.toString());
				flag = ConnectionType.Not_Connected;
			}
		}
		 if(flag == ConnectionType.Connected_Win && !server.isClosed())
		 {
			 System.out.println("5. Win condition arised,server still running, flag state was"+flag.toString());
			 flag = ConnectionType.Not_Connected;
			 this.closing_all();
		 }
	 }
	 }

 }
 public String getLocalAddress() {
  return server.getInetAddress().getHostAddress();
 }

 public int getPort() {
  System.out.println("Sending port info->");
  return server.getLocalPort();
 }
 public int getPassword() {
  System.out.println("Sending password info ->");
  return password;
 }
 public BufferedReader getInputStream() {
  return in;
 }
 public PrintWriter getOutputStream() {
    return out;
 }
 public void setPassword(int password) {
  System.out.println("Password has changed!");
  this.password = password;
 }
 public String getFlag() {
  return flag.toString();
 }
 public void setFlag(ConnectionType flag) {
  this.flag = flag;
 }
 public boolean isClosed() {
  return server.isClosed();
 }
 public boolean isBound() {
  return server.isBound();
 }


/*public void setPort(int port) {
	try {
		if(!this.server.isClosed())
		{
		this.server.close();
		}
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	this.port = port;
	try {
		this.server = new ServerSocket(this.port, backlog,this.ip);
	} catch (IOException e) {
		e.printStackTrace();
	}
	System.out.println("Port has changed -> server starting again");
	
}
*/	

public int getBacklog() {
	return backlog;
}


/*public void setBacklog(int backlog) {
	try {
		if(!server.isClosed())
		{
		server.close();
		}
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	this.backlog = backlog;
	try {
		server = new ServerSocket(this.port, backlog,this.ip);
	} catch (IOException e) {
		e.printStackTrace();
	}
	System.out.println("Backlog has changed -> server starting again");
}*/
}