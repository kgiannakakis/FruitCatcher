package gr.sullenart.games.fruitcatcher.models;

public class Level {
    private int number;
    
    private GameModel[] games;
    
    private int completedBonusPoints;
    
    public int getNumber() {
		return number;
	}



	public void setNumber(int number) {
		this.number = number;
	}



	public GameModel[] getGames() {
		return games;
	}



	public void setGames(GameModel[] games) {
		this.games = games;
	}



	public int getCompletedBonusPoints() {
		return completedBonusPoints;
	}



	public void setCompletedBonusPoints(int completedBonusPoints) {
		this.completedBonusPoints = completedBonusPoints;
	}



	public Level(int number, GameModel[] games, int completedBonusPoints) {
        this.number = number;
        this.games = games;
        this.completedBonusPoints = completedBonusPoints;
    }
}
