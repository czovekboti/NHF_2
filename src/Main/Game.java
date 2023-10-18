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
        this.boardSize = boardSize+1;
        System.out.println(this.boardSize);
        this.board = new Cell[this.boardSize][this.boardSize];
        this.currentPlayer = 'B'; // Fekete kezd
        // Cellák inicializálása
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                this.board[i][j] = new Cell(i, j);
                
            }
        }
        
    }
    public Cell getCell(int col, int row) {
		return board[row][col];
    }
    public int getBoardSize() {
    	return boardSize;
    }
    public void makeMove(int row, int col) {
    	// Nyitás, két fekete egy fehér bábut rak le a kezdő játékos
    	if (round < 2) {
    		currentPlayer = 'B';
    		board[col][row].setCellState(currentPlayer);
    		round+=1;
    		System.out.println(round);
    	}
    	else if(round == 2) {
    		switchPlayer();
    		board[col][row].setCellState(currentPlayer);
    		round+=1;
    		System.out.println(round);
    		
    	}
    	//eldöntik hogy cserélnek-e majd utána folytatdik a játék
    	else if(round == 3) {
    		board[col][row].setCellState(currentPlayer);
    		round+=1;
    		System.out.println(round);
    	}
    	//javaslat feltevése
    	else if(round == 4  || round == 5) {
    		if(board[col][row].getCellState()=='E') {
	    		currentPlayer = 'S';
	    		board[col][row].setCellState(currentPlayer);	
	    		round+=1;	
	    		System.out.println(round);
    		}
    	}
    	else if(round == 6) {
    		currentPlayer = 'B';
    		if(board[col][row].getCellState()=='S') {
    			board[col][row].setCellState(currentPlayer);
    			System.out.println(round);
    			round+=1;
    			resetSelectedCells();
    		}
    	}
    	else if(round > 6) {	
    		if(board[col][row].getCellState()=='E') {
	    		switchPlayer();
	    		board[col][row].setCellState(currentPlayer);
		    	if(checkForWin(currentPlayer)) {
		    		System.out.println("nyert"+currentPlayer);
		    	}
		    	System.out.println(round);
    		}
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
    //Emptyre állítja azokat a cellákat amelyek selectedek
    private void resetSelectedCells() {
    	for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                if(this.board[i][j].getCellState() == 'S' ){
                	this.board[i][j].setCellState('E');
                }
            }
        }	
    }
    //ellenőrizzük hogy nyert e valamelyik játékos
    public boolean checkForWin(char currentPlayer) {
    	boolean nyert=false;
    	//sorban 5 
    	int szamlalo = 0;
    	for (int i =0; i<boardSize;i++) { 
    		szamlalo= 0;
    		for (int j = 0; j < this.boardSize; j++) {
	    		if (board[i][j].getCellState() == currentPlayer) {
	    			szamlalo +=1;
	    			if (szamlalo ==5) {
	    				if(currentPlayer == 'W') {
	    					System.out.println("nyert a fehér");
	    					return true;
	    				}
	    				//fekete rakott ki 5-öt sorban
	    				if(currentPlayer == 'B' && board[i][j+1].getCellState()!='B') {
	    					System.out.println("nyert a fekete");
	    					return true;
	    				}
	    				//Overeline
	    				else {
	    					System.out.println("nyert a fehér");
	    					return true;
	    				}
	    			}
	    		}
	    		
	    		else {
	    			szamlalo=0;
	    		}
    		}
    	}
    	//oszlopban 5 
    	for (int i =0; i<boardSize;i++) { 
    		szamlalo= 0;
    		for (int j = 0; j < this.boardSize; j++) {
	    		if (board[j][i].getCellState() == currentPlayer) {
	    			szamlalo +=1;
	    			if (szamlalo ==5) {
	    				if(currentPlayer == 'W') {
	    					System.out.println("nyert a fehér");
	    					return true;
	    				}
	    				//fekete rakott ki 5-öt oszlopban
	    				if(currentPlayer == 'B' && board[j+1][i].getCellState() != 'B') {
	    					System.out.println("nyert a fekete");
	    					return true;
	    				}
	    				//Overeline
	    				else {
	    					System.out.println("nyert a fehér");
	    					return true;
	    				}
	    			}
	    		}
	    		
	    		else {
	    			szamlalo=0;
	    		}
    		}
    	}
    	return nyert;
    }
    //ellenőrzi, hogy döntetlen-e az eredmény
    public void checkForDraw() {
    	for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                if(this.board[i][j].getCellState() == 'E' ){
                	return;
                }
            }
        }
    	System.out.println("Nyert");
    }
    
}
