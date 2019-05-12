package display;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import mygame.*;


class GameWindow{
	JFrame mainWindow = new JFrame("GameWindow");
	JPanel gameSpace=new JPanel(null); 	//thi will contain all the tiles
	JPanel gui_box=new JPanel(null); 	//thi will contain all the gui and hud info
	
    JLabel[][] tiles; 		//1 for each tile
    JLabel units; 		//1 for each unit
    
    int tiles_x;	//number of tiels in a row
    int tiles_y;	//number of tiles in a coloumn
    
    int map_sizeW;	//map width in pixels
    int map_sizeH;	//map height in pixels
    
    int stepH;		//tile height in pixels
    int stepW;		//tile width in pixels
    
	 public GameWindow(int map_width, int map_heigth, int gui_width) {
		 	mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
		 	//set sizes
	        mainWindow.setSize(map_width+gui_width, map_heigth);
	        gameSpace.setPreferredSize(new Dimension(map_width+gui_width, map_heigth));
	        map_sizeW=map_width;
	        map_sizeH=map_heigth;
	        
	        
	        //adding components to mainWindow
	        mainWindow.add(gui_box);
	        mainWindow.add(gameSpace);
	        
	        //launching window
	        mainWindow.validate();
	        mainWindow.pack();
	        mainWindow.setVisible(true);
	 }
	 
	 //print map
	 public void displayMap(int[][] test_map, int x, int y) {
		 tiles_x=x;
		 tiles_y=y;
		 stepH=map_sizeW / tiles_x;
		 stepW=map_sizeH / tiles_y;
		 
		 
		 tiles=new JLabel[x][y];
		 
		 //create texture buffers
		 BufferedImage default_texture = null;
		 //BufferedImage texture1 = null;
		 //BufferedImage texture2 = null;
		 //BufferedImage texture3 = null;
		 
		 //load textures
		 try {
			 default_texture = ImageIO.read(new File("D:\\github_reps\\JavaHF_rep\\Game\\assets\\default_tile.png"));
			 //texture1 = ImageIO.read(new File(""));
			 //texture2= ImageIO.read(new File(""));
			 //texture3= ImageIO.read(new File(""));
			} catch (IOException e) {
			    e.printStackTrace();
			}
		 
		 //size the textures
		 Image sized_texture_default = default_texture.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 //Image sized_texture1 = texture1.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 //Image sized_texture2 = texture2.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 //Image sized_texture3 = texture3.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 
		 //print map
		 for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				tiles[j][i]=new JLabel();
				tiles[j][i].setPreferredSize(new Dimension(stepW, stepH));
				tiles[j][i].setSize(stepW,stepH);
				
				switch (test_map[j][i]) {
				case 1:
					//tiles[i][j].setIcon(new ImageIcon(sized_texture1));
					break;
				case 2:
					//tiles[i][j].setIcon(new ImageIcon(sized_texture2));
					break;
				case 3:
					//tiles[i][j].setIcon(new ImageIcon(sized_texture3));
					break;
				default:
					tiles[j][i].setIcon(new ImageIcon(sized_texture_default));
					break;
				}			
				gameSpace.add(tiles[j][i]);
				tiles[j][i].setBounds(j*stepW, i*stepH, stepW, stepH);
			}
		}
	 }
		
	 
	 
	 //adds a unit to the display
	 public void displayUnit_add(int x, int y, int textureID) {}
	 
	 //moves a unit on the display (probably wont be used)
	 public void displayUnit_mov(int x_start, int y_start, int x_stop, int y_stop) {}
	 
	 //removes a unit from the display
	 public void displayUnit_del(int x, int y) {}
	 
	 //removes and redraws all units on map display
	 public void displayUpdate(int[] player_units, int[] enemy_units) {
		 
		 //remove all units
		 //
		 //jpanel.remove(label7);
		 //jpanel.revalidate();
		 //jpanel.repaint();
		 
		 
	 }
}

