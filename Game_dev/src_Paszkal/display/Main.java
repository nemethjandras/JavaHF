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
		int port =  7000;
		Server servero = new Server(url,port,password,1);
		Client cliento = new Client(url,port);
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
		NetworkData incoming =null;
		if(launcherWindow.start_sandbox || launcherWindow.start_hosting) 
		{
			initData=new initService(launcherWindow.mapSize);
			gameData=new Game(initData);
			gameData.playerOne.gold=Integer.parseInt(launcherWindow.startGold);
			gameData.playerTwo.gold=Integer.parseInt(launcherWindow.startGold);
		if (launcherWindow.start_hosting)
		{
			NetworkData sending = new NetworkData(gameData,true,false);
			servero.sending(sending);
		}
		}
		else 
		{
			//get init game data from host: map, mapSize, start gold
			
			incoming =cliento.incoming();
			gameData = incoming.Data;
             
		}

		//OPEN GAME WINDOW
		GameWindow mainWindow=new GameWindow(900,900,400,gameData.map);
		
		if(launcherWindow.start_sandbox || launcherWindow.start_hosting) 
		{
			mainWindow.control=new Control(gameData.playerOne,gameData.playerTwo, gameData.map);
		}
		else 
		{
			mainWindow.control=new Control(gameData.playerTwo,gameData.playerOne, gameData.map);
			mainWindow.endTurn();
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
			if( launcherWindow.start_hosting)
			{
			if(mainWindow.onTurn == true)
			{
			  
			  gameData.playerTwo = mainWindow.control.enemy; 
			  gameData.playerOne =mainWindow.control.player;
			  gameData.map = mainWindow.control.map; 
			  NetworkData sending = new NetworkData(gameData,!mainWindow.onTurn,mainWindow.win);
			  servero.sending(sending);
			  mainWindow.displayMap();
			  mainWindow.updateMoneyDisplay();
			  mainWindow.updateBaseHpDisplay();
			  
			}
			else
			{
			  incoming = servero.incoming();
			  gameData =incoming.Data;
			  mainWindow.control.player = gameData.playerOne;
			  mainWindow.control.enemy = gameData.playerTwo;
			  mainWindow.control.map = gameData.map;
			  mainWindow.onTurn = incoming.EndTurn;
			  mainWindow.win = incoming.Won;
			  mainWindow.displayMap();
			  mainWindow.updateMoneyDisplay();
			  mainWindow.updateBaseHpDisplay();
			}
			}
			else
			{
				if(mainWindow.onTurn == true)
				{
				  gameData.playerTwo = mainWindow.control.player; 
				  gameData.playerOne =mainWindow.control.enemy;
				  gameData.map = mainWindow.control.map; 
				  NetworkData sending = new NetworkData(gameData,!mainWindow.onTurn,mainWindow.win);
				  cliento.sending(sending);
				  mainWindow.displayMap();
				  mainWindow.updateMoneyDisplay();
				  mainWindow.updateBaseHpDisplay();
				}
				else
				{
				  incoming = cliento.incoming();
				  gameData =incoming.Data;
				  mainWindow.control.player = gameData.playerTwo;
				  mainWindow.control.enemy = gameData.playerOne;
				  mainWindow.control.map = gameData.map;
				  mainWindow.onTurn = incoming.EndTurn;
				  mainWindow.win = incoming.Won;
				  mainWindow.displayMap();
				  mainWindow.updateMoneyDisplay();
				  mainWindow.updateBaseHpDisplay();
				}
				
			}
				
			  
		}
		//delay 3 sec
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cliento.closing_all();
		servero.closing_all();
		mainWindow.mainWindow.dispose();
		
	}
	}

}  