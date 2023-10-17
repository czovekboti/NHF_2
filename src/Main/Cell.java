package Main;

import java.awt.Color;
import java.awt.Graphics;

public class Cell {
	
	public int row;
	public int col;
	private boolean isBlack;
	private  boolean isWhite;
	public Cell(int row,int col) {
		this.row = row;
		this.col = col;
		
	}
	//visszaadja a cella állapotát
	protected char getCellState() {
		char state = 'E';
		if (isBlack) {
			state = 'B';
		}
		if(isWhite) {
			state = 'W';
		}
		
		return state;
	}
	// Beállítja a cella állapotát
	protected void setCellState(char s) {
		if(s =='B') {
			this.isBlack=true;
		}
		else if(s =='W') {
			this.isWhite=true;
		}
		
	}
	public void paint(Graphics g, int cellSize, int startX, int startY) {
        int x = startX+ col * cellSize+cellSize;
        int y = startY + row * cellSize+cellSize;

        // Rajzolás a cella állapotától függően
        if (isBlack) {
            g.setColor(Color.BLACK);
            g.fillOval(x - cellSize / 2, y - cellSize / 2, cellSize, cellSize);
        	}
        if (isWhite) {
            g.setColor(Color.WHITE);
            g.fillOval(x - cellSize / 2, y - cellSize / 2, cellSize, cellSize);
        	} 
        else {
            // Rajzolj keretet, ha a cella üres
            g.setColor(Color.BLACK);
            g.drawRect(x, y, cellSize, cellSize);
        }
        g.setColor(Color.BLACK);
	}
	
}
