package mygame;

public class Map {
	int xLen;
	int yLen;
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
