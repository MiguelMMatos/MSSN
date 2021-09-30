package Projeto.Boids;

import processing.core.PVector;

public class Break extends Behavior{

	public Break(float weight) {
		super(weight);
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		return new PVector();
	}

}
