package gr.sullenart.games.fruitcatcher.layout;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class LayoutItem extends AbstractLayoutItem {

	private TextureRegion region;
	
	private float originX, originY, scaleX, scaleY, rotation;

	public TextureRegion getRegion() {
		return region;
	}

	public float getOriginX() {
		return originX;
	}

	public float getOriginY() {
		return originY;
	}

	public float getScaleX() {
		return scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}

	public float getRotation() {
		return rotation;
	}

	public LayoutItem(TextureRegion region) {
		this.region = region;
		originX = originY = 0;
		scaleX = scaleY = 1f;
		rotation = 0;
		width = region.getRegionWidth();
		height = region.getRegionHeight();
	}
	
	public LayoutItem(TextureRegion region, float scale, float rotation) {
		this.region = region;
		originX = region.getRegionWidth()/2;
		originY = region.getRegionHeight()/2;
		scaleX = scaleY = scale;
		this.rotation = rotation;
		width = (int) (scale*region.getRegionWidth());
		height = (int) (scale*region.getRegionHeight());
	}	
	
	public void draw(SpriteBatch batch) {
		batch.draw(region, 
				x, y, 
				originX, originY, 
				width, height, 
				scaleX, scaleY, 
				rotation);		
	}
	
	public boolean isPressed(Vector3 touchPos) {
        float touchX = touchPos.x;
        float touchY = touchPos.y;		
		if (touchX > x && touchX < x + width && 
				touchY > y && touchY < y + height) {
				return true;
		}
		return false;		
	}
}
