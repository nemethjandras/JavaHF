
Playerek példányosítása és map generálása-példányosítása: Game classon keresztül, initService class segítségével


	initService mapGenerator = new initService("small");	//itt a paraméter a pálya mérete, ez lehet: "small":7x7, "medium":9x9, "large":11x11
	Game game = new Game(mapGenerator);						
															//innentől kezdve elérhetők a játékosok és a térkép
															//game.playerOne (FONTOS: ez a térkép bal oldalán lévő játékos!)
															//game.playerTwo (FONTOS: ez a térkép jobb oldalán lévő játékos!)
															//game.map a térkép, game.map.grid[][]-al lehet a cellára hivatkozni (2D tömb a Cell class példányaiból)












Cellák típusa megjelenítéshez:

	map.grid[i][j].valueForView változó értéke a következők értékeket veheti fel:
	    //0 - not available for units
	    //1 - base
	    //2 - winning point
	    //3 - production cell
	    //4 - bonus action (+1 action if the unit stays there at the end of the turn) cell
	    //5 - fight bonus ( (+1 * number of units in stack) on defender) cell
	    //6 - simple cell without bonus








Player függvényeinek használata:


	akciókkor meghívandó:

			int move(int xPrev, int yPrev, int xCurr, int yCurr, Cell curr)
				visszatérhet: 1 - sikeres végrehajtás
							 -1 - invalid akció


			int merge(int xPrev, int yPrev, int xCurr, int yCurr, Cell curr)
				visszatérhet: 1 - sikeres végrehajtás
							 -1 - invalid akció


			int split(int xPrev, int yPrev, int xCurr, int yCurr, Cell curr, int numOfSplitted)
				visszatérhet: 1 - sikeres végrehajtás
							 -1 - invalid akció


			int splitAndMerge(int xPrev, int yPrev, int xCurr, int yCurr, Cell curr, int numOfSplitted)
				visszatérhet: 1 - sikeres végrehajtás
							 -1 - invalid akció


			int fight(int xPrev, int yPrev, int xCurr, int yCurr, Map map, Player defender)
				visszatérhet: 1 - sikeres végrehajtás
							 -1 - invalid akció
							 77 - a játékos lenullázta az ellenfél bázisát és nyert






	vásárláskor meghívandó:

			int buyUnit(int unitType, int amount)
				paraméterek: unitType 		---> 0-Worker, 1-Infantry, 2-Archer, 3-Paladin
				visszatérhet: 1 - sikeres végrehajtás
							 -1 - invalid egység vásárlás (pl játékos 1 körben csak egyszer vásárolhat, stb.)


			int buyDevelopement(int devNum)
				paraméterek: unitType		---> 0-Worker árcsökkentés, 1-Infatry árcsökkentés, 2-Archer árcsökkentés, 3-Paladin árcsökkentés,  
	    										 4-Worker termelésnövelés, 5-Infatry élet+sebzésnövelés, 6-Archer élet+sebzésnövelés, 7-Paladin élet+sebzésnövelés
				visszatérhet: 1 - sikeres végrehajtás
							 -1 - invalid fejlesztés vásárlás







	játékos körének lezárásakor meghívandó:

			int endTurn(Map map)
				visszatérhet: 1 - sikeres végrehajtás
							 77 - a játékos összegyűjtött 13 győzelmi cellát és nyert




