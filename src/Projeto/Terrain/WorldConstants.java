package Projeto.Terrain;

public class WorldConstants {
	
	//World
	public final static double[] WINDOW = {-10, 10, -10, 10};
	
	//Terrain
	public final static int NROWS = 40;
	public final static int NCOLS = 50;
	
	public static enum PatchType {
			EMPTY, OBSTACLE, FERTILE, FOOD
	}
	
	public static enum TypeOfFood {
		GRASS, CORPSES, ANIMALS
	}
	
	public final static int NSTATES = PatchType.values().length;
	
	public static int[][] TERRAIN_COLORS = {
			{150,110,100},{160,30,70},{200,200,60},{40,200,20}
	};
	
	public final static float[] REGENERATION_TIME = {6F, 10F};

	public static enum PokeTypes{
		MAGIKARP, SQUIRLE, SHARPEDO, OMASTAR
	}
}
