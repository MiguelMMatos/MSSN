package Projeto.Terrain.ca;

import Projeto.Setup.SubPlot;

import processing.core.PApplet;

public class MajorityCA extends CellularAutomata{
	private SubPlot plt;
	
	public MajorityCA(PApplet p, SubPlot plt, int nrows, int ncols, int nStates, int radiusNeigh) {
		super(p, plt, nrows, ncols, nStates, radiusNeigh);
		this.plt = plt;
	}
	
	public void display(PApplet p) {
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols; j++) {
				cells[i][j].display(p);
			}
		}
	}
	
	@Override
	protected void createCells() {
		for(int i = 0; i < this.nrows; i++) {
			for(int j = 0; j < this.ncols; j++) {
				this.cells[i][j] = new MajorityCell(this, i , j);
			}
		}
		setMooreNeighbors();
	}
	
	public boolean majorityRule() {
		for(int i = 0; i < this.nrows; i++) {
			for(int j = 0; j < this.ncols; j++) {
				((MajorityCell) cells[i][j]).computeHistogram();
			}
		}
		
		boolean anyChanged = false;
		for(int i = 0; i < this.nrows; i++) {
			for(int j = 0; j < this.ncols; j++) {
				boolean changed = 
						((MajorityCell) cells[i][j]).applyMajorityRule();
				if(changed)
					anyChanged = true;
			}
		}
		
		return anyChanged;
	}
	

}
