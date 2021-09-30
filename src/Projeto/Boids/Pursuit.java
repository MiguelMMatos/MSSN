package Projeto.Boids;


import processing.core.PVector; 

public class Pursuit extends Behavior{

	public Pursuit(float weight) {
		super(weight);
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		Body bodyTarget = me.eye.foodTarget;
		if(bodyTarget != null) {

			PVector vel = bodyTarget.getVel().copy();
			PVector d = vel.mult(me.dna.deltaTPursuit);
			PVector target =  PVector.add(bodyTarget.getPos(), d);

			return PVector.sub(target, me.getPos()).mult(10);
		}
		return null;
	}

}
