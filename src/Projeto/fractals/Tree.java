package Projeto.fractals;


import Projeto.Setup.SubPlot;
import processing.core.PApplet;
import processing.core.PVector;

public class Tree {
	private LSystem lsys;
	private Turtle turtle;
	private PVector position;
	
	private int numberOfSeasonsToGrow;
	private float scallingFactor;
	private float intervalBetweenSeasons;
	private float now;
	private float nextSeasonTime;
	
	private float growthRate;
	private float length;
	
	private PApplet p;
	
	public Tree(String axiom, Rule[] rules, PVector position, float referenceLength, float angle, int niter, float scallingFactor
			, float intervalBetweenSeasons, PApplet p) {
		
		lsys = new LSystem(axiom, rules);
		
		this.length = 0;
		this.growthRate = referenceLength / intervalBetweenSeasons;
		turtle = new Turtle(0, angle);
		
		this.position = position;
		this.numberOfSeasonsToGrow = niter;
		this.scallingFactor = scallingFactor;
		this.intervalBetweenSeasons = intervalBetweenSeasons;
		
		this.now = p.millis() / 1000f;
		this.nextSeasonTime = now + this.intervalBetweenSeasons;
		
		this.p = p;
	}
	
	public void grow(float dt) {
		now += dt;
		
		if(now < this.nextSeasonTime) {
			this.length += this.growthRate * dt;
			turtle.setLen(this.length);
		}
		else if(lsys.getGeneration() < this.numberOfSeasonsToGrow) {
			lsys.nextGeneration();
			length *= this.scallingFactor;
			this.growthRate *= this.scallingFactor;
			turtle.setLen(length);
			
			this.nextSeasonTime = now + this.intervalBetweenSeasons;
		}	
	}
	
	public void display(PApplet p, SubPlot plt) {
		p.pushMatrix();
		turtle.setPose(position, (float)Math.PI/2, plt, p);
		turtle.render(lsys, plt, p);
		p.popMatrix();
	}

}

















