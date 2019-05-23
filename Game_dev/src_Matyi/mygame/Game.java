package mygame;

import java.io.Serializable;

public class Game implements Serializable{
	private static final long serialVersionUID = 726040503589477458L;
	public Player playerOne;
    public Player playerTwo;
    public Map map;

    public Game(initService initParam){
        playerOne = new Player(initParam.xBaseOne, initParam.yBaseOne, initParam.startGold);
        playerTwo = new Player(initParam.xBaseTwo, initParam.yBaseTwo, initParam.startGold);
        map = new Map(initParam, playerOne.homeBase, playerTwo.homeBase);
    }
}
