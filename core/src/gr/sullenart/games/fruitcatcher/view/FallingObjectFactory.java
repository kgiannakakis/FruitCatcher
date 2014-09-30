package gr.sullenart.games.fruitcatcher.view;

import gr.sullenart.games.fruitcatcher.images.ImageProvider;
import gr.sullenart.games.fruitcatcher.models.FallingObjectType;
import gr.sullenart.games.fruitcatcher.models.FruitType;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class FallingObjectFactory {

    private ImageProvider imageProvider;
    
    private int season = 0;
    
    public void setSeason(int season) {
    	this.season = season;
    }

    public FallingObjectFactory(ImageProvider imageProvider) {
        this.imageProvider = imageProvider;
    }

    public FallingObject getStar() {
        TextureRegion [] textureRegions = new TextureRegion[2];
        textureRegions[0] = imageProvider.getStarFrame(2);
        textureRegions[1] = imageProvider.getStarFrame(3);
        
        FallingObjectState model = new FallingObjectState();
        model.setType(FallingObjectType.BonusObject);
        return new FallingObject(imageProvider, textureRegions, 
                                 model);
    }
    
    public FallingObject getBadObject() {
        TextureRegion [] textureRegions = new TextureRegion[2];
        textureRegions[0] = imageProvider.getBadAppleFrame(1);
        textureRegions[1] = imageProvider.getBadAppleFrame(2);
        
        FallingObjectState model = new FallingObjectState();
        model.setType(FallingObjectType.BadFruit);        
        return new FallingObject(imageProvider, textureRegions, 
                                 model);
    }  

    public FallingObject getFruit() {
        TextureRegion [] textureRegions = new TextureRegion[1];
        int fruitType = MathUtils.random(0, imageProvider.getFruitsCount() - 1);
        textureRegions[0] =  imageProvider.getFruit(fruitType);
        
        boolean inSeason = FruitType.isInSeason(fruitType, season);
        FallingObjectState state = new FallingObjectState();
        if (inSeason) {
        	state.setType(FallingObjectType.SeasonalFruit);
        }
        else {
        	state.setType(FallingObjectType.Fruit);
        }
        state.setIndex(fruitType);
        return new FallingObject(imageProvider, textureRegions, 
                                 state);
    }

	public FallingObject getObjectFromState(FallingObjectState foState) {
		FallingObjectType type = foState.getType();
		TextureRegion [] textureRegions = null;
		
		switch(type) {
		case Fruit:
		case SeasonalFruit:
			textureRegions = new TextureRegion[1];
			textureRegions[0] =  imageProvider.getFruit(foState.getIndex());
			break;
		case BadFruit:
	        textureRegions = new TextureRegion[2];
	        textureRegions[0] = imageProvider.getBadAppleFrame(1);
	        textureRegions[1] = imageProvider.getBadAppleFrame(2);			
			break;
		case BonusObject:
			textureRegions = new TextureRegion[2];
	        textureRegions[0] = imageProvider.getStarFrame(2);
	        textureRegions[1] = imageProvider.getStarFrame(3);			
			break;
		}
		
		return new FallingObject(imageProvider, textureRegions, foState);
	}      
    
}