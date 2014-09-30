package gr.sullenart.games.fruitcatcher;

import gr.sullenart.games.fruitcatcher.HighScoreManager.HighScore;

import java.util.List;

public interface GameEventListener {

    void showAds(boolean show);
    
    void getHighScoreName();

	void showScores(List<HighScore> highScores);
}