package Projeto.Boids;

import java.util.ArrayList;
import java.util.List;

import Projeto.Setup.SubPlot;
import Projeto.Terrain.Patch;
import Projeto.Terrain.Terrain;
import Projeto.Terrain.WorldConstants;
import processing.core.PApplet;

public class ControlFlock {
	private PApplet p;
	private SubPlot plt;
	
	private List<Boid> boidsThatDied;
	private List<Boid> boidsToReproduce;
	private Corpses corpses;

	private FlockPrays flockPraysMagikarp;
	private float[] sacWeights_PraysMagikarp = {0.1f, 1.2f, 2f, 0.5f, 1f, 10f};
	private static float[] defaultPraysSacValuesMagikarp = new float[]{0.1f, 1.2f, 2f, 0.5f, 1f, 10f};

	private FlockPrays flockPraysSquirtle;
	private float[] sacWeights_PraysSquirtle = {0.1f, 1.2f, 2f, 0.5f, 1f, 10f};
	private static float[] defaultPraysSacValuesSquirtle = new float[]{0.1f, 1.2f, 2f, 0.5f, 1f, 10f};
	
	private FlockPredators flockPredatorsSharpedo;
	private float[] sacWeights_PredatorsSharpedo = {0.1f, 1.2f, 2f, 1f, 4f};
	private static float[] defaultPredatorsSacValuesSharpedo = new float[]{0.1f, 1.2f, 2f, 1f, 4f};

	private FlockPredators flockPredatorsOmastar;
	private float[] sacWeights_PredatorsOmastar = {0.1f, 1.2f, 2f, 1f, 4f};
	private static float[] defaultPredatorsSacValuesOmastar = new float[]{0.1f, 1.2f, 2f, 1f, 4f};
	
	private Terrain terrain;
	private DatabaseBoidDeaths databaseDeath;
	
	private List<Patch> eatable;
	private List<Patch> obstacle;
	private LoadImagens imagens;

	public ControlFlock(PApplet p, SubPlot plt, Terrain terrain,LoadImagens imagens, DatabaseBoidDeaths databaseDeath) {
		this.p = p;
		this.plt = plt;
		this.terrain = terrain;
		this.imagens=imagens;

		this.corpses = new Corpses(p, plt);

		this.databaseDeath = databaseDeath;
		
		boidsThatDied = new ArrayList<Boid>();
		boidsToReproduce = new ArrayList<Boid>();
        
		flockPraysMagikarp = createFlockPray(WorldConstants.PokeTypes.MAGIKARP, sacWeights_PraysMagikarp,25);
		flockPraysSquirtle = createFlockPray(WorldConstants.PokeTypes.SQUIRLE, sacWeights_PraysSquirtle,25);

		flockPredatorsSharpedo = createFlockPredators(WorldConstants.PokeTypes.SHARPEDO,4);
		flockPredatorsOmastar = createFlockPredators(WorldConstants.PokeTypes.OMASTAR,4);

		updateEye();

		terrain.updateObstaclePatches();
		obstacle = terrain.getObstaclePatch();
		System.out.println(obstacle.size());
	}
	
	public void display(float dt) {
		eatable.clear();
		eatable.addAll(terrain.getPatchEatable());
		flockPraysMagikarp.setPatches(eatable);
		flockPraysSquirtle.setPatches(eatable);

		displayCorpses();
		displayPredators(dt);
		displayPrays(dt);
		checkDeaths();

		takeDamage();
	}

	private void takeDamage(){
		seeIfTakeDamagePrays(flockPraysMagikarp);
		seeIfTakeDamagePrays(flockPraysSquirtle);
		seeIfTakeDamagePredators(flockPredatorsSharpedo);
		seeIfTakeDamagePredators(flockPredatorsOmastar);
	}

	private void seeIfTakeDamagePrays(FlockPrays flock){
		for(Boid b : flock.getBoids()){
			//Verifica no terreno se o boid esta dentro dum patch de dano
			//E comeca a dar dano
			b.getEnergy().setTakingDamage(terrain.checkIfIsTakingDamage(b.getPixelCoord()));
		}
	}

	private void seeIfTakeDamagePredators(FlockPredators flock){
		for(Boid b : flock.getBoids()){
			//Verifica no terreno se o boid esta dentro dum patch de dano
			//E comeca a dar dano
			b.getEnergy().setTakingDamage(terrain.checkIfIsTakingDamage(b.getPixelCoord()));
		}
	}

	private void displayCorpses(){
		corpses.display(p);
	}

	private void deadsPrey(List<Boid> flock){
		for(Boid b : flock){
			if(!b.isAlive()){
				boidsThatDied.add(b);
				corpses.addCorpse(b, 0);
				updateDatabaseDeaths(b);
			}
			else if(b.canReproduce()){
				b.setCanReproduce(false);
				boidsToReproduce.add(b);
			}
		}
	}

	private void deadsPred(List<Boid> flock){
		for(Boid b : flock){
			if(!b.isAlive()){
				boidsThatDied.add(b);
				updateDatabaseDeaths(b);
			}
			else if(b.canReproduce()){
				boidsToReproduce.add(b);
				b.setCanReproduce(false);
			}
		}
	}

	private void updateDatabaseDeaths(Boid b){
		if(WorldConstants.PokeTypes.MAGIKARP == b.getType()){
			databaseDeath.increasePraysDeath();
		}
		else if(WorldConstants.PokeTypes.OMASTAR == b.getType()){
			databaseDeath.increasePredatoresOmastarDeath();
		}
		else if(WorldConstants.PokeTypes.SQUIRLE == b.getType()){
			databaseDeath.increasePraysSquirtleDeath();
		}
		else if(WorldConstants.PokeTypes.SHARPEDO == b.getType()){
			databaseDeath.increasePredatorDeath();
		}
	}

	private void checkDeaths() {
		deadsPrey(flockPraysMagikarp.getBoids());
		deadsPrey(flockPraysSquirtle.getBoids());
		deadsPred(flockPredatorsSharpedo.getBoids());
		deadsPred(flockPredatorsOmastar.getBoids());

		if(boidsToReproduce.size() > 0){
			for(Boid b : boidsToReproduce){
				float[] wc = plt.getPixelCoord(b.getPos().x , b.getPos().y);
				addBoid(b.getType(), wc[0], wc[1]);
			}
			boidsToReproduce.clear();
		}
		
		if(boidsThatDied.size() > 0) {
			for(Boid b : boidsThatDied) {
				flockPraysMagikarp.getBoids().remove(b);
				flockPraysSquirtle.getBoids().remove(b);
				flockPredatorsSharpedo.getBoids().remove(b);
				flockPredatorsOmastar.getBoids().remove(b);
			}
				
			boidsThatDied.clear();
		}
	}
	
	private FlockPrays createFlockPray(WorldConstants.PokeTypes type, float[] sac,int nboids) {
		FlockPrays flock = new FlockPrays(nboids, 1f, 0.2f, p.color(0, 100, 200), sac, terrain, p,  plt, type);
		
		eatable = new ArrayList<Patch>();
		eatable.addAll(terrain.getPatchEatable());
				
		flock.setPatches(eatable);

		return flock;
	}
	
	private FlockPredators createFlockPredators(WorldConstants.PokeTypes type,int nboids) {
		FlockPredators flock = new FlockPredators(nboids, 1f, 0.2f, 0, sacWeights_PredatorsSharpedo, p, plt,type);

		return flock;
	}
	
	private void displayPredators(float dt) {
		displayPredator(flockPredatorsSharpedo, dt);
		displayPredator(flockPredatorsOmastar, dt);
	}

	private void displayPredator(FlockPredators flock, float dt){
		flock.checkFoodBoids(terrain, dt);

		if(flock.stop == false)
			flock.applyBehavior(dt);

		flock.display(dt);
		flock.ChangeColorAll(p,plt);//resetColor
	}

	private void displayPrays(float dt) {
		displayPray(flockPraysMagikarp, dt);
		displayPray(flockPraysSquirtle, dt);
	}

	private void displayPray(FlockPrays flock ,float dt){
		flock.applyBehavior(dt);
		flock.display(dt);
		flock.checkFoodBoids(terrain);
		flock.ChangeColorAll(p,plt);//resetColor
	}
	
	public List<Boid> getBoidsPraysMagikarp() {

		return this.flockPraysMagikarp.getBoids();
	}

	public List<Boid> getBoidsPraysSquirtle() {

		return this.flockPraysSquirtle.getBoids();
	}

	public List<Boid> getBoidsPredSharpedo() {
		return this.flockPredatorsSharpedo.getBoids();
	}
	public List<Boid> getBoidsPredOmastar() {
		return this.flockPredatorsOmastar.getBoids();
	}
	
	public void addBoid(WorldConstants.PokeTypes type, float x, float y) {
		if(WorldConstants.PokeTypes.MAGIKARP == type){
			this.flockPraysMagikarp.addBoid(x, y, type);
		}
		else if(WorldConstants.PokeTypes.OMASTAR == type){
			this.flockPredatorsOmastar.addBoid(x, y, type);
		}
		else if(WorldConstants.PokeTypes.SQUIRLE == type){
			this.flockPraysSquirtle.addBoid(x, y, type);
		}
		else if(WorldConstants.PokeTypes.SHARPEDO == type){
			this.flockPredatorsSharpedo.addBoid(x, y, type);
		}
	}

	public float[] getSacWeights(WorldConstants.PokeTypes type){
		if(WorldConstants.PokeTypes.MAGIKARP == type)
			return flockPraysMagikarp.getSacWeights();

		else if(WorldConstants.PokeTypes.OMASTAR == type)
			return flockPredatorsOmastar.getSacWeights();

		else if(WorldConstants.PokeTypes.SQUIRLE == type)
			return flockPraysSquirtle.getSacWeights();

		else if(WorldConstants.PokeTypes.SHARPEDO == type)
			return  flockPredatorsSharpedo.getSacWeights();

		return null;
	}


	public void updateSacWeights(WorldConstants.PokeTypes type, int i, int value){
		if(value != -1){
			if(WorldConstants.PokeTypes.MAGIKARP == type){
				sacWeights_PraysMagikarp[i] = (value == 0)? 0 :  defaultPraysSacValuesMagikarp[i];
				updateBehaviorBoids(flockPraysMagikarp.getBoids(), sacWeights_PraysMagikarp, i);
			}
			else if(WorldConstants.PokeTypes.OMASTAR == type){
				sacWeights_PredatorsOmastar[i] = (value == 0)? 0 :  defaultPredatorsSacValuesOmastar[i];
				updateBehaviorBoids(flockPredatorsOmastar.getBoids(), sacWeights_PredatorsOmastar, i);
			}
			else if(WorldConstants.PokeTypes.SQUIRLE == type){
				sacWeights_PraysSquirtle[i] = (value == 0)? 0 :  defaultPraysSacValuesSquirtle[i];
				updateBehaviorBoids(flockPraysSquirtle.getBoids(), sacWeights_PraysSquirtle, i);
			}
			else if(WorldConstants.PokeTypes.SHARPEDO == type){
				sacWeights_PredatorsSharpedo[i] = (value == 0)? 0 :  defaultPredatorsSacValuesSharpedo[i];
				updateBehaviorBoids(flockPredatorsSharpedo.getBoids(), sacWeights_PredatorsSharpedo, i);
			}
		}
	}

	private void updateBehaviorBoids(List<Boid> boids,float[] sac, int i){
		for(Boid b : boids){
			b.updateBehavior(i, sac[i]);
		}
	}

	public int getSizePredators(){
		return flockPredatorsOmastar.getBoids().size() + flockPredatorsSharpedo.getBoids().size();
	}

	public int getSizePrays(){
		return flockPraysMagikarp.getBoids().size() + flockPraysSquirtle.getBoids().size();
	}
	private void updatePray(){
		List<Boid> Goodboids=new ArrayList<Boid>();
		Goodboids.addAll(flockPraysMagikarp.getBoids());
		Goodboids.addAll(flockPraysSquirtle.getBoids());

		for(Boid b: Goodboids){
			b.getEye().setBadTrackingBodies(flockPredatorsSharpedo.getBoids(),flockPredatorsOmastar.getBoids());
			b.getEye().look();
		}
	}


	private void updateEye(){
		List<Boid> Goodboids=new ArrayList<Boid>();
		Goodboids.addAll(flockPraysMagikarp.getBoids());
		Goodboids.addAll(flockPraysSquirtle.getBoids());

		List<Boid> badBoids=new ArrayList<Boid>();
		badBoids.addAll(flockPredatorsSharpedo.getBoids());
		badBoids.addAll(flockPredatorsOmastar.getBoids());


		for(Boid b: Goodboids){
			b.getEye().setBadTrackingBodies(flockPredatorsSharpedo.getBoids(),flockPredatorsOmastar.getBoids());
			b.getEye().look();
		}


		flockPredatorsSharpedo.setTargetsM(flockPraysMagikarp.getBoids());
		flockPredatorsSharpedo.setTargetsS(flockPraysSquirtle.getBoids());
		flockPredatorsOmastar.setTargetsM(flockPraysMagikarp.getBoids());
		flockPredatorsOmastar.setTargetsS(flockPraysSquirtle.getBoids());

		flockPraysMagikarp.setTargetsO(flockPredatorsOmastar.getBoids());
		flockPraysMagikarp.setTargetsS(flockPredatorsSharpedo.getBoids());
		flockPraysSquirtle.setTargetsO(flockPredatorsOmastar.getBoids());
		flockPraysSquirtle.setTargetsS(flockPredatorsSharpedo.getBoids());


	}
}
