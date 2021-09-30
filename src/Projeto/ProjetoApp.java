package Projeto;

import Projeto.Boids.*;
import Projeto.Graphics.GraphicManager;
import Projeto.Graphics.GraphicManagerComportamentos;
import Projeto.Graphics.GraphicManagerV2;
import Projeto.Graphics.Button;
import Projeto.Music.Music;
import Projeto.Setup.IProcessingApp;
import Projeto.Setup.SubPlot;
import Projeto.Terrain.TerrainManager;
import Projeto.Terrain.WorldConstants;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetoApp implements IProcessingApp {
	private GraphicManager gM;
	private TerrainManager terrainManager;
	private ControlFlock flockControl;
	static LoadImagens imagens;
	private SubPlot pltGame;
	private DatabaseBoidDeaths databaseDeath;
	private Music music;

	private Button btnBack;

	private Main_Menu mainMenu;
	private MenuHelp menuHelp;

	private float[] viewportGame = {0f,0f,0.75f,1f};
	private float[] viewportGraphs = {0.75f,0f,1f ,1f};
	private GraphicManagerV2 graphics;
	private GraphicManagerComportamentos graphicsComportamentos;


	List<Boid> boids;
	Boid boidSelectedNow = null;
	int offSet=20;

	boolean w=false,a=false,s=false,d=false;
	boolean campoDeVisaoLigado=false;
	int x=0,y=0;
	PVector vd = new PVector(0,0);
	int color;

	private float flow = 20;

	float dragLastTime=0;

	private boolean mainPanel = true;

	private int menuSelection = 0;

	@Override
	public void setup(PApplet p) {
		imagens=new LoadImagens(p);

		databaseDeath = new DatabaseBoidDeaths();
		pltGame = new SubPlot(WorldConstants.WINDOW, viewportGame, p.width, p.height);
		terrainManager = new TerrainManager(p, pltGame);
		flockControl = new ControlFlock(p, pltGame, terrainManager.getTerrain(),imagens, databaseDeath);
		graphics = new GraphicManagerV2(p,viewportGraphs, flockControl, databaseDeath,pltGame);
		gM = new GraphicManager(p);
		graphicsComportamentos = new GraphicManagerComportamentos(p,viewportGraphs, flockControl);
		mainMenu = new Main_Menu(this, p);
		menuHelp = new MenuHelp(this, p);
		music = new Music();
		color = p.color(255,0,0);

		createBackButton();
	}

	private void createBackButton(){
		float[] bb = pltGame.getBoudingBox();
		btnBack = new Button(null, LoadImagens.btnBack ,bb[0] + (bb[2] - LoadImagens.btnBack.width), bb[3] - LoadImagens.btnBack.height);
	}

	@Override
	public void draw(PApplet p, float dt) {
		switch (menuSelection){
			case 0:
				mainMenu.display();
				break;
			case 1:
				menuHelp.display();
				break;
			case 2:
				runGame(p, dt);
				break;
		}


	}

	private void runGame(PApplet p, float dt){
		terrainManager.display(p,pltGame);

		//Control Boid
		boidSelectedVision(p);

		if(boidSelectedNow!=null&& boids.contains(boidSelectedNow)){
			boidSelectedNow.setSelectedGold(true);
		}
		flockControl.display(dt);

		if(boidSelectedNow!=null && !boidSelectedNow.isAuto())
			controlBoidSelected(dt);
		updateBoids();

		if(mainPanel)
			graphics.display(p);
		else
			graphicsComportamentos.display(p);

		btnBack.displayImageWithHover(p, true);
	}

	@Override
	public void mousePressed(PApplet p) {
		music.PlayMusicClick();
		if(menuSelection == 0)
			mainMenu.mousePressed(p);
		else if(menuSelection == 1)
			menuHelp.mousePressed(p);
		else if(menuSelection == 2)
			mousePressedBehavior(p);

	}

	private void mousePressedBehavior(PApplet p){

		if(pltGame.isInside(p.mouseX, p.mouseY))
			selectBoidStats(p);

		if(mainPanel){
			graphics.mousePressed(p);
		}
		else
			graphicsComportamentos.mousePressed(p);

		graphics.checkForClickInsideImage(p.mouseX, p.mouseY);

		if(btnBack.clickImage(p.mouseX, p.mouseY)){
			mainPanel = !mainPanel;
		}
		else if(mainPanel && pltGame.isInside(p.mouseX, p.mouseY) && this.graphics.isBoidSelected()) {
			this.flockControl.addBoid(graphics.getType(), p.mouseX, p.mouseY);
		}
	}

	@Override
	public void mouseReleased(PApplet p) {

	}

	@Override
	public void keyPressed(PApplet p) {
		if(menuSelection == 2)
			keyBehavior(p);
	}

	private void keyBehavior(PApplet p){
		if(p.key =='c'){
			if(boidSelectedNow!=null&& boids.indexOf(boidSelectedNow)!=-1)
				boidSelectedNow.setAuto(!boidSelectedNow.isAuto());
		}
		if(p.key =='v'){
			campoDeVisaoLigado=!campoDeVisaoLigado;
		}
		if(p.key =='w'){
			w=true;
		}
		if(p.key =='a'){
			a=true;
		}
		if(p.key =='d'){
			d=true;
		}
		if(p.key =='s'){
			s=true;
		}

		if(p.key =='p'){
			mainPanel = !mainPanel;
		}
	}


	@Override
	public void keyReleased(PApplet p) {
		if(menuSelection == 2){
			if(p.key =='w'){
				w=false;
			}
			if(p.key =='a'){
				a=false;
			}
			if(p.key =='d'){
				d=false;
			}
			if(p.key =='s'){
				s=false;
			}
		}
	}

	@Override
	public void mouseDragged(PApplet p) {
		if(menuSelection == 2){
			float now = p.millis();
			if((now-dragLastTime)>0.001){
				if(mainPanel && pltGame.isInside(p.mouseX, p.mouseY) && this.graphics.isBoidSelected()) {
					this.flockControl.addBoid(graphics.getType(), p.mouseX, p.mouseY);
					music.PlayMusicClick();
				}
				dragLastTime=now;
			}
		}
	}
	
	public void updateBoids(){
		List<Boid> GoodboidsMagikarp=flockControl.getBoidsPraysMagikarp();
		List<Boid> GoodboidsSquirtle=flockControl.getBoidsPraysSquirtle();
		List<Boid> badBoidsOmastar=flockControl.getBoidsPredOmastar();
		List<Boid> badBoidSharpedo=flockControl.getBoidsPredSharpedo();

		boids=new ArrayList<Boid>();
		boids.addAll(GoodboidsMagikarp);
		boids.addAll(GoodboidsSquirtle);
		boids.addAll(badBoidsOmastar);
		boids.addAll(badBoidSharpedo);
	}
	private void boidSelectedVision(PApplet p){
		if(boidSelectedNow!=null&&boids.indexOf(boidSelectedNow)!=-1){
			boids.get(boids.indexOf(boidSelectedNow)).look();
			if(campoDeVisaoLigado)
				boids.get(boids.indexOf(boidSelectedNow)).getEye().display(p,pltGame);

			List<Boid> vision = boids.get(boids.indexOf(boidSelectedNow)).getEye().getFarSight();
			List<Boid> visionBad = boids.get(boids.indexOf(boidSelectedNow)).getEye().getFarSightBad();
			for(Body b : vision){
				if(boids.indexOf(b)!=-1){
					((Boid)b).ChangeColor(true);
				}
			}
			for(Body b : visionBad){
				if(boids.indexOf(b)!=-1){
					((Boid)b).ChangeColor(true);
				}
			}
		}
	}

	private void selectBoidStats(PApplet p){
		if(boidSelectedNow != null && boids.indexOf(boidSelectedNow) != -1 )
			boids.get(boids.indexOf(boidSelectedNow)).setAuto(true);

		float[] aa;
		boolean selected = false;

		for(Body b : boids){
			aa = pltGame.getPixelCoord(b.getPos().x,b.getPos().y);
			if(aa[0] >= p.mouseX-offSet && aa[0] <= p.mouseX+offSet && aa[1] >= p.mouseY-offSet && aa[1] <= p.mouseY + offSet){
				boidSelectedNow = (Boid)b;
				selected=true;
				graphics.setSelectedBoid(p, boidSelectedNow);
				break;
			}
		}

		if(!selected) {
			boidSelectedNow=null;
			graphics.setSelectedBoid(p, null);
		}
		if(!selected && !graphics.isInside(p.mouseX, p.mouseY))
			selected = true;
	}

	private void controlBoidSelected(float dt) {
		if(w){
			y+=1000;
		}
		if(a){
			x-=1000;
		}
		if(d){
			x+=1000;
		}
		if(s){
			y-=1000;
		}
		vd=new PVector(x,y);
		boidSelectedNow.move(dt*2, vd);
		y=0;
		x=0;
	}

	public void setMenuSelection(int i){
		menuSelection = i;
	}
}
