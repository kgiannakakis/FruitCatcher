package gr.sullenart.games.fruitcatcher;

import java.util.List;

import gr.sullenart.ads.AdsManager;
import gr.sullenart.games.fruitcatcher.HighScoreManager.HighScore;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ScoresActivity extends ListActivity  {

	private AdsManager adsManager = new AdsManager();

	private List<HighScore> scores;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

    	setContentView(R.layout.scores);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String scoresStr = bundle.getString("scores");
        scores = HighScoreManager.deserialize(scoresStr);

        final ListView listView = getListView();
        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        listView.setFocusable(false);

        LinearLayout layout = (LinearLayout)findViewById(R.id.banner_layout_scores);
        adsManager.addAdsView(this, layout);
    }

	   @Override
	    protected void onResume() {
	        super.onResume();
			setListAdapter(new ScoreItemAdapter(this, scores));
	    }
}
