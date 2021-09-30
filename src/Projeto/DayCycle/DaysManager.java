package Projeto.DayCycle;

public class DaysManager {
	
	private double timeNow, timeBetweenDays;
	private int daysPassed;

	public void DaysManager(double timeBetweenDays) {
		daysPassed = 0;
		
		this.timeBetweenDays = timeBetweenDays * 1000;
		this.timeNow = System.currentTimeMillis() + timeBetweenDays;	
	}
	
	public boolean checkIfDayPassed() {
		if(timeNow < System.currentTimeMillis()) {
			anotherDayPassed();
			return true;
		}
		
		return false;		
	}
	
	private void anotherDayPassed() {
		daysPassed++;
	}
	
}
