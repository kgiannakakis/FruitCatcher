package gr.sullenart.games.fruitcatcher;

import gr.sullenart.games.fruitcatcher.HighScoreManager.HighScore;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScoreItemAdapter extends BaseAdapter {

    private final Context context;
    private final List<HighScore> scores;

    public ScoreItemAdapter(Context context, List<HighScore> scores) {
        this.context = context;
        this.scores = scores;
    }

	@Override
	public int getCount() {
		return this.scores.size();
	}

	@Override
	public Object getItem(int position) {
		return this.scores.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HighScore highScore = this.scores.get(position);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(
											Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout view = (LinearLayout)
				inflater.inflate(R.layout.score_item, null, false);


		TextView textView1 = ((TextView) view.findViewById(R.id.score_index));
		TextView textView2 = ((TextView) view.findViewById(R.id.score_name));
		TextView textView3 = ((TextView) view.findViewById(R.id.score_points));

		textView1.setText(String.format("%d.", position + 1));
		textView2.setText(getPlayersDisplayName(highScore.name));
		textView3.setText("" + highScore.score);

		return view;
	}

	private final int MAX_ALLOWED_NAME_LENGTH = 20;
	
	private String getPlayersDisplayName(String name) {
		if (name.length() > MAX_ALLOWED_NAME_LENGTH) {
			return name.substring(0, MAX_ALLOWED_NAME_LENGTH) + ".. ";
		}
		else {
			String delimiter = "";
			for(int i=name.length(); i<13; i++) {
				delimiter += " ";
			}
			return name + delimiter;
		}
	}

}
