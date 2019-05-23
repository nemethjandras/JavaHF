package display;

import mygame.*;
import display.*;
import control.*
;

public class Main {

	public static void main(String[] args) 
	{
		boolean repeate=true;
		
		while(repeate)
		{


		//LAUNCHER WINDOW
		Launcher launcherWindow=new Launcher(500,300);
		launcherWindow.setButtons();
		
		while(!launcherWindow.start_sandbox && !launcherWindow.start_hosting && !launcherWindow.start_client) 
		{
			System.out.format("%b\n", launcherWindow.start_sandbox);
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
		//playerOne >> player, playTwo >> enemy
		mainWindow.control=new Control(gameData.playerOne,gameData.playerTwo, gameData.map);
		mainWindow.createGui();
		mainWindow.displayMap();
		mainWindow.updateMoneyDisplay();
		mainWindow.updateBaseHpDisplay();
		mainWindow.sandbox_mode=launcherWindow.start_sandbox;
		
		while(!mainWindow.win && !mainWindow.lose) {
			//real time update, watching flags and calling startTurn(), defeat() if needed
		}
		
	}
	}

}  