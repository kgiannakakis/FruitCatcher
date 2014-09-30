package gr.sullenart.games.fruitcatcher.layout;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractLayoutItem {
	protected float x, y;
	
	protected int width, height;	
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}	
	
	public abstract void draw(SpriteBatch batch);
}
