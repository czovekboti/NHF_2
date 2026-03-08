package Main;
import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Main {
	private int size;
    private JFrame frame;
    private States currentState;
    public GamePanel gamePanel;
    public Main() {    	
    	//Ablak létrehozása
        frame = new JFrame("Renju");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon logo = new ImageIcon("C:\\Users\\CzövekBotond\\eclipse-workspace\\NHF_2\\src\\logo.jpg");
        frame.setIconImage(logo.getImage());
        frame.setSize(770, 823);
        frame.setLocationRelativeTo(null);
        currentState = States.MAIN_MENU;
        showCurrentMenu();
        frame.setVisible(true);
    }
    public void setsize(int n) {
    	size= n;
    }
    public int getsize() {
    	return size;
    }
    public JFrame getFrame() {return frame;} 
    //Állapotgép 
    private void showCurrentMenu() {
        switch (currentState) {
            case MAIN_MENU:
                frame.setContentPane(new MainMenu(this));
                break;
            case PLAYER_MENU:
                frame.setContentPane(new PlayerMenu(this));
                break;
            case SIZE_MENU:
                frame.setContentPane(new SizeMenu(this));
                break;
            case AI_GAME:
                frame.setContentPane(new GamePanel(new AiGame(this.getsize()),this,this.frame));
                break;
            case PLAYERS_GAME:
                frame.setContentPane(new GamePanel(new PlayersGame(this.getsize()), this,this.frame));
                break;
            case LOADED_GAME:
            	frame.setContentPane(gamePanel); 
        }
        
        if (currentState != States.PLAYERS_GAME && currentState != States.AI_GAME && currentState != States.LOADED_GAME) {
	        Image backgroundImage = new ImageIcon("C:\\Users\\CzövekBotond\\eclipse-workspace\\NHF_2\\src\\logo.jpg").getImage();;
	    	frame.setLayout(new BorderLayout());
	        frame.add(new JLabel(new ImageIcon(backgroundImage)));}
        	frame.revalidate();
        	frame.repaint();
        
    }

    public void setCurrentStatePaint(States newState) {
        currentState = newState;
        showCurrentMenu();
    }
    public void setCurrentState(States newState) {
        currentState = newState;
        
    }
    public States getCurrentState() {
        return currentState;
    }
    
    

    public static void main(String[] args) {
    	SwingUtilities.invokeLater(() -> {
            new Main();
        });
    }
    

}
