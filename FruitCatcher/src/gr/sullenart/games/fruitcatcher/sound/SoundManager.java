package gr.sullenart.games.fruitcatcher.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

	private boolean isSoundOn;

	private Sound catchSound;

	private Sound missSound;
	
	private Sound tictacSound;

	private Sound dingdongSound;
	
	private Sound bellSound;
	
	public void setSoundOn(boolean isSoundOn) {
		this.isSoundOn = isSoundOn;
	}
	
	public SoundManager() {
		
	}
	
	public void load() {
        catchSound = Gdx.audio.newSound(Gdx.files.internal("catch.mp3"));
        missSound = Gdx.audio.newSound(Gdx.files.internal("miss.mp3"));
        tictacSound = Gdx.audio.newSound(Gdx.files.internal("tictac.mp3"));
        dingdongSound = Gdx.audio.newSound(Gdx.files.internal("dingdong.mp3"));
        bellSound = Gdx.audio.newSound(Gdx.files.internal("bell.mp3"));
	}
	
	public void dispose() {
        catchSound.dispose();
        dingdongSound.dispose();
        tictacSound.dispose();
        missSound.dispose();
        bellSound.dispose();
	}
	
	public void playCatchSound() {
		if (isSoundOn) {
			catchSound.play();
		}
	}

	public void playDingDongSound() {
		if (isSoundOn) {
			dingdongSound.play();
		}
	}
	
	public void playMissSound() {
		if (isSoundOn) {
			missSound.play();
		}
	}
	
	public void playTicTacSound() {
		if (isSoundOn) {
			tictacSound.play();
		}
	}	
	
	public void playBellSound() {
		if (isSoundOn) {
			bellSound.play();
		}
	}		
	
}
