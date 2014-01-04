package gr.sullenart.games.fruitcatcher.view;

import gr.sullenart.games.fruitcatcher.images.ImageProvider;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TimesTwoAnimation {
	private final float ANIMATION_PERIOD = 0.05f;
	
	private int STEP_FULL_SIZE_COUNT = 10;
	
	private int STEP_TOTAL_COUNT = 30;
	
	private TextureRegion frame;
	
	private float x;
	
	private float y;
	
	private float time;
	
	private int step;
	
	private float width;
	
	private float height;
	
	public TimesTwoAnimation(ImageProvider imageProvider, float x, float y) {
		frame = imageProvider.getTimes2();
		this.x = x;
		this.y = y;
		
		width = frame.getRegionWidth();
		height = frame.getRegionHeight();
		
		time = 0;
	}
	
    public void draw(SpriteBatch batch) {
    	float scale = 1;
    	float posX = x;
    	float posY = y;    	
    	if (step > STEP_FULL_SIZE_COUNT){
    		scale = scale / (step - STEP_FULL_SIZE_COUNT);
    		
    		posX += width*(1-scale)/2;
    		posY += height*(1-scale)/2;
    	}

    	batch.draw(frame, posX, posY, 0, 0, 
    			frame.getRegionWidth(), frame.getRegionHeight(),
    			scale, scale,
    			0);
    }
    
    public boolean isFinished(float deltaTime) {
    	time += deltaTime;
    	if (time > ANIMATION_PERIOD) {
    		time -= ANIMATION_PERIOD;
    		step++;
    	}
    	return step > STEP_TOTAL_COUNT;
    }
}
