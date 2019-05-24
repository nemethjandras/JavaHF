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

public class Launcher{
	int x;
	int y;

	JFrame mainWindow = new JFrame("Launcher Window");
	JPanel panel=new JPanel(null); 
	
	JLabel text1=new JLabel("Map size:    ");
	JLabel text2=new JLabel("Starter gold:");
	
	JButton hostButton=new JButton();
	JButton joinButton=new JButton();
	JButton sbButton=new JButton();
	JTextField textIn=new JTextField();
	
	String[] mapSize_selection= {"small","medium","large"};
	String[] startGold_selection= {"30","50","100"};
	JComboBox mapSize_select=new JComboBox(mapSize_selection);
	JComboBox startGold_select=new JComboBox(startGold_selection);
	
	String startGold="30";
	String mapSize="small";
	String ID="1234";
	
	//flags
	public boolean start_sandbox;
	public boolean start_hosting;
	public boolean start_client;

    //===================================================================================
	
	 public Launcher(int width, int height) {
		 	mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		 	
		 	//set sizes
	        mainWindow.setSize(width, height);
	        mainWindow.setMinimumSize(new Dimension(500, 300));
	        panel.setPreferredSize(new Dimension(width, height));
	        
	        x=width;
	        y=height;
	        start_sandbox=false;
	        start_hosting=false;
	        start_client=false;
	        
	        
	        //adding components to mainWindow
	        mainWindow.add(panel);
	        
	        //launching window
	        mainWindow.validate();
	        mainWindow.pack();
	        mainWindow.setVisible(true);
	 }
	 
	 public void setButtons() {

		 	mapSize_select.setBounds(mainWindow.getInsets().left+x/20*4,y/20*5,x/20*5,y/20*3);
		    panel.add(mapSize_select);
		    mapSize_select.setVisible(true);
		    mapSize_select.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
		          mapSize = (String)mapSize_select.getSelectedItem();
		      }
		    });
		    
		    
		    startGold_select.setBounds(x/20*15-mainWindow.getInsets().left,y/20*5,x/20*5,y/20*3);
		    panel.add(startGold_select);
		    startGold_select.setVisible(true);
		    startGold_select.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
		          startGold = (String)startGold_select.getSelectedItem();
		      }
		    });
		    
		    text1.setBounds(mainWindow.getInsets().left,y/20*5,x/20*4,y/20*3);
		    panel.add(text1);
		    text1.setVisible(true);
		    
		    text2.setBounds(x/20*11-mainWindow.getInsets().left,y/20*5,x/20*4,y/20*3);
		    panel.add(text2);
		    text2.setVisible(true);
		    
		    

		    hostButton.setBounds(mainWindow.getInsets().left,y/20*10,x/20*9,y/20*3);
		    panel.add(hostButton);
		    hostButton.setVisible(true);
		    hostButton.setText("Start Hosting");
		    hostButton.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
					System.out.format("ID: %s\n",ID);
		    	  	//ID=textIn.getText();
		    	  	start_hosting=true; //only after connection is established
			    	mainWindow.dispose();
		      }
		    });
		    
		    

		    sbButton.setBounds(mainWindow.getInsets().left,y/20*15,x/20*9,y/20*3);
		    panel.add(sbButton);
		    sbButton.setVisible(true);
		    sbButton.setText("Start Sandbox");
		    sbButton.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
		    	  start_sandbox=true;
		    	  System.out.format("start sandbox pressed");
		    	  mainWindow.dispose();
		      }
		    });
		    
		    
		    
		    joinButton.setBounds(x/20*11-mainWindow.getInsets().left,y/20*10,x/20*9,y/20*3);
		    panel.add(joinButton);
		    joinButton.setVisible(true);
		    joinButton.setText("Join Game");
		    joinButton.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
		    	  //ID=textIn.getText();
		    	  System.out.format("ID: %s\n",ID);
		    	  start_client=true; //only after conenction is established
		    	  mainWindow.dispose();
		      }
		    });
		 
		    /*
		    //textIn.setSize(x-mainWindow.getInsets().left*4,y/20);
		    textIn.setBounds(mainWindow.getInsets().left,y/20,x-mainWindow.getInsets().left*2,y/20*3);
		    panel.add(textIn);
		    textIn.setVisible(true);
		    textIn.setText("Multiplayer ID");
		    textIn.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
				  System.out.format("ID: %s\n",ID);
		    	  ID=textIn.getText();
		      }
		    });
		    */
	 }
}