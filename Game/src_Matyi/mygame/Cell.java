package mygame;

public class Cell {
	public int xPos;
    public int yPos;
    public final boolean availableForUnits;
    public boolean isEmpty;
    
    public final int production;
    //TODO: care about by newTurnInit (separate in Player!) -> map is needed for Player by newTurnInit
    public final int effectOnMove;
    //TODO: care about by fight! (in fight class) -> map is needed for Player by fight
    public final int effectOnDefender;
    
    public final boolean isWinningPoint;    
    public final boolean isBase;
    
    public int valueForView;

    Cell(int xCoord, int yCoord, boolean availForUnits, boolean isEmpt, int effOnMove, int effOnDef, boolean isWinPoint, int prod, boolean isB, int forView){
        xPos = xCoord;
        yPos = yCoord;
        availableForUnits = availForUnits;
        isEmpty = isEmpt;
        
        production = prod;
        effectOnMove = effOnMove;
        effectOnDefender = effOnDef;
        
        isWinningPoint = isWinPoint;
        isBase = isB;
        
        valueForView = forView;
    }
}
