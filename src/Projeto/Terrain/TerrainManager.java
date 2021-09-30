package Projeto.Terrain;

import Projeto.Setup.SubPlot;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class TerrainManager {
	private Terrain terrain;
	public TerrainManager(PApplet p, SubPlot plt) {
		terrain = new Terrain(p, plt);
		terrain.setStateColors(getColors(p));
		terrain.initRandom();
		
		//Fazer 5 vezes majority
		for(int i = 0; i < 5; i++) {
			terrain.majorityRule();
		}
		
		terrain.setPatches();
	}
	
	public void display(PApplet p,SubPlot plt) {
		terrain.regenerate();
		terrain.display(p);
	}
	
	
	private int[] getColors(PApplet p) {
		int[] colors = new int[WorldConstants.NSTATES];
		
		for(int i = 0; i < WorldConstants.NSTATES; i++) {
			colors[i] = p.color( WorldConstants.TERRAIN_COLORS[i][0],
					WorldConstants.TERRAIN_COLORS[i][1],
					WorldConstants.TERRAIN_COLORS[i][2]);
		}
		
		return colors;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}

}
