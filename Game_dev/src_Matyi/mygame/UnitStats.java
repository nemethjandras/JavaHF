package mygame;

import java.io.Serializable;

public class UnitStats implements Serializable {
	private static final long serialVersionUID = 1L;

	int workerEffectiveness;
	
	int infantryHealth;
	int infantryDamage;
	
	int archerHealth;
	int archerDamage;
	
	int paladinHealth;
	int paladinDamage;
	
	public UnitStats() {
		workerEffectiveness = 1;
		
		infantryHealth = 10;
		infantryDamage = 6;
		
		archerHealth = 8;
		archerDamage = 4;
		
		paladinHealth = 12;
		paladinDamage = 8;
	}
	
}
