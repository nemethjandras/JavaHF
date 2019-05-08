package mygame;

import java.util.ArrayList;

public class Player {
	public String name;  
    public int gold;
    public Base homeBase;
    public ArrayList<Unit> units;

    Player(String pName, int xPosBase, int yPosBase, int startGold ){
        name = pName;
    	gold = startGold;
        homeBase = new Base(xPosBase, yPosBase);
        units = new ArrayList<Unit>();
    }
    
    public void newTurnInit() {
    	
    }
    
    public void makeDevelopement(int devNum){
    	
    }
    
    public void makeUnit(int unitNum, int amount){

    }
    
    //itt Cell "effectOnMove" paramétert figyelembe venni
    public void unitMove(int xStart, int yStart, int xTarget, int yTarget, ArrayList<Unit> marcher){
    	
    }
    
    //itt Cell "effectOnDef" paramétert figyelembe venni
    public void fight(int xPos, int yPos, Player defender) {
    	Fight lifeOrDeath = new Fight(xPos, yPos, this, defender); 
    }
    
}
