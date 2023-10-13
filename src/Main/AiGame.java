package Main;

import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.random.*;
public class AiGame extends Game { 

	private char AIplayer;
	public AiGame(int boardsize) {
		super(boardsize);
	}
	//
	private void setAIPlayer() {
		Random rand = new Random();
		int random = rand.nextInt(1);
		if (random ==1) {
			AIplayer = 'B';
		}
		else {
			AIplayer = 'W';
		}
	}
	// Játék folyása 
	public void Opening() {
		setAIPlayer();

	}



}
