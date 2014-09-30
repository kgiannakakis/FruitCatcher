package gr.sullenart.games.fruitcatcher.view;

import gr.sullenart.games.fruitcatcher.images.ImageProvider;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class FallingObject {

    protected final int DROP_SPEED = 200;
    
    protected final float ANIMATION_PERIOD = 0.3f;

    protected int width = 48;
    
    protected int height = 48;
    
    protected Rectangle rect;
    
    protected int frame = 0;
    
    protected float time = 0;
    
    protected TextureRegion [] textureRegions;
    
    protected FallingObjectState state;
    
    protected Rectangle getPosition() {
        return rect;
    }
    
    public FallingObject(ImageProvider imageProvider, TextureRegion [] textureRegions,
    					 FallingObjectState state) {
        rect = new Rectangle();
        rect.width = width;
        rect.height = height;
        
        this.textureRegions = textureRegions;
        this.state = state;
        
        if(state.getPosX() < 0 || state.getPosY() < 0) {
        	rect.x = MathUtils.random(0, imageProvider.getScreenWidth()-width);
        	rect.y = imageProvider.getScreenHeight();
        }
        else {
        	rect.x = state.getPosX();
        	rect.y = state.getPosY();
        }
        
        state.setPosX((int) rect.x);
        state.setPosY((int) rect.y);
    }
    
    public void moveDown(float delta) {
        rect.y -= DROP_SPEED * delta;
        state.setPosY((int) rect.y);
        time += delta;
        if (time > ANIMATION_PERIOD) {
        	time -= ANIMATION_PERIOD;
        	frame += 1;
        	if(frame >= textureRegions.length) {
        		frame = 0;
        	}
        }
    }
    
    public boolean isBottomOfScreen() {
        return rect.y + width < 0;
    }
    
    public void draw(SpriteBatch batch) {
    	batch.draw(textureRegions[frame], rect.x, rect.y);
    }
    
    public boolean isOverlapping(Rectangle otherRect) {
        return rect.overlaps(otherRect);
    }
    
    public int getPoints() {
        return state.getPoints();
    }

	public FallingObjectState getState() {
		return state;
	}
}

