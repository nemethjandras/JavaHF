package mygame;

public class Worker extends Unit {
	public boolean isProducting;
	public int effectiveness;

    Worker(int xPosi, int yPosi){
        super(0, 0, 1, 1, 0, xPosi, yPosi);
        isProducting = false;
        effectiveness = 1;
    }
}
