package gr.sullenart.games.fruitcatcher.screens;

import gr.sullenart.games.fruitcatcher.FruitCatcherGame;
import gr.sullenart.games.fruitcatcher.images.ImageProvider;
import gr.sullenart.games.fruitcatcher.view.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class HelpScreen implements Screen, InputProcessor {
	
	//private String TAG = HelpScreen.class.getName();
	
	private ImageProvider imageProvider;
	
    private Texture backgroundImage;
    
    private Button [] buttons;
    
    private SpriteBatch batch;
    private OrthographicCamera camera; 

	private FruitCatcherGame game;

	private BitmapFont font;

	private int startLine;
	
	private int lastLineIndex;
	
	private int lineHeight = 30;
	
	private int touchStartY;
	
	public HelpScreen(FruitCatcherGame game) {
    	super();
    	this.game = game;
    }

	@Override
	public void show() {
        imageProvider = game.getImageProvider();
        backgroundImage = imageProvider.getBackgroundSpring();
        buttons = new Button [1];
        buttons[0] = new Button(imageProvider.getBack());        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());
        batch = new SpriteBatch();
        
        buttons[0].setPos(10, 10);
        
        font = new BitmapFont(Gdx.files.internal("fonts/poetsen.fnt"),
        			 		  Gdx.files.internal("fonts/poetsen.png"), false);
        
        startLine = 0;
        lineHeight = 30;
        lastLineIndex = game.getTextResources().getHelpLines().length - 1; 
        
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
        
        int lineY = imageProvider.getScreenHeight() - lineHeight - 45;
        for(int l=startLine; l<=lastLineIndex; l++) {
        	String line = game.getTextResources().getHelpLines()[l];
	        font.draw(batch, line, 150, lineY);
	        lineY -= lineHeight;
	        if (lineY < 0) {
	        	break;
	        }
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
		else if (keycode == Keys.UP && startLine > 0) {
			startLine--;
		}
		else if (keycode == Keys.DOWN && startLine < lastLineIndex) {
			startLine++;
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
		touchStartY = screenY;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3();
        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos);
        
        for(int i=0;i<buttons.length;i++) {          	
        	if (buttons[i].isPressed(touchPos)) {
        		if (i == 0) {
        			game.gotoMenuScreen();
        		}
        		break;
        	}
        }
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		int diffY = touchStartY - screenY;
		int linesDiff = diffY / lineHeight;
		if (linesDiff != 0) {
			touchStartY = screenY;
			scrolled(linesDiff);
		}
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		startLine += amount;
		if (startLine < 0) {
			startLine = 0;
		}
		if (startLine > lastLineIndex) {
			startLine = lastLineIndex;
		}
		return true;
	}	
	
}
