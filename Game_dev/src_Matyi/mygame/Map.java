package mygame;

import java.io.Serializable;

public class Map implements Serializable{
	private static final long serialVersionUID = 1L;
	public int xLen;
	public int yLen;
    public Cell[][] grid;

    Map(initService initParam, Base homeBaseOne, Base homeBaseTwo){
        xLen = initParam.xLen;
        yLen = initParam.yLen;
    	grid = new Cell[xLen][yLen];
        for(int i = 0; i < xLen; i++) {
        	for(int j = 0; j < yLen; j++) {
            	if (i == homeBaseOne.xPos && j == homeBaseOne.yPos)
            		grid[i][j] = homeBaseOne;
            	else if (i == homeBaseTwo.xPos && j == homeBaseTwo.yPos)
            		grid[i][j] = homeBaseTwo;
            	else {
            		grid[i][j] = initParam.getGeneratedCell(i, j);
            	}
            }
        }
    }
}
