package Projeto.Boids;

import Projeto.Setup.SubPlot;
import processing.core.PApplet;
import processing.core.PVector;

public class Body extends Mover {

    protected int color;
    protected float radius;
    private boolean gotEaten = false;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Body (PVector pos, PVector vel, float mass, float radius, int color) {
        super(pos, vel, mass);

        this.color = color;
        this.radius = radius;
    }
    
    public Body(Body body) {
    	super(body.pos, body.vel, body.mass);
    	
    	this.color = body.color;
    	this.radius = body.radius;
    }

    public void display(PApplet p, SubPlot plt) {
        //push() - guardar as caracteristas para depois as chamar
        p.pushStyle();
        p.fill(color);
        p.noStroke();
        float[] pp = plt.getPixelCoord(pos.x,pos.y);
        p.ellipse(pp[0], pp[1], 100*radius, 100*radius);
        p.popStyle();
    }
    
    public void setGotEaten() {
    	this.gotEaten = true;
    }
    
    public boolean checkIfGotEaten() {
    	return this.gotEaten;
    }


}
