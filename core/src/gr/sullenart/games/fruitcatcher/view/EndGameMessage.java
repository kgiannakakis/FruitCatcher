package gr.sullenart.games.fruitcatcher.view;

import gr.sullenart.games.fruitcatcher.images.ImageProvider;
import gr.sullenart.games.fruitcatcher.layout.AbstractLayoutItem;
import gr.sullenart.games.fruitcatcher.layout.Layout;
import gr.sullenart.games.fruitcatcher.layout.LayoutItem;
import gr.sullenart.games.fruitcatcher.layout.LayoutRow;
import gr.sullenart.games.fruitcatcher.layout.Margin;
import gr.sullenart.games.fruitcatcher.layout.SimpleLayoutManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class EndGameMessage {
	
	private Texture board;
	
	private int boardX;
	
	private int boardY;
	
	private boolean success;

	private SimpleLayoutManager simpleLayoutManager;
	
	private LayoutItem actionLi;
	
	public EndGameMessage(ImageProvider imageProvider, int level, 
						  int challenge, int goal, int score,
						  int totalScore, int unlockedLevel) {
		
		this.board = imageProvider.getBoard();
	
		TextureRegion pointsLabel = imageProvider.getPointsLabel();
		TextureRegion resultLabel= imageProvider.getSuccessLabel();
		TextureRegion restart = imageProvider.getRestart();
		TextureRegion next = imageProvider.getBack();
		
		int screenWidth = imageProvider.getScreenWidth();
		int screenHeight = imageProvider.getScreenHeight();
		
		boardX = (screenWidth - board.getWidth()) / 2;
		boardY = (screenHeight - board.getHeight()) / 2;
		
		int boardTopPadding = 50;
		int boardBottomPadding = 50;
		
		simpleLayoutManager = new SimpleLayoutManager(board.getWidth(), 
			    board.getHeight(),
			    boardX, boardY);
		
		success = score >= goal;
		AbstractLayoutItem resultLi;
		if (success && unlockedLevel > 0) {
			LayoutItem [] levelItems = new LayoutItem [] {
					new LayoutItem(imageProvider.getLevel()),
					new LayoutItem(imageProvider.getNumber(unlockedLevel+1)),
					new LayoutItem(imageProvider.getUnlockedLabel())
			};
			
			resultLi = new LayoutRow(levelItems, 10);			
		}
		else {
			resultLabel = success ? imageProvider.getSuccessLabel() : 
									imageProvider.getYouLoseLabel();		
			resultLi = new LayoutItem(resultLabel);
		}
		
		simpleLayoutManager.add(resultLi, 
				Layout.ALIGN_TOP | Layout.CENTER_HORIZONTAL, 
				new Margin(0, boardTopPadding, 0, 0));		
		
		LayoutRow pointsRow = NumberRow.getRow(imageProvider, pointsLabel, score);
		simpleLayoutManager.addBelow(pointsRow, resultLi, 
				Layout.CENTER_HORIZONTAL, 
				new Margin(0, 25, 0, 0));					
	
		LayoutRow goalRow = NumberRow.getRow(imageProvider, 
						imageProvider.getTotalLabel(), totalScore);
		
		simpleLayoutManager.addBelow(goalRow, pointsRow, 
				Layout.CENTER_HORIZONTAL, 
				new Margin(0, 25, 0, 0));	
		
		
		if (success) {
			actionLi = new LayoutItem(next, 1f, 180f);
			simpleLayoutManager.add(actionLi, 
					Layout.ALIGN_BOTTOM | Layout.CENTER_HORIZONTAL, 
					new Margin(0, 0, 0, boardBottomPadding));	
		}
		else {
			actionLi = new LayoutItem(restart, 0.66f, 0f);
			simpleLayoutManager.add(actionLi, 
					Layout.ALIGN_BOTTOM | Layout.CENTER_HORIZONTAL, 
					new Margin(0, 0, 0, boardBottomPadding));	
		}
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(board, boardX, boardY);
		simpleLayoutManager.draw(batch);
	}
	
	public boolean isPressed(Vector3 touchPos) {
		return actionLi.isPressed(touchPos);
	}	
}
