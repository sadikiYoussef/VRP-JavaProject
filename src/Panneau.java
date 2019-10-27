

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
 
public class Panneau extends JPanel {
	
	Solution s;
	Node l;
	private JButton bouton = new JButton("start");

	public void paintComponent(Graphics g){
		
		
		Font font = new Font("Courier", Font.PLAIN, 20);
	    g.setFont(font);
	    for (int j=0 ; j < s.NoOfVehicles ; j++)
	    {
	    	Graphics2D g2 = (Graphics2D) g;
	    	g2.setStroke(new BasicStroke(5));
	    	
		    g.setColor(new Color((float)Math.random(), (float)Math.random(), (float)Math.random())); 
	        int RoutSize = s.Vehicles[j].Route.size();
	        for (int k = 0; k < RoutSize ; k++) {
		        //	g.drawLine(l.Node_X,l.Node_Y,s.Vehicles[j].Route.get(k).Node_X,s.Vehicles[j].Route.get(k).Node_Y); 
		//        int a=0;
		//        a=(int)(s.Vehicles[j].Route.get(k).Node_Y -l.Node_Y )/(s.Vehicles[j].Route.get(k).Node_X -l.Node_X );
		        double Xa =(double) l.Node_X;
		        double Ya =(double) l.Node_Y;
		        double Xb =(double) s.Vehicles[j].Route.get(k).Node_X;
		        double Yb =(double) s.Vehicles[j].Route.get(k).Node_Y;
		    	
		        
		        
		        int Delta_x = (l.Node_X - s.Vehicles[j].Route.get(k).Node_X);
	            int Delta_y = (l.Node_Y - s.Vehicles[j].Route.get(k).Node_Y);
	
	            double distance = Math.sqrt((Delta_x * Delta_x) + (Delta_y * Delta_y));
		        int X1,X2,Y1,Y2;
		       
		        X1 = (int) (Xa + (Xb-Xa)/distance*15);
		        X2 = (int) (Xb - (Xb-Xa)/distance*15);
		        Y1 = (int) (Ya + (Yb-Ya)/distance*15);
		        Y2 = (int) (Yb - (Yb-Ya)/distance*15);
		        
		        double angle = Math.atan2(Delta_x, Delta_y);
		        AffineTransform at = AffineTransform.getTranslateInstance(X1, Y1);
		        at.concatenate(AffineTransform.getRotateInstance(angle));
		//        g2.transform(at);
		        g2.drawLine(X1,Y1,X2,Y2);
		        l = s.Vehicles[j].Route.get(k);
	
		        g2.drawOval(l.Node_X-15,l.Node_Y-15, 30, 30);
		        g2.drawString(""+l.NodeId+"",l.Node_X-5,l.Node_Y+8);
		        g2.drawString("M",X2,Y2);
		        
		        g2.drawString(""+l.demand+"",l.Node_X+15,l.Node_Y+15);
	        }
	    }
	    g.setColor(Color.black);
	    g.fillRect(475, 305, 50, 50);
	    g.drawRect(10, 10, 1025, 670);
	    
	    
	    g.drawString(s.toString(),1100,550);
	    g.drawString("SADIKI Youssef",1100,600);
	    g.drawString("AIT OFKKIR Chaimae",1100,650);

	    try {
	        Image img = ImageIO.read(new File("C:\\Users\\Sadik\\Desktop\\ensias.png"));
	        g.drawImage(img, 1160, 0, this);
	        //Pour une image de fond
	     //   g.drawImage(img, 762, 0, 5, 50, this);
	      } catch (IOException e) {
	        e.printStackTrace();
	      } 
	    
	}
	public Panneau(Solution s,Node l) {
		this.s = s;
		this.l = l;
		
	}
  
} 
