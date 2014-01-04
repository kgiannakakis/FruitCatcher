package gr.sullenart.games.fruitcatcher.screens;

import gr.sullenart.games.fruitcatcher.FruitCatcherGame;
import gr.sullenart.games.fruitcatcher.images.ImageProvider;
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

public class MenuScreen implements Screen, InputProcessor {
	
	//private String TAG = MenuScreen.class.getName();
    
    private OrthographicCamera camera;    
    
    private SpriteBatch batch;  
    
    private FruitCatcherGame game;

    private ImageProvider imageProvider;
	
    private Texture backgroundImage;
    
    private Button [] buttons;
    
    private Button helpButton;
    
	private TextureRegion logo;

	private int logoX;

	private int logoY;
	
	private Button [] soundButtons;
    
	private boolean soundOn;
	
	//private long soundButtonTimePressed = 0;
	
    public MenuScreen(FruitCatcherGame game) {
    	super();
    	this.game = game;
    	
    	soundOn = State.isSoundOn();
    	game.getSoundManager().setSoundOn(soundOn);
    }
    
	@Override
	public void show() {
		imageProvider = game.getImageProvider();
		imageProvider.load();
        backgroundImage = imageProvider.getBackgroundSpring();
        TextureRegion buttonBg = imageProvider.getButton();
        buttons = new Button [3];
        buttons[0] = new Button(buttonBg, imageProvider.getStart());
        buttons[1] = new Button(buttonBg, imageProvider.getKids());
        buttons[2] = new Button(buttonBg, imageProvider.getScores());
        helpButton = new Button(imageProvider.getHelp());
        
        soundButtons = new Button[2];
        soundButtons[0] = new Button(imageProvider.getSoundImage(false));
        soundButtons[1] = new Button(imageProvider.getSoundImage(true));
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());
        batch = new SpriteBatch();
        
        logo = imageProvider.getLogo();
        logoX = (imageProvider.getScreenWidth() - logo.getRegionWidth())/2;
        logoY = (imageProvider.getScreenHeight() - logo.getRegionHeight() - 10)-50;
        
        int buttonMargin = 15;
        int buttonsHeight = 3*buttonMargin;
        for(int i=0; i<buttons.length; i++) {
        	buttonsHeight += buttons[i].getRegionHeight();
        }
        
        for(int i=buttons.length-1;i>=0;i--) {
        	int x, y;
        	x = (imageProvider.getScreenWidth() - buttons[i].getRegionWidth())/2;
        	if (i == buttons.length - 1) {
        	y = ((imageProvider.getScreenHeight() - buttonsHeight) / 2) - 10;
        	}
        	else {
        		y = ((int) buttons[i+1].getPosY()) + 
        			buttons[i+1].getRegionHeight() + buttonMargin;
        	}
        	buttons[i].setPos(x, y);
        }
        
        float x = imageProvider.getScreenWidth() - helpButton.getRegionWidth() - 10;
        float y = 10;
        helpButton.setPos(x, y);
        
        soundButtons[0].setPos(10, 10);
        soundButtons[1].setPos(10, 10);
        
        Gdx.input.setInputProcessor(this);
	}    
    
	@Override
	public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundImage, 0, 0);
        batch.draw(logo, logoX, logoY);
        for(int i=0;i<buttons.length;i++) {
        	buttons[i].draw(batch);
        }
        helpButton.draw(batch);
        
        if (!soundOn) {
        	soundButtons[0].draw(batch);
        }
        else {
        	soundButtons[1].draw(batch);
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
			Gdx.app.exit();
			return true;
		}
		return false;
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
        Vector3 touchPos = new Vector3();
        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos);
        
        for(int i=0;i<buttons.length;i++) {          	
        	if (buttons[i].isPressed(touchPos)) {
        		if (i == 0) {
        			game.gotoLevelScreen();
        		}
        		else if (i == 1) {
        			game.startGameKidsMode();
        			game.gotoGameScreen(null);
        		}
        		else if (i == 2) {
        			game.showHighscores();
        		}
        		break;
        	}
        }
        if (helpButton.isPressed(touchPos)) {
        	game.showHelp();
        }
        if (soundOn && soundButtons[0].isPressed(touchPos)) {
        	soundOn = false;
        	State.setSoundOn(soundOn);
        	game.getSoundManager().setSoundOn(soundOn);
        }
        else if (!soundOn && soundButtons[1].isPressed(touchPos)) {
        	soundOn = true;
        	State.setSoundOn(soundOn);
        	game.getSoundManager().setSoundOn(soundOn);
        }  				
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
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
