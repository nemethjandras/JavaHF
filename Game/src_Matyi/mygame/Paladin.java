package src_Matyi.mygame;

public class Paladin extends Unit {
	
	Paladin(int number,  int availableAct, UnitStats stats, int xPosi, int yPosi){
		//unitType, number, healthVal , availableAct, damageVal, actPerRound, xPosi, yPosi
        super(3, number, stats.paladinHealth, availableAct, stats.paladinDamage, 2, xPosi, yPosi);
    }
	
    public Unit split(int xCurr, int yCurr, int numOfsplitted, UnitStats stats){
    	Paladin splitted = new Paladin(numOfsplitted, availableAction-1, stats, xCurr, yCurr);
    	
   	   	num -= numOfsplitted;
   	   	healthValue -= numOfsplitted * stats.paladinHealth;
    	
    	return splitted;
    }
    
    public void damageHandling(int sumEnemyDamage, UnitStats stats) {
        healthValue -= sumEnemyDamage;
        num = Math.floorDiv(healthValue, stats.paladinHealth) + 1;
    }
	
	public void newTurn(UnitStats stats){
		availableAction = actionPerRound;
		healthValue = num * stats.paladinHealth;		
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
