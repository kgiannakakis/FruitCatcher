package gr.sullenart.games.fruitcatcher.models;

import gr.sullenart.games.fruitcatcher.view.FallingObjectState;

public class GameModel {
    private int duration;    
    
    private int goal;
    
    private int fruitPeriod;
    
    private int badObjectsPeriod;
    
    private int bonusObjectsPeriod;

    private int difficulty;
    
    public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public int getFruitPeriod() {
		return fruitPeriod;
	}

	public void setFruitPeriod(int fruitPeriod) {
		this.fruitPeriod = fruitPeriod;
	}

	public int getBadObjectsPeriod() {
		return badObjectsPeriod;
	}

	public void setBadObjectsPeriod(int badObjectsPeriod) {
		this.badObjectsPeriod = badObjectsPeriod;
	}

	public int getBonusObjectsPeriod() {
		return bonusObjectsPeriod;
	}

	public void setBonusObjectsPeriod(int bonusObjectsPeriod) {
		this.bonusObjectsPeriod = bonusObjectsPeriod;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public GameModel(int duration, int fruitPeriod, int badObjectsPeriod,
                     int bonusObjectsPeriod, int difficulty) {
        this.duration = duration;
        this.fruitPeriod = fruitPeriod;
        this.badObjectsPeriod = badObjectsPeriod;
        this.bonusObjectsPeriod = bonusObjectsPeriod;
        this.difficulty = difficulty;
        
        int fruits = (duration/fruitPeriod)-1;
        float r = FruitType.SEASONAL_FRUITS_RATIO;
		float fruitPoints = fruits*( 
        		((1-r)*FallingObjectState.FRUIT_OUT_OF_SEASON_POINTS) +
        		(r*FallingObjectState.FRUIT_IN_SEASON_POINTS)
        		);
        
        
        goal = (((int) fruitPoints)*difficulty)/100;        
    }
}
