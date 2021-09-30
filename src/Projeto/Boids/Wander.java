package Projeto.Boids;

import processing.core.PVector; 


public class Wander extends Behavior{

	public Wander(float weight) {
		super(weight);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public PVector getDesiredVelocity(Boid me) {
		PVector center = me.getPos().copy();
		center.add(me.getVel().copy().mult(me.dna.deltaTWander));
		PVector target = new PVector(me.dna.radiusWander * (float)Math.cos(me.phiWander), 
				    me.dna.radiusWander * (float)Math.sin(me.phiWander));
		
		target.add(center);
		me.phiWander +=  2 * (Math.random() - 0.5f) * me.dna.deltaPhiWander;
		
		return PVector.sub(target, me.getPos());
	}

}
