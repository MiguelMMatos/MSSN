package Projeto.Boids;

import Projeto.Terrain.WorldConstants.TypeOfFood;

public class Food {
	
	TypeOfFood foodType;

	public Food(TypeOfFood foodType) {
		this.foodType = foodType;
	}
	
	public TypeOfFood getFoodType() {
		return this.foodType;
	}
}
