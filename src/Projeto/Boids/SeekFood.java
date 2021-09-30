package Projeto.Boids;

import processing.core.PVector;

public class SeekFood extends Behavior{

	public SeekFood(float weight) {
		super(weight);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public PVector getDesiredVelocity(Boid me) {
		Body bodyTarget = me.eye.foodTarget;

		if(bodyTarget != null) {
			return PVector.sub(bodyTarget.getPos(), me.getPos());
		}

		return null;
	}

}
