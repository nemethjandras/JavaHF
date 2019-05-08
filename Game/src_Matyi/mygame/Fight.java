package mygame;
import java.util.ArrayList;

public class Fight {
	Cell battleField;
	boolean[] attackerIndex;
	boolean[] defenderIndex;
	int sumHealth_att;
	int sumHealth_def;
	int sumAttackVal_att;
	int sumAttackVal_def;
	
	Fight(int xPos, int yPos, Player attacker, Player defender){
    	attackerIndex = new boolean[attacker.units.size()];
    	defenderIndex = new boolean[defender.units.size()];
	}
}
