/*
 * Uses code by Mario Zechner (contact@badlogicgames.com), 
 * 				Nathan Sweet (admin@esotericsoftware.com)
 * 
 */

package gr.sullenart.games.fruitcatcher;

import gr.sullenart.ads.AdsManager;
import gr.sullenart.games.fruitcatcher.HighScoreManager.HighScore;

import java.lang.ref.WeakReference;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication 
                implements GameEventListener {
    
	private final static int DIALOG_HIGH_SCORE_ID = 1;
	
	private final static int HIDE_ADS = 0;
	private final static int SHOW_ADS = 1;
    private final static int HIGH_SCORE_DIALOG = 2;	
    private final static int SHOW_HIGH_SCORES = 3;
	
    private AdsManager adsManager;

    private FruitCatcherGame fruitCatcherGame;

    private List<HighScore> highScores;
    
    private static class MessageHandler extends Handler {
    	private final WeakReference<MainActivity> mainActivityRef;
    	
    	MessageHandler(MainActivity mainActivity) {
    		mainActivityRef = new WeakReference<MainActivity>(mainActivity);
    	}
    	
		@SuppressWarnings("deprecation")
		@Override
        public void handleMessage(Message msg) {
			MainActivity mainActivity = null;
			if (mainActivityRef != null) {
				mainActivity = mainActivityRef.get();
			}
			
            switch(msg.what) {
                case SHOW_ADS:
                	if (mainActivity != null) {
                		mainActivity.adsManager.showAdd(true);
                	}
                    break;
                case HIDE_ADS:
                	if (mainActivity != null) {
                		mainActivity.adsManager.showAdd(true);
                	}
                    break;
                case HIGH_SCORE_DIALOG:
                	if (mainActivity != null) {
                		mainActivity.showDialog(MainActivity.DIALOG_HIGH_SCORE_ID);
                	}
                	break;
                case SHOW_HIGH_SCORES:
                	if (mainActivity != null) {
	                	Intent intentScores = new Intent(mainActivity, ScoresActivity.class);
	                	String scoresStr = HighScoreManager.serialize(mainActivity.highScores);
	                	intentScores.putExtra("scores", scoresStr);
	                	mainActivity.startActivity(intentScores); 
                	}
            }
        }    	
    }
    
	protected Handler handler;
	
    @Override public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout layout = new RelativeLayout(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        //cfg.useGL20 = true;
        cfg.useAccelerometer = true;
        cfg.useCompass = false;        
        
        String locale = getString(R.string.locale);
        
        fruitCatcherGame = new FruitCatcherGame(this, locale);
		View gameView = initializeForView(fruitCatcherGame, cfg);

        adsManager = new AdsManager();

        layout.addView(gameView);

        RelativeLayout.LayoutParams adParams = 
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        adsManager.addAdsView(this, layout, adParams);

        handler = new MessageHandler(this);

        adsManager.showInterstitial(this, "interstitial ad unit");
        adsManager.showAdd(true);

        setContentView(layout);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog;
        switch(id) {
        case DIALOG_HIGH_SCORE_ID:
        	dialog = createHighScoreDialog();
            break;
        default:
        	dialog = null;
        	break;
        }

        return dialog;
    }    
    
    private Dialog createHighScoreDialog() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.high_score_name_dialog);
        dialog.setTitle(R.string.app_name);

        Button okButton = (Button) dialog.findViewById(R.id.high_score_name_ok_button);

		SharedPreferences preferences =
            PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String oldPlayersName = preferences.getString("players_name", "");        
        
        final EditText playersNameEditText = 
        	(EditText) dialog.findViewById(R.id.players_name);
        playersNameEditText.setText(oldPlayersName);

        okButton.setOnClickListener(new OnClickListener() {
        			public void onClick(View v) {
        				String playerName = playersNameEditText.getText().toString();
        				finishHighScoreDialog(playerName);
                        dialog.dismiss();
        			}
        		});
        return dialog;	    	
    }
    
	private void finishHighScoreDialog(String username) {
        SharedPreferences preferences =
    		PreferenceManager.getDefaultSharedPreferences(getBaseContext());        
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("players_name", username);
        editor.commit(); 
        
        fruitCatcherGame.addHighScore(username);
	}    
    
    @Override
    public void showAds(boolean show) {
       handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
    }

	@Override
	public void getHighScoreName() {
		handler.sendEmptyMessage(HIGH_SCORE_DIALOG);
	}

	@Override
	public void showScores(List<HighScore> highScores) {
		this.highScores = highScores;
		handler.sendEmptyMessage(SHOW_HIGH_SCORES);
	}
    
}