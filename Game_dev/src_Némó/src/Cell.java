package src;
	public class Cell {
		private int xPos;
	    private int yPos;
	    private final boolean availableForUnits;
	    private boolean isEmpty;
	    
	    private final int production;
	    //TODO: care about by newTurnInit (separate in Player!) -> map is needed for Player by newTurnInit
	    private final int effectOnMove;
	    //TODO: care about by fight! (in fight class) -> map is needed for Player by fight
	    private final int effectOnDefender;
	    
	    private final boolean isWinningPoint;    
	    private final boolean isBase;
	    
	    private int valueForView;

	   public  Cell(int xCoord, int yCoord, boolean availForUnits, boolean isEmpt, int effOnMove, int effOnDef, boolean isWinPoint, int prod, boolean isB, int forView){
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

		public int getxPos() {
			return xPos;
		}

		public void setxPos(int xPos) {
			this.xPos = xPos;
		}

		public int getyPos() {
			return yPos;
		}

		public void setyPos(int yPos) {
			this.yPos = yPos;
		}

		public boolean isEmpty() {
			return isEmpty;
		}

		public void setEmpty(boolean isEmpty) {
			this.isEmpty = isEmpty;
		}

		public int getValueForView() {
			return valueForView;
		}

		public void setValueForView(int valueForView) {
			this.valueForView = valueForView;
		}

		public boolean isAvailableForUnits() {
			return availableForUnits;
		}

		public int getProduction() {
			return production;
		}

		public int getEffectOnMove() {
			return effectOnMove;
		}

		public int getEffectOnDefender() {
			return effectOnDefender;
		}

		public boolean isWinningPoint() {
			return isWinningPoint;
		}

		public boolean isBase() {
			return isBase;
		}
	}

