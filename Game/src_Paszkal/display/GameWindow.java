package display;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import mygame.*;
import control.*;

public class GameWindow{
	int onTurn;
	Control control;
	
	//frame specific variables
	JFrame mainWindow = new JFrame("GameWindow");
	JPanel gameSpace=new JPanel(null); 	//thi will contain all the tiles
	JPanel gui_box=new JPanel(null); 	//thi will contain all the gui and hud info
	int gui_sizeW;
	
	//buttons and hud
	JButton b_split = new JButton();
	JButton b_buy1 = new JButton();
	JButton b_buy2 = new JButton();
	JButton b_buy3 = new JButton();
	JButton b_buy4 = new JButton();
	JButton b_turnover = new JButton();
	
	//game display variables
    JLabel[][] tiles; 		//1 for each tile
    JLabel[] units; 		//1 for each unit
    JLabel select=null;
    int unitNum;
    int tiles_x;	//number of tiels in a row
    int tiles_y;	//number of tiles in a coloumn
    int map_sizeW;	//map width in pixels
    int map_sizeH;	//map height in pixels
    int stepH;		//tile height in pixels
    int stepW;		//tile width in pixels
    
    //textures
    Image select_mark;
    Image default_tile_texture;
    Image default_unit_texture;
    //add more textures
    
	 public GameWindow(int map_width, int map_heigth, int gui_width) {
		 	mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        unitNum=0;
		 	
		 	//set sizes
	        mainWindow.setSize(map_width+gui_width, map_heigth);
	        gameSpace.setPreferredSize(new Dimension(map_width+gui_width, map_heigth));
	        map_sizeW=map_width;
	        map_sizeH=map_heigth;
	        gui_sizeW=gui_width;
	        
	        
	        //adding components to mainWindow
	        mainWindow.add(gui_box);
	        mainWindow.add(gameSpace);
	        
	        //launching window
	        mainWindow.validate();
	        mainWindow.pack();
	        mainWindow.setVisible(true);
	 }
	 
	 
	 public void addButtons() { 
		 //change to text space type
		    b_split.setSize(gui_sizeW,map_sizeH/20);
		    b_split.setBounds(map_sizeW, map_sizeH/20*3+map_sizeH/2, gui_sizeW, map_sizeH/20);
		    gameSpace.add(b_split);
		    b_split.setVisible(true);
		    b_split.setText("Split the Unit");
		    
		 //fill with function call
		    b_buy1.setSize(gui_sizeW,map_sizeH/20);
		    b_buy1.setBounds(map_sizeW, map_sizeH/20*4+map_sizeH/2, gui_sizeW, map_sizeH/20);
		    gameSpace.add(b_buy1);
		    b_buy1.setVisible(true);
		    b_buy1.setText("Buy unit1");
		    
		    b_buy2.setSize(gui_sizeW,map_sizeH/20);
		    b_buy2.setBounds(map_sizeW, map_sizeH/20*5+map_sizeH/2, gui_sizeW, map_sizeH/20);
		    gameSpace.add(b_buy2);
		    b_buy2.setVisible(true);
		    b_buy2.setText("Buy unit2");
		    
		    b_buy3.setSize(gui_sizeW,map_sizeH/20);
		    b_buy3.setBounds(map_sizeW, map_sizeH/20*6+map_sizeH/2, gui_sizeW, map_sizeH/20);
		    gameSpace.add(b_buy3);
		    b_buy3.setVisible(true);
		    b_buy3.setText("Buy unit3");
		    
		    b_buy4.setSize(gui_sizeW,map_sizeH/20);
		    b_buy4.setBounds(map_sizeW, map_sizeH/20*7+map_sizeH/2, gui_sizeW, map_sizeH/20);
		    gameSpace.add(b_buy4);
		    b_buy4.setVisible(true);
		    b_buy4.setText("Buy unit4");
		 
		  //set onTurn flag
		    b_turnover.setSize(gui_sizeW,map_sizeH/20);
		    b_turnover.setBounds(map_sizeW, map_sizeH/20*9+map_sizeH/2, gui_sizeW, map_sizeH/20);
		    gameSpace.add(b_turnover);
		    b_turnover.setVisible(true);
		    b_turnover.setText("END TURN");
	 }
	 
	 //print map
	 public void displayMap(int[][] test_map, int x, int y) {
		 tiles_x=x;
		 tiles_y=y;
		 stepH=map_sizeW / tiles_x;
		 stepW=map_sizeH / tiles_y;
		 loadTextures();
		 tiles=new JLabel[x][y];
		
		 
		 //print map
		 for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				tiles[j][i]=new JLabel();
				tiles[j][i].setPreferredSize(new Dimension(stepW, stepH));
				tiles[j][i].setSize(stepW,stepH);;
				switch (test_map[j][i]) {
				case 1:
					//tiles[i][j].setIcon(new ImageIcon(tile_texture1));
					break;
				case 2:
					//tiles[i][j].setIcon(new ImageIcon(tile_texture2));
					break;
				case 3:
					//tiles[i][j].setIcon(new ImageIcon(tile_texture3));
					break;
				default:
					tiles[j][i].setIcon(new ImageIcon(default_tile_texture));
					break;
				}			
				gameSpace.add(tiles[j][i]);
				tiles[j][i].setBounds(j*stepW, i*stepH, stepW, stepH);
			    tiles[j][i].addMouseListener(new MyMouseListener(j, i,this, control));

			}
		}
		 gameSpace.revalidate();
	 }
		
	 //adds a unit to the display
	 public void loadTextures() {
		 //buffer tile textures
		 BufferedImage default_tile_texture_buffer = null;
		 //BufferedImage tile_texture1_buffer = null;
		 //BufferedImage tile_texture2_buffer = null;
		 //BufferedImage tile_texture3_buffer = null;
		 
		 //load textures
		 try {
			 default_tile_texture_buffer = ImageIO.read(new File("D:\\github_reps\\JavaHF_rep\\Game\\assets\\default_tile.png"));
			 //tile_texture1_buffer = ImageIO.read(new File(""));
			 //tile_texture2_buffer = ImageIO.read(new File(""));
			 //tile_texture3_buffer = ImageIO.read(new File(""));
			} catch (IOException e){
			    e.printStackTrace();
			}
		 
		 //size the textures
		 default_tile_texture = default_tile_texture_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 //tile_texture1 = tile_texture1_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 //tile_texture2 = tile_texture2_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 //tile_texture3 = tile_texture3_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 
		 //buffer unit images
		 BufferedImage default_unit_texture_buffer = null;
		 //BufferedImage unit_texture1_buffer = null;
		 //BufferedImage unit_texture2_buffer = null;
		 //BufferedImage unit_texture3_buffer = null;
		 
		 //load textures
		 try {
			 default_unit_texture_buffer = ImageIO.read(new File("D:\\github_reps\\JavaHF_rep\\Game\\assets\\default_unit.png"));
			 //unit_texture1_buffer = ImageIO.read(new File(""));
			 //unit_texture2_buffer = ImageIO.read(new File(""));
			 //unit_texture3_buffer = ImageIO.read(new File(""));
			} catch (IOException e){
			    e.printStackTrace();
			}
		 
		 //size the textures
		default_unit_texture = default_unit_texture_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 //unit_texture1 = unit_texture1_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 //unit_texture2 = unit_texture2_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 //unit_texture3 = unit_texture3_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
	 
	 
	 //load select mark
	 BufferedImage select_mark_buffer = null;
	 try {
		 select_mark_buffer = ImageIO.read(new File("D:\\github_reps\\JavaHF_rep\\Game\\assets\\select_mark.png"));
		} catch (IOException e){
		    e.printStackTrace();
		}
	select_mark = select_mark_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
 }
	 
	 //removes and redraws all units on map display
	 public void displayUpdate(int[] type, int[] posx, int[] posy, int num) {
		 
		
		 for (int i = 0; i < unitNum; i++) {
			gameSpace.remove(units[i]);
		}
		units=null;
		gameSpace.revalidate();
		gameSpace.repaint();
		 
		 
		unitNum=num;
		 units=new JLabel[num];
		 for (int i = 0; i < num; i++){
			    units[i]=new JLabel();
				units[i].setPreferredSize(new Dimension(stepW, stepH));
				units[i].setSize(stepW,stepH);
				
				switch (type[i]) {
				case 1:
					//tiles[i][j].setIcon(new ImageIcon(tile_texture1));
					break;
				case 2:
					//tiles[i][j].setIcon(new ImageIcon(tile_texture2));
					break;
				case 3:
					//tiles[i][j].setIcon(new ImageIcon(tile_texture3));
					break;
				default:
					units[i].setIcon(new ImageIcon(default_unit_texture));
					break;
				}			
				gameSpace.add(units[i],0);
				units[i].setBounds(posx[i]*stepW, posy[i]*stepH, stepW, stepH);
		}
		gameSpace.revalidate();
	 }

	 public void select_display_move(int x, int y){
		 if (select==null){
			select=new JLabel();
			select.setPreferredSize(new Dimension(stepW, stepH));
			select.setSize(stepW,stepH);
			select.setIcon(new ImageIcon(select_mark));
			gameSpace.add(select,0);
		 }
		 select.setBounds(x*stepW, y*stepH, stepW, stepH);
		 gameSpace.revalidate();
	 }
	 
	 public void select_display_remove(){
		 gameSpace.remove(select);
		 select=null;
		 gameSpace.revalidate();
		 gameSpace.repaint();
	 }
}

