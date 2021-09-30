package Projeto.Boids;

import java.util.ArrayList; 
import java.util.List;

import Projeto.Setup.SubPlot;
import Projeto.Terrain.Patch;
import Projeto.Terrain.Terrain;
import Projeto.Terrain.WorldConstants;
import Projeto.Terrain.WorldConstants.TypeOfFood;
import processing.core.PApplet;
import processing.core.PVector;

public class FlockPredators extends Flock{
	private List<Boid> boids;
	private List<Boid> targetsM;
	private List<Boid> targetsS;
	
	private float mass, radius;
	private float[] sacWeights;
	
	public boolean stop = false;
	private int color;

	private static double timeBetweenReproduce = 20;

	public FlockPredators(int nboids, float mass, float radius, int color, float[] sacWeights, PApplet p, SubPlot plt, WorldConstants.PokeTypes type) {
		super(p, plt, type);

		this.color=color;
		this.mass = mass;
		this.radius = radius;
		this.sacWeights = sacWeights;
		
		boids = new ArrayList<Boid>();
		float[] w = plt.getBoudingBox();
		
		for(int i = 0; i < nboids; i++) {
			float x = p.random(w[0], w[2]);
			float y = p.random(w[1], w[3]);
			
			addSingleBoid(x, y, type);
		}
		
		this.setEyes();
	}
	
	public void display(float dt) {
		for(Boid b : this.boids) {
			b.display(p, plt,dt, type);
			//b.getEye().display(p,plt);
		}
	}
	
	public void checkFoodBoids(Terrain terrain, float dt) {
		List<Boid> targets=new ArrayList<Boid>();
		targets.addAll(targetsM);
		targets.addAll(targetsS);
		for(Boid b : boids) {
			b.getEye().setBadTrackingBodies(targetsM,targetsS);
			b.getEye().look();
			//Set food
			if(b.getEye().foodTarget == null||!(targets.contains(b.getEye().foodTarget))) {
				//Ver se alguem esta no range de visao

				List<Boid> farSight = b.getEye().getFarSightBad();
				
				float distanceMin = Integer.MAX_VALUE;
				Boid newTarget = null;
				
				for(Boid target : farSight) {
					if(distanceMin > distanceBetween2Boids(b, (Boid)target)) {
						distanceMin = distanceBetween2Boids(b, (Boid)target);
						newTarget = target;	
					}
				}
				
				b.getEye().foodTarget = newTarget;				
			}
			else {
				if(distanceBetween2Boids(b, (Boid)b.getEye().foodTarget) > b.dna.visionDistance){
					b.getEye().foodTarget = null;
				}
				else {
					for(Boid target : targets) {
						if(distanceBetween2Boids(b, target) < b.dna.deltaTPursuit * 2) {
							b.getEnergy().increaseEnergy(40);
							target.setIsAlive(false);
							b.getEye().foodTarget = null;
						}
					}
				}
			}
		}		
	}
	
	public void applyBehavior(float dt) {
		for(Boid b : this.boids) {
			if(b.isAuto()) {
				if(b.getEye().foodTarget == null || sacWeights[4] == 0) {
					b.applyBehaviors(dt);
				}
				else{
					b.applyBehavior(4, dt);
				}
			}	
			else 
				b.look();
		}	
	}

	public void setTargetsM(List<Boid> targets) {
		this.targetsM = targets;
	}
	public void setTargetsS(List<Boid> targets) {
		this.targetsS = targets;
	}
	public List<Boid> getBoids() {
		return boids;
	}
	public void ChangeColor(Boid b, PApplet p, SubPlot plt, int color) {
		boids.get(boids.indexOf(b)).setSelected(false);
		boids.get(boids.indexOf(b)).setSelectedGold(false);
		boids.get(boids.indexOf(b)).setColor(color);
		boids.get(boids.indexOf(b)).setShape(p, plt);
	}
	public void ChangeColorAll(PApplet p, SubPlot plt){
		for(Body b : boids){
			ChangeColor((Boid) b,p,plt,color);
			//b.setColor(p.color(255,0,0));
		}
	}
	private void addSingleBoid(float x, float y, WorldConstants.PokeTypes type) {
		double[] pc = this.plt.getWorldCoord(x, y);
		Food food = new Food(TypeOfFood.ANIMALS);
		DNA dna = new DNA(3);
		Boid b = new Boid(new PVector((float)pc[0],(float)pc[1]), mass, radius, color, p, plt, food,dna,type , timeBetweenReproduce);
		b.addBehavior(new Separate(sacWeights[0]));
		b.addBehavior(new Align(sacWeights[1]));
		b.addBehavior(new Cohesion(sacWeights[2]));
		b.addBehavior(new Wander(sacWeights[3]));
		b.addBehavior(new Pursuit(sacWeights[4]));
		this.boids.add(b);
	}
	
	public void setEyes() {
		
		for(Boid b : this.boids)
			b.setEye(new Eye(b, this.boids, TypeOfFood.ANIMALS));
	}
	
	public boolean addBoid(float x, float y, WorldConstants.PokeTypes type) {
		if(getBoids().size() < 20){
			addSingleBoid(x , y, type);
			boids.get(boids.size() - 1).setEye(new Eye(boids.get(boids.size() - 1), boids, TypeOfFood.ANIMALS));

			return true;
		}
		return false;
	}

	public float[] getSacWeights(){
		return sacWeights;
	}
	
}
