package control;
import display.*;
import mygame.*;

enum Select{
	NOTHING,
	EMPTY,
	FRIENDLY,
	ENEMY
}

public class Control
{
	Player player;
	Player enemy;
	
	
	Select prev;
	int xPrev;
	int yPrev;
	int typePrev; // unit type 0 is worker, 1 2 3 are fighting units
	
	Select curr;
	int xCurr;
	int yCurr;
	int typeCurr;
	
	public Control(Player player_in, Player enemy_in) {
		player=player_in;
		enemy=enemy_in;
		
		prev=Select.NOTHING;
		xPrev=-1;
		yPrev=-1;
		typePrev=-1;
		
		curr=Select.NOTHING;
		xCurr=-1;
		yCurr=-1;
		typeCurr=-1;
	}
	
	public void resetSelect() {
		prev=Select.NOTHING;
		xPrev=-1;
		yPrev=-1;
		typePrev=-1;
		
		curr=Select.NOTHING;
		xCurr=-1;
		yCurr=-1;
		typeCurr=-1;
	}
  
	public void newSelect(int x, int y) {
		prev=curr;
		xPrev=xCurr;
		yPrev=yCurr;
		typePrev=typeCurr;
		
		xCurr=x;
		yCurr=y;
		querryUnits(x,y);
	}
	
	public void querryUnits(int x, int y) {
		int num=player.units.size();
		for (int i = 0; i < num; i++) {
			if(player.units.get(i).xPos==x && player.units.get(i).yPos==y) {
				curr=Select.FRIENDLY;
				typeCurr=player.units.get(i).type;
				return;
			}
		}
		num=enemy.units.size();
		for (int i = 0; i < num; i++) {
			if(enemy.units.get(i).xPos==x && enemy.units.get(i).yPos==y) {
				curr=Select.ENEMY;
				typeCurr=enemy.units.get(i).type;
				return;
			}
		}
		return;
	}
  
	public int execute() {
		if(curr==Select.EMPTY && prev==Select.FRIENDLY) {
			//move units from xPrev,yPrev to xCurr, yCurr
			
			return 1;
		}
		if(curr==Select.ENEMY && prev==Select.FRIENDLY) {
			//attack with units on xPrev,yPrev at units on xCurr, yCurr
			
			return 1;
		}
		if(curr==Select.FRIENDLY && prev==Select.FRIENDLY && typePrev==typeCurr &&typeCurr!=0) {
			//merge units on xPrev,yPrev with units on xCurr, yCurr
			
			return 1;
		}
		//TODO split command 
		
		return 0;
	}
	
}