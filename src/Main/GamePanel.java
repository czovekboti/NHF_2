package Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel{
	private int boardSize;
	private Cell board[][];
	private int selectedRow;
    private int selectedCol;
    private int cellSize;
    private int startY;
    private int startX;   
    private int round;
    // GamePanel konstruktor
    public GamePanel(int boardSize) {
        this.boardSize = boardSize+=1;
        this.selectedRow = -1;
        this.selectedCol= -1;
        board = new Cell[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = new Cell(i,j);
            }
        }
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
            	repaint();
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
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                int x = startX + col * cellSize;
                int y = startY + row * cellSize;
                g.drawRect(x, y, cellSize, cellSize);  
                char cellState = board[row][col].getCellState();
                // A paint metódus hívása a cellára
                board[row][col].paint(g, cellSize, startX, startY);                
            	}
        	}        
    }
    private void updateSelectedCell(int x, int y) {
    	selectedRow = ((x+cellSize/2)/cellSize);
    	selectedCol= ((y+cellSize/2)/cellSize);
    	if (selectedCol < 1 || selectedCol > boardSize - 1 || selectedRow < 1 || selectedRow > boardSize - 1) {
            selectedCol = -1;
            selectedRow = -1;
        }    	
    }
    private void highlightCell(Graphics g) {
    	g.setColor(new Color(255,255,255,100));
    	int centerX = startX + selectedRow*cellSize;
    	int centerY = startY + selectedCol*cellSize;
    	int radius = cellSize/2;
    	g.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
    }
    private void placePiece(int x, int y) {
        if (selectedRow != -1 && selectedCol != -1) {
            // Itt hozzáadhatod a bábu elhelyezését a megfelelő helyre
            System.out.println("Bábu letéve a cellára: " + selectedRow + ", " + selectedCol);
            board[selectedCol][selectedRow].setCellState('B');
            System.out.println(board[selectedCol][selectedRow].getCellState());
        }
    }
    

}
