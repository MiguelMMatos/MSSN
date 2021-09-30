package Projeto.Particles;


import Projeto.Boids.Body;
import Projeto.Setup.SubPlot;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class ParticleSystem extends Body {

	private List<Particle> particles;
	private PSControl psc;
	
	public ParticleSystem(PVector pos, PVector vel, float mass, float radius, PSControl psc) {
		super(pos, vel, mass, radius, 0);
		
		this.particles = new ArrayList<Particle>();
		this.psc = psc;
	}
	
	@Override
	public void move(float dt) {
		super.move(dt);
		AddParticles(dt);
		
		for(int i  = particles.size() - 1; i >= 0; i--) {
			Particle p = particles.get(i);
			p.move(dt);
			if( p.isDead() )
				particles.remove(i);
		}
	}
	
	private void AddParticles(float dt) {
		float particlesPerFrame = psc.getFlow() * dt;
		int n = (int)particlesPerFrame;
		float f = particlesPerFrame - n;
		
		for(int i = 0; i < n; i++) {
			AddOneParticle();
		}
		
		if(Math.random() < f)
			AddOneParticle();
	}
	
	private void AddOneParticle() {
		Particle newParticle = new Particle(this.pos, psc.getRndVel(), psc.getRndRadius(), psc.getColor(), psc.getRndLifetime() );
		
		this.particles.add(newParticle);
	}
	
	public PSControl getPSControl() {
		return this.psc;
	}
	
	@Override
	public void display(PApplet p, SubPlot plt) {
		
		for(Particle particle : this.particles) {
			 particle.display(p, plt);
		}
	}

	
}
