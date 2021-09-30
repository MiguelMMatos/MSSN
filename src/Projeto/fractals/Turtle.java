package Projeto.fractals;


import Projeto.Setup.SubPlot;
import processing.core.PApplet;
import processing.core.PVector;

public class Turtle {
	private float len;
	private float angle;
	
	public Turtle(float len, float angle) {
		this.len = len;
		this.angle = angle;
	}
	
	public void setPose(PVector position, float orientation, SubPlot plt, PApplet p) {
		float[] pp = plt.getPixelCoord(position.x, position.y);
		p.translate(pp[0], pp[1]);
		p.rotate(-orientation);
	}
	
	public void scalling(float s) {
		len *= s;
	}
	
	
	public float getLen() {
		return len;
	}

	public void setLen(float len) {
		this.len = len;
	}

	public void render(LSystem lsys, SubPlot plt, PApplet p )
	{
		p.stroke(0);
		float[] lenPix = plt.getVectorCoord(len, len);
		
		for(int i = 0; i < lsys.getSequence().length(); i++) {
			char c = lsys.getSequence().charAt(i);
			
			if(c == 'F' || c == 'G') {
				p.line(0, 0, lenPix[0], 0);
				p.translate(lenPix[0], 0);
			}
			else if(c == 'f') 
				p.translate(lenPix[0], 0);
			else if(c == '+') 
				p.rotate(angle);
			else if(c == '-') 
				p.rotate(-angle);
			else if(c == '[')
				p.pushMatrix();
			else if(c == ']')
				p.popMatrix();
		}
	}
	public void display(PApplet p, SubPlot plt, PVector startingPos, LSystem lsys, float degrees){
		p.pushMatrix();
		setPose(startingPos,PApplet.radians(degrees),plt, p);
		render(lsys,plt,p);
		p.popMatrix();
	}

}
