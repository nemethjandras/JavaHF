package mygame;

public class Cell {
	public int xPos;
    public int yPos;
    public final boolean availableForUnits;
    public boolean isEmpty;
    public final int effectOnMove;
    public final int effectOnDefender;
    public final boolean isWinningPoint;
    public final int production;
    public final boolean isBase;

    Cell(int xCoord, int yCoord, boolean availForUnits, boolean isEmpt, int effOnMove, int effOnDef, boolean isWinPoint, int prod, boolean isB){
        xPos = xCoord;
        yPos = yCoord;
        availableForUnits = availForUnits;
        isEmpty = isEmpt;
        effectOnMove = effOnMove;
        effectOnDefender = effOnDef;
        isWinningPoint = isWinPoint;
        production = prod;
        isBase = isB;
    }
}
