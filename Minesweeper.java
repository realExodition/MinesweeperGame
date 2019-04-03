import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;


public class Minesweeper implements ActionListener{
	//has to be less than 99
	public static int W = 10;
	public static int H = 10;
	//how big the buttons are and the JFrame
	public static int SCALER = 50;
	//number of mines
	public static int NUM_MINES = 20;
	
	JFrame frame = new JFrame ("Minesweeper");
	JButton reset = new JButton("Reset");
	JButton[][] buttons = new JButton [W][H];

	int[][] counts = new int[W][H];
	Container grid = new Container();
	
	//sentinal value to see if the location is populated by a mine
	final int MINE = 10;
	
	public Minesweeper() {
		frame.setSize(W*SCALER, H*SCALER);
		frame.setLayout(new BorderLayout());
		frame.add(reset, BorderLayout.NORTH);
		reset.addActionListener(this);
		//Button grids
		grid.setLayout(new GridLayout(W,H));
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[i].length; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].addActionListener(this);
				grid.add(buttons[i][j]);
			}
		}
		frame.add(grid, BorderLayout.CENTER);
		
		createMines();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Minesweeper();
	}
	public void createMines() {
		//lists of the locations that has no defined shape to account for different board sizes
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < counts.length; i++) {
			for(int j = 0; j < counts[i].length; j++) {
				list.add(i*100+j);
				
			}
		}
		//resting counts places mines
		counts = new int[W][H];
		for(int i = 0; i < NUM_MINES; i++) {
			int x = (int)(Math.random()*list.size());
			counts[list.get(x)/100][list.get(x)%100] = MINE;
			list.remove(x);
		}
		
		//checking neighbors then adding the mines near to the counts array
		for(int i = 0; i < counts.length; i++) {
			for(int j = 0; j < counts[i].length; j++) {
				if(counts[i][j] != MINE) {
					int neighborCount = 0;
					if (i > 0 && j > 0 && counts[i-1][j-1] == MINE) {//upper left
						neighborCount++;
					}
					if (j > 0 && counts[i][j-1] == MINE) {//up
						neighborCount++;
					}
					if (i <counts.length-1 && j > 0 && counts[i+1][j-1] == MINE) {//upper right
						neighborCount++;
					}					
					if (i > 0 && counts[i-1][j] == MINE) {//left
						neighborCount++;
					}
					if (i <counts.length-1 && counts[i+1][j] == MINE) {//right
						neighborCount++;
					}
					if (i > 0 && j < counts[i].length - 1 && counts[i-1][j+1] == MINE) {//down left
						neighborCount++;
					}
					if (j <counts[i].length-1 && counts[i][j+1]== MINE) {//down 
						neighborCount++;
					}
					if (i <counts.length-1 && j< counts[i].length - 1 && counts[i+1][j+1]== MINE) {//down right
						neighborCount++;
					}
					counts[i][j] = neighborCount;
				}
			}
		}
		
	}

	public void Lose() {
		for(int i = 0;i < buttons.length; i++) {
			for(int j = 0; j < buttons[i].length; j++) {
				if(buttons[i][j].isEnabled()) {
					if(counts[i][j] != MINE) {
						if(counts[i][j]>0 && counts[i][j] != MINE) {
							buttons[i][j].setText(counts[i][j] + "");
							buttons[i][j].setEnabled(false);
						}
						else {
							buttons[i][j].setText("");
							buttons[i][j].setEnabled(false);
						}
					}
					else {
						buttons[i][j].setForeground(Color.RED);
						buttons[i][j].setText("*");
						buttons[i][j].setEnabled(false);
					}
				}
			}
		}
	}
	
	public void clearZero(ArrayList<Integer> toClear) {
		if(toClear.size()==0) {
			return;
		}
		else {
			int x = toClear.get(0) / 100;
			int y = toClear.get(0) % 100;
			toClear.remove(0);
				if(x>0&&y>0&&buttons[x-1][y-1].isEnabled()) {//up left
					buttons[x-1][y-1].setText(counts[x-1][y-1] + "");
					buttons[x-1][y-1].setEnabled(false);
					if(counts[x-1][y-1] == 0) {
						toClear.add((x-1)*100+y-1);
					}
				}
				if (y>0&&buttons[x][y-1].isEnabled()) {//up
					buttons[x][y-1].setText(counts[x][y-1] + "");
					buttons[x][y-1].setEnabled(false);
					if(counts[x][y-1] == 0) {
						toClear.add((x)*100+y-1);
					}
				}
				if(x < counts.length-1 && y > 0 &&buttons[x+1][y-1].isEnabled()) {//up right
					buttons[x+1][y-1].setText(counts[x+1][y-1] + "");
					buttons[x+1][y-1].setEnabled(false);
					if(counts[x+1][y-1] == 0) {
						toClear.add((x+1)*100+y-1);
					}
				}
				if(x>0&&buttons[x-1][y].isEnabled()) {// left
					buttons[x-1][y].setText(counts[x-1][y] + "");
					buttons[x-1][y].setEnabled(false);
					if(counts[x-1][y] == 0) {
						toClear.add((x-1)*100+y);
					}
				}
				if(x < counts.length-1 && buttons[x+1][y].isEnabled()) {// right
					buttons[x+1][y].setText(counts[x+1][y] + "");
					buttons[x+1][y].setEnabled(false);
					if(counts[x+1][y] == 0) {
						toClear.add((x+1)*100+y);
					}
				}
				if(x>0&&y<counts[x].length-1&&buttons[x-1][y+1].isEnabled()) {//down left
					buttons[x-1][y+1].setText(counts[x-1][y+1] + "");
					buttons[x-1][y+1].setEnabled(false);
					if(counts[x-1][y+1] == 0) {
						toClear.add((x-1)*100+y+1);
					}
				}
				if (y < counts[x].length-1&&buttons[x][y+1].isEnabled()) {//down
					buttons[x][y+1].setText(counts[x][y+1] + "");
					buttons[x][y+1].setEnabled(false);
					if(counts[x][y+1] == 0) {
						toClear.add((x)*100+y+1);
					}
				}
				if(x < counts.length-1 && y < counts[x].length-1 && buttons[x+1][y+1].isEnabled()) {//down right
					buttons[x+1][y+1].setText(counts[x+1][y+1] + "");
					buttons[x+1][y+1].setEnabled(false);
					if(counts[x+1][y+1] == 0) {
						toClear.add((x+1)*100+y+1);
					}
				}
			}
			clearZero(toClear);
			cleanUp();
		}
	
	public void cleanUp() {
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[i].length; j++) {
				if(buttons[i][j].isEnabled()) {
					
				}
				else {
					if(counts[i][j]>0 && counts[i][j] != MINE) {
						buttons[i][j].setText(counts[i][j] + "");
						buttons[i][j].setEnabled(false);
					}
					else if (counts[i][j] == 0){
						buttons[i][j].setText("");
						buttons[i][j].setEnabled(false);					
					}
				}
			}
		}
	}
	public void win() {
		boolean won = true;
		for(int i = 0; i < counts.length; i++) {
			for(int j = 0; j < counts[i].length; j++) {
				if(counts[i][j] != MINE && buttons[i][j].isEnabled()==true) {
					won = false;
				}
			}
		}
		if(won == true) {
			JOptionPane.showMessageDialog(frame, "You Win");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(reset)) {
			//reset
			for(int i = 0; i < buttons.length; i++) {
				for(int j = 0; j < buttons[i].length; j++) {
					buttons[i][j].setEnabled(true);
					buttons[i][j].setText("");
				}
			}
			createMines();
		}
		else {
			for(int i = 0; i < buttons.length; i++) {
				for(int j = 0; j < buttons[i].length; j++) {
					if(e.getSource().equals(buttons[i][j])) {
						if(counts[i][j]>0 && counts[i][j] != MINE) {
							buttons[i][j].setText(counts[i][j] + "");
							buttons[i][j].setEnabled(false);
							win();
						}
						else if (counts[i][j] == MINE){
							buttons[i][j].setForeground(Color.RED);
							buttons[i][j].setText("*");
							buttons[i][j].setEnabled(false);
							Lose();
							JOptionPane.showMessageDialog(frame, "You Lose");
						}
						else if (counts[i][j] == 0){
							buttons[i][j].setText("");
							buttons[i][j].setEnabled(false);
							ArrayList<Integer> toClear = new ArrayList<Integer>();
							toClear.add(i*100+j);
							clearZero(toClear);
							win();
						}
						else {
							buttons[i][j].setText(counts[i][j] + "");
							buttons[i][j].setEnabled(false);
							win();
						}
					}
				}
			}
			
		}
	}

}
