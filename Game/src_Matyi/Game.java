package mygame;

public class Game {
    public Player playerOne;
    public Player playerTwo;
    public Map map;

    Game(initService initParam){
        playerOne = new Player(initParam.nameOne, initParam.xBaseOne, initParam.yBaseOne, initParam.startGold);
        playerTwo = new Player(initParam.nameTwo, initParam.xBaseTwo, initParam.yBaseTwo, initParam.startGold);
        map = new Map(initParam, playerOne.homeBase, playerTwo.homeBase);
    }
}
