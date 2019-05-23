package display;

import java.awt.Desktop.*;

import java.awt.*;
//import java.awt.desktop.PrintFilesEvent;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.*;

import mygame.*;
import control.*;

public class GameWindow{
	public boolean onTurn=true; //change to false, only true for testing
	public boolean split_on=false;
	public Control control;
	public Map map;
	public boolean sandbox_mode=false;
	public boolean win=false;
	public boolean lose=false;
	
	//frame specific variables
	JFrame mainWindow = new JFrame("GameWindow");
	JPanel gameSpace=new JPanel(null); 	//thi will contain all the tiles
	JPanel gui_box=new JPanel(null); 	//thi will contain all the gui and hud info
	int gui_sizeW;
	
	//buttons and hud
	String msg="Money: ";
	String msg1="Your Base's HP:  ";
	String msg2="Enemy Base's HP: ";
	String money="0";
	String base_hp1="0";
	String base_hp2="0";
	
	JLabel hud1=new JLabel(msg+money);
	JLabel hud2=new JLabel(msg1+base_hp1);
	JLabel hud3=new JLabel(msg2+base_hp2);
	JButton b_split = new JButton();
	JButton b_buy1 = new JButton();
	JButton b_buy2 = new JButton();
	JButton b_buy3 = new JButton();
	JButton b_buy4 = new JButton();
	JButton b_turnover = new JButton();
	
	//game display variables
	JLabel offTurn=null;
	JLabel victory_msg=null;
	JLabel defeat_msg=null;
	 String[] buy_nums= {"0","1","2","3","4","5","6","7","8","9","10"};
	JComboBox c_buy1=new JComboBox(buy_nums);
	JComboBox c_buy2=new JComboBox(buy_nums);
	JComboBox c_buy3=new JComboBox(buy_nums);
	JComboBox c_buy4=new JComboBox(buy_nums);
	String temp1="0";
	String temp2="0";
	String temp3="0";
	String temp4="0";
	JLabel yourBase;
	JLabel enemyBase;
    JLabel[][] tiles; 		//1 for each tile
    JLabel[] units; 		//1 for each unit
    JLabel select=null;
    JLabel select_old=null;
    int unitNum;
    int tiles_x;	//number of tiels in a row
    int tiles_y;	//number of tiles in a coloumn
    int map_sizeW;	//map width in pixels
    int map_sizeH;	//map height in pixels
    int stepH;		//tile height in pixels
    int stepW;		//tile width in pixels
    
    //textures
    Image victory;
    Image defeat;
    Image offTurn_msg;
    Image select_mark;
    Image select_mark_old;
    Image default_tile_texture;
    Image default_unit_texture;
    
    Image tile_0_texture;
    Image tile_1_texture;
    Image tile_2_texture;
    Image tile_3_texture;
    Image tile_4_texture;
    Image tile_5_texture;
    Image tile_6_texture;
    
    Image p_base;
    Image e_base;
    
    Image p_unit0_texture;
    Image p_unit1_texture;
    Image p_unit2_texture;
    Image p_unit3_texture;
    
    Image e_unit0_texture;
    Image e_unit1_texture;
    Image e_unit2_texture;
    Image e_unit3_texture;
    //add more textures
    
	 public GameWindow(int map_width, int map_heigth, int gui_width, Map newmap) {
		 	mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        unitNum=0;
	        map=newmap;
	        tiles_x=-1;
	        tiles_y=-1;
		 	
		 	//set sizes
	        mainWindow.setSize(map_width+gui_width, map_heigth);
	        mainWindow.setMinimumSize(new Dimension(600, 400));
	        gameSpace.setPreferredSize(new Dimension(map_width+gui_width, map_heigth));
	        map_sizeW=map_width;
	        map_sizeH=map_heigth;
	        gui_sizeW=gui_width;
	        /*
	        mainWindow.addComponentListener(new ComponentAdapter( ) {
	        	  public void componentResized(ComponentEvent ev) {
	        	   map_sizeH=mainWindow.getHeight()-mainWindow.getInsets().top-mainWindow.getInsets().bottom;
	        	   map_sizeW=map_sizeH;
	        	   gui_sizeW=mainWindow.getWidth()-map_sizeW-mainWindow.getInsets().left-mainWindow.getInsets().right;
	        	   if(gui_sizeW<200)
	        		   mainWindow.setSize(mainWindow.getWidth()+10, mainWindow.getHeight());
	        	   displayMap();
	        	   displayUpdate();
	        	   createGui();
	        	  }
	        	});
	        	*/
	        
	        //adding components to mainWindow
	        mainWindow.add(gui_box);
	        mainWindow.add(gameSpace);
	        
	        //launching window
	        mainWindow.validate();
	        mainWindow.pack();
	        mainWindow.setVisible(true);
	 }
	 
	 
	 public void createGui() { 
			 hud1.setPreferredSize(new Dimension(map_sizeW/2, map_sizeW/8));
			 hud1.setSize(gui_sizeW,map_sizeH/10);
			 gameSpace.add(hud1,0);
			 hud1.setBounds(map_sizeW+gui_sizeW/10, 0, gui_sizeW, map_sizeH/10);
			 hud1.setFont (hud1.getFont ().deriveFont (22.0f));
			 
			 hud2.setPreferredSize(new Dimension(map_sizeW/2, map_sizeW/8));
			 hud2.setSize(gui_sizeW,map_sizeH/10);
			 gameSpace.add(hud2,0);
			 hud2.setBounds(map_sizeW+gui_sizeW/10, map_sizeH/10, gui_sizeW, map_sizeH/10);
			 hud2.setFont (hud2.getFont ().deriveFont (22.0f));
			 
			 hud3.setPreferredSize(new Dimension(map_sizeW/2, map_sizeW/8));
			 hud3.setSize(gui_sizeW,map_sizeH/10);
			 gameSpace.add(hud3,0);
			 hud3.setBounds(map_sizeW+gui_sizeW/10, map_sizeH/10*2, gui_sizeW, map_sizeH/10);
			 hud3.setFont (hud3.getFont ().deriveFont (22.0f));
			 
		 	c_buy1.setSize(gui_sizeW,map_sizeH/20);
		 	c_buy1.setBounds(map_sizeW+gui_sizeW/10*7, map_sizeH/10*4, gui_sizeW/10*3, map_sizeH/10);
		    gameSpace.add(c_buy1);
		    c_buy1.setVisible(true);
		    c_buy1.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
		          temp1 = (String)c_buy1.getSelectedItem();
		      }
		    });
		    
		 	c_buy2.setSize(gui_sizeW,map_sizeH/20);
		 	c_buy2.setBounds(map_sizeW+gui_sizeW/10*7, map_sizeH/10*5, gui_sizeW/10*3, map_sizeH/10);
		    gameSpace.add(c_buy2);
		    c_buy2.setVisible(true);
		    c_buy2.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
		          temp2 = (String)c_buy2.getSelectedItem();
		      }
		    });
		    
		 	c_buy3.setSize(gui_sizeW,map_sizeH/20);
		 	c_buy3.setBounds(map_sizeW+gui_sizeW/10*7, map_sizeH/10*6, gui_sizeW/10*3, map_sizeH/10);
		    gameSpace.add(c_buy3);
		    c_buy3.setVisible(true);
		    c_buy3.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
		          temp3 = (String)c_buy3.getSelectedItem();
		      }
		    });
		    
		 	c_buy4.setSize(gui_sizeW,map_sizeH/20);
		 	c_buy4.setBounds(map_sizeW+gui_sizeW/10*7, map_sizeH/10*7, gui_sizeW/10*3, map_sizeH/10);
		    gameSpace.add(c_buy4);
		    c_buy4.setVisible(true);
		    c_buy4.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
		          temp4 = (String)c_buy4.getSelectedItem();
		      }
		    });
		 
		 // BUTTONS
		    b_split.setSize(gui_sizeW,map_sizeH/20);
		    b_split.setBounds(map_sizeW, map_sizeH/10*3, gui_sizeW, map_sizeH/10);
		    gameSpace.add(b_split);
		    b_split.setVisible(true);
		    b_split.setText("Split units");
		    b_split.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
		    	  split_on=true;
		    	  select_display_remove();
		    	  control.resetSelect();
		      }
		    });
		    
		    b_buy1.setSize(gui_sizeW,map_sizeH/20);
		    b_buy1.setBounds(map_sizeW, map_sizeH/10*4, gui_sizeW/10*7, map_sizeH/10);
		    gameSpace.add(b_buy1);
		    b_buy1.setVisible(true);
		    b_buy1.setText("Buy Worker");
		    b_buy1.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
		    	  System.out.format("buy1:%d\n",Integer.parseInt(temp1));
		    	  //buy Integer.parseInt(temp1) units of type 1
		    	  control.player.buyUnit(0, Integer.parseInt(temp1));
		    	  displayUpdate();
		 		  updateMoneyDisplay();
		 		 select_display_remove();
		    	  control.resetSelect();
		      }
		    });
		    
		    b_buy2.setSize(gui_sizeW,map_sizeH/20);
		    b_buy2.setBounds(map_sizeW, map_sizeH/10*5, gui_sizeW/10*7, map_sizeH/10);
		    gameSpace.add(b_buy2);
		    b_buy2.setVisible(true);
		    b_buy2.setText("Buy Infantry");
		    b_buy2.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
		    	  if(onTurn) {
		    	  System.out.format("buy2:%d\n",Integer.parseInt(temp2));
		    	  //buy Integer.parseInt(temp2) units of type 2
		    	  control.player.buyUnit(1, Integer.parseInt(temp2));
		    	  displayUpdate();
		 		  updateMoneyDisplay();
		 		 select_display_remove();
		    	  control.resetSelect();
		    	  }
		      }
		    });
		    
		    b_buy3.setSize(gui_sizeW,map_sizeH/20);
		    b_buy3.setBounds(map_sizeW, map_sizeH/10*6, gui_sizeW/10*7, map_sizeH/10);
		    gameSpace.add(b_buy3);
		    b_buy3.setVisible(true);
		    b_buy3.setText("Buy Archer");
		    b_buy3.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
		    	  if(onTurn) {
		    	  System.out.format("buy3:%d\n",Integer.parseInt(temp3));
		    	  //buy Integer.parseInt(temp3) units of type 3
		    	  control.player.buyUnit(2, Integer.parseInt(temp3));
		    	  displayUpdate();
		 		  updateMoneyDisplay();
		 		 select_display_remove();
		    	  control.resetSelect();
		    	  }
		      }
		    });
		    
		    b_buy4.setSize(gui_sizeW,map_sizeH/20);
		    b_buy4.setBounds(map_sizeW, map_sizeH/10*7, gui_sizeW/10*7, map_sizeH/10);
		    gameSpace.add(b_buy4);
		    b_buy4.setVisible(true);
		    b_buy4.setText("Buy Paladin");
		    b_buy4.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
		    	  if(onTurn) {
		    	  System.out.format("buy4:%d\n",Integer.parseInt(temp4));
		    	  //buy Integer.parseInt(temp4) units of type 4
		    	  control.player.buyUnit(3, Integer.parseInt(temp4));
		    	  displayUpdate();
		    	  updateMoneyDisplay();
		    	  select_display_remove();
		    	  control.resetSelect();
		    	  }
		      }
		    });
		    
		    b_turnover.setSize(gui_sizeW,map_sizeH/20);
		    b_turnover.setBounds(map_sizeW, map_sizeH/10*9, gui_sizeW, map_sizeH/10);
		    gameSpace.add(b_turnover);
		    b_turnover.setVisible(true);
		    b_turnover.setText("END TURN");
		    b_turnover   .addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
		    	  if(onTurn) {
		    	  System.out.format("endturn\n");
		    	  select_display_remove();
		    	  endTurn();
		    	  if (sandbox_mode) {
		    		  startTurn();
				}
		    	  }
		      }
		    });
		    
	 }
	 
	 //print map
	 public void displayMap() {
		 
		 int x=map.xLen;
		 int y=map.yLen;
		 
		 tiles_x=x;
		 tiles_y=y;
		 stepH=map_sizeW / tiles_x;
		 stepW=map_sizeH / tiles_y;
		 loadTextures();
		 tiles=new JLabel[x][y];
		 System.out.format("Dimensions: %d %d\n", x,y);

		 
		 //print map
		 for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				tiles[j][i]=new JLabel();
				tiles[j][i].setPreferredSize(new Dimension(stepW, stepH));
				tiles[j][i].setSize(stepW,stepH);
				switch (map.grid[j][i].valueForView) {
				
				case 0:
					tiles[j][i].setIcon(new ImageIcon(tile_0_texture));
					break;
				case 1:
					tiles[j][i].setIcon(new ImageIcon(tile_1_texture));
					break;
				case 2:
					tiles[j][i].setIcon(new ImageIcon(tile_2_texture));
					break;
				case 3:
					tiles[j][i].setIcon(new ImageIcon(tile_3_texture));
					break;
				case 4:
					tiles[j][i].setIcon(new ImageIcon(tile_4_texture));
					break;
				case 5:
					tiles[j][i].setIcon(new ImageIcon(tile_5_texture));
					break;
				case 6:
					tiles[j][i].setIcon(new ImageIcon(tile_6_texture));
					break;
					
				default:
					tiles[j][i].setIcon(new ImageIcon(default_tile_texture));
					break;
				}
				//System.out.format("%d\t", map.grid[j][i].valueForView);
				gameSpace.add(tiles[j][i]);
				tiles[j][i].setBounds(j*stepW, i*stepH, stepW, stepH);
			    tiles[j][i].addMouseListener(new ControlMouseListener(j, i,this, control));

			}
		}
		 
		 System.out.format("%d %d\t", control.player.homeBase.xPos,control.player.homeBase.yPos);
		 System.out.format("%d %d\t", control.enemy.homeBase.xPos,control.enemy.homeBase.yPos);
		 
			yourBase=new JLabel();
			yourBase.setPreferredSize(new Dimension(stepW, stepH));
			yourBase.setSize(stepW,stepH);
			yourBase.setIcon(new ImageIcon(p_base));
			gameSpace.add(yourBase,0);
			yourBase.setBounds(control.player.homeBase.xPos*stepW, control.player.homeBase.yPos*stepH, stepW, stepH);
			
			enemyBase=new JLabel();
			enemyBase.setPreferredSize(new Dimension(stepW, stepH));
			enemyBase.setSize(stepW,stepH);
			enemyBase.setIcon(new ImageIcon(e_base));
			gameSpace.add(enemyBase,0);
			enemyBase.setBounds(control.enemy.homeBase.xPos*stepW, control.enemy.homeBase.yPos*stepH, stepW, stepH);

		 gameSpace.revalidate();
	 }
		
	 //adds a unit to the display
	 public void loadTextures() {
		 //buffer tile textures
		 BufferedImage default_tile_texture_buffer = null;
		 BufferedImage tile_texture0_buffer = null;
		 BufferedImage tile_texture1_buffer = null;
		 BufferedImage tile_texture2_buffer = null;
		 BufferedImage tile_texture3_buffer = null;
		 BufferedImage tile_texture4_buffer = null;
		 BufferedImage tile_texture5_buffer = null;
		 BufferedImage tile_texture6_buffer = null;
		 
		 BufferedImage e_base_buff = null;
		 BufferedImage p_base_buff = null;
		 
		 //load textures
		 try {
			 default_tile_texture_buffer = ImageIO.read(new File(".\\assets\\default_tile.png"));
			 tile_texture0_buffer = ImageIO.read(new File(".\\assets\\0_tile.png"));
			 tile_texture1_buffer = ImageIO.read(new File(".\\assets\\1_tile.png"));
			 tile_texture2_buffer = ImageIO.read(new File(".\\assets\\2_tile.png"));
			 tile_texture3_buffer = ImageIO.read(new File(".\\assets\\3_tile.png"));
			 tile_texture4_buffer = ImageIO.read(new File(".\\assets\\4_tile.png"));
			 tile_texture5_buffer = ImageIO.read(new File(".\\assets\\5_tile.png"));
			 tile_texture6_buffer = ImageIO.read(new File(".\\assets\\6_tile.png"));
			 
			 e_base_buff = ImageIO.read(new File(".\\assets\\e_base.png"));
			 p_base_buff = ImageIO.read(new File(".\\assets\\p_base.png"));
			} catch (IOException e){
			    e.printStackTrace();
			}
		 
		 //size the textures
		 default_tile_texture = default_tile_texture_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 tile_0_texture = tile_texture0_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 tile_1_texture = tile_texture1_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 tile_2_texture = tile_texture2_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 tile_3_texture = tile_texture3_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 tile_4_texture = tile_texture4_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 tile_5_texture = tile_texture5_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 tile_6_texture = tile_texture6_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 
		 p_base = p_base_buff.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		 e_base = e_base_buff.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);

		 
		 //buffer unit images
		 BufferedImage default_unit_texture_buffer = null;
		 BufferedImage p_unit0_texture_buffer = null;
		 BufferedImage p_unit1_texture_buffer = null;
		 BufferedImage p_unit2_texture_buffer = null;
		 BufferedImage p_unit3_texture_buffer = null;
		 BufferedImage e_unit0_texture_buffer = null;
		 BufferedImage e_unit1_texture_buffer = null;
		 BufferedImage e_unit2_texture_buffer = null;
		 BufferedImage e_unit3_texture_buffer = null;
		 
		 //load textures
		 try {
			 default_unit_texture_buffer = ImageIO.read(new File(".\\assets\\default_unit.png"));
			 p_unit0_texture_buffer = ImageIO.read(new File(".\\assets\\p_unit0.png"));
			 p_unit1_texture_buffer = ImageIO.read(new File(".\\assets\\p_unit1.png"));
			 p_unit2_texture_buffer = ImageIO.read(new File(".\\assets\\p_unit2.png"));
			 p_unit3_texture_buffer = ImageIO.read(new File(".\\assets\\p_unit3.png"));
			 e_unit0_texture_buffer = ImageIO.read(new File(".\\assets\\e_unit0.png"));
			 e_unit1_texture_buffer = ImageIO.read(new File(".\\assets\\e_unit1.png"));
			 e_unit2_texture_buffer = ImageIO.read(new File(".\\assets\\e_unit2.png"));
			 e_unit3_texture_buffer = ImageIO.read(new File(".\\assets\\e_unit3.png"));
			} catch (IOException e){
			    e.printStackTrace();
			}
		 
		 //size the textures
		default_unit_texture = default_unit_texture_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		p_unit0_texture = p_unit0_texture_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		p_unit1_texture = p_unit1_texture_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		p_unit2_texture = p_unit2_texture_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		p_unit3_texture = p_unit3_texture_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		e_unit0_texture = e_unit0_texture_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		e_unit1_texture = e_unit1_texture_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		e_unit2_texture = e_unit2_texture_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
		e_unit3_texture = e_unit3_texture_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);

	 
	 
	 //load select mark
	 BufferedImage select_mark_buffer = null;
	 BufferedImage select_mark_old_buffer = null;
	 try {
		 select_mark_buffer = ImageIO.read(new File(".\\assets\\select_mark.png"));
		 select_mark_old_buffer = ImageIO.read(new File(".\\assets\\select_mark.png"));
		} catch (IOException e){
		    e.printStackTrace();
		}
	select_mark = select_mark_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
	select_mark_old = select_mark_old_buffer.getScaledInstance(stepW, stepH, Image.SCALE_SMOOTH);
	
	 BufferedImage offTurn_buffer = null;
	 BufferedImage victory_buffer=null;
	 BufferedImage defeat_buffer=null;
	 try {
		 offTurn_buffer = ImageIO.read(new File(".\\assets\\offTurn_msg.png"));
		 victory_buffer = ImageIO.read(new File(".\\assets\\victory.png"));
		 defeat_buffer = ImageIO.read(new File(".\\assets\\defeat.png"));
		} catch (IOException e){
		    e.printStackTrace();
		}
	 offTurn_msg = offTurn_buffer.getScaledInstance(map_sizeW/10*4, map_sizeH/10*1, Image.SCALE_SMOOTH);
	 victory = victory_buffer.getScaledInstance(map_sizeW/10*4, map_sizeH/10*1, Image.SCALE_SMOOTH);
	 defeat = defeat_buffer.getScaledInstance(map_sizeW/10*4, map_sizeH/10*1, Image.SCALE_SMOOTH);
 }
	 
	 //removes and redraws all units on map display
	 public void displayUpdate() {
		 int num =control.player.len_forView;
		 int[] type=control.player.type_forView;
		 int[] posx=control.player.x_forView;
		 int[] posy=control.player.y_forView;
		 int[] unit_num=control.player.num_forView;
		 
		 
		 //enemy
		 int num2 =control.enemy.len_forView;
		 int[] type2=control.enemy.type_forView;
		 int[] posx2=control.enemy.x_forView;
		 int[] posy2=control.enemy.y_forView;
		 int[] unit_num2=control.enemy.num_forView;
		 
		 for (int i = 0; i < unitNum; i++) {
		 	gameSpace.remove(units[i]);
		 }
		 units=null;
		 gameSpace.revalidate();
		 gameSpace.repaint();
		 String asd;

		 unitNum=num+num2;
		 units=new JLabel[num+num2];
		 for (int i = 0; i < num; i++){
			    units[i]=new JLabel();
				units[i].setPreferredSize(new Dimension(stepW, stepH));
				units[i].setSize(stepW,stepH);
				
				switch (type[i]) {
				case 0:
					units[i].setIcon(new ImageIcon(p_unit0_texture));
					break;
				case 1:
					units[i].setIcon(new ImageIcon(p_unit1_texture));
					break;
				case 2:
					units[i].setIcon(new ImageIcon(p_unit2_texture));
					break;
				case 3:
					units[i].setIcon(new ImageIcon(p_unit3_texture));
					break;
				default:
					units[i].setIcon(new ImageIcon(default_unit_texture));
					break;
				}			
				gameSpace.add(units[i],0);
				units[i].setBounds(posx[i]*stepW, posy[i]*stepH, stepW, stepH);
				asd=" "+unit_num[i]+" ";
				units[i].setText(asd);
		        units[i].setHorizontalTextPosition(JLabel.CENTER);
		        units[i].setVerticalTextPosition(JLabel.CENTER);
		}
		 
		 

		
		 for (int i = num; i < unitNum; i++){
			    units[i]=new JLabel();
				units[i].setPreferredSize(new Dimension(stepW, stepH));
				units[i].setSize(stepW,stepH);
				
				switch (type2[i]) {
				case 0:
					units[i].setIcon(new ImageIcon(e_unit0_texture));
					break;
				case 1:
					units[i].setIcon(new ImageIcon(e_unit1_texture));
					break;
				case 2:
					units[i].setIcon(new ImageIcon(e_unit2_texture));
					break;
				case 3:
					units[i].setIcon(new ImageIcon(e_unit3_texture));
					break;	
				default:
					units[i].setIcon(new ImageIcon(default_unit_texture));
					break;
				}			
				gameSpace.add(units[i],0);
				units[i].setBounds(posx2[i]*stepW, posy2[i]*stepH, stepW, stepH);
				asd=" "+unit_num2[i]+" ";
				units[i].setText(asd);
		        units[i].setHorizontalTextPosition(JLabel.CENTER);
		        units[i].setVerticalTextPosition(JLabel.CENTER);
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
			//System.out.format("init select \n");
		 }
		 //System.out.format("move select \n");
		 select.setBounds(x*stepW, y*stepH, stepW, stepH);
		 gameSpace.revalidate();
	 }
	 
	 public void select_display_remove(){
		 if(select!=null) 
		 {
			 gameSpace.remove(select);
		 }
		 //gameSpace.remove(select_old);
		 //select_old=null;
		 select=null;
		 gameSpace.revalidate();
		 gameSpace.repaint();
	 }
	 
	 public void endTurn() {
	   	 onTurn=false;
	   	 select_display_remove();
	   	 control.resetSelect();
	   	 
		 if(77==control.player.endTurn(map)) {
			 victory();
		 }
	   	  
		 if (offTurn==null){
			 offTurn=new JLabel();
			 offTurn.setPreferredSize(new Dimension(map_sizeW/2, map_sizeW/8));
			 offTurn.setSize(stepW,stepH);
			 offTurn.setIcon(new ImageIcon(offTurn_msg));
			 gameSpace.add(offTurn,0);
			 offTurn.setBounds(map_sizeW/10*3, map_sizeH/20*9, map_sizeW/10*4,  map_sizeH/10*1);
		 }
		 offTurn.setVisible(true);
		 gameSpace.revalidate();
	 }
	 
	 public void startTurn() {
		 onTurn=true;
		 displayUpdate();
		 updateMoneyDisplay();
		 if(offTurn!=null) {
			 offTurn.setVisible(false);
		 }
	 }

	 public void updateMoneyDisplay() {
		 money=Integer.toString(control.player.gold);
		 hud1.setText(msg+money);
	 }
	 
	 public void updateBaseHpDisplay() {
		 base_hp1=Integer.toString(control.player.homeBase.health);
		 base_hp2=Integer.toString(control.enemy.homeBase.health);
		 hud2.setText(msg1+base_hp1);
		 hud3.setText(msg2+base_hp2);
	 }
	 
	 public void victory() {
	   	 onTurn=false;
	   	 win=true;
	   	  
		 victory_msg=new JLabel();
		 victory_msg.setPreferredSize(new Dimension(map_sizeW/2, map_sizeW/8));
		 victory_msg.setSize(stepW,stepH);
		 victory_msg.setIcon(new ImageIcon(victory));
		 gameSpace.add(victory_msg,0);
		 victory_msg.setBounds(map_sizeW/10*3, map_sizeH/20*9, map_sizeW/10*4,  map_sizeH/10*1);
		 victory_msg.setVisible(true);
		 gameSpace.revalidate();
	 }
	 
	 public void defeat() {
	   	 onTurn=false;
	   	 lose=true;
	   	  
		 defeat_msg=new JLabel();
		 defeat_msg.setPreferredSize(new Dimension(map_sizeW/2, map_sizeW/8));
		 defeat_msg.setSize(stepW,stepH);
		 defeat_msg.setIcon(new ImageIcon(defeat));
		 gameSpace.add(defeat_msg,0);
		 defeat_msg.setBounds(map_sizeW/10*3, map_sizeH/20*9, map_sizeW/10*4,  map_sizeH/10*1);
		 defeat_msg.setVisible(true);
		 gameSpace.revalidate();
	 }

}

