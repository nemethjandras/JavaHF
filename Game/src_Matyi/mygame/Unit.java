package mygame;

abstract public class Unit {
	public int healthValue;
    public final int attackValue;
    public int availableAction;
    public final int actionPerRound;
    public final int attackRange;
    public int xPos;
    public int yPos;
    public int type;

    protected Unit(int healthVal, int attackVal, int availableAct, int actPerRound, int attRange, int xPosi, int yPosi) {
        healthValue = healthVal;
        attackValue = attackVal;
        availableAction = availableAct;
        actionPerRound = actPerRound;
        attackRange = attRange;
        xPos = xPosi;
        yPos = yPosi;
    }

    public void fight() {
        if (availableAction > 0){
            availableAction -= 1;
        }
        else {
            System.out.println("Fight action cannot be made, 0 action is available for this unit");
        }
    }

    public void move() {
        if (availableAction > 0){
            availableAction -= 1;
        }
        else {
            System.out.println("Move action cannot be made, 0 action is available for this unit");
        }
    }


    public void newTurn(){}
}
