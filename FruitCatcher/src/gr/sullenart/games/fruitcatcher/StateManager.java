package gr.sullenart.games.fruitcatcher;

import gr.sullenart.games.fruitcatcher.models.GameState;
import gr.sullenart.games.fruitcatcher.screens.GameScreenState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class StateManager {

	private static final String STATE_DATA_FILE = "data/gamestate-v2.json";
	
	public static class StateBundle {
		public GameScreenState gameScreenState;
		public GameState gameState;
	}
	
	public void persist(GameScreenState gameScreenState, GameState gameState) {
        FileHandle stateDataFile = Gdx.files.local(STATE_DATA_FILE);
        
        StateBundle stBundle = new StateBundle();
        stBundle.gameScreenState = gameScreenState;
        stBundle.gameState = gameState;
        
        Json json = new Json();
        String state = json.toJson(stBundle);
        stateDataFile.writeString(state, false);
        
        //Gdx.app.log("GameScreen", state);
	}
	
	public StateBundle retrieveState() {
		FileHandle stateDataFile = Gdx.files.local(STATE_DATA_FILE);
		if( stateDataFile.exists() ) {
			Json json = new Json();
			try {
				String stateStr = stateDataFile.readString();
                return json.fromJson(StateBundle.class, stateStr );
            } catch( Exception e ) {
                Gdx.app.error( "StateManager", 
                		"Unable to parse existing game screen state data file", e );
            }
		}
		return null;
	}
	
}
