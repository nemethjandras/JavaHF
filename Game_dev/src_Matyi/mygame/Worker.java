package mygame;

public class Worker extends Unit {
	private static final long serialVersionUID = 1L;
	public int effectiveness;

    Worker(int availableAct, int workerEffectiveness, int xPosi, int yPosi){
    	//unitType, number, healthVal, availableAct, attackVal, actPerRound, xPosi, yPosi
        super(0, 1, 0, availableAct, 0, 1, xPosi, yPosi);
        effectiveness = workerEffectiveness;
    }
    
    
    public Unit split(int xCurr, int yCurr, int numOfsplitted, UnitStats stats){
    	//this method can never be called, because Workers cannot be merged or splitted
    	Worker noOne = new Worker(-1, -1, -1, -1);
    	return noOne;
    }
    
    public void damageHandling(int sumEnemyDamage, UnitStats stats) {
    	//this method can never be called, because Workers die in fight without causing&suffering damage
    	return;
    }
    
    public void newTurn(UnitStats stats){ 
    	availableAction = actionPerRound;
    }
    
    public void setEffectiveness(int eff) {
    	effectiveness = eff;
    }
    
    public int getEffectiveness() {
    	return this.effectiveness;
    }
}
