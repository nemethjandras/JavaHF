package mygame;

public class Archer extends Unit {
	private static final long serialVersionUID = 1L;

	Archer(int number, int availableAct, UnitStats stats, int xPosi, int yPosi) {
		//unitType, number, healthVal, availableAct, damageVal, actPerRound, xPosi, yPosi
        super(2, number, stats.archerHealth, availableAct, stats.archerDamage, 1, xPosi, yPosi);
    }

	
    public Unit split(int xCurr, int yCurr, int numOfsplitted, UnitStats stats){
    	Archer splitted = new Archer(numOfsplitted, availableAction-1, stats, xCurr, yCurr);
    	
   	   	num -= numOfsplitted;
   	   	healthValue -= numOfsplitted * stats.archerHealth;
    	
    	return splitted;
    }
    
    
    public void damageHandling(int sumEnemyDamage, UnitStats stats) {
        healthValue -= sumEnemyDamage;
        num = Math.floorDiv(healthValue, stats.archerHealth) + 1;
    }
	
	public void newTurn(UnitStats stats){
    	availableAction = actionPerRound;
    	healthValue = num * stats.archerHealth;
    }
	
	public void setEffectiveness(int eff) {
		//is never called
		return;
	}
	
	public int getEffectiveness() {
		//is never called
		return -1;
	}
}
