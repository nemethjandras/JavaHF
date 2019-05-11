package display;

import mygame.*;
import display.*;
 


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
		
		//usage:
		//test_map = map.grid[][]
		//x = map.xLen
		//y = map.yLen
		
		GameWindow MainWindow=new GameWindow(600,600,600);	
		MainWindow.displayMap(test_map,10,10);
	}

}  