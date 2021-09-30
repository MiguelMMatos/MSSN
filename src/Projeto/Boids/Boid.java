package Projeto.Boids;

import java.util.ArrayList;
import java.util.List;


import Projeto.Particles.PSControl;
import Projeto.Particles.ParticleSystem;
import Projeto.Setup.SubPlot;
import Projeto.Terrain.WorldConstants;
import processing.core.*;

public class Boid extends Body{
	private SubPlot plt;
	private PShape shape;
	protected DNA dna;
	protected Eye eye;
	private List<Behavior> behaviors;
	protected float phiWander;
	private double[] window;
	private boolean auto=true;

	private float sumWeights;

	private boolean isAlive = true;

	private Food food;

	private Energy energy;
	private static float defaultEnergy = 50;
	private boolean canReproduce = false;
	private static int energyRequiredToReproduce = 75;
	private static int energyCostToReproduce = 25;

	private double timeNow, timeBetweenReproduce;

	public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	private float[] velParms = {PApplet.radians(360), PApplet.radians(360), 1, 3};
	private float[] lifetimeParams = {0.1f, 0.5f};
	private float[] radiusParams = {0.07f, 0.1f};
	private float flow = 20;
	private ParticleSystem ps;
	PImage imageSelected;

	private WorldConstants.PokeTypes type;
	private boolean selected=false;
	private boolean selectedGold=false;

	public Boid(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt, Food food, DNA dna, WorldConstants.PokeTypes type, double timeBetweenReproduce) {
		super(pos, new PVector(), mass, radius, color);
		this.dna = dna;
		this.plt = plt;
		this.behaviors = new ArrayList<Behavior>();

		this.type = type;
		this.food = food;
		if(plt!=null){
			this.window = plt.getWindow();
			setShape(p, plt);
		}

		energy = new Energy(defaultEnergy);

		PSControl psc = new PSControl(this.velParms, this.lifetimeParams, this.radiusParams, this.flow, color);
		ps = new ParticleSystem(new PVector(pos.x, pos.y), new PVector(), 1f, .2f, psc);

		this.timeBetweenReproduce = timeBetweenReproduce;
		timeNow = System.currentTimeMillis() + (timeBetweenReproduce * 1000);
	}

	public void setEye(Eye eye) {
		this.eye = eye;
	}

	public Eye getEye() {
		return eye;
	}

	public PVector getWorldCoord() {
		PVector pos = getPos();
		double[] wc = plt.getWorldCoord(pos.x, pos.y);

		return new PVector((float)wc[0], (float)wc[1]);
	}

	public PVector getPixelCoord() {
		PVector pos = getPos();
		float[] wc = plt.getPixelCoord(pos.x, pos.y);

		return new PVector((float)wc[0], (float)wc[1]);
	}

	public void setShape(PApplet p, SubPlot plt) {
		float[] rr = plt.getVectorCoord(radius, radius);
		shape = p.createShape();
		shape.beginShape();

		shape.noStroke();
		shape.fill(color);
		shape.vertex(-rr[0], rr[0]/2);
		shape.vertex(rr[0], 0);
		shape.vertex(-rr[0], -rr[0]/2);
		shape.vertex(-rr[0]/2, 0);

		shape.endShape(PConstants.CLOSE);
	}

	private void updateSumWeights() {
		this.sumWeights = 0;

		for(Behavior beh : this.behaviors) {
			sumWeights += beh.getWeight();
		}
	}

	public void updateBehavior(int i, float weight){
		this.behaviors.get(i).setWeight(weight);
	}

	public void addBehavior(Behavior behavior) {
		this.behaviors.add(behavior);

		updateSumWeights();
	}

	public void removeBehavior(Behavior behavior) {
		if(this.behaviors.contains(behavior))
			this.behaviors.remove(behavior);

		updateSumWeights();
	}

	public void applyBehavior(int i, float dt) {
		if(eye != null)
			eye.look();

		Behavior behavior = this.behaviors.get(i);
		PVector vd = behavior.getDesiredVelocity(this);

		move(dt, vd);
	}

	public void look(){
		if(eye != null)
			eye.look();
	}

	public void applyBehaviors(float dt) {
		if(eye != null)
			eye.look();

		PVector vd = new PVector();

		for(Behavior behavior : this.behaviors) {
			PVector vdd = behavior.getDesiredVelocity(this);
			if(vdd != null) {
				vdd.mult(behavior.getWeight() / this.sumWeights);
				vd.add(vdd);
			}
		}

		move(dt, vd);
	}

	public void move(float dt, PVector vd) {
		vd.normalize().mult(dna.maxSpeed);
		PVector fs = PVector.sub(vd, vel);
		applyForce(fs.limit(dna.maxForce));
		super.move(dt);

		if(pos.x < window[0])
			pos.x += window[1] - window[0];

		if(pos.y < window[2])
			pos.y += window[3] - window[2];

		if(pos.x >= window[1])
			pos.x -= window[1] - window[0];

		if(pos.y >= window[3])
			pos.y -= window[3] - window[2];


		if(!energy.decreaseEnergy(dt))
			this.isAlive = false;

		if(energy.getEnergy() >= energyRequiredToReproduce && System.currentTimeMillis() > timeNow){
			timeNow = System.currentTimeMillis() + (timeBetweenReproduce * 1000);
			energy.decreaseEnergyByValue(energyCostToReproduce);
			canReproduce = true;
		}
	}

	@Override
	public void display(PApplet p, SubPlot plt) {
		p.pushMatrix();

		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		float[] vv = plt.getVectorCoord(vel.x, vel.y);
		PVector vaux = new PVector(vv[0], vv[1]);


		p.translate(pp[0], pp[1]);
		p.rotate(-vaux.heading());
		p.shape(shape);

		p.popMatrix();
	}

	public void display(PApplet p, SubPlot plt, float dt, WorldConstants.PokeTypes type) {
		ps.pos.x=this.pos.x;
		ps.pos.y=this.pos.y;
		ps.move(dt);
		ps.display(p, plt);



		if(WorldConstants.PokeTypes.MAGIKARP == type){
			if(selected)
				imageSelected=(this.vel.x<0)? LoadImagens.Glowmagikarp : LoadImagens.GlowINVmagikarp;
			else if(selectedGold)
				imageSelected=(this.vel.x<0)? LoadImagens.YELLOWGlowmagikarp : LoadImagens.YELLOWGlowINVmagikarp;
			else
				imageSelected=(this.vel.x<0)? LoadImagens.magikarp : LoadImagens.INVmagikarp;

		}
		else if(WorldConstants.PokeTypes.OMASTAR == type){
			if(selected)
				imageSelected=(this.vel.x<0)? LoadImagens.Glowomastar : LoadImagens.GlowINVomastar;
			else if(selectedGold)
				imageSelected=(this.vel.x<0)? LoadImagens.YELLOWGlowomastar : LoadImagens.YELLOWGlowINVomastar;
			else
				imageSelected=(this.vel.x<0)?imageSelected=LoadImagens.omastar:LoadImagens.INVomastar;
		}
		else if(WorldConstants.PokeTypes.SQUIRLE == type){
			if(selected)
				imageSelected=(this.vel.x<0)? LoadImagens.Glowsquirtle : LoadImagens.GlowINVsquirtle;
			else if(selectedGold)
				imageSelected=(this.vel.x<0)? LoadImagens.YELLOWGlowsquirtle : LoadImagens.YELLOWGlowINVsquirtle;
			else
				imageSelected=(this.vel.x<0)? LoadImagens.squirtle : LoadImagens.INVsquirtle;
		}
		else if(WorldConstants.PokeTypes.SHARPEDO == type){
			if(selected)
				imageSelected=this.getEye().getFarSightBad().size()>0?(this.vel.x<0)?LoadImagens.Glowsharpedo_Attack:LoadImagens.GlowINVsharpedo_Attack:(this.vel.x<0)?LoadImagens.Glowsharpedo:LoadImagens.GlowINVsharpedo;
			else if(selectedGold)
				imageSelected=this.getEye().getFarSightBad().size()>0?(this.vel.x<0)?LoadImagens.YELLOWGlowsharpedo_Attack:LoadImagens.YELLOWGlowINVsharpedo_Attack:(this.vel.x<0)?LoadImagens.YELLOWGlowsharpedo:LoadImagens.YELLOWGlowINVsharpedo;
			else
				imageSelected=this.getEye().getFarSightBad().size()>0?(this.vel.x<0)?LoadImagens.sharpedo_Attack:LoadImagens.INVsharpedo_Attack:(this.vel.x<0)?LoadImagens.sharpedo:LoadImagens.INVsharpedo;
		}


		p.pushMatrix();
		float[] bbb = plt.getPixelCoord(this.pos.x,this.pos.y);
		p.image(imageSelected, bbb[0]-imageSelected.height/2f, bbb[1]-imageSelected.width/2f);
		p.popMatrix();
		//display(p,plt)
	}

	public Energy getEnergy() {
		return energy;
	}

	public boolean isAlive() {
		return this.isAlive;
	}

	public void setIsAlive(boolean value) {
		this.isAlive = value;
	}

	public void ChangeColor(boolean selected) {
		this.selected=selected;
	}

	public WorldConstants.PokeTypes getType(){return type;}

	public void setCanReproduce(boolean value){
		canReproduce = value;
	}

	public boolean canReproduce(){
		return canReproduce;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public void setSelectedGold(boolean selected) {
		this.selectedGold = selected;
}



}


