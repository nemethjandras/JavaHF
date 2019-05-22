package display;

import mygame.*;
import display.*;
import control.*
;

public class Main {

	public static void main(String[] args) 
	{

		initService initData=new initService("small");
		Game gameData=new Game(initData);
	
		GameWindow MainWindow=new GameWindow(900,900,400,gameData.map);	
		MainWindow.createGui();
		MainWindow.displayMap();
		//playerOne >> player, playTwo >> enemy
		MainWindow.control=new Control(gameData.playerOne,gameData.playerTwo, gameData.map);
		MainWindow.updateMoneyDisplay();
		
		MainWindow.sandbox_mode=true;

	}

}  