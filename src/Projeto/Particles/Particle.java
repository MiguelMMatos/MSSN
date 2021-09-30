package Projeto.Particles;


import Projeto.Boids.Body;
import Projeto.Setup.SubPlot;
import processing.core.PApplet;
import processing.core.PVector;

public class Particle extends Body {
	
	private float lifespan;
	private float timer;
	
	
	protected Particle(PVector pos, PVector vel, float radius, int color, float lifespan) {
		super(pos, vel, 0f, radius, color);
		this.lifespan = lifespan;
		this.timer = 0f;
	}
	
	@Override
	public void move(float dt) {
		super.move(dt);
		timer += dt;
	}
	
	public boolean isDead() {
		return this.timer > this.lifespan;
	}
	
	@Override
	public void display(PApplet p, SubPlot plt) {
		p.pushStyle();
		
		float alpha = PApplet.map(timer, 0, lifespan, 255, 0);
		p.fill(this.color, alpha);
		
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		float[] r = plt.getVectorCoord(this.radius, this.radius);
		
		p.noStroke();
		p.circle(pp[0], pp[1], 2*r[0]);
		
		p.popStyle();
	}
	
}
