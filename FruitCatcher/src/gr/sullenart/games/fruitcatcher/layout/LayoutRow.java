package gr.sullenart.games.fruitcatcher.layout;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LayoutRow extends AbstractLayoutItem {

	private LayoutItem [] layoutItems;
	
	private int hMargin;
	
	public LayoutRow(LayoutItem [] layoutItems, int hMargin) {
		this.layoutItems = layoutItems;
		this.hMargin = hMargin;
		
		width = 0;
		height = 0;
		for(LayoutItem li: layoutItems) {
			if (li != null) {
				if (width > 0) {
					width += hMargin + li.getWidth();
				}
				else {
					width += li.getWidth();
				}
				if (li.getHeight() > height) {
					height = li.getHeight();
				}
			}
		}
	}
	
	@Override
	public void setX(float x) {
		this.x = x;
		
		float startX = 0;
		for(LayoutItem li: layoutItems) {
			if (li != null) {
				li.setX(x + startX);
				startX += hMargin + li.getWidth();
			}
		}			
	}
	
	@Override
	public void setY(float y) {
		this.y = y;
		
		for(LayoutItem li: layoutItems) {
			if (li != null) {
				float startY = y + (height - li.getHeight())/2;
				li.setY(startY);
			}
		}			
	}	
	
	@Override
	public void draw(SpriteBatch batch) {
		for(LayoutItem li: layoutItems) {
			if (li != null) {
				li.draw(batch);
			}
		}
	}

}
