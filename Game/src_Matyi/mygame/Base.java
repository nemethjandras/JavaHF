package mygame;

public class Base extends Cell {
	public int health;
    public boolean[] madeDevelopements;
    public int[] developementsPrice;
    public int[] unitsPrice;

    Base(int xCoord, int yCoord){
        super(xCoord, yCoord, true, false, 0, 0, false, 0, true, 0);
        health = 100;
        madeDevelopements = new boolean[]{false, false, false, false, false, false, false, false};
        developementsPrice = new int[]{10, 10, 10, 10, 10, 10, 10, 10};
        unitsPrice = new int[]{8, 8, 10, 10};
    }

    //Developements: 0-ReducePrice_Archer, 1-ReducePrice_Infatry, 2-ReducePrice_Paladin, 3-ReducePrice_Worker,  
    //				 4-SkillImprove_Archer, 5-SkillImprove_Infatry, 6-SkillImprove_Paladin, 7-SkillImprove_Worker
    
    public void buyDevelopement(){

    }

    public void buyUnit(){

    }
}
