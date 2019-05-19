package mygame;

import java.util.ArrayList;

public class Player {
	public String name;  
	public Base homeBase;
	
    public int gold;
    public ArrayList<Unit> units;
    
    public int len_forView;
    public int[] x_forView;
    public int[] y_forView;
    public int[] type_forView;
    public int[] num_forView;
    
    public UnitStats stats;

    Player(String pName, int xPosBase, int yPosBase, int startGold ){
        name = pName;
        homeBase = new Base(xPosBase, yPosBase);
        
    	gold = startGold;
        units = new ArrayList<Unit>();
        
        len_forView = 0;
        x_forView = new int[0];
        y_forView = new int[0];
        type_forView = new int[0];
        num_forView = new int[0];
        
        stats = new UnitStats();
    }
    
    public void generateArr_forView() {
    	//necessary to call when
    		//action is done
    		//unit is bought
    	len_forView = this.units.size();
        x_forView = new int[len_forView];
        y_forView = new int[len_forView];
        type_forView = new int[len_forView];
        num_forView = new int[len_forView];
        
		for (int i = 0; i < len_forView; i++) {
			x_forView[i] = this.units.get(i).xPos;
		    y_forView[i] = this.units.get(i).yPos;
		    type_forView[i] = this.units.get(i).type;
		    num_forView[i] = this.units.get(i).num;
		}
    }
    
    public void newTurnInit(Map map) {
    	//TODO:
    		//unit newTurn (health, action)
    		//cells: effOnMove bonus action!
    		//production (worker.effectiveness * cells)
    }
    
    public void buyDevelopement(int devNum){
    	//TODO:
    		//gold check and subtract (with base variables)
    		//base method call
    		//update in stats
    		//update in units
    }
    
    public int buyUnit(int unitType, int amount){
    	if ((this.homeBase.unitsPrice[unitType] * amount > this.gold) || (unitType == 0 && amount > 1) ) {
    		return -1;
    	}
    	else {
    		gold -= this.homeBase.unitsPrice[unitType] * amount;
    		if (unitType == 0) { 	//worker
    			Unit bought = new Worker(0, this.stats.workerEffectiveness, homeBase.xPos + 1, homeBase.yPos + 1);
    			this.units.add(bought);
    		}
    		if (unitType == 1) { 	//infantry
    			Unit bought = new Infantry(amount, 0, this.stats, homeBase.xPos + 1, homeBase.yPos + 1);
    			this.units.add(bought);
    		}
    		if (unitType == 2) { 	//archer
    			Unit bought = new Archer(amount, 0, this.stats, homeBase.xPos + 1, homeBase.yPos + 1);
    			this.units.add(bought);
    		}
    		else { 	//paladin
    			Unit bought = new Paladin(amount, 0, this.stats, homeBase.xPos + 1, homeBase.yPos + 1);
    			this.units.add(bought);
    		}
    		
    		this.generateArr_forView();
        	return 1;
    	}
    }
    
    
    public int getUnitIndex(int xPosi, int yPosi) {
    	//it is supposed that there is a unit on the cell [xPosi][yPosi]
    			//otherwise: return value is -1
    	int unitIndex = -1;
    	int unitArrNum = this.units.size();
		for (int i = 0; i < unitArrNum; i++) {
			if( (this.units.get(i).xPos == xPosi) && (this.units.get(i).yPos == yPosi) ) {
				unitIndex = i;
			}
		}
		return unitIndex;
    }
    
    public int move(int xPrev, int yPrev, int xCurr, int yCurr) {
    	//move units from xPrev,yPrev to xCurr, yCurr
    	
    	int unitInd = this.getUnitIndex(xPrev, yPrev);
		
    	//check if action is valid (unit has action and cell [xCurr][yCurr] is in 4 neighbourhood)
    	if (this.units.get(unitInd).isActionValid(xCurr, yCurr) == false) {
			return -1;
		}
		
		this.units.get(unitInd).move(xCurr, yCurr);
		
		this.generateArr_forView();
        return 1;
    }
    

    public int merge(int xPrev, int yPrev, int xCurr, int yCurr){
    	//merge units on xPrev,yPrev with units on xCurr, yCurr
    	
    	int unitInd_prev = this.getUnitIndex(xPrev, yPrev);
    	int unitInd_curr = this.getUnitIndex(xCurr, yCurr);
    	
		//if worker: merge cannot be done
		if (this.units.get(unitInd_prev).isUnitWorker()) {
			return -1;
		}
    	
    	//check if action is valid (unit has action and cell [xCurr][yCurr] is in 4 neighbourhood)
    	if (this.units.get(unitInd_prev).isActionValid(xCurr, yCurr) == false) {
			return -1;
		}
    	
    	
    	
    	this.units.get(unitInd_curr).merge(this.units.get(unitInd_prev));
    	this.units.remove(unitInd_prev);
    	
    	this.generateArr_forView();
        return 1;
    }
    
    
    public int split(int xPrev, int yPrev, int xCurr, int yCurr, int numOfsplitted){
    	//split "numOfsplitted" pieces of units from xPrev,yPrev to xCurr, yCurr
		
    	int unitInd = this.getUnitIndex(xPrev, yPrev);
    	
		//if worker: split cannot be done
		if (this.units.get(unitInd).isUnitWorker()) {
			return -1;
		}
    	
    	//check if action is valid (unit has action and cell [xCurr][yCurr] is in 4 neighbourhood)
    	if (this.units.get(unitInd).isActionValid(xCurr, yCurr) == false) {
			return -1;
		}
    	
    	
    	
    	this.units.add(this.units.get(unitInd).split(xCurr, yCurr, numOfsplitted, this.stats));
    	
    	this.generateArr_forView();
    	return 1;
    }
    
    
    public int splitAndMerge(int xPrev, int yPrev, int xCurr, int yCurr, int numOfsplitted){
    	
    	int unitInd_prev = this.getUnitIndex(xPrev, yPrev);
    	int unitInd_curr = this.getUnitIndex(xCurr, yCurr);
    	
    	//if worker: split cannot be done
    	if (this.units.get(unitInd_prev).isUnitWorker()) {
    		return -1;
    	}
    	    	
    	//check if action is valid (unit has action and cell [xCurr][yCurr] is in 4 neighbourhood)
    	if (this.units.get(unitInd_prev).isActionValid(xCurr, yCurr) == false) {
    		return -1;    	
    	}
    	
    	//update numOfsplitted
    	int numOfSM = numOfsplitted + this.units.get(unitInd_curr).num;   
    	
    	//"inverted" merged (from curr to prev) 
    	this.units.get(unitInd_prev).merge(this.units.get(unitInd_curr));
    	this.units.remove(unitInd_curr);    	
	
    	//split from curr to prev with updated numOfSplitted
    	this.units.add(this.units.get(unitInd_prev).split(xCurr, yCurr, numOfSM, this.stats));
    	
    	this.generateArr_forView();
    	return 1;
    }
    
    

    public int fight(int xPrev, int yPrev, int xCurr, int yCurr, Map map, Player defender) {
    	//battle between units on xPrev,yPrev and enemy units on xCurr, yCurr
    	
    	int unitAttInd = this.getUnitIndex(xPrev, yPrev);
 
		
		//check if action is valid (unit has action and cell [xCurr][yCurr] is in 4 neighbourhood)
		if (this.units.get(unitAttInd).isActionValid(xCurr, yCurr) == false) {
			return -1;
		}
		
		//if worker: fight cannot be done
		if (this.units.get(unitAttInd).isUnitWorker()) {
			return -1;
		}
		
		
		//option A: if def is homeBase
	if(defender.homeBase.xPos == xCurr && defender.homeBase.yPos == yCurr) {
		int sumDamage = this.units.get(unitAttInd).damageValue * this.units.get(unitAttInd).num;
		
			//effect on attacker
		this.units.get(unitAttInd).availableAction = 0;
		
			//effect on defender
		defender.homeBase.damageHandling(sumDamage);
		
		if (defender.homeBase.isDestroyed()) {
			return 77;
		}
		else {
			return 1;
		}
	}
		
		
		//else (if att is not worker and def is not homeBase): fight flow
    	Fight battle = new Fight(xCurr, yCurr, xPrev, yPrev, map, this, defender);
    	
    	int unitDefInd = defender.getUnitIndex(xCurr, yCurr);
    	
    	
    		//option A: if def is worker
    	if (battle.defIsWorker()) {
    		defender.units.remove(unitDefInd);
    		this.units.get(unitAttInd).move(xCurr, yCurr); 
    		
    		defender.generateArr_forView();
    		return 1;
    	}
    	
    	
    		//else (if def is not worker): damage calculation
    	
    	int sumAttDamage = battle.attackerDamageCalc();
    	int sumDefDamage = battle.defenderDamageCalc();
    	
    			//option C: result-both dies
    	if ( (sumAttDamage >= defender.units.get(unitDefInd).healthValue) && (sumDefDamage >= this.units.get(unitDefInd).healthValue)) {
    				//effect on attacker
    		this.units.remove(unitAttInd);
    				
    				//effect on defender
    		defender.units.remove(unitDefInd);
    		
    		this.generateArr_forView();
    		defender.generateArr_forView();
    		return 1;
    	}
    	
    			//option D: result-attacker wins
    	if (sumAttDamage >= defender.units.get(unitDefInd).healthValue) {
    				//effect on attacker
    		this.units.get(unitAttInd).availableAction = 0;
    		this.units.get(unitAttInd).xPos = xCurr;
    		this.units.get(unitAttInd).yPos = yCurr;
    		this.units.get(unitAttInd).damageHandling(sumDefDamage, this.stats);
    		
    				//effect on defender
    		defender.units.remove(unitDefInd);
    		
    		this.generateArr_forView();
    		defender.generateArr_forView();
    		return 1;
    	}
    	
    			//option E: result-defender wins
    	if (sumDefDamage >= this.units.get(unitDefInd).healthValue) {
    				//effect on attacker
    		this.units.remove(unitAttInd);
    		
    				//effect on defender
    		defender.units.get(unitAttInd).damageHandling(sumAttDamage, defender.stats);
    		
    		this.generateArr_forView();
    		defender.generateArr_forView();
    		return 1;
    	}
    	
    			//option F: result-no one dies
    	else {
    				//effect on attacker
    		this.units.get(unitAttInd).availableAction = 0;
    		this.units.get(unitAttInd).damageHandling(sumDefDamage, this.stats);
    		
    				//effect on defender
    		defender.units.get(unitAttInd).damageHandling(sumAttDamage, defender.stats);
    		
    		this.generateArr_forView();
    		defender.generateArr_forView();
    		return 1;
    	}
    }
}
