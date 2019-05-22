package control;


import java.awt.*;
import java.awt.event.*;

import javax.swing.SwingUtilities;

import display.*;
import mygame.*;

public class ControlMouseListener  extends MouseAdapter
{
	int posx;
	int posy;
	GameWindow display;
	
	public ControlMouseListener(int x, int y, GameWindow t, Control c){
		posx=x;
		posy=y;
		display=t;

	}
			
	public void mousePressed (MouseEvent e) 
	{	
		//System.out.format("Clicked: %d, %d ", posx, posy);
		if(SwingUtilities.isLeftMouseButton(e) && display.onTurn) 
		{
			//System.out.format("left \n");
			display.select_display_move(posx, posy);
			
			display.control.newSelect(posx, posy,display.split_on);
			display.split_on=false;
			int temp=display.control.execute();
			if(1==temp)
			{
				System.out.format("valid command \n");
				display.select_display_remove();
				display.displayUpdate();
				display.control.resetSelect();
			}
			else if(temp==-1){
				System.out.format("invalid command \n");
				display.select_display_remove();
				display.control.resetSelect();
			}
			else if(temp==77) {
				System.out.format("ENEMY BASE DESTROYED \n");
			}
			else if(temp==2) {				
				System.out.format("first select \n");}
			
		}
		if(SwingUtilities.isRightMouseButton(e) && display.onTurn) 
		{
			//System.out.format("right \n");
			display.select_display_remove();

		}
	}
  
  
}