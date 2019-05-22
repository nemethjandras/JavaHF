package mygame;

import java.util.Arrays;

public class initService {
	
	//map parameters
	int xLen;
	int yLen;
	
	//player base parameters
	int xBaseOne;
	int yBaseOne;
	
	int xBaseTwo;
	int yBaseTwo;
	
	int startGold;
	
    int [][] cellType;
    //0 - not available for units
    //1 - base
    //2 - winning point
    //3 - production cell
    //4 - bonus action cell
    //5 - fight bonus cell
    //6 - simple cell without bonus
	
	
	public initService(String mapSize){
		this.startGold = 50;
		
		if(mapSize == "small"){
			this.xLen = 7;
			this.yLen = 7;
		}	
		
		else if(mapSize == "medium") {
			this.xLen = 9;
			this.yLen = 9;
		}
		
		else if(mapSize == "large") {
			this.xLen = 11;
			this.yLen = 11;
		}
		
		this.xBaseOne = (int)(Math.random() * xLen);			
		this.yBaseOne = 0;								//base on the left
		
		this.xBaseTwo =  (int)(Math.random() * yLen);	//base on the right [0,xLen)
		this.yBaseTwo = yLen - 1;	
		
		
	    cellType = new int[xLen][yLen];
	    for(int i = 0; i < xLen; i++) {
        	for(int j = 0; j < yLen; j++) {
            	cellType[i][j] = 10;	//init with dummy
            }
        }
	    
	    cellType[xBaseOne][yBaseOne] = 1;
	    cellType[xBaseTwo][yBaseTwo] = 1;
	    
		int xMid = (xLen - 1)/2;
		int yMid = (yLen - 1)/2;
		int remainCells = (xLen * yLen) - 2;	//minus base cells
		
		//unit generate cell: not available
		cellType[xBaseOne][yBaseOne+1] = 0;		//unit generate cell on the left
		cellType[xBaseTwo][yBaseTwo-1] = 0;		//unit generate cell on the right
		
		remainCells -= 2;		//minus unit generate cells;
		
		
		//notAvail cells at mid
		if (mapSize == "small") {		
			for(int i = 0; i < xLen; i++) {
				remainCells -= 1;
				if (i == 0 || i == 3 || i == 4) {
					cellType[i][yMid] = 0;		//notAvail at mid
				}
				else {
					cellType[i][yMid] = 6;		//simple at mid
				}
			}
		}
		
		else if (mapSize == "medium") {		//notAvail at mid
			for(int i = 0; i < xLen; i++) {
				remainCells -= 1;
				if (i == 0 || i == 1 || i == 4 || i == 5) {
					cellType[i][yMid] = 0;		//notAvail at mid
				}
				else {
					cellType[i][yMid] = 6;		//simple at mid
				}
			}
		}
		
		else if (mapSize == "large") {		
			for(int i = 0; i < xLen; i++) {
				remainCells -= 1;
				if (i == 1 || i == 2 || i == 3 || i == 7 || i == 8 || i == 9) {
					cellType[i][yMid] = 0;		//notAvail at mid
				}
				else {
					cellType[i][yMid] = 6;		//simple at mid
				}
			}
		}
		
		
		
		
		//winning point cells (half left, half right)
		int winPointNum = 0;
		while(winPointNum < 8){	
			int xRand = (int)(Math.random() * xLen);	//[0, xLen);
			int yRand = (int)(Math.random() * yMid);	//[0, yMid);
			if (cellType[xRand][yRand] == 10) {
				cellType[xRand][yRand] = 2;		//winning point on the left
				winPointNum += 1;
				remainCells -= 1;
			}
		}
		
		while(winPointNum < 16){	
			int xRand = (int)(Math.random() * xLen);	//[0, xLen)
			int yRand = (yMid + 1) + (int)(Math.random() * (yLen - (yMid + 1)));	//[yMid + 1, yLen);
			if (cellType[xRand][yRand] == 10) {
				cellType[xRand][yRand] = 2;		//winning point on the right
				winPointNum += 1;
				remainCells -= 1;
			}
		}
		
		
		
		//production cells (half left, half right)
		int prodNum = 0;
		if (mapSize == "small") {		
			prodNum = 10;
		}
		
		else if (mapSize == "medium") {		
			prodNum = 16;
		}
		
		else if (mapSize == "large") {		
			prodNum = 26;
		}
		
		int count = 0;
		
		while(count < prodNum/2){	
			int xRand = (int)(Math.random() * xLen);	//[0, xLen);
			int yRand = (int)(Math.random() * yMid);	//[0, yMid);
			if (cellType[xRand][yRand] == 10) {
				cellType[xRand][yRand] = 3;		//production cells on the left
				count += 1;
				remainCells -= 1;
			}
		}
		
		while(count < prodNum){	
			int xRand = (int)(Math.random() * xLen);	//[0, xLen)
			int yRand = (yMid + 1) + (int)(Math.random() * (yLen - (yMid + 1)));	//[yMid + 1, yLen);
			if (cellType[xRand][yRand] == 10) {
				cellType[xRand][yRand] = 3;		//production cells on the right
				count += 1;
				remainCells -= 1;
			}
		}
		
		
		
		
		//remaining: 2/4-nothing, 1/4-effectOnAction, 1/4-effectOnDefender, 
		
		//simple cells without effect (half left, half right)
		int simpleNum = 0;
		if (mapSize == "small") {		
			simpleNum = 6;
		}
		
		else if (mapSize == "medium") {		
			simpleNum = 18;
		}
		
		else if (mapSize == "large") {		
			simpleNum = 32;
		}
		
		count = 0;
		
		while(count < simpleNum/2){	
			int xRand = (int)(Math.random() * xLen);	//[0, xLen);
			int yRand = (int)(Math.random() * yMid);	//[0, yMid);
			if (cellType[xRand][yRand] == 10) {
				cellType[xRand][yRand] = 6;		//simple cells on the left
				count += 1;
				remainCells -= 1;
			}
		}
		
		while(count < simpleNum){	
			int xRand = (int)(Math.random() * xLen);	//[0, xLen)
			int yRand = (yMid + 1) + (int)(Math.random() * (yLen - (yMid + 1)));	//[yMid + 1, yLen);
			if (cellType[xRand][yRand] == 10) {
				cellType[xRand][yRand] = 6;		//simple cells on the right
				count += 1;
				remainCells -= 1;
			}
		}
		
		
		
		
		
		//bonus action cells
		int effActNum = 0;
		if (mapSize == "small") {		
			effActNum = 3;
		}
				
		else if (mapSize == "medium") {		
			effActNum = 9;
		}
				
		else if (mapSize == "large") {		
			effActNum = 16;
		}
				
		count = 0;
				
		while(count < effActNum){	
			int xRand = (int)(Math.random() * xLen);	//[0, xLen);
			int yRand = (int)(Math.random() * yLen);	//[0,yLen);
			if (cellType[xRand][yRand] == 10) {
				cellType[xRand][yRand] = 4;		//bonus action cells
				count += 1;
				remainCells -= 1;
			}
		}
		
		
		
		
		//bonus on defender cells
		int effDefNum = 0;
		if (mapSize == "small") {		
			effDefNum = 3;
		}
				
		else if (mapSize == "medium") {		
			effDefNum = 9;
		}
				
		else if (mapSize == "large") {		
			effDefNum = 16;
		}
				
		count = 0;
				
		while(count < effActNum){	
			int xRand = (int)(Math.random() * xLen);	//[0, xLen);
			int yRand = (int)(Math.random() * yLen);	//[0,yLen);
			if (cellType[xRand][yRand] == 10) {
				cellType[xRand][yRand] = 5;		//bonus on fight cells
				count += 1;
				remainCells -= 1;
			}
		}
		
		
		
		
		System.out.println("remainCells = " + Integer.toString(remainCells));
	}
	
	public Cell getGeneratedCell(int x, int y) {
	    
	    //cellType[x][y] == 1 -> base, never called    
	    
	    //Cell constructor parameters: xCoord, yCoord, forView, availForUnits, isWinPoint, production, effOnAction, effOnDef
	    
		if (cellType[x][y] == 0) {	//0 - not available for units
			return new Cell(x, y, 0, false, false, 0, 0, 0);
		}
		
		else if (cellType[x][y] == 2) {	//2 - winning point
			return new Cell(x, y, 2, true, true, 0, 0, 0);
		}
		
		else if (cellType[x][y] == 3) {	//3 - production cell
			return new Cell(x, y, 3, true, false, 10, 0, 0);
		}
		
		else if (cellType[x][y] == 4) {	//4 - bonus action cell
			return new Cell(x, y, 4, true, false, 0, 1, 0);
		}
		
		else if (cellType[x][y] == 5) {	//5 - fight bonus cell
			return new Cell(x, y, 5, true, false, 0, 0, 1);
		}
		
		else {	//6 - simple cell without bonus
			return new Cell(x, y, 6, true, false, 0, 0, 0);
		}
		
	}	
	
	public void printCellType() {
		int notAvCount = 0;
		int baseCount = 0;
		int winPointCount = 0;
		int prodCount = 0;
		int actCount = 0;
		int fightCount = 0;
		int simpleCount = 0;
		int dummyCount = 0;
		for(int i = 0; i < xLen; i++) {
			 System.out.println(Arrays.toString(cellType[i]));
			 for(int j = 0; j < yLen; j++) {
					if (cellType[i][j] == 0) {	//0 - not available for units
						notAvCount += 1;
					}
					
					else if (cellType[i][j] == 1) {	//2 - base cell 
						baseCount += 1;
					}
					
					else if (cellType[i][j] == 2) {	//2 - winning point
						winPointCount += 1;
					}
					
					else if (cellType[i][j] == 3) {	//3 - production cell
						prodCount += 1;
					}
					
					else if (cellType[i][j] == 4) {	//4 - bonus action cell
						actCount += 1;
					}
					
					else if (cellType[i][j] == 5) {	//5 - fight bonus cell
						fightCount += 1;
					}
					
					else if (cellType[i][j] == 6) {	//6 - simple cell
						simpleCount += 1;
					}
					
					else {	//10 - dummy
						dummyCount += 1; 
					}
			 }
		 }
		
		System.out.println("NotAv: " + Integer.toString(notAvCount));
		System.out.println("Base: " + Integer.toString(baseCount));
		System.out.println("WinPoint: " + Integer.toString(winPointCount));
		System.out.println("Production: " + Integer.toString(prodCount));
		System.out.println("ActBonus: " + Integer.toString(actCount));
		System.out.println("FightBonus: " + Integer.toString(fightCount));
		System.out.println("Simple: " + Integer.toString(simpleCount));
		System.out.println("Dummy: " + Integer.toString(dummyCount));
		
		System.out.println("\n\n\n");
	}
	 
}
