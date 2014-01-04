package gr.sullenart.games.fruitcatcher.models;

public class GameModelFactory {

	public static final int LEVEL_COUNT = 3;
	
	public static final int CHALLENGE_COUNT = 4;
	
    private Level [] levels;
    
    private static GameModel kidsGameModel;
    
    public Level[] getLevels() {
		return levels;
	}    
    
	public void setLevels(Level[] levels) {
		this.levels = levels;
	}

    public GameModel getGameModel(int level, int challenge) {
        return levels[level].getGames()[challenge];
    }
    
	public void init() {
        levels = new Level[LEVEL_COUNT];
        
        GameModel[] games1 = new GameModel[CHALLENGE_COUNT];
        games1[0] = new GameModel(20000, 1000, 3000, 6000, 60);
        games1[1] = new GameModel(20000, 1000, 3000, 6000, 65);
        games1[2] = new GameModel(20000, 1000, 3000, 6000, 70);
        games1[3] = new GameModel(20000, 1000, 3000, 6000, 80);

        GameModel[] games2 = new GameModel[CHALLENGE_COUNT];
        games2[0] = new GameModel(30000, 800, 2500, 5000, 65);
        games2[1] = new GameModel(30000, 800, 2500, 5000, 70);
        games2[2] = new GameModel(30000, 800, 2500, 5000, 75);
        games2[3] = new GameModel(30000, 800, 2500, 5000, 85);
        
        GameModel[] games3 = new GameModel[CHALLENGE_COUNT];
        games3[0] = new GameModel(30000, 600, 2000, 6000, 70);
        games3[1] = new GameModel(30000, 600, 2000, 6000, 75);
        games3[2] = new GameModel(30000, 600, 2000, 6000, 85);
        games3[3] = new GameModel(30000, 600, 2000, 6000, 90);        
        
        levels[0] = new Level(0, games1, 100);
        levels[1] = new Level(1, games2, 200);
        levels[2] = new Level(2, games3, 300);
    }

	public GameModel getKidsGameModel() {
		if (kidsGameModel == null) {
			kidsGameModel = new GameModel(30000, 1000, 6000, 5000, 10);
		}
		return kidsGameModel;
	}

}
