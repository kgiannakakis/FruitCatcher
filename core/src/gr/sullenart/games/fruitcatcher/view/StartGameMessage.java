package gr.sullenart.games.fruitcatcher.view;

import gr.sullenart.games.fruitcatcher.images.ImageProvider;
import gr.sullenart.games.fruitcatcher.layout.Layout;
import gr.sullenart.games.fruitcatcher.layout.LayoutItem;
import gr.sullenart.games.fruitcatcher.layout.LayoutRow;
import gr.sullenart.games.fruitcatcher.layout.Margin;
import gr.sullenart.games.fruitcatcher.layout.SimpleLayoutManager;
import gr.sullenart.games.fruitcatcher.models.FruitType;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class StartGameMessage {
	
	private Texture board;
	
	private TextureRegion seasonLabel;
	
	private TextureRegion startLabel;
	
	private int boardX;
	
	private int boardY;
	
	private LayoutItem startLayoutItem;
	
	private SimpleLayoutManager simpleLayoutManager;
	
	public StartGameMessage(ImageProvider imageProvider, int level, int challenge, int goal) {
		this.board = imageProvider.getBoard();
		
        switch(challenge % 4) {
            case 0:
                seasonLabel = imageProvider.getSpringLabel();
                break;
            case 1:
                seasonLabel = imageProvider.getSummerLabel();
                break;
            case 2:
                seasonLabel = imageProvider.getAutumnLabel();
                break;
            case 3:
                seasonLabel = imageProvider.getWinterLabel();
                break;
        }
		
		TextureRegion goalLabel = imageProvider.getGoalLabel();
		
		startLabel = imageProvider.getStart();
		
		int screenWidth = imageProvider.getScreenWidth();
		int screenHeight = imageProvider.getScreenHeight();
		
		boardX = (screenWidth - board.getWidth()) / 2;
		boardY = (screenHeight - board.getHeight()) / 2;
		
		int boardTopPadding = 50;
		int boardBottomPadding = 50;
		//int boardLeftPadding = 35;
		
		simpleLayoutManager = new SimpleLayoutManager(board.getWidth(), 
			    board.getHeight(),
			    boardX, boardY);
		
		LayoutRow seasonRow = new LayoutRow(new LayoutItem [] {
				new LayoutItem(seasonLabel), new LayoutItem(imageProvider.getFruitsLabel())
		}, 10);
		simpleLayoutManager.add(seasonRow, 
				Layout.ALIGN_TOP | Layout.CENTER_HORIZONTAL, 
				new Margin(0, boardTopPadding, 0, 0));

		int [] fruitTypesInSeason = FruitType.getFruitsInSeason(challenge);
		LayoutItem [] seasonFruits = new LayoutItem[fruitTypesInSeason.length];
		for(int i=0; i< fruitTypesInSeason.length; i++) {
			int fruitType = fruitTypesInSeason[i];
			seasonFruits[i] = new LayoutItem(imageProvider.getFruitBig(fruitType), 
											 1.0f, 0);
		}
		
		LayoutRow fruitsRow = new LayoutRow(seasonFruits, 20);
		simpleLayoutManager.addBelow(fruitsRow, seasonRow, 
				Layout.CENTER_HORIZONTAL, 
				new Margin(0, 20, 0, 0));
		
		LayoutRow goalRow = NumberRow.getRow(imageProvider, goalLabel, goal);
		simpleLayoutManager.addBelow(goalRow, fruitsRow, 
				Layout.ALIGN_LEFT | Layout.CENTER_HORIZONTAL, 
				new Margin(0, 25, 0, 0));				

		startLayoutItem = new LayoutItem(startLabel);
		simpleLayoutManager.add(startLayoutItem, 
				Layout.CENTER_HORIZONTAL | Layout.ALIGN_BOTTOM, 
				new Margin(0, 0, 0, boardBottomPadding));
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(board, boardX, boardY);
		simpleLayoutManager.draw(batch);
	}
	
	public boolean isStartPressed(Vector3 touchPos) {
		return startLayoutItem.isPressed(touchPos);
	}	
}
