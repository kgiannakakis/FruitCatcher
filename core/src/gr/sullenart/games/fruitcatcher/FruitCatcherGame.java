package gr.sullenart.games.fruitcatcher;

import gr.sullenart.games.fruitcatcher.HighScoreManager.HighScore;
import gr.sullenart.games.fruitcatcher.StateManager.StateBundle;
import gr.sullenart.games.fruitcatcher.images.ImageProvider;
import gr.sullenart.games.fruitcatcher.models.GameModel;
import gr.sullenart.games.fruitcatcher.models.GameModelFactory;
import gr.sullenart.games.fruitcatcher.models.GameState;
import gr.sullenart.games.fruitcatcher.screens.GameScreen;
import gr.sullenart.games.fruitcatcher.screens.GameScreenState;
import gr.sullenart.games.fruitcatcher.screens.HelpScreen;
import gr.sullenart.games.fruitcatcher.screens.LevelScreen;
import gr.sullenart.games.fruitcatcher.screens.MenuScreen;
import gr.sullenart.games.fruitcatcher.sound.SoundManager;
import gr.sullenart.games.fruitcatcher.text.TextResources;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class FruitCatcherGame extends Game {
	
	private MenuScreen menuScreen;
	
	private LevelScreen levelScreen;
	
	private GameScreen gameScreen;
	
	private ImageProvider imageProvider;
	
	private SoundManager soundManager;
	
    private GameModelFactory gameModelFactory;
    
    private GameState gameState;
    
    private StateManager stateManager;
    
    private HighScoreManager highScoreManager;
    
    private GameEventListener gameEventListener;
    
    private int highScore;
    
    private String locale;

	private TextResources textResources;
    
    public FruitCatcherGame(GameEventListener gameEventListener, String locale) {
    	this.gameEventListener = gameEventListener;
    	this.locale = locale;
    }
    
    public int getTotalScore() {
		return gameState.getTotalScore();
	}

	public void setTotalScore(int totalScore) {
		gameState.setTotalScore(totalScore);
	}

	public GameModel getModel(int level, int challenge) {
    	return gameModelFactory.getGameModel(level, challenge);
    }
    
	public ImageProvider getImageProvider() {
		return imageProvider;
	}

	public SoundManager getSoundManager() {
		return soundManager;
	}

    public GameState getGameState() {
        return gameState;
    }
    
	public GameModel getGameModel() {
		if (gameState.isKidsMode()) {
			return gameModelFactory.getKidsGameModel();
		}
		
		int level = gameState.getLevel();
		int challenge = gameState.getChallenge();
		
		return gameModelFactory.getGameModel(level, challenge);
	}
	
	public void startGame(int level) {
		gameState.setActive(true);
		gameState.setTotalScore(0);
		gameState.setLevel(level);
		gameState.setChallenge(0);

        gameEventListener.showAds(false);
	}
	
	public void startGameKidsMode() {
		gameState.setActive(true);
		gameState.setTotalScore(0);
		gameState.setKidsMode(true);
		gameState.setLevel(0);
		gameState.setChallenge(0);
	}	
	
	public void endGame(int score) {
		gameState.setActive(false);
		gameState.setKidsMode(false);
		persist(new GameScreenState());
		if (highScoreManager.isHighScore(score)) {
			if (gameEventListener != null) {
				highScore = score;
				gameEventListener.getHighScoreName();
			}
		}
        gameEventListener.showAds(true);
	}
	
	public void addHighScore(String name) {
		Gdx.app.log(this.getClass().getName(), "New high score: " + highScore + 
							" for " + name );
		highScoreManager.addHighScore(name, highScore);
	}
	
	public void restartLevel() {
		startGame(gameState.getLevel());
	}
	
	public void gotoNextGame() {
		if (gameState.isKidsMode()) {
			return;
		}
		
		int level = gameState.getLevel();
		int challenge = gameState.getChallenge();
		
		if (challenge < GameModelFactory.CHALLENGE_COUNT - 1) {
			challenge++;
			gameState.setChallenge(challenge);
		}
		else if (level < GameModelFactory.LEVEL_COUNT - 1) {
			level++;
			challenge = 0;
			gameState.setChallenge(challenge);
			gameState.setLevel(level);
		}
		else {
			challenge = 0;
			gameState.setChallenge(challenge);			
		}
	}

	@Override
	public void create() {
        textResources = new TextResources(locale);
		
		imageProvider = new ImageProvider(locale);
        imageProvider.load();
		
        soundManager = new SoundManager();
        soundManager.load();
        
        menuScreen = new MenuScreen(this);
        levelScreen = new LevelScreen(this);
        gameScreen = new GameScreen(this, null);
        
        gameModelFactory = new GameModelFactory();
        gameModelFactory.init();
        
        stateManager = new StateManager();
        
        highScoreManager = new HighScoreManager(textResources);

        for(HighScore highScore: highScoreManager.getHighScores()) {
        	Gdx.app.log(this.getClass().getName(), highScore.name + " " + highScore.score);
        }
        
        StateBundle stateBundle = stateManager.retrieveState();
        
        if (stateBundle == null) {
        	gameState = new GameState();
        	gotoMenuScreen();
        }
        else {
        	gameState = stateBundle.gameState;
        	if (gameState.isActive()){
        		gotoGameScreen(stateBundle.gameScreenState);
        	}
        	else {
        		gotoMenuScreen();	
        	}
        }
	}

	public void gotoMenuScreen() {
		setScreen(new MenuScreen(this));
	}
	
	public void gotoLevelScreen() {
		setScreen(new LevelScreen(this));
	}

	public void gotoGameScreen(GameScreenState gameScreenState) {
		setScreen(new GameScreen(this, gameScreenState));
	}	
	
	public void persist(GameScreenState gameScreenState) {
		stateManager.persist(gameScreenState, gameState);
	}
	
	public void showHelp() {
		setScreen(new HelpScreen(this));
	}
	
	public TextResources getTextResources() {
		return textResources;
	}
	
	public void showHighscores() {
		if (gameEventListener != null) {
			gameEventListener.showScores(highScoreManager.getHighScores());
		}
	}	
	
	@Override
	public void dispose() {
		imageProvider.dispose();
		soundManager.dispose();
		
		menuScreen.dispose();
		levelScreen.dispose();
		gameScreen.dispose();
	}
}
