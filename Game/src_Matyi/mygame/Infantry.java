package src_Matyi.mygame;

public class Infantry extends Unit {
	public boolean isDefensive = false;

	Infantry(int number, int availableAct, UnitStats stats, int xPosi, int yPosi){
		//unitType, number, healthVal, availableAct, damageVal, actPerRound, xPosi, yPosi
		super(1, number, stats.infantryHealth, availableAct, stats.infantryDamage, 1, xPosi, yPosi);
	}

    
    public Unit split(int xCurr, int yCurr, int numOfsplitted, UnitStats stats){
   	   	Infantry splitted = new Infantry(numOfsplitted, availableAction-1, stats, xCurr, yCurr);
   	   	
   	   	num -= numOfsplitted;
   	   	healthValue -= numOfsplitted * stats.infantryHealth;
   	   	
    	return splitted;
    }
    
    public void damageHandling(int sumEnemyDamage, UnitStats stats) {
        healthValue -= sumEnemyDamage;
        num = Math.floorDiv(healthValue, stats.infantryHealth) + 1;
    }
	
	public void newTurn(UnitStats stats){
    	availableAction = actionPerRound;
    	healthValue = num * stats.infantryHealth;
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
