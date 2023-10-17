package Main;

public class Game {
    private char currentPlayer;
    private char player1 = 'W';
    private char player2 = 'B';    
    private int boardSize;
    private Cell[][] board;    
    private int round = 0;
    public int getRound() {
    	return round;
    }
    public void setRound(int n) {
    	round = n;
    }
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
    public void makeMove(int row, int col) {
    	// Nyitás, két fekete egy fehér bábut rak le a kezdő játékos
    	if (round < 3) {
    		currentPlayer = 'B';
    		board[col][row].setCellState(currentPlayer);
    		round+=1;
    	}
    	if(round == 3) {
    		switchPlayer();
    		board[col][row].setCellState(currentPlayer);
    		round+=1;
    	}
    	if(round == 4) {
    		board[col][row].setCellState(currentPlayer);
    	}
    	//javaslat feltevése
    	if(round == 5  && round == 6) {
    		round+=1;
    	}
    	if(round > 6) {
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
