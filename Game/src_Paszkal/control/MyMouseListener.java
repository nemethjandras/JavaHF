package control;


import java.awt.*;
import java.awt.event.*;

import javax.swing.SwingUtilities;

import display.*;
import mygame.*;

public class MyMouseListener  extends MouseAdapter
{
	int posx;
	int posy;
	GameWindow display;
	Control control;
	
	public MyMouseListener(int x, int y, GameWindow t, Control c){
		posx=x;
		posy=y;
		display=t;
		control=c;
	}
			
	public void mouseClicked (MouseEvent e) 
	{	
		System.out.format("Clicked: %d, %d ", posx, posy);
		if(SwingUtilities.isLeftMouseButton(e)) 
		{
			System.out.format("left \n");
			display.select_display_move(posx, posy);
			//control.newSelect(posx, posy);
			//control.execute();
		}
		if(SwingUtilities.isRightMouseButton(e)) 
		{
			System.out.format("right \n");
			display.select_display_remove();
			//control.resetSelect();
		}
	}
  
  
}