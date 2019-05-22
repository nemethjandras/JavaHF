package mygame;

public class Cell {
	public int xPos;
    public int yPos;
    public final boolean availableForUnits;
    
    public final int production;
    public final int effectOnAction;
    public final int effectOnDefender;
    
    public final boolean isWinningPoint;    
    
    public int valueForView;
    //0 - not available for units
    //1 - base
    //2 - winning point
    //3 - production cell
    //4 - bonus action cell
    //5 - fight bonus cell
    //6 - simple cell without bonus

    Cell(int xCoord, int yCoord, int forView, boolean availForUnits, boolean isWinPoint, int prod, int effOnAction, int effOnDef){
        xPos = xCoord;
        yPos = yCoord;
        valueForView = forView;
        
        availableForUnits = availForUnits;
 
        isWinningPoint = isWinPoint;
        production = prod;
        
        effectOnAction = effOnAction;
        effectOnDefender = effOnDef;
        
        
    }
}
