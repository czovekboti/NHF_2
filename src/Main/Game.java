package Main;

public class Game {
    protected char currentPlayer;  
    protected int boardSize;
    protected Cell[][] board;    
    private int round;
    private char winner;
    public Cell[][] getBoard() {
		return board;
	}
	public int getRound() {
    	return round;
    }
    public char getCurrentPlayer(){
    	return currentPlayer;
    }
    
    public void setRound(int n) {
    	round = n;
    }
    public void setCurrentPlayer(char cp) {currentPlayer= cp;}
    public void makeAImove() {}    
    public char getWinner(){return winner;}
    public Game(int boardSize) {
    	this.winner='0';
        this.boardSize = boardSize;
        this.round = 0;
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
    	if(col<=boardSize-1 && row<=boardSize-1) {		
    		return board[row][col];}
    	else {
    		return board[0][0];
    	}
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
    		
    	}
    	else if(round == 2) {
    		switchPlayer();
    		board[col][row].setCellState(currentPlayer);
    		round+=1;
    		
    		
    	}
    	//eldöntik hogy cserélnek-e majd utána folytatdik a játék
    	else if(round == 3) {
    		board[col][row].setCellState(currentPlayer);
    		round+=1;
    		
    	}
    	//javaslat feltevése
    	else if(round == 4  || round == 5) {
    		if(board[col][row].getCellState()=='E') {
	    		currentPlayer = 'S';
	    		board[col][row].setCellState(currentPlayer);	
	    		round+=1;	
	    		
    		}
    	}
    	else if(round == 6) {
    		currentPlayer = 'W';
    		if(board[col][row].getCellState()=='S') {
    			board[col][row].setCellState(currentPlayer);
    			round+=1;
    			resetSelectedCells();
    		}
    	}
    	else if(round > 6) {	
    		if(board[col][row].getCellState()=='E') {
	    		switchPlayer();
	    		board[col][row].setCellState(currentPlayer);
		    	checkForWin(currentPlayer); 
		    	
    		}
    	}
    }
    protected void switchPlayer() {
    	currentPlayer = (currentPlayer=='B') ? 'W' : 'B';  	
    }   
    //Emptyre állítja azokat a cellákat amelyek selectedek
    protected void resetSelectedCells() {
    	for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                if(this.board[i][j].getCellState() == 'S' ){
                	this.board[i][j].setCellState('E');
                }
            }
        }	
    }
    protected int checkCellState(Cell cell, char currentPlayer, int szamlalo) {
		if (cell.getCellState() == currentPlayer) {
		        szamlalo += 1;
		    } else {
		        szamlalo = 0;
		    }
		    return szamlalo;
	}
    public char returnWin(char currentPlayer, int i, int j) {
        char nyert='E';
        if (currentPlayer == 'W') {
    		nyert ='W';
        
        }
        else if (currentPlayer == 'B' && i < boardSize && j < boardSize) {
            if (board[i][j].getCellState() != 'B') {
                nyert='B';
                
            } else {
                nyert='W';
            }
        }
        else if (currentPlayer == 'B' && !(i < boardSize && j < boardSize)) {
    		nyert ='B';
        }
        return nyert; 
    }

    //ellenőrizzük hogy nyert e valamelyik játékos
    protected boolean checkForWin(char currentPlayer) {
    	boolean nyert=false;
    	//sorban 5 
    	int szamlalo;
    	for (int i =0; i<boardSize;i++) { 
    		szamlalo= 0;
    		for (int j = 0; j < this.boardSize; j++) {
    			szamlalo = checkCellState(board[i][j], currentPlayer,szamlalo);
    			if (szamlalo ==5) {
    				winner = returnWin(currentPlayer,i, j+1);
    				nyert = true;
    			}
	    	}
    	}
    	//oszlopban 5 
    	for (int i =0; i<boardSize;i++) { 
    		szamlalo= 0;
    		for (int j = 0; j < this.boardSize; j++) {
    			szamlalo = checkCellState(board[j][i], currentPlayer,szamlalo);
    			if (szamlalo ==5) {
    				winner = returnWin(currentPlayer,j+1,i);
    				nyert = true;
    			}
	    			
    		}
    	}
    	//átlóban
    	szamlalo = 0;
    	for (int i = 0; i < boardSize - 4; i++) {
    	    for (int j = 0; j < boardSize - 4; j++) {
    	        if (board[i][j].getCellState() == currentPlayer) {
    	            for (int k = 0; k < 5; k++) {
    	                szamlalo = checkCellState(board[i + k][j + k], currentPlayer, szamlalo);
    	                if (szamlalo == 5) {
    	                    winner = returnWin(currentPlayer, i + k + 1, j + k + 1);
    	                    return true;
    	                }
    	            }
    	        } else {
    	            szamlalo = 0;
    	        }
    	    }
    	}
    	//átlóban
    	szamlalo = 0;
    	for (int i = 4; i < boardSize; i++) {
    	    for (int j = 0; j < boardSize - 4; j++) {
    	        if (board[i][j].getCellState() == currentPlayer) {
    	            for (int k = 0; k < 5; k++) {
    	                szamlalo = checkCellState(board[i - k][j + k], currentPlayer, szamlalo);
    	                if (szamlalo == 5) {
    	                    winner = returnWin(currentPlayer, i + 1, j - 1);
    	                    return true;
    	                }
    	            }
    	        } else {
    	            szamlalo = 0;
    	        }
    	    }
    	}
    	
    	return nyert;
    }
    //ellenőrzi, hogy döntetlen-e az eredmény
    protected boolean checkForDraw() {
    	for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                if(this.board[i][j].getCellState() != 'E' ){
                	return false;
                }
            }
        }
    	return true;
    }
	public char getAIplayer() {
		// TODO Auto-generated method stub
		return (Character) null;
	}
	public void setAIplayer(char c) {}
    
}
