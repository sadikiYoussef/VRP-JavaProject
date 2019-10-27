

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


 
public class Fenetre extends JFrame {
	
	Solution s;
	Node l;
	
	private JPanel pan = new JPanel();
	private JButton bouton = new JButton("start");
//	Bouton bouton = new Bouton("555555555");
	  
  public Fenetre(Solution s){
	 this.s = s;
	this.l =new Node(500,330);
    this.setTitle("la presentation de la solution");
    this.setSize(1400, 730);
    this.setLocationRelativeTo(null);               
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(new Panneau(s,l));
    
  //  pan.add(bouton);
  //  this.setContentPane(pan);
   // this.getContentPane().add(bouton, BorderLayout.AFTER_LAST_LINE);

    this.setVisible(true);
  }     
}