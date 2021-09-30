package Projeto.Boids;

import java.util.ArrayList;  
import java.util.List;

import processing.core.PVector;

public class Patrol extends Behavior{
	private List<PVector> path = new ArrayList<PVector>();
	private int pathPoint = 0;

	public Patrol(float weight) {
		super(weight);
	}
	
	@Override
	public PVector getDesiredVelocity(Boid me) {
		PVector vd = PVector.sub(me.eye.target.getPos(), me.getPos());
		float dist = vd.mag();
		float R = me.dna.radiusArrive;
		
		if( dist < R) {
			pathPoint = (pathPoint + 1) % path.size();
		}
		
		Body bodyTarget = me.eye.target;
		return PVector.sub(bodyTarget.getPos(), me.getPos());
	}
	
	public void definePath(PVector path) {
		this.path.add(path);
	}

}
