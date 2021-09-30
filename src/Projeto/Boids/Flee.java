package Projeto.Boids;

import processing.core.PVector;

import java.util.List;

public class Flee extends Behavior{

	public Flee(float weight) {
		super(weight);
	}
	
	@Override
	public PVector getDesiredVelocity(Boid me) {
		List<Boid> list= me.eye.getFarSightBad();

		Boid bodyTarget = null;
		float distance = -1;
		for(Boid target : list) {
			PVector  v1 = new PVector(me.getPos().x, me.getPos().y);
			PVector  v2 = new PVector(target.getPos().x, target.getPos().y);
			float d = v1.dist(v2);
			if(distance==-1 ||distance > d) {
				distance = d;
				bodyTarget = target;
			}
		}
		if(bodyTarget!=null){
			PVector vd = PVector.sub(bodyTarget.getPos(), me.getPos());

			return vd.mult(-5);
		}else return null;
	}

}
