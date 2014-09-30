package gr.sullenart.games.fruitcatcher.screens;

import gr.sullenart.games.fruitcatcher.view.FallingObjectState;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class GameScreenState implements Serializable {
    public long lastFruitTime;
    
    public long lastBadObjectTime;
    
    public long lastBonusItemTime;
    
    public long startGameTime;
    
    public int basketX;
    
    public boolean isPaused;
    
    public boolean isStarted;
    
    public boolean isFinished;
    
    public int score;
    
    public int secondsRemaining;
    
    public Array<FallingObjectState> fallingObjectStates;

    public GameScreenState() {
    	fallingObjectStates = new Array<FallingObjectState>();
    }
    
	@Override
	public void write(Json json) {
		json.writeValue("startGameTime", startGameTime);
		json.writeValue("lastBadObjectTime", lastBadObjectTime);
		json.writeValue("lastBonusItemTime", lastBonusItemTime);
		json.writeValue("lastFruitTime", lastFruitTime);
		json.writeValue("isPaused", isPaused);
		json.writeValue("isStarted", isStarted);
		json.writeValue("basketX", basketX);
		json.writeValue("score", score);
		json.writeValue("secondsRemaining", secondsRemaining);
		json.writeValue("isFinished", isFinished);
		
		json.writeArrayStart("fallingObjects");
		for(FallingObjectState fallingObjectState: fallingObjectStates) {
			json.writeValue(fallingObjectState, FallingObjectState.class);
		}
		json.writeArrayEnd();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void read(Json json, JsonValue jsonData) {
		startGameTime = json.readValue("startGameTime", Long.class, jsonData);
		lastBadObjectTime = json.readValue("lastBadObjectTime", Long.class, jsonData);
		lastBonusItemTime = json.readValue("lastBonusItemTime", Long.class, jsonData);
		lastFruitTime = json.readValue("lastFruitTime", Long.class, jsonData);
		score = json.readValue("score", Integer.class, jsonData);
		basketX = json.readValue("basketX", Integer.class, jsonData);
		isPaused = json.readValue("isPaused", Boolean.class, jsonData);
		isStarted = json.readValue("isStarted", Boolean.class, jsonData);
		isFinished = json.readValue("isFinished", Boolean.class, jsonData);
		secondsRemaining = json.readValue("secondsRemaining", Integer.class, jsonData);
		
		fallingObjectStates = json.readValue( "fallingObjects", Array.class,
				FallingObjectState.class, jsonData ); 
	}    
    
}
