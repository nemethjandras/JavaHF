package mygame;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
	private static final long serialVersionUID = 1L;

	public Base homeBase;
	
    public int gold;
    public ArrayList<Unit> units;
    
    public boolean isBuyAvailable;
    
    public int len_forView;
    public int[] x_forView;
    public int[] y_forView;
    public int[] type_forView;
    public int[] num_forView;
    
    public UnitStats stats;

    Player(int xPosBase, int yPosBase, int startGold ){
        homeBase = new Base(xPosBase, yPosBase);
        
    	gold = startGold;
        units = new ArrayList<Unit>();
        
        isBuyAvailable = true;
        
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
    
    
    public int endTurn(Map map) {
    	int winPointNum = 0;
    	int unitArrNum = this.units.size();
		for (int i = 0; i < unitArrNum; i++) {
			
			isBuyAvailable = true;	
			
			this.units.get(i).newTurn(stats);	//units health and action
			
			int xUnit = this.units.get(i).xPos;
			int yUnit = this.units.get(i).yPos;
			this.units.get(i).availableAction += map.grid[xUnit][yUnit].effectOnAction;	//cell bonus to unit action
			
			if (this.units.get(i).type == 0) {	//gold production of workers
				this.gold += this.units.get(i).getEffectiveness() * map.grid[xUnit][yUnit].production;
			}
			
			if (map.grid[xUnit][yUnit].isWinningPoint == true) {
				winPointNum += 1;
			}
		}
		
		this.gold += this.homeBase.homeProduction;	//gold production homeBase
		
		if (winPointNum >= 13) {
			return 77;	//winning the game
		}
		
		else {
			return 1;
		}
		
    }
    
    
    public int buyDevelopement(int devNum){
    	if (devNum < 0 || devNum > 7 || 
    			(this.homeBase.developmentsPrice[devNum] > this.gold) || (this.homeBase.madeDevelopments[devNum] == true) ) {
    		return -1;
    	}
    	else {
    		gold -= this.homeBase.developmentsPrice[devNum];
    		this.homeBase.madeDevelopments[devNum] = true;
    		
    		if (devNum < 4) {	//priceReduce
    			this.homeBase.unitsPrice[devNum] -= 2;	
    			return 1;
	    		}
    		
    		else if (devNum == 4) {	//improveSkill-worker
    			this.stats.workerEffectiveness = 2;
    			
    			int unitArrNum = this.units.size();
	    		for (int i = 0; i < unitArrNum; i++) {
	    			if (this.units.get(i).type == 0) {
	    				this.units.get(i).setEffectiveness(this.stats.workerEffectiveness);
	    			}
	    		}	
	    		return 1;
    		}
    	
    		else {	//improveSkill other unit
    			int healthImpr = 2;
    			int damageImpr = 2;
    			int unitType = devNum - 4;
    			
    			if (devNum == 5) { 	//infantry-improveSkill 
	    			this.stats.infantryHealth += healthImpr;
	    			this.stats.infantryDamage += damageImpr;
	    		}
	    		if (devNum == 6) { 	//archer-improveSkill 
	    			this.stats.archerHealth += healthImpr;
	    			this.stats.archerDamage += damageImpr;
	    		}
	    		else { 	//paladin-improveSkill 
	    			this.stats.paladinHealth += healthImpr;
	    			this.stats.paladinDamage += damageImpr;
	    		}    		
	    		
	    		int unitArrNum = this.units.size();
	    		for (int i = 0; i < unitArrNum; i++) {
	    			if (this.units.get(i).type == unitType) {
	    				this.units.get(i).healthValue += this.units.get(i).num * healthImpr;
	    				this.units.get(i).damageValue += damageImpr;
	    			}
	    		}
	    		
	    		return 1;
    		}
    	}
    }
    
    
    public int buyUnit(int unitType, int amount){
    	
    	//check if buying is possible
    	if ( (isBuyAvailable == false) || (this.homeBase.unitsPrice[unitType] * amount > this.gold) || (unitType == 0 && amount > 1) || (amount <= 0) ) {
    		return -1;
    	}
    	else {
    		gold -= this.homeBase.unitsPrice[unitType] * amount;
    		
    		int shift = 0;
    		if (this.homeBase.yPos == 0) {	//player on the left side -> generate unit on the right side of the base
    			shift = + 1;
    		}
    		else {	//player on the right side -> generate unit on the left side of the base
    			shift = - 1;
    		}
    		
    		
    		if (unitType == 0) { 	//worker
    			Unit bought = new Worker(1, this.stats.workerEffectiveness, homeBase.xPos, homeBase.yPos + shift);
    			this.units.add(bought);
    		}
    		
    		else if (unitType == 1) { 	//infantry
    			Unit bought = new Infantry(amount, 1, this.stats, homeBase.xPos, homeBase.yPos + shift);
    			this.units.add(bought);
    		}
    		
    		else if (unitType == 2) { 	//archer
    			Unit bought = new Archer(amount, 1, this.stats, homeBase.xPos, homeBase.yPos + shift);
    			this.units.add(bought);
    		}
    		
    		else { 	//paladin
    			Unit bought = new Paladin(amount, 2, this.stats, homeBase.xPos, homeBase.yPos + shift);
    			this.units.add(bought);
    		}
    		
    		isBuyAvailable = false;
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
    
    
    public int move(int xPrev, int yPrev, int xCurr, int yCurr, Cell curr) {
    	//move units from xPrev,yPrev to xCurr, yCurr
    	
    	int unitInd = this.getUnitIndex(xPrev, yPrev);
		
    	//check if action is valid (unit has action and cell [xCurr][yCurr] is in 4 neighbourhood) 
    		//and cell is available for units
    	if ( (this.units.get(unitInd).isActionValid(xCurr, yCurr) == false)
    			|| (curr.availableForUnits == false) ) {
			return -1;
		}
		
		this.units.get(unitInd).move(xCurr, yCurr);
		
		this.generateArr_forView();
        return 1;
    }
    

    public int merge(int xPrev, int yPrev, int xCurr, int yCurr, Cell curr){
    	//merge units on xPrev,yPrev with units on xCurr, yCurr
    	
    	int unitInd_prev = this.getUnitIndex(xPrev, yPrev);
    	int unitInd_curr = this.getUnitIndex(xCurr, yCurr);
    	
		//if worker: merge cannot be done
		if (this.units.get(unitInd_prev).isUnitWorker()) {
			return -1;
		}
    	
    	//check if action is valid (unit has action and cell [xCurr][yCurr] is in 4 neighbourhood)
			//and cell is available for units
    	if ( (this.units.get(unitInd_prev).isActionValid(xCurr, yCurr) == false)
    			|| (curr.availableForUnits == false) ) {
			return -1;
		}
    	
    	
    	
    	this.units.get(unitInd_curr).merge(this.units.get(unitInd_prev));
    	this.units.remove(unitInd_prev);
    	
    	this.generateArr_forView();
        return 1;
    }
    
    
    public int split(int xPrev, int yPrev, int xCurr, int yCurr, Cell curr, int numOfSplitted){
    	//split "numOfsplitted" pieces of units from xPrev,yPrev to xCurr, yCurr
    	int unitInd = this.getUnitIndex(xPrev, yPrev);
    	
		//if worker: split cannot be done
		if (this.units.get(unitInd).isUnitWorker()) {
			return -1;
		}
    	
    	//check if action is valid (unit has action and cell [xCurr][yCurr] is in 4 neighbourhood)
			//and cell is available for units
			//and is numOfSplitted not too big
    	if ( (this.units.get(unitInd).isActionValid(xCurr, yCurr) == false)
    			|| (curr.availableForUnits == false) 
    			|| (this.units.get(unitInd).num <= numOfSplitted) ){
			return -1;
		}
    	
    	
    	
    	this.units.add(this.units.get(unitInd).split(xCurr, yCurr, numOfSplitted, this.stats));
    	
    	this.generateArr_forView();
    	return 1;
    }
    
    
    public int splitAndMerge(int xPrev, int yPrev, int xCurr, int yCurr, Cell curr, int numOfSplitted){
    	
    	int unitInd_prev = this.getUnitIndex(xPrev, yPrev);
    	int unitInd_curr = this.getUnitIndex(xCurr, yCurr);
    	
    	//if worker: split cannot be done
    	if (this.units.get(unitInd_prev).isUnitWorker()) {
    		return -1;
    	}
    	    	
    	//check if action is valid (unit has action and cell [xCurr][yCurr] is in 4 neighbourhood)
    		//and is cell available for units
    		//and is numOfSplitted not too big
    	if ( (this.units.get(unitInd_prev).isActionValid(xCurr, yCurr) == false)
    			|| (curr.availableForUnits == false) 
    			|| (this.units.get(unitInd_prev).num <= numOfSplitted) ){
    		return -1;    	
    	}
    	
    	//update numOfsplitted
    	int numOfSM = numOfSplitted + this.units.get(unitInd_curr).num;   
    	
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
    		//and cell is available for units
		if ( (this.units.get(unitAttInd).isActionValid(xCurr, yCurr) == false) 
				|| (map.grid[xCurr][yCurr].availableForUnits == false) ){
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
			return 77;		//winning the game
		}
		else {
			return 1;
		}
	}
		
		
		//else (if att is not worker and def is not homeBase): fight flow
    	Fight battle = new Fight(xCurr, yCurr, xPrev, yPrev, map, this, defender);
    	
    	int unitDefInd = defender.getUnitIndex(xCurr, yCurr);
    	
    	
    		//option B: if def is worker
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
    		defender.units.get(unitDefInd).damageHandling(sumAttDamage, defender.stats);
    		
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
    		defender.units.get(unitDefInd).damageHandling(sumAttDamage, defender.stats);
    		
    		this.generateArr_forView();
    		defender.generateArr_forView();
    		return 1;
    	}
    }

    

	 
}


