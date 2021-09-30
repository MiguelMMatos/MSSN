package Projeto.Terrain;

import Projeto.Setup.SubPlot;
import Projeto.Terrain.WorldConstants.PatchType;
import Projeto.Terrain.ca.MajorityCell;
import processing.core.PApplet;
import processing.core.PVector;

public class Patch extends MajorityCell{

	private long eatenTime;
	private int timeToGrow;
	
	public static float energy = 20f;
	
	private SubPlot plt;
	
	public Patch(SubPlot plt, Terrain terrain, int row, int col, int timeToGrow) {
		super(terrain, row, col);
		this.plt = plt;
		
		this.timeToGrow = timeToGrow;
		this.eatenTime = System.currentTimeMillis();
	}
	
	
	public void setFertile() {
		state = PatchType.FERTILE.ordinal();
		eatenTime = System.currentTimeMillis() + timeToGrow;
	}
	
	public boolean isGrass() {
		return ((state == PatchType.FOOD.ordinal()) || (state == PatchType.FERTILE.ordinal()) || (state == PatchType.OBSTACLE.ordinal()));
	}
	
	public boolean isEatable() {
		return (state == PatchType.FOOD.ordinal() );
	}
	
	public void regenerate() {
		if( state == PatchType.FERTILE.ordinal() && System.currentTimeMillis() > ( eatenTime)) {
			state = PatchType.FOOD.ordinal();
		}
	}

	public boolean isObstacle(){
		return (state == PatchType.OBSTACLE.ordinal());
	}
	
	public PVector getWorldCoord() {
		double[] pp = plt.getWorldCoord(getCol(), getRow() );
		return new PVector((float)pp[0], (float)pp[1]);
	}
}
