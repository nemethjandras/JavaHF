package mygame;

public class Base extends Cell {
	public int health;
	public int homeProduction;
	
    public boolean[] madeDevelopments;
    public int[] developmentsPrice;
    public int[] unitsPrice;

    Base(int xCoord, int yCoord){
        super(xCoord, yCoord, 1, true, false, 0, 0, 0);
        health = 100;
        homeProduction = 10;
        
        madeDevelopments = new boolean[]{false, false, false, false, false, false, false, false};
        developmentsPrice = new int[]{10, 10, 10, 10, 10, 10, 10, 10};
        unitsPrice = new int[]{10, 8, 8, 10};
    }

    //Developments array: 0-ReducePrice_Worker, 1-ReducePrice_Infatry, 2-ReducePrice_Archer, 3-ReducePrice_Paladin,  
    //				 4-ImproveStat_Worker, 5-ImproveStat_Infatry, 6-ImproveStat_Archer, 7-ImproveStat_Paladin, 
    //unitsPrice array: 0-Worker, 1-Infantry, 2-Archer, 3-Paladin
    
    
    public boolean isDestroyed() {
    	if (health > 0) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }
    
    public void damageHandling(int sumEnemyDamage) {
    	health -= sumEnemyDamage;
    }
}
