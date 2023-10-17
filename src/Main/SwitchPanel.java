package Main;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
public class SwitchPanel extends JPanel {
    private JButton yesButton;
    private JButton noButton;
    public SwitchPanel() {
        setLayout(new FlowLayout());
        String title = "Szeretnél színeket cserélni?";
        TitledBorder border = BorderFactory.createTitledBorder(title);
        this.setBorder(border);
        yesButton = new JButton("Yes");
        noButton = new JButton("No");
        add(yesButton);
        add(noButton);
    }
    public JButton getYesButton() {
        return yesButton;
    }
    public JButton getNoButton() {
        return noButton;
    }
}