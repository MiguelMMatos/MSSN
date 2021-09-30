package Projeto.Graphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class Graphic {
	private PApplet p;
	
	private String title;
	
	private int posX, posY, sizeX, sizeY;
	private float xDifference = 0;
	private float currentX;
	
	private List<PVector> points;
	private int numberOfPoints = 5;
	
	public Graphic(String title, int posX, int posY, int sizeX, int sizeY, PApplet p) {
		this.p = p;
		points = new ArrayList<PVector>();
		
		this.title = title;
		
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.currentX = posX;
	}
	
	public void plotGraph(float valueY, float dt) {
		if(this.points.size() > 0)
			drawAxes();
		
		drawPoints(valueY, dt);
		
		drawTitle();
	}
	
	private void drawTitle() {
		p.text(this.title, posX + (sizeX / 2), (posY - sizeY) - 20);
	}
	
	private void drawAxes() {	
		
		//Draw Y markers
		float maxValue = 100;
		int difference = (int) (maxValue / 10);
		
		p.strokeWeight(3);
		for(int i = difference; i <= maxValue; i+= difference) {
			int currentValue = (int) p.map(i, 100, 0, posY - sizeY, posY);
			p.fill(0);
			p.text(i, posX - 35, currentValue);
			p.line(posX - 5, currentValue, posX + 5, currentValue);
					
		}
		p.strokeWeight(2);
		
		//Draw Y axes
		p.line(posX, posY, posX , posY - sizeX);
		
		//Draw X axes
		p.line(posX, posY, posX + sizeX, posY);
	}
	
	private void drawPoints(float valueY, float dt) {
		p.strokeWeight(3);
		
		addPoint(valueY);
		
		for(int i = 0; i < this.points.size(); i++) {
			p.circle(points.get(i).x, points.get(i).y, 2);
			
			if(i < points.size() - 1) 
				p.line(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
		}
	
		currentX += dt * 1000;	
	}
	
	private void resetPointsPosition() {
		xDifference = this.points.get(0).x - posX;
		for(int i = 0; i < this.points.size(); i++) {
			this.points.get(i).x -= xDifference;
		}
		
		currentX -= xDifference;
	}
	
	private void addPoint(float valueY) {
		if(this.points.size() == numberOfPoints) {
			this.points.remove(0);
			resetPointsPosition();
		}
				
		this.points.add(new PVector(currentX, valueY));	
	}
	
	
}
