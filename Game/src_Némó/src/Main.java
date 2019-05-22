

import java.util.concurrent.TimeUnit;
class Main 
{ 
public static void main(String args[]) 
{
	//initialize server / client
	  // ConnectionType  end = ConnectionType.Connected_Win;
	   int password = 2300;
	   String url = "localhost";
	   //Server servero = new Server(url,6747,password,8);
	   Client cliento = new Client(url,6748);
	   System.out.println(cliento.getPort());
	   //servero.listening();
	   //System.out.println(servero.getFlag());
	   cliento.SendingPassword(password);
	   //servero.password_check();
	  // System.out.println(servero.getFlag());
	   cliento.AckCheck();
	  // servero.datatransfer_check();
}
}
	 