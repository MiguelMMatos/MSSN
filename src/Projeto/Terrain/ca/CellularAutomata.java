package Projeto.Terrain.ca;

import Projeto.Setup.SubPlot;
import processing.core.PApplet;

public class CellularAutomata {
	protected int nrows;
	protected int ncols;
	protected int nStates;
	private int radiusNeigh;
	protected Cell[][] cells;
	private int[] colors;
	protected float cellWidth, cellHeight; //Pixeis
	protected float xmin, ymin;
	
	private SubPlot plt;
	
	public CellularAutomata(PApplet p, SubPlot plt, int nrows, int ncols, int nStates, int radiusNeigh) {
		this.plt = plt;
		
		this.nrows = nrows;
		this.ncols = ncols;
		this.nStates = nStates;
		this.radiusNeigh = radiusNeigh;
		cells = new Cell[nrows][ncols];
		colors = new int[nStates];
		
		float[] bb = plt.getBoudingBox();
		xmin = bb[0];
		ymin = bb[1];
		
		cellWidth = bb[2]/ncols;
		cellHeight = bb[3]/nrows;
		
		createCells();
		setStateColors(p);
	}
	
	public void display(PApplet p) {
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols; j++) {
				cells[i][j].display(p);
			}
		}
	}
	
	public void setStateColors(PApplet p) {
		for(int i = 0; i < nStates; i++) {
			colors[i] = p.color(p.random(255), p.random(255), p.random(255));
		}
	}
	
	public void setStateColors(int[] colors) {
		this.colors = colors;
	}
	
	public int[] getStateColors() {
		return colors;
	}
	
	public Cell world2Cell(double x, double y) {
		float[] xy = plt.getPixelCoord(x,y);
		
		return pixel2Cell(xy[0], xy[1]);
	}
	
	public Cell pixel2Cell(float x, float y) {
		int row = (int) ((y - ymin) / this.cellHeight);
		int col = (int) ((x - xmin) / this.cellWidth);
		
		if(row >= this.nrows) 
			row = nrows - 1;
			
		if(col >= this.ncols)
			col = this.ncols - 1;
		
		if(row < 0 )
			row = 0;
		
		if(col < 0)
			col = 0;
		
		return cells[row][col];
	}
	
	protected void createCells() {
		for(int i = 0; i < this.nrows; i++) {
			for(int j = 0; j < this.ncols; j++) {
				this.cells[i][j] = new Cell(this, i , j);
			}
		}
		setMooreNeighbors();
	}
	
	public void initRandom() {
		for(int i = 0; i < this.nrows; i++) {
			for(int j = 0; j < this.ncols; j++) {
				this.cells[i][j].setState((int)((nStates - 1) * Math.random()));
			}
		}
	}
	
	protected void setMooreNeighbors() {
		
		//Numero de vizinhos
		int NN = (int) Math.pow(2*this.radiusNeigh + 1, 2);
		
		for(int i = 0; i < this.nrows; i++) {
			for(int j = 0; j < this.ncols; j++) {
				Cell[] neigh = new Cell[NN];
				int n = 0;
				
				for(int ii = -this.radiusNeigh; ii <= this.radiusNeigh; ii++) {
					int row = (i + ii + nrows) % nrows;
					for(int jj = -this.radiusNeigh; jj <= this.radiusNeigh; jj++) {
						int col = (j + jj + ncols) % ncols;
						neigh[n++] = cells[row][col];
					}
				}
				this.cells[i][j].setNeighbors(neigh);
			}
		}
	}
}
