package Projeto;


import Projeto.Boids.LoadImagens;
import Projeto.Graphics.Button;
import Projeto.Setup.SubPlot;
import Projeto.Terrain.WorldConstants;
import processing.core.PApplet;
import processing.core.PImage;

public class Main_Menu {
	private PApplet p;
	private SubPlot plt;
	private ProjetoApp app;

	private float[] viewport = {0f, 0f, 1f, 1f};

	private Button btnPlay, btnHelp;
	private PImage background;

	public Main_Menu(ProjetoApp app, PApplet p) {
		this.p = p;
		this.plt = new SubPlot(WorldConstants.WINDOW, viewport, p.width, p.height);
		this.app = app;


		btnPlay = new Button(null, LoadImagens.btnPlay,p.width - (LoadImagens.btnPlay.width * 1.2f), p.height * 0.22f);
		btnHelp = new Button(null, LoadImagens.btnHelp,p.width - (LoadImagens.btnHelp.width * 1.2f), p.height * 0.36f);
		background = LoadImagens.background;
	}
	
	public void display() {
		p.image(background, 0, 0);

		btnPlay.displayImageWithHover(p, true);
		btnHelp.displayImageWithHover(p, true);
	}

	public void displayLoading() {
		p.image(background, 0, 0);
	}

	public void mousePressed(PApplet p){
		if(btnPlay.clickImage(p.mouseX, p.mouseY)){
			app.setMenuSelection(2);
		}
		else if(btnHelp.clickImage(p.mouseX, p.mouseY)){
			app.setMenuSelection(1);
		}
	}
	
}
