package display;

import mygame.*;
import display.*;
import control.*
;

public class Main {

	public static void main(String[] args) 
	{
		//test input
		int x=10;
		int y=10;
		int[][] test_map=new int[10][10];
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				test_map[i][j]=0;
			}
		}
		
		
		int[] units=new int[2];
		units[0]=0;
		units[1]=0;
		int[] xpos=new int[2];
		xpos[0]=0;
		xpos[1]=4;
		int[] ypos=new int[2];
		ypos[0]=0;
		ypos[1]=4;
		int[] unit_num=new int[2];
		unit_num[0]=10;
		unit_num[1]=15;

		
		
		//players needed for control test
		//Player playe;
		//Player enemy;

		//height is prefered to be the multiple of 10
		GameWindow MainWindow=new GameWindow(900,900,400);	
		//usage:
		//test_map = map.grid[][]
		//x = map.xLen
		//y = map.yLen
		MainWindow.displayMap(test_map,x,y);
		MainWindow.createGui();
		//MainWindow.control=new Control(player,enemy);
		
		MainWindow.displayUpdate(units,xpos,ypos,unit_num,2);
		xpos[0]+=1;
		ypos[0]+=1;
		MainWindow.displayUpdate(units,xpos,ypos,unit_num,2);
	}

}  