package Main;

import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private char currentPlayer;
    private char player1;
    private char player2;
    
    private int boardSize;
    private Cell[][] board;    
    private int round = 0;

    public Game(int boardSize) {
        this.boardSize = boardSize + 1;
        this.board = new Cell[this.boardSize][this.boardSize];
        this.currentPlayer = 'B'; // Fekete kezd
        // Cellák inicializálása
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                this.board[i][j] = new Cell(i, j);
            }
        }
        
    }
    public Cell getCell(int row, int col) {
    	
		return board[row][col];
    	
    }
    public int getBoardSize() {
    	return boardSize;
    }
    public void startGame() {
        // Implementáld a játék kezdéséhez szükséges logikát
    	round =1;
    }
    public void Opening() {
    	currentPlayer = 'B';
    	for (int i=0;i<3;i++) {
//    		makeMove();
    	}
    		
    }
    
    public void makeMove(int row, int col) {
        // Implementáld a lépés végrehajtásához szükséges logikát
    	if(round>3) {
	    	board[col][row].setCellState(currentPlayer);
	    	switchPlayer();
    	}
    }

    public void pass() {
        // Implementáld a passzoláshoz szükséges logikát
    }

    public void adjustStones(int row, int col) {
        // Implementáld a kövek igazításához szükséges logikát
    	
    }
    
    private void switchPlayer() {
        // Implementáld a játékosváltáshoz szükséges logikát
    	currentPlayer = (currentPlayer=='B') ? 'W' : 'B';
    	
    }

    

    private void checkForWin() {
    	// Implementáld a győzelem ellenőrzéséhez szükséges logikát
    
    }
    
}
