package Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class GamePanel extends JPanel{
	private Game game;
	private Main main;
	private JMenuBar menuBar;
	private int boardSize;
	private int selectedRow;
    private int selectedCol;
    private int cellSize;
    private int startY;
    private int startX;   
    // GamePanel konstruktor
    public GamePanel(Game game, Main main, JFrame frame) {
    	this.main=main;
    	setLayout(new BorderLayout());        
    	this.game =game;
        this.selectedRow = -1;
        this.selectedCol= -1;
        this.boardSize = game.getBoardSize()+1;
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("Fájl");
        menuBar.add(fileMenu);

        // Menüpont a mentéshez
        JMenuItem saveMenuItem = new JMenuItem("Mentés");
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String fileName = "game_save.txt";
                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                    writer.write(main.getCurrentState().name());
                    writer.newLine();
                    writer.write(String.valueOf(game.getBoardSize()));
                    writer.newLine();
                    writer.write(""+game.getRound());
                    writer.newLine();
                    writer.write(game.getCurrentPlayer());
                    writer.newLine();
                    if(main.getCurrentState()==States.AI_GAME) {
                    	writer.write(game.getAIplayer());
                    	writer.newLine();}
                    Cell[][] board = game.getBoard();
                    for (int i = 0; i < board.length; i++) {
                        for (int j = 0; j < board[i].length; j++) {
                            writer.write(board[i][j].getCellState() + " ");
                        }
                        writer.newLine();
                    }
                    writer.close();
                    JOptionPane.showConfirmDialog(null, "Játék sikeresen elmentve", "", JOptionPane.PLAIN_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        fileMenu.add(saveMenuItem);

        // Menüpont a kilépéshez
        JMenuItem exitMenuItem = new JMenuItem("Kilépés");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Biztosan ki akarsz lépni?", "Kilépés", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    main.setCurrentStatePaint(States.MAIN_MENU);
                }
            }
        });
        fileMenu.add(exitMenuItem);
    	
        // Cellák kijelöléséhez szükséges
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                updateSelectedCell(e.getX(), e.getY());
                repaint();
            }            
        });
        // Kattintásra bábu elhelyezése
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	placePiece(e.getX(), e.getY());
            	if(game.getWinner()!='0') {
            		displayWinner(game.getWinner());
            	}
            	
            	repaint();
            }
        });
    }
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(100,149,237,150));      	
    	drawBoard(g);
    	drawCells(g);
    	highlightCell(g);
    }
	private void drawCells(Graphics g) {
		for (int row = 0; row < this.boardSize; row++) {
            for (int col = 0; col < this.boardSize; col++) {     
            	game.getCell(row, col).paint(g, cellSize, startX, startY);
        	}
        }		
	}
    private void drawBoard(Graphics g) { 
        g.setColor(Color.black);
        cellSize = Math.min(getWidth() / boardSize, getHeight() / boardSize);
	    startX = (getWidth() - cellSize * boardSize) /2 ;
	    startY = (getHeight() - cellSize * boardSize) / 2;
	    //Kirajzolja a táblára a lerakott bábukat
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                int x = startX + col * cellSize;
                int y = startY + row * cellSize;
                g.drawRect(x, y, cellSize, cellSize);                  
            	}
        	}        
    }
    //Kiszámolja melyik cellán áll az egér
    private void updateSelectedCell(int x, int y) {
    	selectedCol = ((x+cellSize/2)/cellSize);
    	selectedRow= ((y+cellSize/2)/cellSize);
    	if (selectedCol < 1 || selectedCol > boardSize -1  || selectedRow < 1 || selectedRow > boardSize - 1) {
            selectedCol = -1;
            selectedRow = -1;
        }    
    	
    }
    //Kirajzolja azt a cellát, amelyen áll az egér
    private void highlightCell(Graphics g) {
    	if(selectedRow != -1 && selectedCol != -1) {
    		if(game.getCell(selectedCol-1, selectedRow-1).getCellState() == 'E' || game.getCell(selectedCol-1, selectedRow-1).getCellState() == 'S'){
		    	g.setColor(new Color(255,255,255,100));
		    	int centerX = startX + selectedCol*cellSize;
		    	int centerY = startY + selectedRow*cellSize;
		    	int radius = cellSize/2;
		    	g.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);}
    		}
    	
    }
    
    private void placePiece(int x, int y) {
        if (selectedRow != -1 && selectedCol != -1) {
        	if(game.getCell(selectedCol-1,selectedRow-1).getCellState()=='E'||game.getCell(selectedCol-1,selectedRow-1).getCellState()=='S') {
        		game.makeMove(selectedCol-1, selectedRow-1);
        		game.makeAImove();}
        }
    }
    public void displayWinner(char winner) {
    	String message = (game.getWinner() == 'W') ? "Nyert a fehér!" : "Nyert a fekete!";
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        JButton customButton = new JButton("Új játék");
        optionPane.setOptions(new Object[]{customButton});
        customButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setCurrentStatePaint(States.MAIN_MENU);
                Window window = SwingUtilities.getWindowAncestor(optionPane);
                window.dispose(); // Ablak bezárása gomb lenyomásakor
            }
        });
        JDialog dialog = optionPane.createDialog("Játék vége");
        dialog.setVisible(true);
        dialog.dispose();
    }

}
