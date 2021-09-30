package Projeto.Boids;

import java.util.ArrayList;
import java.util.List;

import Projeto.Setup.SubPlot;
import Projeto.Terrain.Patch;
import Projeto.Terrain.WorldConstants;
import Projeto.Terrain.WorldConstants.TypeOfFood;
import processing.core.PApplet;
import processing.core.PVector;

public class Eye {
	private List<Boid> goodTrackingBodies;
	private List<Boid> badTrackingBodies1;
	private List<Boid> badTrackingBodies2;
	private List<Patch> foodTrackingBodies;

	private List<Boid> farSightGood;
	private List<Boid> nearSightGood;
	private List<Boid> farSightBad;
	private List<Boid> nearSightBad;

	public void setGoodTrackingBodies(List<Boid> goodTrackingBodies) {
		this.goodTrackingBodies = goodTrackingBodies;
	}

	private List<Patch> allFoodBodies;
	protected Body foodTarget;

	private Boid me;
	protected Boid target;

	private TypeOfFood foodType;

	public Eye(Boid me, List<Boid> allTrackingBodies, TypeOfFood foodType) {
		this.me = me;
		this.goodTrackingBodies = allTrackingBodies;
		
		this.target = null;

		this.foodType = foodType;
	}

	public List<Boid> getFarSight() {
		return farSightGood;
	}
	public List<Patch> getfoodTrackingBodies() {
		return foodTrackingBodies;
	}

	public List<Boid> getNearSight() {
		return nearSightGood;
	}

	public List<Boid> getFarSightBad() {
		return farSightBad;
	}

	public List<Boid> getNearSightBad() {
		return nearSightBad;
	}
	public void setTarget(Boid target) {
		this.target = target;
	}

	public void setBadTrackingBodies(List<Boid> badTrackingBodies1,List<Boid> badTrackingBodies2){
		this.badTrackingBodies1 = badTrackingBodies1;
		this.badTrackingBodies2 = badTrackingBodies2;
	}
	public void look() {
		farSightGood = new ArrayList<Boid>();
		nearSightGood = new ArrayList<Boid>();
		farSightBad = new ArrayList<Boid>();
		nearSightBad = new ArrayList<Boid>();
		//foodTrackingBodies = new ArrayList<Patch>();

		//if(foodTrackingBodies!=null){
		//for(int i = 0; i < WorldConstrants.NROWS; i++) {
		//for(int j = 0; j < WorldConstrants.NCOLS; j++) {
		//float[]aa=terrain.cells[i][j].getPos();
		//if(farSight(new PVector(aa[0],aa[1])))
		//foodTrackingBodies.add((Patch)terrain.cells[i][j]);


		if(goodTrackingBodies!=null){
			for(Boid b : this.goodTrackingBodies) {

				if(farSight(b.getPos()))
					farSightGood.add(b);

				if(nearSight(b.getPos()))
					nearSightGood.add(b);

			}
		}

		List<Boid> badTrackingBodies = new ArrayList<Boid>();
		if(badTrackingBodies1!=null)badTrackingBodies.addAll( badTrackingBodies1);
		if(badTrackingBodies2!=null)badTrackingBodies.addAll( badTrackingBodies2);
		if(badTrackingBodies.size()>0){
			for(Boid b : badTrackingBodies) {
				if(farSight(b.getPos()))
					farSightBad.add(b);
				if(nearSight(b.getPos()))
					nearSightBad.add(b);
			}
		}
	}

	public void targetForEachBoidType() {
		if (allFoodBodies != null&&((foodTarget == null)||(allFoodBodies.contains(foodTarget)))) {
			if (foodType == TypeOfFood.GRASS) {
				// Search food
				for (Patch patch : allFoodBodies) {
					if (nearSight(patch.getWorldCoord())) {
						foodTarget = new Body(patch.getWorldCoord(), new PVector(), 1f, 1f, 0);
						//foodTarget =(Boid) new Boid(patch.getWorldCoord(), 1f, 1f, 0, null,null,null,null,null,0);
						break;
					}else if(farSight(patch.getWorldCoord())){
						foodTarget = new Body(patch.getWorldCoord(), new PVector(), 1f, 1f, 0);
						//foodTarget =(Boid) new Boid(patch.getWorldCoord(), 1f, 1f, 0, null,null,null,null,null,0);
						break;
					}
				}
			}
		}
	}

	public void display(PApplet p, SubPlot plt) {
		p.pushStyle();
		p.pushMatrix();
		float[] pp = plt.getPixelCoord(me.getPos().x, me.getPos().y);
		p.translate(pp[0], pp[1]);
		p.rotate(-me.getVel().heading());
		p.noFill();
		p.stroke(255, 0, 0);
		p.strokeWeight(3);
		float[] dd1 = plt.getVectorCoord(me.dna.visionDistance, me.dna.visionDistance);
		float[] dd2 = plt.getVectorCoord(me.dna.visionSafeDistance, me.dna.visionSafeDistance);
		p.rotate(me.dna.visionAngle);
		p.line(0, 0, dd1[0], 0);
		p.rotate(-2 * me.dna.visionAngle);
		p.line(0, 0, dd1[0], 0);
		p.rotate(me.dna.visionAngle);
		p.arc(0, 0, 2 * dd1[0], 2 * dd1[0], -me.dna.visionAngle, me.dna.visionAngle);
		p.stroke(255, 0, 255);
		p.circle(0, 0, 2 * dd2[0]);
		p.popMatrix();
		p.popStyle();
	}

	private boolean inSight(PVector t, float maxDistance, float maxAngle) {
		PVector r = PVector.sub(t, me.getPos());
		float d = r.mag();
		float angle = PVector.angleBetween(r, me.getVel());
		return (d < maxDistance && angle < maxAngle && d > 0);
	}

	private boolean farSight(PVector t) {
		return inSight(t, me.dna.visionDistance, me.dna.visionAngle);
	}

	private boolean nearSight(PVector t) {
		return inSight(t, me.dna.visionSafeDistance, (float) Math.PI);
	}

	public void setAllFoodBodies(List<Patch> allFoodBodies) {
		this.allFoodBodies = allFoodBodies;
	}

	public void resetFoodTarget() {
		this.foodTarget = null;
	}
	
	public void setallTrackingBodies4(List<Boid> bodies) {
		this.goodTrackingBodies = bodies;
	}

}