package gr.sullenart.games.fruitcatcher.layout;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SimpleLayoutManager {
	private int width;
	
	private int height;
	
	private int offsetX;
	
	private int offsetY;	
	 
	private List<AbstractLayoutItem> layoutItems;
	
	public SimpleLayoutManager(int width, int height, int offsetX, int offsetY) {
		this.width = width;
		this.height = height;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		
		layoutItems = new ArrayList<AbstractLayoutItem>();
	}
	
	public void add(AbstractLayoutItem li, int flags, Margin margin) {
		layoutItems.add(li);
		
		int w = li.getWidth();
		int h = li.getHeight();
		int x = offsetX;
		int y = offsetY;		
		
		if (margin == null) {
			margin = new Margin();
		}
		
		if ((flags & Layout.ALIGN_LEFT) == Layout.ALIGN_LEFT) {
			x = offsetX + margin.left;
		}
		
		if ((flags & Layout.ALIGN_RIGHT) == Layout.ALIGN_RIGHT) {
			x = offsetX + width - w - margin.right;
		}
		
		if ((flags & Layout.ALIGN_TOP) == Layout.ALIGN_TOP) {
			y = offsetY + height - h - margin.top;
		}
		
		if ((flags & Layout.ALIGN_BOTTOM) == Layout.ALIGN_BOTTOM) {
			y = offsetY +  margin.bottom;
		}		
		
		if ((flags & Layout.CENTER_VERTICAL) == Layout.CENTER_VERTICAL) {
			y = offsetY + (height - h)/2;
		}
		if ((flags & Layout.CENTER_HORIZONTAL) == Layout.CENTER_HORIZONTAL) {
			x = offsetX + (width - w)/2;
		}			
		li.setX(x);
		li.setY(y);
	}
	
	public void addBelow(AbstractLayoutItem li, AbstractLayoutItem topLi,
						 int flags, Margin margin) {
		layoutItems.add(li);
		
		int w = li.getWidth();
		int h = li.getHeight();
		float x = offsetX;
		float y = offsetY;		
		
		if (margin == null) {
			margin = new Margin();
		}
		
		if ((flags & Layout.ALIGN_LEFT) == Layout.ALIGN_LEFT) {
			x = offsetX + margin.left;
		}
		else if ((flags & Layout.ALIGN_RIGHT) == Layout.ALIGN_RIGHT) {
			x = offsetX + width - w - margin.right;
		}
		else {
			x = topLi.getX() + margin.left;
		}
		
		y = topLi.getY() - h - margin.top;
		
		if ((flags & Layout.CENTER_HORIZONTAL) == Layout.CENTER_HORIZONTAL) {
			x = offsetX + (width - w)/2;
		}			
		li.setX(x);
		li.setY(y);
	}
	
	public void addRight(AbstractLayoutItem li, AbstractLayoutItem leftLi,
			 int flags, Margin margin) {
		layoutItems.add(li);
		
		int h = li.getHeight();
		float x = offsetX;
		float y = offsetY;		
		
		if (margin == null) {
			margin = new Margin();
		}
		
		if ((flags & Layout.ALIGN_TOP) == Layout.ALIGN_TOP) {
			y = offsetY + height - h - margin.top;
		}
		else if ((flags & Layout.ALIGN_BOTTOM) == Layout.ALIGN_BOTTOM) {
			y = offsetY +  margin.bottom;
		}
		else {
			y = leftLi.getY();
		}
		
		x = leftLi.getX() + leftLi.getWidth() + margin.left;
		
		if ((flags & Layout.CENTER_VERTICAL) == Layout.CENTER_VERTICAL) {
			y = offsetY + (height - h)/2;
		}
		
		li.setX(x);
		li.setY(y);
	}
	
	public void draw(SpriteBatch batch) {
		for(AbstractLayoutItem li: layoutItems) {
			li.draw(batch);
		}
	}
}
