package Main;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainMenu extends JPanel {
    
    public MainMenu(Main main) {
    	
    	setLayout(null);       
        JButton playButton = new JButton("Play");
        playButton.setBounds(325, 250,150,100);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setCurrentStatePaint(States.SIZE_MENU);
            }
        });        
        add(playButton);
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(325, 500,150,100);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        add(exitButton);
        
        JButton loadButton = new JButton("Load");
        loadButton.setBounds(325, 375,150,100);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
                    String fileName = "game_save.txt";
                    BufferedReader reader = new BufferedReader(new FileReader(fileName));
                    // Olvasd be az állapotokat
                    String cs = reader.readLine();
                    States currentState = States.valueOf(cs);
                    int boardSize = Integer.parseInt(reader.readLine());
                    int round = Integer.parseInt(reader.readLine());
                    System.out.println(round);
                    char currentPlayer = reader.readLine().charAt(0);
                    char AIplayer= '0';
                    if(currentState==States.AI_GAME) {
                    	AIplayer = reader.readLine().charAt(0);
                    	System.out.println(AIplayer);
                    }
                    Game game;
                    switch(currentState) {
                		case AI_GAME:
                			game = new AiGame(boardSize);
                			main.gamePanel = new GamePanel(game, main, main.getFrame());
                			game.setAIplayer(AIplayer);
                			game.setRound(round);
                			
                			main.setCurrentStatePaint(States.LOADED_GAME);
                			main.setCurrentState(States.AI_GAME);
                			
                			break;
                		default:
                			game = new Game(boardSize);
                			main.gamePanel= new GamePanel(game, main, main.getFrame());
                			main.setCurrentState(States.LOADED_GAME);
                			main.setCurrentState(States.PLAYERS_GAME);
                    }
                    
                    game.setCurrentPlayer(currentPlayer);
                    for (int i = 0; i < boardSize; i++) {
                        String[] rowValues = reader.readLine().split(" ");
                        for (int j = 0; j < boardSize; j++) {
                            char cellState = rowValues[j].charAt(0);
                            // Felépítsd a Cell objektumokat a tábla rekonstrukcióhoz
                            game.getBoard()[i][j].setCellState(cellState);
                        }
                    }
                    main.getFrame().revalidate();
                    main.getFrame().repaint();
                    System.out.println(game.getRound());
                    reader.close();
                    JOptionPane.showConfirmDialog(null, "Játék sikeresen betöltve", "", JOptionPane.PLAIN_MESSAGE);
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            	
            	;
            }
        });
        add(loadButton);
        
        
    }
    
    
}


