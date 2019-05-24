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
		int port =  5000;
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
			NetworkData sending = new NetworkData(gameData.map,gameData.playerOne,gameData.playerTwo,true,false);
			servero.sending(sending);
		}
		}
		else 
		{
			//get init game data from host: map, mapSize, start gold
			initData=new initService(launcherWindow.mapSize);
			gameData=new Game(initData);
			incoming =cliento.incoming();
			gameData.map = incoming.map;
			gameData.playerOne=incoming.playerOne;
			gameData.playerTwo=incoming.playerTwo;
             
		}

		//OPEN GAME WINDOW
		GameWindow mainWindow=new GameWindow(900,900,400,gameData.map);

		
		if(launcherWindow.start_sandbox || launcherWindow.start_hosting) 
		{
			mainWindow.control=new Control(gameData.playerOne,gameData.playerTwo, gameData.map);
			mainWindow.createGui();
			mainWindow.displayMap();
		}
		else 
		{
			mainWindow.control=new Control(gameData.playerTwo,gameData.playerOne, gameData.map);
			mainWindow.createGui();
			mainWindow.displayMap();
			mainWindow.endTurn();
		}
		
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
				while(!mainWindow.onTurn)
				{
				  incoming = servero.incoming();
				  mainWindow.control.player =incoming.playerOne;
				  mainWindow.control.enemy =incoming.playerTwo;
				  
				  //System.out.format("Turns: %b %b \n",mainWindow.onTurn,incoming.EndTurn);
				  
				  mainWindow.displayUpdate();
				  mainWindow.updateMoneyDisplay();
				  mainWindow.updateBaseHpDisplay();
				  if(!mainWindow.onTurn && !incoming.EndTurn) 
				  {
					  mainWindow.startTurn();
				  }
				  if(incoming.Won) 
				  {
					  mainWindow.defeat();
					  break;
				  }
					
				}
				
				
				while(mainWindow.onTurn)
				{
				 try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				  NetworkData sending = new NetworkData(gameData.map,mainWindow.control.player, mainWindow.control.enemy,mainWindow.onTurn,mainWindow.win);
				  servero.sending(sending);
				}
				  NetworkData sending = new NetworkData(gameData.map,mainWindow.control.player, mainWindow.control.enemy,mainWindow.onTurn,mainWindow.win);
				  servero.sending(sending);
				

			}
			else if(launcherWindow.start_client)
			{
				while(!mainWindow.onTurn)
				{
				  incoming = cliento.incoming();
				  mainWindow.control.player =incoming.playerTwo;
				  mainWindow.control.enemy =incoming.playerOne;
				  
				  System.out.format("Turns: %b %b \n",mainWindow.onTurn,incoming.EndTurn);
				  
				  mainWindow.displayUpdate();
				  mainWindow.updateMoneyDisplay();
				  mainWindow.updateBaseHpDisplay();
				  if(!mainWindow.onTurn && !incoming.EndTurn) 
				  {
					  mainWindow.startTurn();
				  }
			  	  if(incoming.Won) 
			  	  {
			  		  mainWindow.defeat();
			  	  }
				}
				
				while(mainWindow.onTurn)
				{
				 try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				  NetworkData sending = new NetworkData(gameData.map,mainWindow.control.enemy, mainWindow.control.player,mainWindow.onTurn,mainWindow.win);
				  cliento.sending(sending);
				}
				  NetworkData sending = new NetworkData(gameData.map,mainWindow.control.enemy, mainWindow.control.player,mainWindow.onTurn,mainWindow.win);
				  cliento.sending(sending);
				

				
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