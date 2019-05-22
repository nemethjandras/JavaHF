package mygame;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {

		 initService mapGenerator = new initService("small");
		 Game theGame = new Game(mapGenerator);
		 for(int i = 0; i < mapGenerator.xLen; i++) {
			 System.out.println(Arrays.toString(mapGenerator.cellType[i]));
		 }
		 
		 
		 
/*
 * //printing for test
		 String forPrint;
		 for(int i = 0; i < theGame.map.xLen; i++) {
	        	for(int j = 0; j < theGame.map.yLen; j++) {
	        		forPrint = Integer.toString(theGame.map.grid[i][j].xPos) + ',' + Integer.toString(theGame.map.grid[i][j].yPos);
	        		forPrint = forPrint + ", isAvailableForUnits:" + Boolean.toString(theGame.map.grid[i][j].availableForUnits);
	        		forPrint = forPrint + ", isWinningPoint:" + Boolean.toString(theGame.map.grid[i][j].isWinningPoint);
	        		forPrint = forPrint + ", production:" + Integer.toString(theGame.map.grid[i][j].production);
	        		
	        		if (theGame.playerOne.homeBase.xPos == theGame.map.grid[i][j].xPos
	        				&& theGame.playerOne.homeBase.yPos == theGame.map.grid[i][j].yPos) {
	        			forPrint += "		homeBase of player One";
	        		}
	        		
	        		else if (theGame.playerTwo.homeBase.xPos == theGame.map.grid[i][j].xPos
	        				&& theGame.playerTwo.homeBase.yPos == theGame.map.grid[i][j].yPos) {
	        			forPrint += "		homeBase of player Two";
	        		} 
	        		System.out.println(forPrint);
	            }
	     }
*/
		 
		 
		 
		 
		 
 /*
  * test fight option A: homeBase Attack
  * 
		 System.out.println("Player One Base health: " + Integer.toString(theGame.playerOne.homeBase.health));
		 theGame.playerTwo.units.add(new Paladin(5, 2, theGame.playerTwo.stats, theGame.playerOne.homeBase.xPos, theGame.playerOne.homeBase.yPos + 1));
		 System.out.println("\n");
			System.out.println("Player One units:");
			theGame.playerOne.printUnits();
			System.out.println("Player Two units:");
			theGame.playerTwo.printUnits();
		 
		
		int fightret = theGame.playerTwo.fight(theGame.playerOne.homeBase.xPos, theGame.playerOne.homeBase.yPos+1, theGame.playerOne.homeBase.xPos, theGame.playerOne.homeBase.yPos, theGame.map, theGame.playerOne);
		 System.out.println("fightret: " + Integer.toString(fightret));	
		
		 System.out.println("Player One Base health: " + Integer.toString(theGame.playerOne.homeBase.health));
			System.out.println("\n");
			System.out.println("Player One units:");
			theGame.playerOne.printUnits();
			System.out.println("Player Two units:");
			theGame.playerTwo.printUnits();
			
			*/
		 
		 
		 
		 
/*
 * //test developement buy
		
		 System.out.println("Gold: " + Integer.toString(theGame.playerOne.gold));
		 int buyret = theGame.playerOne.buyUnit(3, 1);
			System.out.println("buyret: " + Integer.toString(buyret));
			 System.out.println("Player One units:");
				theGame.playerOne.printUnits();
				System.out.println("Gold: " + Integer.toString(theGame.playerOne.gold));
		 
				
		 int devret0 = theGame.playerOne.buyDevelopement(7);
		 System.out.println("Gold: " + Integer.toString(theGame.playerOne.gold));
		System.out.println("devret0: " + Integer.toString(devret0));

		 
		System.out.println("\n\nMadedeveloplemnts");
		 System.out.println(Arrays.toString(theGame.playerOne.homeBase.madeDevelopments));
		 
		 System.out.println("\n\nUnitsPrice");
		 System.out.println(Arrays.toString(theGame.playerOne.homeBase.unitsPrice));
		 
		 System.out.println(Integer.toString(theGame.playerOne.stats.paladinHealth));
		 System.out.println(Integer.toString(theGame.playerOne.stats.paladinDamage));
		 
		 
		 System.out.println("\n\nPlayer One units:");
			theGame.playerOne.printUnits();
			
			*/
			
			
			
			
			
			
/*
 //* test : buy units, split, merge, fight with options B,C,D,E,F and newturn
	
	  	 
		 //unit buy
		int buyret = theGame.playerOne.buyUnit(3, 2);
		System.out.println("buyret: " + Integer.toString(buyret));
		int buyret2 = theGame.playerOne.buyUnit(1, 1);
		System.out.println("buyret2: " + Integer.toString(buyret2));
		
		 theGame.playerTwo.units.add(new Paladin(5, 2, theGame.playerTwo.stats, theGame.playerOne.units.get(0).xPos, theGame.playerOne.units.get(0).yPos + 2));
		//theGame.playerTwo.units.add(new Worker(1, 1, theGame.playerOne.units.get(0).xPos, theGame.playerOne.units.get(0).yPos + 2));

		 System.out.println("Player One units:");
		theGame.playerOne.printUnits();
		System.out.println("Player Two units:");
		theGame.playerTwo.printUnits();
			
		
		//split
		
		int splitret = theGame.playerOne.split(theGame.playerOne.units.get(0).xPos, theGame.playerOne.units.get(0).yPos, theGame.playerOne.units.get(0).xPos, theGame.playerOne.units.get(0).yPos+1, theGame.map.grid[theGame.playerOne.units.get(0).xPos][theGame.playerOne.units.get(0).yPos+1], 1);
		System.out.println("\n\n");
		System.out.println("splitret: " + Integer.toString(splitret));
		
		
		System.out.println("\n");
		System.out.println("Player One units:");
		theGame.playerOne.printUnits();
		System.out.println("Player Two units:");
		theGame.playerTwo.printUnits();
		
		//merge
		
		int mergeret = theGame.playerOne.merge(theGame.playerOne.units.get(0).xPos, theGame.playerOne.units.get(0).yPos, theGame.playerOne.units.get(0).xPos, theGame.playerOne.units.get(0).yPos+1, theGame.map.grid[theGame.playerOne.units.get(0).xPos][theGame.playerOne.units.get(0).yPos+1]);
		System.out.println("\n\n");
		System.out.println("mergeret: " + Integer.toString(mergeret));
		
		
		System.out.println("\n");
		System.out.println("Player One units:");
		theGame.playerOne.printUnits();
		System.out.println("Player Two units:");
		theGame.playerTwo.printUnits();
		
		//fight
		
		int fightret = theGame.playerOne.fight(theGame.playerOne.units.get(0).xPos, theGame.playerOne.units.get(0).yPos, theGame.playerOne.units.get(0).xPos, theGame.playerOne.units.get(0).yPos+1, theGame.map, theGame.playerTwo);
		System.out.println("\n\n");
		System.out.println("fightret: " + Integer.toString(fightret));
		
		
		System.out.println("\n");
		System.out.println("Player One units:");
		theGame.playerOne.printUnits();
		System.out.println("Player Two units:");
		theGame.playerTwo.printUnits();
		
		theGame.playerOne.newTurnInit(theGame.map);
		
		System.out.println("\nAfter newturn");
		System.out.println("Player One units:");
		theGame.playerOne.printUnits();
		System.out.println("Player Two units:");
		theGame.playerTwo.printUnits();
		
		
		//theGame.playerOne.move(0, 0, 1, 0);
		//theGame.playerOne.split(0, 0, 1, 0, 1);
		//theGame.playerOne.merge(0, 0, 1, 0);
		//theGame.playerOne.splitAndMerge(0, 0, 1, 0, 1);
		
		
		*/
	

		
	/*
	 * function for test in player
	 
		    public void printUnits() {
		    	int unitNumOne = units.size();
		    	String forPrint_unitOne;
			   	 for (int i = 0; i < unitNumOne; i++) {
			   		forPrint_unitOne =  "Type: " + Integer.toString(units.get(i).type);
			   		forPrint_unitOne += ", HealthValue: " + Integer.toString(units.get(i).healthValue);
			   		forPrint_unitOne += ", NumValue: " + Integer.toString(units.get(i).num);
			   		forPrint_unitOne += ", availableAction: " + Integer.toString(units.get(i).availableAction);
			   		forPrint_unitOne += ", Damage Value: " + Integer.toString(units.get(i).damageValue);
			   		forPrint_unitOne += ", On x-y : " + Integer.toString(units.get(i).xPos)
			   			+ "-" + Integer.toString(units.get(i).yPos);
			   		System.out.println(forPrint_unitOne);
			   		}
		    }
		    */
	
	}

}
