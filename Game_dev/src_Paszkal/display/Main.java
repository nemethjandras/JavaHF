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
		int port =  6000;
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
		
		int winFlag=0;
		int turnFlag=0;
		
		while(winFlag<=1 && !mainWindow.lose) {
			//real time update, watching flags and calling startTurn(), defeat() if needed
			//mainWindow.displayMap();
			//mainWindow.updateMoneyDisplay();
			//mainWindow.updateBaseHpDisplay();
			if( launcherWindow.start_hosting)
			{
				if(turnFlag <=1)
				{
				  NetworkData sending = new NetworkData(gameData,mainWindow.onTurn,mainWindow.win);
				  servero.sending(sending);
				  if(!mainWindow.onTurn) turnFlag++; 
				}
				else
				{
				  incoming = servero.incoming();
				  gameData =incoming.Data;
				  
				  mainWindow.displayUpdate();
				  mainWindow.updateMoneyDisplay();
				  mainWindow.updateBaseHpDisplay();
				  if(!mainWindow.onTurn && !incoming.EndTurn) 
				  {
					  mainWindow.startTurn();
					  turnFlag=0;
				  }
				  if(incoming.Won) mainWindow.defeat();
					
				}
			}
			else if(launcherWindow.start_client)
			{
				if(turnFlag<=1)
				{
				  NetworkData sending = new NetworkData(gameData,mainWindow.onTurn,mainWindow.win);
				  cliento.sending(sending);
				  if(!mainWindow.onTurn) turnFlag++; 
				}
				else
				{
				  incoming = cliento.incoming();
				  gameData =incoming.Data;
				  
				  System.out.format("Turns: %b %b \n",mainWindow.onTurn,incoming.EndTurn);
				  
				  mainWindow.displayUpdate();
				  mainWindow.updateMoneyDisplay();
				  mainWindow.updateBaseHpDisplay();
				  if(!mainWindow.onTurn && !incoming.EndTurn) mainWindow.startTurn();
				  {
					  mainWindow.startTurn();
					  turnFlag=0;
				  }
			  	  if(incoming.Won) mainWindow.defeat();
				}
				
			}
				
			if(mainWindow.win) winFlag++; 
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