package Main;

//PlayerMenu.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerMenu extends JPanel {	
	 public PlayerMenu(Main main) {
	     setLayout(null);	
	     JButton aiButton = new JButton("AI");
	     aiButton.addActionListener(new ActionListener() {
	     @Override
	     public void actionPerformed(ActionEvent e) {
	         main.setCurrentStatePaint(States.AI_GAME);
	     }
	     });
	     JButton playersButton = new JButton("1v1");
	     playersButton.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	             main.setCurrentStatePaint(States.PLAYERS_GAME);
	         }
	     });
	     aiButton.setBounds(325, 400,150,100);
	     playersButton.setBounds(325, 250,150,100);
	     add(aiButton);
	     add(playersButton);
	 }
}
