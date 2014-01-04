package gr.sullenart.games.fruitcatcher;

import gr.sullenart.games.fruitcatcher.text.TextResources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class HighScoreManager {

	private static final String SCORES_DATA_FILE = "data/scores-v1.json";
	
	private final int HIGH_SCORES_COUNT = 5;
	
	public static class HighScore {
		public String name;
		public int score;
		public boolean isHighlighted;
		
		public HighScore() {
			isHighlighted = false;
		}
		
		public HighScore(String name, int score, boolean isHighlighted) {
			this.name = name;
			this.score = score;
			this.isHighlighted = isHighlighted;
		}
	}
	
	public List<HighScore> highScores;
	
	private TextResources textResources;
	
	public HighScoreManager(TextResources textResources) {
		this.textResources = textResources;
		retrieveHighScores();
	}
	
	public boolean isHighScore(int score) {
		HighScore minHighScore = highScores.get(highScores.size() - 1);
		return score > minHighScore.score;
	}
	
	public void addHighScore(String name, int score) {
		highScores.add(new HighScore(name, score, true));
		
		Collections.sort(highScores, new Comparator<HighScore> () {
			@Override
			public int compare(HighScore score1, HighScore score2) {
				return score2.score - score1.score;
			}
			
		});
		
		while(highScores.size() > HIGH_SCORES_COUNT) {
			highScores.remove(HIGH_SCORES_COUNT);
		}
		
		persist();
	}
	
	public List<HighScore> getHighScores() {
		return highScores;
	}
	
	@SuppressWarnings("unchecked")
	private void retrieveHighScores() {
		FileHandle highScoresFile = Gdx.files.local(SCORES_DATA_FILE);
		if( highScoresFile.exists() ) {
			Json json = new Json();
			try {
				String encScoresStr = highScoresFile.readString();
				String scoresStr = Base64Coder.decodeString( encScoresStr );
				highScores = json.fromJson(ArrayList.class, HighScore.class, scoresStr);
				return;
            } catch( Exception e ) {
                Gdx.app.error( HighScoreManager.class.getName(), 
                		"Unable to parse high scores data file", e );
            }
		}
		highScores = new ArrayList<HighScore>();
		String playerName = textResources.getDefaultPlayerName();
		for(int i=0;i<HIGH_SCORES_COUNT;i++){
			highScores.add(new HighScore(playerName, 50 - 10*i, false));
		}
	}
	
	private void persist() {
		FileHandle highScoresFile = Gdx.files.local(SCORES_DATA_FILE);
		
        Json json = new Json();
        String scoresStr = json.toJson(highScores);
        String encScoresStr = Base64Coder.encodeString(scoresStr);
        highScoresFile.writeString(encScoresStr, false);		
	}
	
	public static String serialize(List<HighScore> highScores) {
        Json json = new Json();
        String scoresStr = json.toJson(highScores);
        return scoresStr;
	}
	
	@SuppressWarnings("unchecked")
	public static List<HighScore> deserialize(String scoresStr) {
		Json json = new Json();
		try {
			return json.fromJson(ArrayList.class, HighScore.class, scoresStr);
        } catch( Exception e ) {
            Gdx.app.error( HighScoreManager.class.getName(), 
            		"Unable to deserialize high scores", e );
        }
        return null;
	}
}
