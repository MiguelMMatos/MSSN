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

public class FlockPrays extends Flock{
	private List<Boid> boids;
	private int color;
	private float mass, radius;
	private float[] sacWeights;
	private List<Boid> targetsS;
	private List<Boid> targetsO;

	private static double timeBetweenReproduce = 5f;

	public FlockPrays(int nboids, float mass, float radius, int color, float[] sacWeights, Terrain terrain, PApplet p, SubPlot plt, WorldConstants.PokeTypes type) {
		super(p, plt, type);
		this.color=color;
		this.mass = mass;
		this.radius = radius;
		this.sacWeights = sacWeights;
		
		boids = new ArrayList<Boid>();
		float[] w = plt.getBoudingBox();
		
		
		for(int i = 0; i < nboids; i++) {
			float x = p.random((float)w[0], (float)w[2]);
			float y = p.random((float)w[1], (float)w[3]);
			
			this.addSingleBoid(x, y, type);
		}
		setEyes();
	}
	
	public void checkFoodBoids(Terrain terrain) {
		List<Patch> eat = terrain.getPatchEatable();
		setPatches(eat);
		
		//Boid Comeu
		for(Boid b : boids) {
			b.getEye().resetFoodTarget();
			b.getEye().targetForEachBoidType();
			b.getEye().setBadTrackingBodies(targetsS,targetsO);
			if( terrain.checkIfIsInside(b.getPixelCoord()) != null ) {
				Patch patch = terrain.checkIfIsInside(b.getPixelCoord());
				// comida
				if(patch.isEatable()) {
					b.getEnergy().increaseEnergy(patch.energy);
					b.getEye().resetFoodTarget();
					patch.setFertile();
				}
			}
		}
	}
	
	public void applyBehavior(float dt) {
		for(Boid b: this.boids) {
			if(b.isAuto()) {
				b.applyBehaviors(dt);
			}	
			else {
				b.look();
			}	
		}	
	}
	
	public void display(float dt) {
		for(Boid b : this.boids)
			b.display(p, plt, dt, type);
	}
	
	public void setPatches(List<Patch> eat) {
		for(Boid b : this.boids)
			b.eye.setAllFoodBodies(eat);
	}
	public void setTargetsO(List<Boid> targets) {
		this.targetsO = targets;
	}
	public void setTargetsS(List<Boid> targets) {
		this.targetsS = targets;
	}
	public List<Boid> getBoids() {
		return boids;
	}

	public void setBoids(List<Boid> boids) {
		this.boids = boids;
	}
	
	public Boid getBoid(int n) {
		return boids.get(n);
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
		double[] pc = plt.getWorldCoord(x, y);
		Food food = new Food(TypeOfFood.GRASS);
		DNA dna = new DNA(1);
		Boid b = new Boid(new PVector((float)pc[0],(float)pc[1]), mass, radius, color, p, plt, food,dna, type, timeBetweenReproduce);
		
		b.addBehavior(new Separate(sacWeights[0]));
		b.addBehavior(new Align(sacWeights[1]));
		b.addBehavior(new Cohesion(sacWeights[2]));
		b.addBehavior(new Wander(sacWeights[3]));
		b.addBehavior(new SeekFood(sacWeights[4]));
		b.addBehavior(new Flee(sacWeights[5]));
		this.boids.add(b);

	}
	
	public void setEyes() {

		for(Boid b : this.boids)
			b.setEye(new Eye(b, boids, TypeOfFood.GRASS));
	}
	
	public boolean addBoid(float x, float y, WorldConstants.PokeTypes type) {
		if(getBoids().size() < 100){
			addSingleBoid(x , y, type);

			boids.get(boids.size() - 1).setEye(new Eye(boids.get(boids.size() - 1), boids, TypeOfFood.GRASS));
			return true;
		}
		return false;

	}

	public float[] getSacWeights(){
		return sacWeights;
	}

}
