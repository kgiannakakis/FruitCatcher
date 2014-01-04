package gr.sullenart.games.fruitcatcher.screens;

import gr.sullenart.games.fruitcatcher.FruitCatcherGame;
import gr.sullenart.games.fruitcatcher.images.ImageProvider;
import gr.sullenart.games.fruitcatcher.models.GameModelFactory;
import gr.sullenart.games.fruitcatcher.state.State;
import gr.sullenart.games.fruitcatcher.view.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class LevelScreen implements Screen, InputProcessor {
	
	private String TAG = LevelScreen.class.getName();
	
	private ImageProvider imageProvider;
	
    private Texture backgroundImage;
    
    private Button [] buttons;
    
    private SpriteBatch batch;
    private OrthographicCamera camera; 

	private FruitCatcherGame game;
	
	private boolean [] isLevelUnlocked;
	
	public LevelScreen(FruitCatcherGame game) {
    	super();
    	this.game = game;
    }

	@Override
	public void show() {
		isLevelUnlocked = new boolean[GameModelFactory.LEVEL_COUNT];
		isLevelUnlocked[0] = true;
		for(int i=1; i<GameModelFactory.LEVEL_COUNT; i++) {
			isLevelUnlocked[i] = State.isLevelUnlocked(i);
		}
		
        imageProvider = game.getImageProvider();
        backgroundImage = imageProvider.getBackgroundSpring();
        TextureRegion buttonBg = imageProvider.getButton();
        buttons = new Button [4];
        buttons[0] = new Button(buttonBg, imageProvider.getLevel(), 
        		                imageProvider.getNumber(1), 5);
        if (isLevelUnlocked[1]) {
	        buttons[1] = new Button(buttonBg, imageProvider.getLevel(),
	        		                imageProvider.getNumber(2), 5);
        }
        else {
	        buttons[1] = new Button(buttonBg, imageProvider.getLevelGrey(),
	                imageProvider.getGrey2(), 5);        	
        }
        if (isLevelUnlocked[2]) {
	        buttons[2] = new Button(buttonBg, imageProvider.getLevel(),
	        		                imageProvider.getNumber(3), 5);
        }
        else {
	        buttons[2] = new Button(buttonBg, imageProvider.getLevelGrey(),
	                imageProvider.getGrey3(), 5);        	
        }
        buttons[3] = new Button(imageProvider.getBack());        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());
        batch = new SpriteBatch();
        
        int buttonMargin = 15;
        int buttonsHeight = 2*buttonMargin;
        int centeredButtons = 3;
        for(int i=0; i<centeredButtons; i++) {
        	buttonsHeight += buttons[i].getRegionHeight();
        }
        
        for(int i=centeredButtons-1;i>=0;i--) {
        	int x, y;
        	x = (imageProvider.getScreenWidth() - buttons[i].getRegionWidth())/2;
        	if (i == centeredButtons - 1) {
        		y = (imageProvider.getScreenHeight() - buttonsHeight) / 2;
        	}
        	else {
        		y = ((int) buttons[i+1].getPosY()) + 
        			buttons[i+1].getRegionHeight() + buttonMargin;
        	}
        	buttons[i].setPos(x, y);
        }
        buttons[3].setPos(10, 10);
        
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);        
	}	
	
	@Override
	public void render(float delta) {		
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundImage, 0, 0);        
        for(int i=0;i<buttons.length;i++) {
        	buttons[i].draw(batch);
        }
        batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
        if (batch != null) {
        	batch.dispose();
        }
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACK){
			game.gotoMenuScreen();
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
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3();
        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos);
        
        for(int i=0;i<buttons.length;i++) {          	
        	if (buttons[i].isPressed(touchPos)) {
        		Gdx.app.log(TAG, "Button " + (i+1) + " pressed");
        		if (i < 3  && isLevelUnlocked[i]) {
        			game.startGame(i);
        			game.gotoGameScreen(null);
        		}
        		else if (i == 3) {
        			game.gotoMenuScreen();
        		}
        		break;
        	}
        }
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
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
