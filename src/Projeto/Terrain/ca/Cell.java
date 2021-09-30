package Projeto.Terrain.ca;

import Projeto.Setup.SubPlot;
import Projeto.fractals.LSystem;
import Projeto.fractals.Turtle;
import processing.core.PApplet;
import processing.core.PVector;

public class Cell {
	private int row, col;
	protected int state;
	protected Cell[] neighbors;
	protected CellularAutomata ca;
	
	public Cell( CellularAutomata ca , int row, int col) {
		this.ca = ca;
		this.row = row;
		this.col = col;
		this.state = 0;
		this.neighbors = null;
		
	}
	
	public void setNeighbors(Cell[] neighbors) {
		this.neighbors = neighbors;
	}
	
	public Cell[] getNeighbors() {
		return this.neighbors;
	}
	
	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}
	
	public int getCol() {
		return (int) (ca.xmin + col*ca.cellWidth);
	}
	
	public int getRow() {
		return (int) (ca.ymin + row*ca.cellHeight);
	}
	
	
	
	public void display(PApplet p) {
		p.pushStyle();
		p.fill(ca.getStateColors()[state]);
		p.noStroke();
		p.rect(ca.xmin + col*ca.cellWidth, ca.ymin + row*ca.cellHeight , ca.cellWidth , ca.cellHeight);
		p.popStyle();
	}

	public float getX(){
		return col;
	}
	public float getY(){
		return row;
	}

	public boolean checkIfIsInside(float posX, float posY) {
		if(posX > ca.xmin + col*ca.cellWidth && posX < ca.xmin + col*ca.cellWidth + ca.cellWidth
		&& posY > ca.ymin + row*ca.cellHeight && posY < ca.ymin + row*ca.cellHeight + ca.cellHeight) {
			return true;
		}

		return false;
	}

}
