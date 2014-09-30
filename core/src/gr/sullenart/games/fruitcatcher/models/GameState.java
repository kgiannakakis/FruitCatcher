package gr.sullenart.games.fruitcatcher.models;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;


public class GameState implements Serializable {
    
    private boolean kidsMode = false;
    
    private int level = 0;
    
    private int challenge = 0;
    
    private int totalScore = 0;

    private boolean active = false;

	public boolean isKidsMode() {
		return kidsMode;
	}

	public void setKidsMode(boolean kidsMode) {
		this.kidsMode = kidsMode;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getChallenge() {
		return challenge;
	}

	public void setChallenge(int challenge) {
		this.challenge = challenge;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public void write(Json json) {
		json.writeValue("active", active);
		json.writeValue("challenge", challenge);
		json.writeValue("kidsMode", kidsMode);
		json.writeValue("level", level);
		json.writeValue("totalScore", totalScore);		
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		active = json.readValue("active", Boolean.class, jsonData);
		challenge = json.readValue("challenge", Integer.class, jsonData);
		kidsMode = json.readValue("kidsMode", Boolean.class, jsonData);
		level = json.readValue("level", Integer.class, jsonData);
		totalScore = json.readValue("totalScore", Integer.class, jsonData);
	}
    
}
