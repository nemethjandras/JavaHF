//Példa a mûködésre
class Main 
{ 
public static void main(String args[]) 
{
	
	  
	
	   int [] hello ={10, 20, 30, 40};
	   int password = 2300;
	   String url = "localhost";
	   Server servero = new Server(url,6736,password,8);
	   Client cliento = new Client(url,6736);
	   servero.listening();
	   System.out.println(servero.getFlag());
	   cliento.SendingPassword(password);
	   servero.password_check();
	   System.out.println(servero.getFlag());
	   cliento.AckCheck();
	   servero.datatransfer_check();
	   servero.normal_datatransfer_to_client(hello);
	   cliento.normal_datatransfer_from_server();
	   cliento.normal_datatransfer_to_server(hello);
	   servero.normal_datatransfer_from_client();
	   servero.checkingFlag();
	   servero.closing_all();
	   cliento.closing_all();
}
	
	 
}