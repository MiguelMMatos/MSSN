package Projeto.Boids;

import java.util.ArrayList;
import java.util.List;

import Projeto.Setup.SubPlot;
import processing.core.PApplet;

public class Corpses {
	private List<Corpse> corpses;
	private SubPlot plt;
	private PApplet p;

	private static int maxDeathBodys = 40;

	public Corpses(PApplet p, SubPlot plt) {
		corpses = new ArrayList<Corpse>();
		this.plt = plt;
		this.p = p;
	}
	
	public void display(PApplet p) {
		for(Corpse b : corpses) {
			b.display(p);
		}
	}
	
	public void addCorpse(Body b, int type) {
		int teste = (int) p.random(100);

		if(teste < 30){

			if(corpses.size() >= maxDeathBodys){
				corpses.remove(0);
				corpses.add(new Corpse(plt, b.getPos(), type));
			}
			else
				corpses.add(new Corpse(plt, b.getPos(), type));
		}
	}
}
