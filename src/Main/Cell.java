package Main;

import java.awt.Color;
import java.awt.Graphics;

public class Cell {
	public int row;
	public int col;
	private boolean isBlack;
	private  boolean isWhite;
	private boolean isSelected; 
	public Cell(int col,int row) {
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
		if(isSelected) {
			state= 'S';
		}
		
		return state;
	}
	// Beállítja a cella állapotát
	protected void setCellState(char s) {
		if(s =='B' && isWhite==false) {
			this.isBlack=true;
			
		}
		else if(s =='W' && isBlack ==false) {
			this.isWhite=true;
			this.isSelected = false;
		}
		else if(s =='S' ) {
			this.isSelected=true;
		}
		else if(s=='E') {
			this.isBlack=false;
			this.isSelected = false;
			this.isWhite= false;
		}
		
	}
	public void paint(Graphics g, int cellSize, int startX, int startY) {
        int x = startX+ row * cellSize+cellSize;
        int y = startY + col * cellSize+cellSize;

        // Rajzolás a cella állapotától függően
        if (isBlack) {
            g.setColor(Color.BLACK);
            g.fillOval(x - cellSize / 2, y - cellSize / 2, cellSize, cellSize);
        	}
        if (isWhite) {
            g.setColor(Color.WHITE);
            g.fillOval(x - cellSize / 2, y - cellSize / 2, cellSize, cellSize);
        	} 
        if(isSelected) {
            g.setColor(new Color(0,0,0,100));
            g.fillOval(x - cellSize / 2, y - cellSize / 2, cellSize, cellSize);
        	}
        
        g.setColor(Color.BLACK);
	}
	
}
