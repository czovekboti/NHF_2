package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SizeMenu extends JPanel {
    private Main main;
    private int newSize;

    public SizeMenu(Main main) {
        this.main = main;
        setLayout(null);
        createSizeButton("15x15",170, 200,15);
        createSizeButton("20x20",480, 200,20);
        createSizeButton("25x25", 170, 400,25);
        createSizeButton("30x30",480, 400,30);
       
    }

    private void createSizeButton(String buttonText, int x, int y, int size) {
        JButton sizeButton = new JButton(buttonText);
        
        sizeButton.setBounds(x, y, 150, 100);
        sizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setCurrentStatePaint(States.PLAYER_MENU);
                main.setsize(size);
            }
        });

        add(sizeButton);
    }
}
