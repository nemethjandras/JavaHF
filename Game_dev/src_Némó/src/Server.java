package src;
import java.net.*;
import java.io.*;

//Not_Connected = egy�rtelm�
//Password_fail = rossz a jelsz�, 3 prob�lkoz�st enged, ut�na eldobj a kapcsolatot
//Password_ok = j� a jelsz� de m�g megy egy ACK k�r -> szerver k�ld egy ACK-t kliens megkapja �s visszak�ldi,
//ha ez sikeresen a szerverhez jut, akkor ker�l�nk ebbe az �llapotba, ekkor lehets�ges elind�tani a t�nyleges adatk�ld�st
//,ez�ltal mind a kett� f�l tudja mikor tol j�n a hasznos adat �s nem lesz para
// frissiteni nagy es�lyel nem kell csak k�r�nk�nt sz�val arra kell majd nekem egy flag ha �j k�r 
//Connected = sikeres volt az ACK k�r, j�  kapcsolat mehet a j�t�k
//Connected_Win = valamelyik f�l megnyerte a j�t�kot �s a kapcsolatot el kell dobni 



public class Server extends Network{
 private int backlog;
 private ConnectionType flag;
 private ServerSocket server = null;
 private int password;
 private int counter =0;
 
 
 public Server(String ip, int port, int password, int backlog) {
  this.flag = ConnectionType.Not_Connected;
  this.Won = false;
  this.port =port;
  this.backlog=backlog;
  try {
   this.ip = (Inet4Address) Inet4Address.getByName(ip);
  } catch (UnknownHostException e) {
   e.printStackTrace();
  }
  this.password = password;
 }
 
 public boolean starting() 
 {
   boolean result = false;
 try {
	   server = new ServerSocket(this.port, this.backlog, this.ip);
	   System.out.println("Server started");
	  } catch (IOException i) {
	   result =true;
	   System.out.println(i);
	  }
 return result;
 }
 @Override
 public void sending (NetworkData sendData)
 {
 try {
 //System.out.println("Norm�l m�k�d�s elkezd�d�tt szerver oldalon!");
 ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
 if(flag == ConnectionType.Connected )
 {
	 if (sendData != null)
	 {
	 if (this.Won != true)
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
	 System.out.println("A k�ldend� csomag == null");
	 }
	 
 }
 }
	 catch (IOException i) 
	 {
		   System.out.println(i);
	 }
	 

 }
@Override
 public NetworkData incoming ()
 { 
	 if(flag == ConnectionType.Connected)
	 {
	 try {
		   NetworkData data = null;
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			data =((NetworkData) inputStream.readObject());
			if(data != null)
			{
				incomData = data;
				this.Won = incomData.Won;
				if(Won == true)
				{
					flag =  ConnectionType.Connected_Win;
				}
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
	 }
	 return  incomData;
	 }
 //ACK k�r lecsekkol�sa
 public void datatransfer_check() {
  try {
   if (flag == ConnectionType.Password_Ok || flag == ConnectionType.Connected) {
    if (!socket.isClosed()) {
     String reading = in .readLine();
     if (reading.compareTo("ACK") == 0) {
      System.out.println("ACK arrived");
      System.out.println("Data exchange can be started");
      flag = ConnectionType.Connected;
     } 
     else {
      in.close();
      socket.close();
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
//Jelsz� lecsekkol�sa -> nincs kik�tve egyel�re milyen hossz� lehet (sima int)
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
 //V�rja a kliens v�lasz�t -> egyel�re egy klienst tud fogadni �s kiszolg�lni -> ha kell threadezhetek 
 public void listening() {

  try {
   if (server != null  && flag == ConnectionType.Not_Connected) {
    System.out.println("Server is runnning");
    System.out.println("Waiting for a client ...");
    System.out.println("Listening");
    socket = server.accept();
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    out = new PrintWriter(socket.getOutputStream(), true);
   } else {
    System.out.println("Server is closed !");
    this.closing_datachannels();
   }
  } catch (IOException i) {
   System.out.println(i);
  }
 }
//mindent adat�raml�st megsz�ntet
 @Override
 public void closing_all() {
  try {
	  
    if(outputStream != null) {
	    	outputStream.flush();
		    outputStream.close();
	    		} 
	if(inputStream != null) {
		    inputStream.close();
	}
   if (server !=null) {
    System.out.println("Server is closing!");
    server.close();
    flag = ConnectionType.Not_Connected;
    this.closing_datachannels();
   } else {
    System.out.println("Server is already closed!");
   }
   if (socket != null) {
    System.out.println("Accepted client is closing!");
    flag = ConnectionType.Not_Connected;
    socket.close();
   } else {
    System.out.println("Accepted client is already closed!");
   }
  } catch (IOException i) {
   System.out.println(i);
  }
 }
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
	 if (socket.isClosed())
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
		 if (flag != ConnectionType.Connected_Win && Won == true )
		 {   
			 System.out.println("6. Win condition arised, flag state was"+flag.toString());
			 flag = ConnectionType.Connected_Win;
		 }
		 if (flag == ConnectionType.Connected_Win && Won == false )
		 {   
			 System.out.println("7. Win condition is false, flag state was"+flag.toString());
			 if(!server.isClosed())
			 { 
			 flag =ConnectionType.Connected;
			 }
		 }
		 if (flag == ConnectionType.Connected_Win && Won == true && !server.isClosed())
		 {   
			 System.out.println("8. Win condition arised,but server is still running: flag state was"+flag.toString());
			 flag = ConnectionType.Not_Connected;
			 this.closing_all();
		 }
		 
			 
	 }
	 }

 }
 @Override
 public String getLocalAddress() {
  return server.getInetAddress().getHostAddress();
 }
@Override
 public int getPort() {
  System.out.println("Sending port info->");
  return server.getLocalPort();
 }
 public int getPassword() {
  System.out.println("Sending password info ->");
  return password;
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
 
 @Override
 public boolean isClosed() {
	  return server.isClosed();
	 }
@Override
public boolean isBound() {
	  return server.isBound();
	 }
public int getBacklog() {
	return backlog;
}
}