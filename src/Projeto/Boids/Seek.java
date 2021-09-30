package Projeto.Boids;

import processing.core.PVector;

public class Seek extends Behavior{

	public Seek(float weight) {
		super(weight);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public PVector getDesiredVelocity(Boid me) {
		Body bodyTarget = me.eye.target;
		return PVector.sub(bodyTarget.getPos(), me.getPos());
	}

}
