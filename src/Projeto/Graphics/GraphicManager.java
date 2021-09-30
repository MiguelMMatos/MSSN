package Projeto.Graphics;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class GraphicManager {
	private List<Graphic> graphics;
	private PApplet p;
	
	private static int graphsSize = 200;
	private int posX = 40, posY = 230;
	private int spaceBetweenGraphs = 75;
	
	private double timeNow;
	private static double timeBetweenPlots = 1000;
	
	private float y = 230;
	
	public GraphicManager(PApplet p) {
		this.p = p;
		graphics = new ArrayList<Graphic>();
		addNewGraph("First");
		addNewGraph("Second");
		addNewGraph("Third");
		
		timeNow = p.millis() + timeBetweenPlots;
	}
	
	public void display(float dt) {
		if(checkIfCanDraw()) {
			p.background(255);
			for(Graphic g : graphics)
				g.plotGraph(y ,dt);

		}
		y -= 0.05f;	
	}
	
	private void addNewGraph(String title) {
		graphics.add(new Graphic(title, posX, posY, graphsSize, graphsSize, p));
		
		//Garantir que as novas coordenadas tem espaco para fazer o novo grafico
		if(p.width > posX  + this.spaceBetweenGraphs + this.graphsSize) {
			posX += graphsSize + spaceBetweenGraphs;
		}
		else {
			posX = 40;
			posY += graphsSize + spaceBetweenGraphs;
		}
	}
	
	private boolean checkIfCanDraw() {
		if(p.millis() > this.timeNow ) {
			timeNow = p.millis() + this.timeBetweenPlots;
			return true;
		}
		return false;
	}

}
