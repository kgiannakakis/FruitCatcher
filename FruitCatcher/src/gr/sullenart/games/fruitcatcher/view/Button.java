package gr.sullenart.games.fruitcatcher.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

public class Button {

	private TextureRegion background = null;
	
	private TextureRegion text;
	
	private TextureRegion suffix = null;

	private int margin = 0;
	
	private float startX;
	
	private float endX;
	
	private float startY;
	
	private float endY;

	private float textX;
	
	private float textY;
	
	private float suffixX;
	
	private float suffixY;

	private static long timePressed = 0;
	
	public Button(TextureRegion text) {
		this.text = text;
	}

	public Button(TextureRegion background, TextureRegion text) {
		this.background = background;
		this.text = text;
	}	
	
	public Button(TextureRegion background, TextureRegion text, TextureRegion suffix,
			     int margin) {
		this.background = background;
		this.text = text;
		this.suffix = suffix;
		this.margin  = margin;
	}		
	
	public void setPos(float x, float y) {
		startX = x;
		startY = y;
		
		if (background != null) {
			endX = x + background.getRegionWidth();
			endY = y + background.getRegionHeight();	
			
			int textWidth = text.getRegionWidth();
			if (suffix != null) {
				textWidth += margin + suffix.getRegionWidth(); 
			}
			textX = startX + (background.getRegionWidth()-textWidth)/2;
			textY = startY + (background.getRegionHeight()-text.getRegionHeight())/2;			
		}
		else {
			endX = x + text.getRegionWidth();
			endY = y + text.getRegionHeight();
			textX = startX;
			textY = startY;
		}
		
		if (suffix != null) {
			suffixX = textX + margin + text.getRegionWidth();
			suffixY = textY;
		}
	}
	
	public void draw(SpriteBatch batch) {
		if (background != null) {
			batch.draw(background, startX, startY);
			batch.draw(text, textX, textY);
		}
		else {
			batch.draw(text, startX, startY);
		}
		if (suffix != null) {
			batch.draw(suffix, suffixX, suffixY);
		}
	}
	
	public boolean isPressed(Vector3 touchPos) {
        float x = touchPos.x;
        float y = touchPos.y;		
		if (x > startX && x < endX && y > startY && y < endY) {
			long now = TimeUtils.millis();
			if (now - timePressed > 200) {
				timePressed  = TimeUtils.millis();
				return true;
			}
		}
		return false;
	}

	public int getRegionHeight() {
		if (background != null) {
			return background.getRegionHeight();
		}
		return text.getRegionHeight();
	}

	public int getRegionWidth() {
		if (background != null) {
			return background.getRegionWidth();
		}		
		return text.getRegionWidth();
	}

	public float getPosY() {
		return startY;
	}

	public float getPosX() {
		return startX;
	}	
}
