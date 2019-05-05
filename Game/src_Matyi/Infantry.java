package mygame;

public class Infantry extends Unit {
	public boolean isDefensive = false;

	Infantry(int xPosi, int yPosi){
		super(12, 5, 1, 1, 1, xPosi, yPosi);
	}	
}
