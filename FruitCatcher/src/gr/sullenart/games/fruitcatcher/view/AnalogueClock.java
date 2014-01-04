package gr.sullenart.games.fruitcatcher.view;

import gr.sullenart.games.fruitcatcher.images.ImageProvider;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnalogueClock {
	
	private TextureRegion base;
	
	private TextureRegion[] secondImages;
	
	private TextureRegion[] secondRedImages;
	
	private int seconds;
	
	public AnalogueClock(ImageProvider imageProvider) {		
		base = imageProvider.getClockBase();
		
		secondImages = new TextureRegion[15];
		for(int i=0; i<15; i++) {
			secondImages[i] = imageProvider.getSecond(i+1);
		}
		secondRedImages = new TextureRegion[5];
		for(int i=0; i<5; i++) {
			secondRedImages[i] = imageProvider.getSecondRed(i+1);
		}		
	}
	
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	
	public void draw(SpriteBatch batch, int x, int y) {
		batch.draw(base, x, y);
		float width = 0;
		float height  = 0;
		float rotation = 0;
		float scaleX = 1f;
		float scaleY = 1f;
		float originX = 0;
		float originY = 0;
		float posX = x + (base.getRegionWidth()/2);
		float posY = y +(base.getRegionHeight()/2);
		if (seconds > 30) {
			
		}
		else if (seconds > 15) {
			width = secondImages[14].getRegionWidth();
			height = secondImages[14].getRegionHeight();
			batch.draw(secondImages[14], posX+1, posY+1, 
					   originX, originY, 
					   width, height, scaleX, scaleY, rotation);
			rotation = 270;
			width = secondImages[seconds-16].getRegionWidth();
			height = secondImages[seconds-16].getRegionHeight();			
			batch.draw(secondImages[seconds-16], posX+1, posY+1, 
					   originX, originY, 
					   width, height, scaleX, scaleY, rotation);			
		}
		else if (seconds > 5){
			width = secondImages[seconds-1].getRegionWidth();
			height = secondImages[seconds-1].getRegionHeight();				
			batch.draw(secondImages[seconds-1], posX+1, posY+1, 
					   originX, originY, 
					   width, height, scaleX, scaleY, rotation);
		}		
		else if (seconds > 0){
			width = secondRedImages[seconds-1].getRegionWidth();
			height = secondRedImages[seconds-1].getRegionHeight();				
			batch.draw(secondRedImages[seconds-1], posX+1, posY+1, 
					   originX, originY, 
					   width, height, scaleX, scaleY, rotation);				
		}
	}
}
