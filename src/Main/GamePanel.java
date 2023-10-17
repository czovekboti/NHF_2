package Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel{
	private Game game;
	private int boardSize;
	private int selectedRow;
    private int selectedCol;
    private int cellSize;
    private int startY;
    private int startX;   
    // GamePanel konstruktor
    public GamePanel(Game game) {
    	setLayout(new BorderLayout());        
    	this.game =game;
        this.selectedRow = -1;
        this.selectedCol= -1;
        this.boardSize = game.getBoardSize();
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
//            	if (game.getRound() !=5 && game.getRound()!=6) {
            	placePiece(e.getX(), e.getY());
            	repaint();
            	System.out.println(game.getCell(selectedCol-1,selectedRow-1).getCellState());
//            	}
//            	else {
//            		placePiece(e.getX(), e.getY());
//                	repaint();
//            	}
            }
        });
    }
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(100,149,237,150));      	
    	drawBoard(g);
    	highlightCell(g);
    
    }
    private void drawBoard(Graphics g) {    	
        g.setColor(Color.black);
        cellSize = Math.min(getWidth() / boardSize, getHeight() / boardSize);
	    startX = (getWidth() - cellSize * boardSize) /2;
	    startY = (getHeight() - cellSize * boardSize) / 2;
	    //Kirajzolja a táblára a lerakott bábukat
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                int x = startX + col * cellSize;
                int y = startY + row * cellSize;
                g.drawRect(x, y, cellSize, cellSize);                  
                // A paint metódus hívása a cellára
                game.getCell(row, col).paint(g, cellSize, startX, startY);   
                
            	}
        	}        
    }
    //Kiszámolja melyik cellán áll az egér
    private void updateSelectedCell(int x, int y) {
    	selectedRow = ((x+cellSize/2)/cellSize);
    	selectedCol= ((y+cellSize/2)/cellSize);
    	if (selectedCol < 1 || selectedCol > boardSize - 1 || selectedRow < 1 || selectedRow > boardSize - 1) {
            selectedCol = -1;
            selectedRow = -1;
        }    	
    }
    //Kirajzolja azt a cellát, amelyen áll az egér
    private void highlightCell(Graphics g) {
    	g.setColor(new Color(255,255,255,100));
    	int centerX = startX + selectedRow*cellSize;
    	int centerY = startY + selectedCol*cellSize;
    	int radius = cellSize/2;
    	g.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
    }
    //Bábu elhelyezésére szolgál
    private void placePiece(int x, int y) {
        if (selectedRow != -1 && selectedCol != -1) {
            // Hozzáadja a kiválasztott cellához és oszlophoz az éppen soron lévő játékos bábuját
            game.makeMove(selectedRow-1, selectedCol-1);

        }
    }
    

}
