package Projeto.Boids;

import java.util.ArrayList; 
import java.util.List;

import Projeto.Setup.SubPlot;
import Projeto.Terrain.Patch;
import Projeto.Terrain.WorldConstants;
import processing.core.PApplet;
import processing.core.PVector;

public class Flock {
	protected PApplet p;
	protected SubPlot plt;
	private boolean isAlive;
	protected WorldConstants.PokeTypes type;
	
	public Flock(PApplet p, SubPlot plt, WorldConstants.PokeTypes type) {
		this.p = p;
		this.plt = plt;
		this.type = type;
		
		isAlive = true;
	}
	
	public boolean checkIfIsDead() {
		return isAlive;
	}
	
	public void Died() {
		this.isAlive = false;
	}
	
	protected static List<Body> boidList2BodyList(List<Boid> boids){
		List<Body> bodies = new ArrayList<Body>();
		
		for(Boid b : boids)
			bodies.add(b);

		return bodies;
	}
	
	protected float distanceBetween2Boids(Boid me, Boid target) {
		return p.dist(me.getPos().x, me.getPos().y, target.getPos().x, target.getPos().y);
	}
	
}
