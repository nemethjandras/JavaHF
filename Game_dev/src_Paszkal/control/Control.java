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
	public Player player;
	public Player enemy;
	public int split=0;
	public Map map;
	
	Select prev;
	int xPrev;
	int yPrev;
	int typePrev; // unit type 0 is worker, 1 2 3 are fighting units
	
	Select curr;
	int xCurr;
	int yCurr;
	int typeCurr;
	
	public Control(Player player_in, Player enemy_in, Map map_in) {
		player=player_in;
		enemy=enemy_in;
		map=map_in;
		
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
  
	public void newSelect(int x, int y, boolean split_on) {
		
		prev=curr;
		xPrev=xCurr;
		yPrev=yCurr;
		typePrev=typeCurr;
		
		xCurr=x;
		yCurr=y;
		querryUnits(x,y);
		System.out.format("value: split %d\n", split);
		//System.out.format("selected: %d, %s\n", xCurr,yCurr);
		//System.out.format("selected tile: %d\n", map.grid[xCurr][yCurr].valueForView);
	}
	
	public void querryUnits(int x, int y) {
		int num=player.units.size();
		for (int i = 0; i < num; i++) {
			if(player.units.get(i).xPos==x && player.units.get(i).yPos==y) {
				curr=Select.FRIENDLY;
				System.out.format("selected: FRIENDLY\n");
				typeCurr=player.units.get(i).type;
				return;
			}
		}
		num=enemy.units.size();
		for (int i = 0; i < num; i++) {
			if(enemy.units.get(i).xPos==x && enemy.units.get(i).yPos==y) {
				curr=Select.ENEMY;
				System.out.format("selected: ENEMY\n");
				typeCurr=enemy.units.get(i).type;
				typeCurr=-1;
				return;
			}
		}
		
		if (enemy.homeBase.xPos==x && enemy.homeBase.yPos==y) {
			curr=Select.ENEMY;
			System.out.format("selected: ENEMY\n");
			return;
		}
		
		if (player.homeBase.xPos==x && player.homeBase.yPos==y) {
			curr=Select.NOTHING;
			System.out.format("selected: NOTHING\n");
			typeCurr=-1;
			return;
		}
		
		curr=Select.EMPTY;
		System.out.format("selected: EMPTY\n");
		return;
	}
  
	public int execute() {
		if(curr==Select.EMPTY && prev==Select.FRIENDLY && split==0) {
			//move units from xPrev,yPrev to xCurr, yCurr
			System.out.format("command: move\n");
			return  player.move(xPrev, yPrev, xCurr, yCurr, map.grid[xCurr][yCurr]);
		}
		if(curr==Select.ENEMY && prev==Select.FRIENDLY) {
			//attack with units on xPrev,yPrev at units on xCurr, yCurr
			System.out.format("command: attack\n");
			return player.fight(xPrev, yPrev, xCurr, yCurr, map, enemy);
		}
		/*
		if(curr==Select.FRIENDLY && prev==Select.FRIENDLY && typePrev==typeCurr &&typeCurr!=0 && split) {
			//split units on xPrev,yPrev and merge them with units on xCurr, yCurr
			int ret = player.splitAndMerge(xPrev, yPrev, xCurr, yCurr, map.grid[xCurr][yCurr], numOfsplitted));
			split=false;

		}
		*/
		if(curr==Select.FRIENDLY && prev==Select.FRIENDLY && typePrev==typeCurr &&typeCurr!=0) {
			//merge units on xPrev,yPrev with units on xCurr, yCurr
			System.out.format("command: merge\n");
			return player.merge(xPrev, yPrev, xCurr, yCurr, map.grid[xCurr][yCurr]);

		}
		if(curr==Select.EMPTY && prev==Select.FRIENDLY && split!=0) {
			//split units on xPrev,yPrev to xCurr, yCurr
			System.out.format("command: split %d\n", split);
			return player.split(xPrev, yPrev, xCurr, yCurr, map.grid[xCurr][yCurr], split);


		}
		if(prev==Select.NOTHING) 
		{
		System.out.format("command: first select\n");
		return 2;
		}

		System.out.format("command: ???\n");
		return -1;
	}
	
}
