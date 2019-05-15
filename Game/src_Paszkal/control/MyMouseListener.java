package control;


import java.awt.*;
import java.awt.event.*;

import javax.swing.SwingUtilities;

import display.*;

public class MyMouseListener  extends MouseAdapter
{
	int posx;
	int posy;
	GameWindow target;
	
	public MyMouseListener(int x, int y, GameWindow t){
		posx=x;
		posy=y;
		target=t;
	}
			
	public void mouseClicked (MouseEvent e) 
	{	
		System.out.format("Clicked: %d, %d ", posx, posy);
		if(SwingUtilities.isLeftMouseButton(e)) 
		{
			System.out.format("left \n");
			target.select_display_move(posx, posy);
			//newselect
			//execute
		}
		if(SwingUtilities.isRightMouseButton(e)) 
		{
			System.out.format("right \n");
			target.select_display_remove();
			//remove select texture
		}
	}
  
  
}