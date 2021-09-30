package Projeto.Graphics;

import Projeto.Boids.Boid;
import Projeto.Boids.ControlFlock;
import Projeto.Boids.DatabaseBoidDeaths;
import Projeto.Boids.LoadImagens;
import Projeto.Setup.SubPlot;
import Projeto.Terrain.WorldConstants;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class GraphicManagerV2 {
	private SubPlot pltGraphs;
	private boolean teste;



	private Button btnAuto, btnManual, btnReproduz, btnMata, btnSquirtle, btnMagikarp, btnSharpedo, btnOmastar;

	private SubPlot pltBoid;
	private float[] viewportGraphs = { 0.8f, 0.15f, 0.15f, 0.2f };
	private Boid selectedBoid;
	private float posXcenter;
	private float posYcenter;

	private DatabaseBoidDeaths databaseDeath;

	private boolean firstTime = true;
	
	private Button btnSelected;

	private ControlFlock cf;
	private SubPlot pltGame;

	public GraphicManagerV2(PApplet p, float[] viewport, ControlFlock cf, DatabaseBoidDeaths databaseDeath,SubPlot pltGame) {
		pltGraphs = new SubPlot(WorldConstants.WINDOW, viewport, p.width, p.height);
		pltBoid = new SubPlot(WorldConstants.WINDOW, viewportGraphs, p.width, p.height);

		this.pltGame=pltGame;
		this.databaseDeath = databaseDeath;

		this.cf = cf;

		this.createButtons(p, pltGraphs);

		float[] bb = pltBoid.getBoudingBox();
		posXcenter = bb[0] + (bb[2] / 2);
		posYcenter = bb[1] + (bb[3] / 2);
	}

	public void display(PApplet p) {
		// Graphs
		float[] bb = pltGraphs.getBoudingBox();
		p.fill(65, 143, 158);
		p.rect(bb[0], bb[1], bb[2], bb[3]);

		displayLogo(p, bb);
		displayPokeselector(p, bb);
		autoOrManualSelector(p, bb);
		showEnergy(p, bb);
		flockAndPredatorsValues(p, bb);
		buttonReproduz(p, bb);
		displayKillCount(p, bb);

		bb = pltBoid.getBoudingBox();
		p.fill(255);
		p.rect(bb[0], bb[1], bb[2], bb[3]);
		displaySelectedBoid(p);
	}

	private void displayLogo(PApplet p, float[] bb) {
		p.image(LoadImagens.logo, bb[0] - 4, bb[1]);
	}

	private void displayPokeselector(PApplet p, float[] bb) {
		this.btnMagikarp.displayImage(p);
		this.btnOmastar.displayImage(p);
		this.btnSquirtle.displayImage(p);
		this.btnSharpedo.displayImage(p);
	}

	public void checkForClickInsideImage(float posX, float posY) {
		if(btnSelected != null && btnSelected.clickImage(posX, posY)) {
			btnSelected.setSelected(false);
			btnSelected = null;
		}
		else {
			buttonChange(this.btnMagikarp, posX, posY);
			buttonChange(this.btnSharpedo, posX, posY);
			buttonChange(this.btnSquirtle, posX, posY);
			buttonChange(this.btnOmastar, posX, posY);
		}	
	}
	
	private void buttonChange(Button button, float posX, float posY) {
		if(button.clickImage(posX, posY)) {
			button.setSelected(true);
			
			if(this.btnSelected != null)
				this.btnSelected.setSelected(false);
			
			this.btnSelected = button;
		}
	}

	private void displayCircle(PApplet p, float posX, float posY, int radius) {
		p.fill(0);
		p.circle(posX, posY, radius);
	}

	private void createButtons(PApplet p, SubPlot pltGraphs) {
		float[] bb = pltGraphs.getBoudingBox();

		this.btnAuto = new Button(0, bb[0] + ((bb[2] - bb[0]) * 0.10f), bb[1] + (bb[3] * 0.23f), 110, 50, "Auto",
				bb[0] + ((bb[2] - bb[0]) * 0.15f), bb[1] + (bb[3] * 0.28f), 31, false);

		this.btnManual = new Button(0, bb[0] + ((bb[2] - bb[0]) * 0.55f), bb[1] + (bb[3] * 0.23f), 110, 50, "Manual",
				bb[0] + ((bb[2] - bb[0]) * 0.55f), bb[1] + (bb[3] * 0.28f), 31, false);

		this.btnReproduz = new Button(p.color(0, 255, 0), bb[0] + ((bb[2] - bb[0]) * 0.10f), bb[1] + (bb[3] * 0.55f),
				110, 50, "Child", bb[0] + ((bb[2] - bb[0]) * 0.15f), bb[1] + (bb[3] * 0.595f), 31, false);

		this.btnMata = new Button(p.color(255, 0, 0), bb[0] + ((bb[2] - bb[0]) * 0.55f), bb[1] + (bb[3] * 0.55f), 110,
				50, "Kill", bb[0] + ((bb[2] - bb[0]) * 0.63f), bb[1] + (bb[3] * 0.595f), 31, false);


		btnSharpedo = new Button(WorldConstants.PokeTypes.SHARPEDO, LoadImagens.sharpedo, bb[0] + ((bb[2] - bb[0]) * 0.05f), bb[1] + (bb[3] * 0.13f));
		btnSquirtle = new Button(WorldConstants.PokeTypes.SQUIRLE, LoadImagens.squirtle, bb[0] + ((bb[2] - bb[0]) * 0.3f), bb[1] + (bb[3] * 0.13f));
		btnMagikarp = new Button(WorldConstants.PokeTypes.MAGIKARP, LoadImagens.magikarp, bb[0] + ((bb[2] - bb[0]) * 0.55f), bb[1] + (bb[3] * 0.13f));
		btnOmastar = new Button(WorldConstants.PokeTypes.OMASTAR, LoadImagens.omastar, bb[0] + ((bb[2] - bb[0]) * 0.79f), bb[1] + (bb[3] * 0.13f));
	}

	private void autoOrManualSelector(PApplet p, float[] bb) {
		this.btnAuto.display(p);
		this.btnManual.display(p);
	}
	
	public void mousePressed(PApplet p) {
		updateButtons(p);
		killBoid(p);
		addBoid(p);
	}

	private void addBoid(PApplet p){
		if(selectedBoid != null && this.btnReproduz.isPressed(p)) {
			float[] wc = pltGame.getPixelCoord(selectedBoid.getPos().x , selectedBoid.getPos().y);
			cf.addBoid(selectedBoid.getType(), wc[0], wc[1]);
		}
	}

	private void killBoid(PApplet p){
		if(btnMata.isPressed(p))
			System.out.println("Aqui");

		if(selectedBoid != null && btnMata.isPressed(p)){
			selectedBoid.setIsAlive(false);
			selectedBoid = null;
			updateButtons(p);
		}
	}

	private void updateButtons(PApplet p){
		if(this.selectedBoid != null) {
			this.btnMata.setIsSelected(true);
			this.btnReproduz.setIsSelected(true);

			if(firstTime)
				btnManual.setIsSelected(true);

			if(btnAuto.isPressed(p)) {
				btnManual.setIsSelected(true);
				btnAuto.setIsSelected(false);
				selectedBoid.setAuto(true);
			}
			else if(btnManual.isPressed(p)) {
				btnManual.setIsSelected(false);
				btnAuto.setIsSelected(true);
				selectedBoid.setAuto(false);
			}

			this.firstTime = false;
		}
		else {
			this.btnMata.setIsSelected(false);
			this.btnReproduz.setIsSelected(false);
			this.btnManual.setIsSelected(false);
			this.btnAuto.setIsSelected(false);

			this.firstTime = true;
		}
	}

	private void showEnergy(PApplet p, float[] bb) {
		p.textSize(25);

		p.fill(0);
		
		p.text("Energia", bb[0] + ((bb[2] - bb[0]) * 0.34f), bb[1] + (bb[3] * 0.35f));

		p.pushMatrix();
		
		if(this.selectedBoid != null) {
			if(!this.selectedBoid.isAlive()) {
				this.selectedBoid = null;
			}	
			else {
				p.fill(0,255,0);
				p.noStroke();
				p.rect(bb[0] + 5, bb[1] + (bb[3] * 0.36f), (updateEnergy(this.selectedBoid.getEnergy().getEnergy(), bb)), 10);
			}	
		}
			
		p.noFill();
		p.stroke(0);
		p.rect(bb[0] + 5, bb[1] + (bb[3] * 0.36f), (bb[2] - bb[0]) - 10, 10);
		
		p.popMatrix();
	}
	
	private float updateEnergy(float energy, float[] bb) {
		if(energy <= 5)
			return 0;
		else if(energy >= 100)
			return (bb[2] - bb[0]) - 10;
		
		return ((bb[2] - bb[0]) * (energy / 100)) - 10;
	}
	
	private void flockAndPredatorsValues(PApplet p, float[] bb) {
		p.textSize(25);

		p.fill(0);
		p.text("Prays size: " + cf.getSizePrays(), bb[0] + 25, bb[1] + (bb[3] * 0.45f));

		p.text("Predatores size: " + cf.getSizePredators(), bb[0] + 25, bb[1] + (bb[3] * 0.5f));
	}

	private void buttonReproduz(PApplet p, float[] bb) {
		p.pushStyle();
		p.textSize(31);
		p.strokeWeight(2);
		p.popStyle();
		this.btnMata.display(p);
		this.btnReproduz.display(p);
	}

	private void displaySelectedBoid(PApplet p) {
		if (this.selectedBoid != null) {
			PVector vel = selectedBoid.getVel();

			p.pushMatrix();

			p.fill(0);
			p.circle(posXcenter, posYcenter, 5);

			float increase = 12;
			p.line(posXcenter, posYcenter, posXcenter + vel.x * increase, posYcenter + (-vel.y * increase));

			p.popMatrix();
		}
	}

	private void displayKillCount(PApplet p, float[] bb) {	
		p.textSize(24);
		
		//Sharpedo
		p.image(LoadImagens.sharpedo, bb[0] + ((bb[2] - bb[0]) * 0.03f), bb[1] + (bb[3] * 0.87f));
		
		//Squirtle
		p.image(LoadImagens.squirtle, bb[0] + ((bb[2] - bb[0]) * 0.03f), bb[1] + (bb[3] * 0.93f));
		
		//Magikarp
		p.image(LoadImagens.magikarp, bb[0] + ((bb[2] - bb[0]) * 0.5f), bb[1] + (bb[3] * 0.87f));
	
		//Omastar
		p.image(LoadImagens.omastar, bb[0] + ((bb[2] - bb[0]) * 0.5f), bb[1] + (bb[3] * 0.93f));
		

		//Alive
		p.fill(0,255,0);
		//Sharpedo
		p.text(cf.getBoidsPredSharpedo().size(), bb[0] + ((bb[2] - bb[0]) * 0.2f), bb[1] + (bb[3] * 0.91f));
		//Squirtle
		p.text(cf.getBoidsPraysSquirtle().size(), bb[0] + ((bb[2] - bb[0]) * 0.2f), bb[1] + (bb[3] * 0.98f));
		//Magikarp
		p.text(cf.getBoidsPraysMagikarp().size(), bb[0] + ((bb[2] - bb[0]) * 0.67f), bb[1] + (bb[3] * 0.91f));
		//Omastar
		p.text(cf.getBoidsPredOmastar().size(), bb[0] + ((bb[2] - bb[0]) * 0.67f), bb[1] + (bb[3] * 0.98f));
		
		//Death
		p.fill(255,0,0);
		//SharpedoD
		p.text(databaseDeath.getPredatoresDeath(), bb[0] + ((bb[2] - bb[0]) * 0.35f), bb[1] + (bb[3] * 0.91f));
		//Squirtle
		p.text(databaseDeath.getPraysSquirtleDeath(), bb[0] + ((bb[2] - bb[0]) * 0.35f), bb[1] + (bb[3] * 0.98f));
		//Magikarp
		p.text(databaseDeath.getPraysDeath(), bb[0] + ((bb[2] - bb[0]) * 0.82f), bb[1] + (bb[3] * 0.91f));
		//Omastar
		p.text(databaseDeath.getPredatoresOmastarDeath(), bb[0] + ((bb[2] - bb[0]) * 0.82f), bb[1] + (bb[3] * 0.98f));
	}

	public boolean isInside(float x, float y){
		double[] wc = pltGraphs.getWorldCoord(x,y);
		return pltGraphs.isInside((float)wc[0],(float) wc[1]);
	}

	public void setSelectedBoid(PApplet p, Boid b) {
		this.selectedBoid = b;
		updateButtons(p);
	}
	
	public boolean isBoidSelected() {
		return (btnSelected != null);
	}
	
	public WorldConstants.PokeTypes getType() {
		return this.btnSelected.getType();
	}

}
