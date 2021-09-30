package Projeto.Boids;


public class DNA {

	public float maxSpeed;
	public float maxForce;
	public float visionDistance;
	public float visionSafeDistance;
	public float visionAngle;
	public float deltaTPursuit;
	public float radiusArrive;
	public float deltaTWander;
	public float radiusWander;
	public float deltaPhiWander;
	
	public DNA(int number) {
		if(number==1){
			Magikarp();
		}else if(number==2){
			Squirtle();
		}else if(number==3){
			Sharpedo();
		}else if(number==4){
			Omastar();
		}

	}
	public void Magikarp(){
		//Physics
		this.maxSpeed = random(3,5);
		this.maxForce = random(4,7);

		//Vision
		this.visionDistance = random(5,5);
		this.visionSafeDistance =  0.25f * this.visionDistance;
		this.visionAngle = (float)Math.PI * 0.8f;

		//Pursuit
		this.deltaTPursuit = random(0.1f, 0.4f);

		//Arrive
		this.radiusArrive = random(3, 5);

		//Wander
		this.deltaTWander = random(0.5f, 0.7f);
		this.radiusWander = random(3,3);
		this.deltaPhiWander = (float) (Math.PI / 8);
	}

	public void Squirtle(){
		//Physics
		this.maxSpeed = random(3,5);
		this.maxForce = random(4,7);

		//Vision
		this.visionDistance = random(5,5);
		this.visionSafeDistance =  0.25f * this.visionDistance;
		this.visionAngle = (float)Math.PI * 0.8f;

		//Pursuit
		this.deltaTPursuit = random(0.1f, 0.4f);

		//Arrive
		this.radiusArrive = random(3, 5);

		//Wander
		this.deltaTWander = random(0.5f, 0.7f);
		this.radiusWander = random(3,3);
		this.deltaPhiWander = (float) (Math.PI / 8);
	}

	public void Sharpedo(){
		//Physics
		this.maxSpeed = random(3,5);
		this.maxForce = random(4,7);

		//Vision
		this.visionDistance = random(2,2);
		this.visionSafeDistance =  0.25f * this.visionDistance;
		this.visionAngle = (float)Math.PI * 0.8f;

		//Pursuit
		this.deltaTPursuit = random(0.3f, 0.4f);

		//Arrive
		this.radiusArrive = random(3, 5);

		//Wander
		this.deltaTWander = random(0.4f, 0.5f);
		this.radiusWander = random(3,3);
		this.deltaPhiWander = (float) (Math.PI / 8);
	}

	public void Omastar(){
		//Physics
		this.maxSpeed = random(3,5);
		this.maxForce = random(4,7);

		//Vision
		this.visionDistance = random(5,5);
		this.visionSafeDistance =  0.25f * this.visionDistance;
		this.visionAngle = (float)Math.PI * 0.8f;

		//Pursuit
		this.deltaTPursuit = random(0.1f, 0.4f);

		//Arrive
		this.radiusArrive = random(3, 5);

		//Wander
		this.deltaTWander = random(0.5f, 0.7f);
		this.radiusWander = random(3,3);
		this.deltaPhiWander = (float) (Math.PI / 8);
	}


	public static float random(float min, float max) {
		return (float)(min + (max - min) * Math.random());
	}
	
	
}
