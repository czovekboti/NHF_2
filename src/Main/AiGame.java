package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JLayer;

public class AiGame extends Game {
    private char AIplayer;
    private int round;
    private List<int[]> possibleMoves;
    private int lastRow;
    private int lastCol;
    public AiGame(int boardsize) {
        super(boardsize);
        Random rand = new Random();
    	int random = rand.nextInt(2);
    	AIplayer = (random == 1) ? 'B' : 'W';	
    	if(currentPlayer== AIplayer) {
        	if(round<2) {
        		board[boardSize/2][boardSize/2].setCellState('B');
        		board[boardSize/2+1][boardSize/2].setCellState('B');
        		board[boardSize/2+2][boardSize/2].setCellState('W');
        		switchPlayer();
        	}
        }
    }
    public char getAIplayer() {
		return AIplayer;
	}
    public void setAIplayer(char c) {
		AIplayer=c;
	}
    public int getRound() {return round;}
    public void setRound(int n) {round=n;}
    public char getAi() {
    	return AIplayer;
    } 
    public boolean isValidMove(int row, int col) {
    	if(col>=0 && col<boardSize && row <boardSize && row>=0) {
    		if(board[row][col].getCellState()=='E') {
    		return true;}
    	}
    	return false;
    }
    public boolean isGameFinished() {
    	if(getWinner() !='0'|| (this.checkForDraw())) {
    		return true;
    	}
    	return false;
    }
    // Minimax algoritmus
    private int[] minimax(int depth, boolean maximizingPlayer, int alpha, int beta) {
        // Ellenfél lépésének értékelése
        if (depth == 0 || isGameFinished()) {
            int evaluation=evaluateBoard(AIplayer);
            return new int[]{-1, -1, evaluation};
        }
        int bestMoveRow = -1;
        int bestMoveCol = -1;
        int bestValue = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int row = 0; row <boardSize; row++) {
            for (int col = 0; col <boardSize; col++) {
                if (isValidMove(row, col)) {
                    // Lépés végrehajtása
                	char opponent = AIplayer=='W'? 'B':'W';
                    board[row][col].setCellState(maximizingPlayer ? AIplayer : opponent);

                    // Rekurzív hívás a következő szintre
                    int[] result = minimax(depth - 1, !maximizingPlayer, alpha, beta);
                    int currentValue = result[2];

                    // Lépés visszavonása
                    board[row][col].setCellState('E');
                    // Értékelés
                    if (maximizingPlayer) {
                        if (currentValue > bestValue) {
                            bestValue = currentValue;
                            bestMoveRow = row;
                            bestMoveCol = col;
                        }
                        alpha = Math.max(alpha, bestValue);
                    } else {
                        if (currentValue < bestValue) {
                            bestValue = currentValue;
                            bestMoveRow = row;
                            bestMoveCol = col;
                        }
                        beta = Math.min(beta, bestValue);
                    }

                    // Alpha-beta vágás
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }

        return new int[]{bestMoveRow, bestMoveCol, bestValue};
    }
    private int evaluateBoard(char player) {
        int playerScore = 0;
        int opponentScore = 0;
        playerScore += evaluateBoardScore(AIplayer);
        opponentScore += evaluateBoardScore(AIplayer=='W' ? 'B':'W');

        return playerScore - opponentScore;
    }
    private int evaluateBoardScore(char player) {
        int playerScore = 0;
        

        // Rows and columns scoring
        for (int i = 0; i <boardSize; i++) {
            int streakLength = 0; 
            for (int j = 0; j <boardSize; j++) {
                if (board[j][i] .getCellState() == player) {
                    streakLength++;
                } else {
                	playerScore += mapStreakLengthToScore(streakLength,player);
                    streakLength = 0;
                }
            }
        }

        // Diagonal scoring
        for (int i = 0; i <boardSize; i++) {
            int streakLength = 0;
            for (int j = 0, k = i; j <boardSize && k <boardSize; j++, k++) {
                if (board[j][k].getCellState() == player) {
                    streakLength++;
                } else {
                	playerScore += mapStreakLengthToScore(streakLength,player);
                    streakLength = 0;
                }
            }
            playerScore += mapStreakLengthToScore(streakLength,player);
        }

        // Diagonal scoring (bottom-left to top-right)
        for (int i = 0; i <boardSize; i++) {
            int streakLength = 0;
            for (int j = boardSize-1, k = i; j >= 0 && k <boardSize; j--, k++) {
                if (board[j][k].getCellState() == player) {
                    streakLength++;
                } else {
                	playerScore += mapStreakLengthToScore(streakLength,player);
                    streakLength = 0;
                }
            }
            playerScore += mapStreakLengthToScore(streakLength,player);
        }

        return playerScore;
    }
    private int mapStreakLengthToScore(int streakLength, char player) {
        int score = 0;

        if (player == AIplayer) {
            switch (streakLength) {
                case 2:
                    score = 5;
                    break;
                case 3:
                    score = 10;
                    break;
                case 4:
                    score = 20;
                    break;
                default:
                    if (streakLength >= 5) {
                        score = 50;
                    }
            }
        } else {
            // Ellenfél pontszámai
            switch (streakLength) {
                case 2:
                    score = 10;
                    break;
                case 3:
                    score = 30;
                    break;
                case 4:
                    score = 60;
                    break;
                case 5:
                    score = 100;
                    break;
            }
        }

        return score;
    }
    
    public void makeAImove() {
    	if(currentPlayer== AIplayer) {
        	if(round ==3) {
        		for(int i = lastCol-2;i<lastCol+2;i++) {
        			for(int j = lastRow-2;j<lastRow+2;j++) {
        				if(isValidMove(i,j)&& round ==3 ) {
        					board[j][i].setCellState(AIplayer);
        					switchPlayer();
        	        		round++;
        	        		break;
        				}
        			}
        		}
        	}
        	else if (round ==4 || round ==5) {
        		for(int i =-3; i<3; i++) {
        			if(isValidMove(lastRow+i,lastCol+i)&&(round==4 || round ==5)) {
        				board[lastRow+i] [lastCol+i].setCellState('S');
        				round++;
        			}}
        		switchPlayer();
        		
        				
        	}
        	else if(round == 6) {
        		for(int i = 0; i<boardSize;i++) {
        			for(int j = 0; j<boardSize;j++) {
        				if(board[i][j].getCellState()=='S') {
        					board[i][j].setCellState(AIplayer);
        					resetSelectedCells();
        					round+=1;
        					switchPlayer();
        					break;
        				}
        			}}
        	}
        	else if(round>6) {
        		int depth;
        		if(boardSize>20) {
        			depth=1;
        		}
        		else {depth=2;}
        		int[] aiMove = minimax(depth, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
                int aiMoveRow = aiMove[0];
                int aiMoveCol = aiMove[1];
                if (isValidMove(aiMoveRow, aiMoveCol)) {
                    board[aiMoveRow][aiMoveCol].setCellState(AIplayer);
                    if(getWinner() =='0') {
                		checkForWin(currentPlayer);
                		}
                    switchPlayer();
        		}
        	}
    	}
    	
    	}   	
    @Override
    public void makeMove(int row, int col) {
    	if(AIplayer=='B'&& round <=3) {
        		round=3;
        }
	    if(currentPlayer != AIplayer) {
	    	
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
	    	else if(round == 3) {
	    		board[col][row].setCellState(currentPlayer);
	    		round+=1;
	    		switchPlayer();
	    	}
	    	//javaslat feltevése
	    	else if(round == 4  || round == 5) {
	    		if(board[col][row].getCellState()=='E') {
		    		currentPlayer = 'S';
		    		board[col][row].setCellState(currentPlayer);	
		    		round+=1;	
		    		
	    		}
	    		round+=1;
	    	}
	    	else if(round == 6) {
	    		currentPlayer = 'W';
	    		if(board[col][row].getCellState()=='S') {
	    			board[col][row].setCellState(currentPlayer);
	    			round+=1;
	    			resetSelectedCells();
	    			switchPlayer();
	    		}
	    	}
	    	else if(round > 6) {	
	    		if(board[col][row].getCellState()=='E') {
		    		board[col][row].setCellState(currentPlayer);
			    	if(!checkForWin(currentPlayer)) {
			    		switchPlayer();	
			    	} 
			    	
	    		}
	    	}
    	checkForWin(currentPlayer); 
    	lastRow=col;
    	lastCol=row;
    	
    }    
    
    }

	

   
}

