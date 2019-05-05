package mygame;

public class initService {
	
	//map parameters
	int xLen;
	int yLen;
	
	//player parameters
	String nameOne;
	int xBaseOne;
	int yBaseOne;
	String nameTwo;
	int xBaseTwo;
	int yBaseTwo;
	int startGold;
	
	initService(String type, String pNameOne, String pNameTwo){
		if(type == "test"){
			this.xLen = 10;
			this.yLen = 10;
			
			this.nameOne = pNameOne;
			this.xBaseOne = 0;
			this.yBaseOne = 0;
			this.nameTwo = pNameTwo;
			this.xBaseTwo = 9;
			this.yBaseTwo = 9;
			this.startGold = 50;
		}
	}
	
	public Cell getGeneratedCell(int x, int y) {
		return new Cell(x, y, true, true, 0, 0, false, 0, false);
	}	
}
