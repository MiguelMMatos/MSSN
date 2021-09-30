package Projeto.Graphics;

import Projeto.Boids.ControlFlock;
import Projeto.Boids.LoadImagens;
import Projeto.Setup.SubPlot;
import Projeto.Terrain.WorldConstants;
import processing.core.PApplet;
import processing.core.PImage;

public class GraphicManagerComportamentos {
	private SubPlot pltGraphs;
	private ControlFlock cf;



	private SubPlot pltSharpedo, pltSquirtle, pltMagikarp, pltOmastar;

	private RadioButton magSeek, magWander, magAlign, magCohesion, magSeparate, magFlee;
	private RadioButton shaSeek, shaWander, shaAlign, shaCohesion, shaSeparate, shaPursuit;
	private RadioButton squirleSeek, squirleWander, squirleAlign, squirleCohesion, squirleSeparate, squirleFlee;
	private RadioButton omastarSeek, omastarWander, omastarAlign, omastarCohesion, omastarSeparate, omastarPursuit;

	public GraphicManagerComportamentos(PApplet p, float[] viewport, ControlFlock cf) {
		pltGraphs = new SubPlot(WorldConstants.WINDOW, viewport, p.width, p.height);
		this.cf = cf;


		createPlots(p);
		createButtonsMagikarp(pltMagikarp);
		createButtonsSharpedo(pltSharpedo);
		createButtonsSquirtle(pltSquirtle);
		createButtonsOmastar(pltOmastar);
	}

	public void display(PApplet p) {
		// Graphs
		float[] bb = pltGraphs.getBoudingBox();
		p.fill(65, 143, 158);
		p.rect(bb[0], bb[1], bb[2], bb[3]);

		displayLogo(p, bb);

		pltDisplays(p, pltMagikarp, WorldConstants.PokeTypes.MAGIKARP);
		pltDisplays(p, pltOmastar, WorldConstants.PokeTypes.OMASTAR);
		pltDisplays(p, pltSharpedo, WorldConstants.PokeTypes.SHARPEDO);
		pltDisplays(p, pltSquirtle, WorldConstants.PokeTypes.SQUIRLE);
	}

	private void createPlots(PApplet p){
		float[] viewportGraphs = { 0.76f, 0.68f, 0.23f, 0.18f };
		pltSharpedo = new SubPlot(WorldConstants.WINDOW, viewportGraphs,  p.width, p.height);

		float[] viewportGraphs1 = { 0.76f, 0.46f, 0.23f, 0.18f };
		pltSquirtle = new SubPlot(WorldConstants.WINDOW, viewportGraphs1,  p.width, p.height);

		float[] viewportGraphs2 = { 0.76f, 0.24f, 0.23f, 0.18f };
		pltMagikarp = new SubPlot(WorldConstants.WINDOW, viewportGraphs2,  p.width, p.height);

		float[] viewportGraphs3 = { 0.76f, 0.02f, 0.23f, 0.18f };
		pltOmastar = new SubPlot(WorldConstants.WINDOW, viewportGraphs3,  p.width, p.height);
	}

	private void displayLogo(PApplet p, float[] bb) {
		p.image(LoadImagens.logo, bb[0] - 4, bb[1]);
	}

	private void pltDisplays(PApplet p, SubPlot plt, WorldConstants.PokeTypes type){
		float[] bb = plt.getBoudingBox();

		p.pushStyle();

		p.strokeWeight(2);
		p.fill(255);
		p.rect(bb[0], bb[1], bb[2], bb[3]);

		if(WorldConstants.PokeTypes.MAGIKARP == type){
			p.image(LoadImagens.magikarp, bb[0] , bb[1]);
			magSeparate.display(p);
			magAlign.display(p);
			magCohesion.display(p);
			magWander.display(p);
			magSeek.display(p);
			magFlee.display(p);
		}
		else if(WorldConstants.PokeTypes.OMASTAR == type){
			p.image(LoadImagens.omastar, bb[0] , bb[1]);
			omastarSeparate.display(p);
			omastarAlign.display(p);
			omastarCohesion.display(p);
			omastarWander.display(p);
			omastarPursuit.display(p);
		}
		else if(WorldConstants.PokeTypes.SQUIRLE == type){
			p.image(LoadImagens.squirtle, bb[0] , bb[1]);
			squirleSeparate.display(p);
			squirleAlign.display(p);
			squirleCohesion.display(p);
			squirleWander.display(p);
			squirleSeek.display(p);
			squirleFlee.display(p);
		}
		else if(WorldConstants.PokeTypes.SHARPEDO == type){
			p.image(LoadImagens.sharpedo, bb[0] , bb[1]);
			shaSeparate.display(p);
			shaAlign.display(p);
			shaCohesion.display(p);
			shaWander.display(p);
			shaPursuit.display(p);
		}

		p.popStyle();
	}


	private RadioButton createButton(SubPlot plt, float[] sac, int x, int y, int textX, int textY, String text, int i){
		float[] bb = plt.getBoudingBox();
		float[] sacW = sac;

		return new RadioButton(bb[0] + x, bb[1] + y, 100, 20, text, bb[0] + textX, bb[1] + textY, 20, (sacW[i] > 0));
	}

	private void createButtonsMagikarp(SubPlot plt){
		magSeparate = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.MAGIKARP), 40, 50, 45, 67, "Separate", 0);
		magAlign = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.MAGIKARP), 40, 80, 45, 97, "Align", 1);
		magCohesion = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.MAGIKARP), 40, 110, 45, 127, "Cohesion", 2);
		magWander = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.MAGIKARP), 200, 50, 205, 67, "Wander", 3);
		magSeek = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.MAGIKARP), 200, 80, 205, 97, "Seek", 4);
		magFlee = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.MAGIKARP), 200, 110, 205, 127, "Flee", 5);
	}

	private void createButtonsSharpedo(SubPlot plt){
		shaSeparate = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.SHARPEDO), 40, 50, 45, 67, "Separate", 0);
		shaAlign = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.SHARPEDO), 40, 80, 45, 97, "Align", 1);
		shaCohesion = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.SHARPEDO), 40, 110, 45, 127, "Cohesion", 2);
		shaWander = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.SHARPEDO), 200, 50, 205, 67, "Wander", 3);
		shaPursuit = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.SHARPEDO), 200, 80, 205, 97, "Pursuit", 4);
	}

	private void createButtonsSquirtle(SubPlot plt){
		squirleSeparate = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.SQUIRLE), 40, 50, 45, 67, "Separate", 0);
		squirleAlign = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.SQUIRLE), 40, 80, 45, 97, "Align", 1);
		squirleCohesion = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.SQUIRLE), 40, 110, 45, 127, "Cohesion", 2);
		squirleWander = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.SQUIRLE), 200, 50, 205, 67, "Wander", 3);
		squirleSeek = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.SQUIRLE), 200, 80, 205, 97, "Seek", 4);
		squirleFlee = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.SQUIRLE), 200, 110, 205, 127, "Flee", 5);
	}

	private void createButtonsOmastar(SubPlot plt){
		omastarSeparate = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.OMASTAR), 40, 50, 45, 67, "Separate", 0);
		omastarAlign = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.OMASTAR), 40, 80, 45, 97, "Align", 1);
		omastarCohesion = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.OMASTAR), 40, 110, 45, 127, "Cohesion", 2);
		omastarWander = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.OMASTAR), 200, 50, 205, 67, "Wander", 3);
		omastarPursuit = createButton(plt, cf.getSacWeights(WorldConstants.PokeTypes.OMASTAR), 200, 80, 205, 97, "Pursuit", 4);
	}

	public void mousePressed(PApplet p){
		mousePressedMagi(p);
		mousePressedSha(p);
		mousePressedSquirtle(p);
		mousePressedOmastar(p);
	}

	private void mousePressedSha(PApplet p){
		cf.updateSacWeights(WorldConstants.PokeTypes.SHARPEDO,0,shaSeparate.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.SHARPEDO,1,shaAlign.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.SHARPEDO,2,shaCohesion.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.SHARPEDO,3,shaWander.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.SHARPEDO,4,shaPursuit.isPressed(p));
	}

	private void mousePressedMagi(PApplet p){
		cf.updateSacWeights(WorldConstants.PokeTypes.MAGIKARP,0,magSeparate.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.MAGIKARP,1,magAlign.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.MAGIKARP,2,magCohesion.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.MAGIKARP,3,magWander.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.MAGIKARP,4,magSeek.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.MAGIKARP,5,magFlee.isPressed(p));
	}

	private void mousePressedSquirtle(PApplet p){
		cf.updateSacWeights(WorldConstants.PokeTypes.SQUIRLE,0,squirleSeparate.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.SQUIRLE,1,squirleAlign.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.SQUIRLE,2,squirleCohesion.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.SQUIRLE,3,squirleWander.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.SQUIRLE,4,squirleSeek.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.SQUIRLE,5,squirleFlee.isPressed(p));
	}

	private void mousePressedOmastar(PApplet p){
		cf.updateSacWeights(WorldConstants.PokeTypes.OMASTAR,0,omastarSeparate.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.OMASTAR,1,omastarAlign.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.OMASTAR,2,omastarCohesion.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.OMASTAR,3,omastarWander.isPressed(p));
		cf.updateSacWeights(WorldConstants.PokeTypes.OMASTAR,4,omastarPursuit.isPressed(p));
	}


}
