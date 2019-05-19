//Példa a mûködésre
import java.util.concurrent.TimeUnit;
class Main 
{ 
public static void main(String args[]) 
{
	//initialize server / client
	   ConnectionType  end = ConnectionType.Connected_Win;
	   int password = 2300;
	   String url = "0.0.0.0";
	   Server servero = new Server(url,6747,password,8);
	   Client cliento = new Client(url,6747);
	   System.out.println(cliento.getPort());
	   servero.listening();
	   System.out.println(servero.getFlag());
	   cliento.SendingPassword(password);
	   servero.password_check();
	   System.out.println(servero.getFlag());
	   cliento.AckCheck();
	   servero.datatransfer_check();
    //initialize window
	 		int x=50;
	 		int y=50;
	 		int[][] test_map=new int[50][50];
	 		
	 		for (int i = 0; i < 50; i++) {
	 			for (int j = 0; j < 50; j++) {
	 				test_map[i][j]=0;
	 			}
	 		}
	 		
	 		
	 		int[] units=new int[2];
	 		units[0]=1;
	 		units[1]=0;
	 		int[] xpos=new int[2];
	 		xpos[0]=1;
	 		xpos[1]=0;
	 		int[] ypos=new int[2];
	 		ypos[0]=1;
	 		ypos[1]=0;
	 		NetworkData hello = new NetworkData(units,xpos,ypos,2);
	 		NetworkData omg   = new NetworkData(units,ypos,xpos,2);
	 		//usage:
	 		//test_map = map.grid[][]
	 		//x = map.xLen
	 		//y = map.yLen
	 		
	 		//height is prefered to be the multiple of 10
	 		GameWindow MainWindow=new GameWindow(900,1000,400);	
	 		MainWindow.displayMap(test_map,x,y);
	 		//kicsúszik a változtattom az ablak méretet
	 		//MainWindow.addButtons();
	 		int i = 0;
	 while (true)
	 {
	  
	   hello.PrintState();
	   servero.sending(hello);
	   hello =cliento.incoming();
	   xpos = hello.getXpos();
	   xpos [0]=i+1;
	   xpos [1]=i;
	   ypos = hello.getYpos();
	   ypos [0]=i+1;
	   ypos [1]=i;
	   units = hello.getUnits();
	   MainWindow.displayUpdate(units,xpos,ypos,2);
	   cliento.sending(omg);
	   hello =servero.incoming();
	   xpos = hello.getXpos();
	   xpos [0]=i+1;
	   xpos [1]=i;
	   ypos = hello.getYpos();
	   ypos [0]=i+1;
	   ypos [1]=i;
	   units = hello.getUnits();
	   MainWindow.displayUpdate(units,xpos,ypos,2);
	  /* try {
		MainWindow.wait(1000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}*/
	   servero.checkingFlag();
	   i=i+1;
	   if(i ==50)
	   {
		   try {
		   TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		   while(i !=0)
		   {
		   hello.PrintState();
		   servero.sending(hello);
		   hello =cliento.incoming();
		   xpos = hello.getXpos();
		   xpos [0]=i-2;
		   xpos [1]=i-1;
		   ypos = hello.getYpos();
		   ypos [0]=i-2;
		   ypos [1]=i-1;
		   units = hello.getUnits();
		   MainWindow.displayUpdate(units,xpos,ypos,2);
		   cliento.sending(omg);
		   hello =servero.incoming();
		   xpos = hello.getXpos();
		   xpos [0]=i-2;
		   xpos [1]=i-1;
		   ypos = hello.getYpos();
		   ypos [0]=i-2;
		   ypos [1]=i-1;
		   units = hello.getUnits();
		   MainWindow.displayUpdate(units,xpos,ypos,2);
		   i=i-1;
		   }
		 if(i==0)
		   {
			   servero.setFlag(end);
			   break;
			   
		   }
	   }
	  }
	   if (end.toString() == servero.getFlag())
	   {
	   servero.closing_all();
	   cliento.closing_all();
	   try {
		TimeUnit.SECONDS.sleep(5);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	   MainWindow.CloseWindow();
	   
	   }
	 }
	   
}
	
	 