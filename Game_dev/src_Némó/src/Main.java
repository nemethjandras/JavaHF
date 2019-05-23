package src;

//Példa a mûködésre
import java.util.concurrent.TimeUnit;
import src.*;
import display.*;
import mygame.Game;
import mygame.initService;
import control.*;

class Main 
{ 
public static void main(String args[]) 
{
	/*/initialize server / client
	  // ConnectionType  end = ConnectionType.Connected_Win;
	   int password = 2300;
	   String url = "25.39.18.11";
	   //Server servero = new Server(url,6747,password,8);
	   Client cliento = new Client(url,6782);
	   System.out.println(cliento.getPort());
	   //servero.listening();
	   //System.out.println(servero.getFlag());
	   cliento.SendingPassword(password);
	   //servero.password_check();
	  // System.out.println(servero.getFlag());
	   cliento.AckCheck();
	   servero.datatransfer_check();
	   */
    //initialize window
	   initService initData=new initService("small");
		Game gameData=new Game(initData);
		int xLen =11;
		int x = (int)(Math.random() * xLen);
		System.out.println(x);
		GameWindow MainWindow=new GameWindow(900,900,400,gameData.map);	
		MainWindow.createGui();
		MainWindow.displayMap();
		//playerOne >> player, playTwo >> enemy
		MainWindow.control=new Control(gameData.playerOne,gameData.playerTwo, gameData.map);
		MainWindow.updateMoneyDisplay();
		
		MainWindow.sandbox_mode=true;
	 		
	 }
	   
}
	
	 