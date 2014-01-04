package gr.sullenart.games.fruitcatcher.screens;

import gr.sullenart.games.fruitcatcher.FruitCatcherGame;
import gr.sullenart.games.fruitcatcher.images.ImageProvider;
import gr.sullenart.games.fruitcatcher.models.FallingObjectType;
import gr.sullenart.games.fruitcatcher.models.GameModel;
import gr.sullenart.games.fruitcatcher.models.GameModelFactory;
import gr.sullenart.games.fruitcatcher.sound.SoundManager;
import gr.sullenart.games.fruitcatcher.state.State;
import gr.sullenart.games.fruitcatcher.view.AnalogueClock;
import gr.sullenart.games.fruitcatcher.view.Basket;
import gr.sullenart.games.fruitcatcher.view.Button;
import gr.sullenart.games.fruitcatcher.view.EndGameMessage;
import gr.sullenart.games.fruitcatcher.view.FallingObject;
import gr.sullenart.games.fruitcatcher.view.FallingObjectFactory;
import gr.sullenart.games.fruitcatcher.view.FallingObjectState;
import gr.sullenart.games.fruitcatcher.view.NumberBoard;
import gr.sullenart.games.fruitcatcher.view.StartGameMessage;
import gr.sullenart.games.fruitcatcher.view.TimesTwoAnimation;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


public class GameScreen implements Screen, InputProcessor {
    
    private SpriteBatch batch;
    
    private OrthographicCamera camera;
    
    private FruitCatcherGame game;
    
    private ImageProvider imageProvider;
    
    private SoundManager soundManager;
    
    private TextureRegion basketRegion;
    
    private Texture backgroundImage;    
    
    private FallingObjectFactory fallingObjectFactory;
    
    private Array<FallingObject> fallingObjects;
    
    private Array<TimesTwoAnimation> timesTwoAnimations;
    
    private int gameDuration;
    
    private Basket basket;
    
    private AnalogueClock clock;

	private NumberBoard numberBoard;
	
	private StartGameMessage startGameMessage;
	
	private EndGameMessage endGameMessage;
    
	private Button backButton;
	
	private boolean tictacSoundPlaying;

	private boolean gameFinishedSoundPlaying;
	
	private int fruitPeriod;

	private int badObjectsPeriod;

	private int bonusObjectsPeriod;
	
	private TextureRegion pauseTexture;
	
	private int pauseX;
	
	private int pauseY;

	private GameModel gameModel;
	
    private int season;
    private int level;
    private int challenge;
    private int goal;
	
    private GameScreenState st;
    
	public GameScreen(FruitCatcherGame game, GameScreenState state) {
		if (state != null) {
			st = state;
		}
		else {
			st = new GameScreenState();
    		resetState();
		}
		this.game = game;
	}

	private void resetState() {
		st.score = 0;
		st.startGameTime = 0;
		st.isStarted= false;
		st.isPaused = false;
		st.fallingObjectStates = new Array<FallingObjectState>();
		st.basketX = -1;
	}
	
    @Override
	public void show() {    	
        imageProvider = game.getImageProvider();
        soundManager = game.getSoundManager();
    	
        camera = new OrthographicCamera();
        camera.setToOrtho(false, imageProvider.getScreenWidth(), 
        						 imageProvider.getScreenHeight());
        batch = new SpriteBatch();
        
        basketRegion = imageProvider.getBasket();
        basket = new Basket(imageProvider.getScreenWidth(), st.basketX);

        fallingObjectFactory = new FallingObjectFactory(imageProvider);
        
        timesTwoAnimations = new Array<TimesTwoAnimation>();
        
        clock = new AnalogueClock(imageProvider);
        
        numberBoard = new NumberBoard(imageProvider);
        
        backButton = new Button(imageProvider.getBack());
        backButton.setPos(10, 10);
        
        pauseTexture = imageProvider.getPause();
        pauseX = (imageProvider.getScreenWidth() - pauseTexture.getRegionWidth())/2;
        pauseY = (imageProvider.getScreenHeight() - pauseTexture.getRegionHeight())/2;
        
        initGame();        
        
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
	}
    
	private void initGame() {		
		level = challenge = goal = 0;
		
		tictacSoundPlaying = false;
		gameFinishedSoundPlaying = false;
		
		endGameMessage = null;
		
        gameDuration = (game.getGameModel().getDuration())/1000;

        if (game.getGameState().isKidsMode()) {
        	season = MathUtils.random(0, 3);
        	goal = 0;
        	level = 0;
        }
        else {
	        level = game.getGameState().getLevel();
	        challenge = game.getGameState().getChallenge();
	        season = challenge % 4;
	        
	        gameModel = game.getModel(level, challenge);
	        goal = gameModel.getGoal();
	        
	        startGameMessage = new StartGameMessage(imageProvider, level,
	        								challenge, goal);
        }    
        
        switch(season) {
        case 0:
        	backgroundImage = imageProvider.getBackgroundSpring();
        	break;
        case 1:
        	backgroundImage = imageProvider.getBackgroundSummer();
        	break;
        case 2:
        	backgroundImage = imageProvider.getBackgroundAutumn();
        	break;
        case 3:
        	backgroundImage = imageProvider.getBackgroundWinter();
        	break;
        }        
        
        fallingObjectFactory.setSeason(season);
        fallingObjects = new Array<FallingObject>();   
        for(FallingObjectState foState: st.fallingObjectStates) {
        	FallingObject obj = fallingObjectFactory.getObjectFromState(foState);
        	fallingObjects.add(obj);
        }        
        
        fruitPeriod = game.getGameModel().getFruitPeriod();
        badObjectsPeriod = game.getGameModel().getBadObjectsPeriod();
        bonusObjectsPeriod = game.getGameModel().getBonusObjectsPeriod();
        
        if (game.getGameState().isKidsMode()) {
        	st.isStarted = true;
        	startGame();
        }        
	}

    private void spawnFruit() {
    	fallingObjects.add(fallingObjectFactory.getFruit());
    	st.lastFruitTime = TimeUtils.millis();
    }  
    
	private void spawnBadApple() {
		fallingObjects.add(fallingObjectFactory.getBadObject());
		st.lastBadObjectTime = TimeUtils.millis();
	}    
    
    private void spawnBonusItem() {
    	fallingObjects.add(fallingObjectFactory.getStar());
    	st.lastBonusItemTime = TimeUtils.millis();    	
    }
    
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        if (st.isStarted && !st.isPaused) {
	        int secondsElapsed = (int) ((TimeUtils.millis() - st.startGameTime)/1000);
	        st.secondsRemaining = gameDuration - secondsElapsed;
	        if (st.secondsRemaining < 0 || st.isFinished) {
	        	st.secondsRemaining = 0;
	        }        
        }
        else if (!st.isPaused){
        	st.secondsRemaining = gameDuration;
        }
        
        batch.begin();
        batch.draw(backgroundImage, 0, 0);

        if (!st.isStarted) {
        	startGameMessage.draw(batch);
        }
        else if (st.secondsRemaining > 0) {
	        batch.draw(basketRegion, basket.getPosition().x, 
	        						 basket.getPosition().y);
	        for(FallingObject fallingObject: fallingObjects) {
	        	fallingObject.draw(batch);
	        }
	        
	        for(TimesTwoAnimation ta: timesTwoAnimations) {
	        	ta.draw(batch);
	        }
	        
	        clock.setSeconds(st.secondsRemaining);
	        clock.draw(batch, 5, imageProvider.getScreenHeight() - 65);	
	        
	        numberBoard.draw(batch, imageProvider.getScreenWidth()-5, 
	        		imageProvider.getScreenHeight()-5, st.score);
        }

        if (st.secondsRemaining == 0){
        	st.isFinished = true;
        	fallingObjects.clear();
        	if (endGameMessage == null) {
        		int unlockedLevel = 0;
        		int totalScore = game.getTotalScore() + st.score;
        		if (st.score >= goal && challenge == GameModelFactory.CHALLENGE_COUNT - 1) {
        			if (!State.isLevelUnlocked(level+1)) {
        				State.unLockLevel(level + 1);
        				unlockedLevel = level + 1;
        			}
        		}
        		else if (st.score < goal) {
        			game.endGame(totalScore);
        		}
                endGameMessage = new EndGameMessage(imageProvider, level,
        				challenge, goal, st.score, totalScore, unlockedLevel);            		
        	}
        	endGameMessage.draw(batch);
        	backButton.draw(batch);
        }
        
        if (st.isPaused) {
        	batch.draw(pauseTexture, pauseX, pauseY);
        }
        
        batch.end();
        
        if (!st.isPaused) {
	        if (st.secondsRemaining == 5 && !tictacSoundPlaying) {
	        	tictacSoundPlaying = true;
	        	soundManager.playTicTacSound();
	        }
	        else if (st.secondsRemaining == 0 && !gameFinishedSoundPlaying ) {
	        	gameFinishedSoundPlaying = true;
	        	if (st.score >= goal) {
	        		soundManager.playDingDongSound();
	        	}
	        	else {
	        		soundManager.playBellSound();
	        	}
	        }
        }
        
        processInput();        
        
        if (st.isPaused) {
        	return;
        }        
        
        if(st.isStarted && st.secondsRemaining > 0) {
        	if (TimeUtils.millis() - st.lastFruitTime > fruitPeriod) {
        		spawnFruit();
        	}
        	if (TimeUtils.millis() - st.lastBadObjectTime > badObjectsPeriod) {
        		spawnBadApple(); 
        	}
        	if (TimeUtils.millis() - st.lastBonusItemTime > bonusObjectsPeriod) {
        		spawnBonusItem(); 
        	}        	
        }
        
        // Update screen objects
        Iterator<FallingObject> iter = fallingObjects.iterator();
        while(iter.hasNext()) {
        	FallingObject fo = iter.next();
            fo.moveDown(Gdx.graphics.getDeltaTime());
            if(fo.isBottomOfScreen()) { 
                iter.remove();
            }
            if(fo.isOverlapping(basket.getPosition())) {
            	if (fo.getPoints() > 0) {
            		soundManager.playCatchSound();
            	}
            	else {
            		soundManager.playMissSound();
            	}
                iter.remove();
                st.score+=fo.getPoints();
                if (fo.getState().getType() == FallingObjectType.SeasonalFruit) {
                	float x = basket.getPosition().x;
                	float y = basket.getPosition().y + 48;
                	timesTwoAnimations.add(
                			new TimesTwoAnimation(imageProvider, x, y));
                }
            }
        }
        
        Iterator<TimesTwoAnimation> itanim = timesTwoAnimations.iterator();
        while(itanim.hasNext()) {
        	TimesTwoAnimation ta = itanim.next();
        	if (ta.isFinished(delta)) {
        		itanim.remove();
        	}
        }
	}
    
    private void processInput() {
        if (st.isStarted && !st.isPaused && st.secondsRemaining > 0) {
	        float delta = Gdx.graphics.getDeltaTime();
	        if(Gdx.input.isKeyPressed(Keys.LEFT)) {
	            basket.moveX(-1, delta);
	        }
	        if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
	            basket.moveX(1, delta);
	        }
	        basket.moveX(Gdx.input.getAccelerometerY(), delta);
        }
    }
	
	@Override
	public void resize(int width, int height) {
		//this.width = width;
		//this.height = height;
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
		if (!st.isPaused && st.isStarted && st.secondsRemaining >= 0) {
			st.isPaused = st.secondsRemaining > 0;
			long pausedTime = TimeUtils.millis();
			st.startGameTime -= pausedTime;
			st.lastBadObjectTime -= pausedTime;
			st.lastBonusItemTime -= pausedTime;
			st.lastFruitTime -= pausedTime;
			
			st.fallingObjectStates = new Array<FallingObjectState>();
			for(FallingObject fo: fallingObjects) {
				st.fallingObjectStates.add(fo.getState());
			}
			st.basketX = (int) basket.getPosition().x;
		}
		game.persist(st);
	}

	@Override
	public void resume() {
		if (st.isPaused) {
			st.isPaused = false;
			long now = TimeUtils.millis();
			st.startGameTime += now;
			st.lastBadObjectTime += now;
			st.lastBonusItemTime += now;
			st.lastFruitTime += now;			
		}
	}

	@Override
	public void dispose() {
        if (batch != null) {
        	batch.dispose();
        }
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACK || keycode == Keys.BACKSPACE){
			game.endGame(0);
			game.gotoMenuScreen();
	    }
        if(keycode == Keys.P) {
        	pause();
        }		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (st.isStarted && !st.isPaused) {
			Vector3 touchPos = new Vector3();
	        touchPos.set(screenX, screenY, 0);
	        camera.unproject(touchPos);            
	        
	        if (st.isStarted && st.secondsRemaining > 0) {
	            basket.setPositionX(touchPos.x); 
	        }
        }
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (st.isPaused) {
			resume();
			return true;
		}
		
		Vector3 touchPos = new Vector3();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);         
		
		if (!st.isStarted) {
			st.isStarted = startGameMessage.isStartPressed(touchPos);
        	if (st.isStarted) {
        		startGame();
        	}
        }
        else if (st.secondsRemaining == 0) {                
            if (backButton.isPressed(touchPos)) {
            	game.endGame(0);
            	game.gotoMenuScreen();
            }
            else if (endGameMessage.isPressed(touchPos)) {            	
            	if (st.score >= goal) {
            		int totalScore = game.getTotalScore() + st.score;
            		game.setTotalScore(totalScore);            		
            		game.gotoNextGame();
            	}
            	else {
            		game.restartLevel();
            	}
        		resetState();
            	initGame();
            }
		}
		return true;
	}

	private void startGame() {
		st.isFinished = false;
		st.startGameTime = TimeUtils.millis();
		st.lastBadObjectTime = st.startGameTime;
		st.lastBonusItemTime = st.startGameTime;
		spawnFruit();		
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (st.isStarted && !st.isPaused) {
			Vector3 touchPos = new Vector3();
	        touchPos.set(screenX, screenY, 0);
	        camera.unproject(touchPos);            
	        
	        if (st.secondsRemaining > 0) {
	            basket.setPositionX(touchPos.x); 
	        }
        }
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
}
