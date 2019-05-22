package src_Matyi.mygame;
import java.util.ArrayList;

public class Fight {
	Cell battleField;
	
	Player playerAtt;
	Player playerDef;
	
	Unit unitAtt;
	Unit unitDef;
	
	//option A: if def = homeBase
		//unitAtt
			//availAtion = 0
		//def homeBase
			//damageHandling
	
	//else: fight flow
		//option B: if def = worker
			//unitAtt 
				//pos update with unit.move (only 1 action used)
			//unitDef
				//delete

		//option B: else if def = enemy base
			
		
		//else
		
			//damage come from:
				//playerAtt:
					//unitAtt
					//archers in neighbourhood
		
				//playerDef:
					//unitDef
					//archers in neighbourhood
					//bonus if infantry
					//cell effectOnDefender
		
		
			//result
					//C option: if both dies
						//unitAtt
							//delete
						//unitDef
							//delete
	
					//D option: if attacker wins
						//unitAtt 
								//availAtion = 0
								//damageHandling 
								//pos update
						//unitDef
								//delete
		
					//E option: if attacker loses
						//unitAtt 
							//delete
						//unitDef
							//damageHandling
				
					//F option: no one dies
						//unitAtt
							//availAtion = 0
							//damageHandling 
						//unitAtt
							//damageHandling 
						
	
	Fight(int xCurr, int yCurr, int xPrev, int yPrev, Map map, Player attacker, Player defender){
		battleField = map.grid[xCurr][yCurr];
		
		playerAtt = attacker;
		playerDef= defender;
		
		int unitNumAtt = playerAtt.units.size();
		for (int i = 0; i < unitNumAtt; i++) {
			if( (playerAtt.units.get(i).xPos == xPrev) && (playerAtt.units.get(i).yPos == yPrev) ) {
				unitAtt = playerAtt.units.get(i);
			}
		}

		int unitNumDef = playerDef.units.size();
		for (int i = 0; i < unitNumDef; i++) {
			if( (playerDef.units.get(i).xPos == xCurr) && (playerDef.units.get(i).yPos == yCurr) ) {
				unitDef = playerDef.units.get(i);
			}			
		}
	}
	
	public boolean defIsWorker(){
		if (unitDef.type == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int attackerDamageCalc() {
		//damage of unit on battlefield
		int sumAttDamage = unitAtt.damageValue * unitAtt.num;
		
		//damage of the archers in the neighbourhood of the battlefield
		int unitNumAtt = playerAtt.units.size();
		for (int i = 0; i < unitNumAtt; i++) {
			if (playerAtt.units.get(i).type == 2) {
				//8-neighbourhood check
				if(  ((Math.abs(playerAtt.units.get(i).xPos - battleField.xPos) == 1) &&
							(Math.abs(playerAtt.units.get(i).yPos - battleField.yPos) < 2))	//up or down from battleField
				   ||((Math.abs(playerAtt.units.get(i).yPos - battleField.yPos) == 1) &&
							(Math.abs(playerAtt.units.get(i).xPos - battleField.xPos) < 2))) { //left or right from battleField
								sumAttDamage += playerAtt.units.get(i).damageValue * playerAtt.units.get(i).num;
				}
			}
		}
		
		return sumAttDamage;
	}
	
	public int defenderDamageCalc() {
		//damage of unit on battlefield - modificated with the effect of the cell
		int sumDefDamage = (unitDef.damageValue + battleField.effectOnDefender) * unitDef.num;
		
		//bonus damage if defender unit is infantry
		if (unitDef.type == 1) {
			sumDefDamage += Math.floorDiv(unitDef.damageValue, 3) * unitDef.num;
		}
		
		//damage of the archers in the neighbourhood of the battlefield
		int unitNumDef = playerDef.units.size();
		for (int i = 0; i < unitNumDef; i++) {
			if (playerDef.units.get(i).type == 2) {
				//8-neighbourhood check
				if(  ((Math.abs(playerDef.units.get(i).xPos - battleField.xPos) == 1) &&
							(Math.abs(playerDef.units.get(i).yPos - battleField.yPos) < 2))	//up or down from battleField
				   ||((Math.abs(playerDef.units.get(i).yPos - battleField.yPos) == 1) &&
							(Math.abs(playerDef.units.get(i).xPos - battleField.xPos) < 2))) { //left or right from battleField
								sumDefDamage += playerDef.units.get(i).damageValue * playerDef.units.get(i).num;
				}
			}
		}
		
		return sumDefDamage;
	}
}
