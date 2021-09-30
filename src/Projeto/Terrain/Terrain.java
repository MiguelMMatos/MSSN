package Projeto.Terrain;

import java.util.ArrayList;
import java.util.List;


import Projeto.Setup.SubPlot;
import Projeto.Terrain.ca.MajorityCA;
import Projeto.fractals.LSystem;
import Projeto.fractals.Rule;
import Projeto.fractals.Turtle;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Terrain extends MajorityCA{
	private List<Patch> eatablePatches;
	private List<Patch> allPatches;
	private List<Patch> obstaclePatch;
	
	private double timeNow;
	private static double timeBetweenTests = 500;
	
	private SubPlot plt;
	private Turtle turtle;
	private LSystem lsys;
	private String imagensDir = "Projeto/imagens/";
	private PImage background;
	private PImage fish;

	public Terrain(PApplet p, SubPlot plt) {
		super(p, plt, WorldConstants.NROWS, WorldConstants.NCOLS, WorldConstants.NSTATES, 1);
		this.plt = plt;

		eatablePatches = new ArrayList<Patch>();
		allPatches = new ArrayList<Patch>();
		obstaclePatch = new ArrayList<Patch>();
		
		timeNow = p.millis() + timeBetweenTests;
		
		createCells();

		Rule[] rules = new Rule[1];
		rules[0] = new Rule('F',"F[+F]F[-F]F");
		lsys = new LSystem("F", rules);
		turtle = new Turtle(0.05f, PApplet.radians(25.7f));
		lsys.nextGeneration();

		background = p.loadImage(imagensDir + "ocean.png");
		fish=p.loadImage(imagensDir + "fish.png");
		fish.resize((int)(fish.width/3.4f),(int)(fish.height/3.4f));
	}
	
	@Override
	public void display(PApplet p) {
		if(timeNow < p.millis()) {
			timeNow = p.millis() + timeBetweenTests;
			getPatchEatable();
		}

		displayPatches(p);
	}
	
	private void displayPatches(PApplet p) {
		float[] bb = plt.getBoudingBox();
		p.fill(255,255,255);
		p.rect(bb[0], bb[1], bb[2], bb[3]);
		p.image(background, bb[0], bb[1]);
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols; j++) {
				//
				//cells[i][j].display(p);
				if((cells[i][j].getState()== WorldConstants.PatchType.FOOD.ordinal())){
					//cells[i][j].display(p);
					float x=((Patch)cells[i][j]).getWorldCoord().x;
					float y=((Patch)cells[i][j]).getWorldCoord().y;
					float[] bbb = plt.getPixelCoord(x,y);
					p.image(fish, bbb[0], bbb[1]);
				}
				else if((cells[i][j].getState()== WorldConstants.PatchType.OBSTACLE.ordinal())){
					float x=((Patch)cells[i][j]).getWorldCoord().x+(20f/WorldConstants.NCOLS/2f);
					float y=((Patch)cells[i][j]).getWorldCoord().y-(20f/WorldConstants.NROWS/2f);
					int lados = 8;

					for(int a=0;a<lados;a++)
						turtle.display(p,plt, new PVector(x,y),lsys,(360/lados)*a);
				}
			}
		}
	}
	
	@Override
	protected void createCells() {
		int minRT = (int) (WorldConstants.REGENERATION_TIME[0] * 100);
		int maxRT = (int) (WorldConstants.REGENERATION_TIME[1] * 100);
		
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols; j++) {
				int timeToGrow = (int) (minRT + (maxRT - minRT) * Math.random());
				cells[i][j] = new Patch(plt, this, i, j, timeToGrow);
			}
		}
		setMooreNeighbors();
	}
	
	
	public void regenerate() {
		for(Patch patch : allPatches) 
				((Patch)patch).regenerate();
	}
	
	public Patch checkIfIsInside(PVector pos) {
		for(Patch patch : allPatches) {
			//Comer
			if( ((Patch)patch).checkIfIsInside(pos.x, pos.y) == true)
				return (Patch)patch;		
		}
		return null;
	}

	public boolean checkIfIsTakingDamage(PVector pos){
		for(Patch patch : obstaclePatch) {
			if( ((Patch)patch).checkIfIsInside(pos.x, pos.y) == true)
				return true;
		}

		return false;
	}
	
	public void setPatches() {
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols; j++) {
				if( ((Patch)cells[i][j]).isGrass() )
					allPatches.add((Patch)cells[i][j]);
			}
		}
	}
	
	public List<Patch> getPatchEatable() {
		eatablePatches.clear();
		
		for(Patch patch : allPatches) {
			if( patch.isEatable() )
				eatablePatches.add(patch);
		}
		
		return eatablePatches;
	}

	public void updateObstaclePatches(){
		obstaclePatch.clear();

		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols; j++) {
				if ((cells[i][j].getState() == WorldConstants.PatchType.OBSTACLE.ordinal())) {
					obstaclePatch.add((Patch) cells[i][j]);
				}
			}
		}

	}

	public List<Patch> getObstaclePatch(){
		return obstaclePatch;
	}
	
}








