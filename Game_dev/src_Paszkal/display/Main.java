package display;

import mygame.*;
import src.*;
import display.*;

import java.util.concurrent.TimeUnit;

import control.*;

public class Main {

	public static void main(String[] args)
	{
		boolean repeate=true;
		int password = 2300;
		String url = "25.39.18.11";
		int port =9000;
		while(repeate)
		{


		//LAUNCHER WINDOW
		Launcher launcherWindow=new Launcher(500,300);
		launcherWindow.setButtons();
		
		while(!launcherWindow.start_sandbox && !launcherWindow.start_hosting && !launcherWindow.start_client) 
		{
			System.out.format("%b\n", launcherWindow.start_sandbox);
		}
		
		//BUILD UP CONNECTION
		if (launcherWindow.start_hosting) 
		{
			boolean server_error;
			Server servero = new Server(url,port,password,1);
			server_error =servero.starting();
			   while (server_error == true)
			   {
			     port = port +1;
				 servero.setPort(port);
				 server_error =servero.starting();
			   }
			   servero.listening();
			   servero.password_check();
			   servero.datatransfer_check();
			
			
		}
		if (launcherWindow.start_client) 
		{
			  
			 boolean client_error;
			 Client cliento = new Client(url,port);
			 client_error=cliento.starting();
			   while (client_error ==true)
			   {
				   System.out.println(cliento.getPort());
				   port = port +1;
				   cliento.setPort(port);
				   client_error =cliento.starting();
			   }
			   cliento.SendingPassword(password);
			   cliento.AckCheck();
		}
		
		
		//GAME DATA INITIATION
		initService initData=null;
		Game gameData=null;
		if(launcherWindow.start_sandbox || launcherWindow.start_hosting) 
		{
			initData=new initService(launcherWindow.mapSize);
			gameData=new Game(initData);
			gameData.playerOne.gold=Integer.parseInt(launcherWindow.startGold);
			gameData.playerTwo.gold=Integer.parseInt(launcherWindow.startGold);
		}
		else 
		{
			//get init game data from host: map, mapSize, start gold

		}

		//OPEN GAMW WINDOW
		GameWindow mainWindow=new GameWindow(900,900,400,gameData.map);
		
		if(launcherWindow.start_sandbox || launcherWindow.start_hosting) 
		{
			mainWindow.control=new Control(gameData.playerOne,gameData.playerTwo, gameData.map);
		}
		else 
		{
			mainWindow.control=new Control(gameData.playerTwo,gameData.playerOne, gameData.map);
			mainWindow.onTurn=false;
		}
		
		mainWindow.createGui();
		mainWindow.displayMap();
		mainWindow.updateMoneyDisplay();
		mainWindow.updateBaseHpDisplay();
		mainWindow.sandbox_mode=launcherWindow.start_sandbox;
		
		while(!mainWindow.win && !mainWindow.lose) {
			//real time update, watching flags and calling startTurn(), defeat() if needed
			//mainWindow.displayMap();
			//mainWindow.updateMoneyDisplay();
			//mainWindow.updateBaseHpDisplay();
		}
		//delay 3 sec
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		mainWindow.mainWindow.dispose();
		
	}
	}

}  