package Projeto.Boids;

public class Energy {
	
	private static float maxEnergy = 100f;
	private float energy;
	
	private float energyDecreaseAmount = 6f;
	private static float energyDamageIncrease = 2f;
	private boolean isTakingDamage = false;
	
	public Energy(float energy) {
		this.energy = energy;
	}
	
	public boolean decreaseEnergy(float dt) {
		energy -= (isTakingDamage)? (energyDecreaseAmount * dt) * energyDamageIncrease :(energyDecreaseAmount * dt);
		
		if(energy <= 0)
			return false;
		return true;
	}

	public boolean decreaseEnergyByValue(int value){
		energy -= value;

		if(energy <= 0)
			return false;
		return true;
	}
	
	public void increaseEnergy(float gainEnergy) {
		if(energy + gainEnergy > maxEnergy)
			energy = maxEnergy;
		else
			energy += gainEnergy;
	}
	
	public float getEnergy() {
		return this.energy;
	}

	public void setTakingDamage(boolean value){
		isTakingDamage = value;
	}
	

}
