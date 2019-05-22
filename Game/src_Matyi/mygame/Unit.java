package src_Matyi.mygame;

abstract public class Unit {
	public int num;
	public int healthValue;
	public int availableAction;
    
    public final int type;	//0:worker, 1:infantry, 2:archer, 3:paladin
	public int damageValue;
    public final int actionPerRound;
    
    public int xPos;
    public int yPos;

    //TODO: (unit constructor parameters): healthVal_oneUnit, attackVal, effectiveness to "unitStats" class, which will be updated when depelovement is made 
    public Unit(int unitType, int number, int healthVal, int availableAct, int damageVal,  int actPerRound, int xPosi, int yPosi) {
    	num = number;
    	healthValue = healthVal * num;
        availableAction = availableAct;
        
        type = unitType;
        damageValue = damageVal;
        actionPerRound = actPerRound;
        
        xPos = xPosi;
        yPos = yPosi;
    }
    

    
    public boolean isUnitWorker() {
    	if (type == 0){
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean hasUnitAction() {
    	if (availableAction > 0){
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean isNeighbourCell(int xCurr, int yCurr) {
    	//4 neighbourhood check
		if(  ((Math.abs(xPos - xCurr) == 1) &&
					(Math.abs(yPos - yCurr) == 0))	//up or down from the actual Cell
		   ||((Math.abs(yPos - yCurr) == 1) &&
					(Math.abs(xPos - xCurr) == 0 ))) { //left or right from the actual Cell
						return true;
		}
		else {
			return false;
		}
	}
    
    public boolean isActionValid(int xCurr, int yCurr) {
    	if(this.hasUnitAction() && this.isNeighbourCell(xCurr, yCurr)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public void move(int xCurr, int yCurr) {
        availableAction -= 1;
        xPos = xCurr;
        yPos = yCurr;
        return;
    }
    

    public void merge(Unit unitToMerge){
    	num += unitToMerge.num;
        healthValue += unitToMerge.healthValue;
        return;
    }
    
    
    abstract public Unit split(int xCurr, int yCurr, int numOfsplitted, UnitStats stats);

    abstract public void damageHandling(int sumEnemyDamage, UnitStats stats);

    abstract public void newTurn(UnitStats stats);
    
    abstract public void setEffectiveness(int eff);
    
    abstract public int getEffectiveness();
}

