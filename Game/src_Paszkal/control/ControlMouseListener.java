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
			
	public void mouseClicked (MouseEvent e) 
	{	
		//System.out.format("Clicked: %d, %d ", posx, posy);
		if(SwingUtilities.isLeftMouseButton(e) && display.onTurn) 
		{
			//System.out.format("left \n");
			display.select_display_move(posx, posy);
			
			display.control.newSelect(posx, posy,display.split_on);
			display.split_on=false;
			if(1==display.control.execute())
			{
				System.out.format("valid command \n");
				display.displayUpdate();
			}
			else {
				System.out.format("invalid command \n");
			}
			
		}
		if(SwingUtilities.isRightMouseButton(e) && display.onTurn) 
		{
			//System.out.format("right \n");
			display.select_display_remove();

		}
	}
  
  
}