package mygame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 initService initParam = new initService("test", "Aladar", "Bela");
		 Game theGame = new Game(initParam);
		 
		 //printing for test
		 String forPrint;
		 for(int i = 0; i < theGame.map.xLen; i++) {
	        	for(int j = 0; j < theGame.map.yLen; j++) {
	        		forPrint = Integer.toString(theGame.map.grid[i][j].xPos) + ',' + Integer.toString(theGame.map.grid[i][j].yPos);
	        		forPrint = forPrint + " - isBase:" + Boolean.toString(theGame.map.grid[i][j].isBase); 
	        		forPrint = forPrint + ", isAvailableForUnits:" + Boolean.toString(theGame.map.grid[i][j].availableForUnits);
	        		forPrint = forPrint + ", isEmpty:" + Boolean.toString(theGame.map.grid[i][j].isEmpty);
	        		forPrint = forPrint + ", isWinningPoint:" + Boolean.toString(theGame.map.grid[i][j].isWinningPoint);
	        		forPrint = forPrint + ", production:" + Integer.toString(theGame.map.grid[i][j].production);
	        		
	        		System.out.println(forPrint);
	        		if (theGame.map.grid[i][j].isBase == true) {
	        			forPrint = "		homeBase of ";
	        			if (theGame.map.grid[i][j].xPos == theGame.playerOne.homeBase.xPos)
	        				forPrint = forPrint + theGame.playerOne.name + " with health:" + Integer.toString(theGame.playerOne.homeBase.health);
	        			else
	        				forPrint = forPrint + theGame.playerTwo.name + " with health:" + Integer.toString(theGame.playerTwo.homeBase.health);;
	        			System.out.println(forPrint);
	        		}
	            }
	     }
	}

}
