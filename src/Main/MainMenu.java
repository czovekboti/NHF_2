package Main;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {
    
    public MainMenu(Main main) {
    	
    	setLayout(null);       
        JButton playButton = new JButton("Play");
        playButton.setBounds(325, 250,150,100);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setCurrentState(States.SIZE_MENU);
            }
        });        
        add(playButton);
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(325, 400,150,100);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        add(exitButton);
        
        
    }
    
    
}


