package gr.sullenart.games.fruitcatcher;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class Main {
	public static void main(String[] args) {
		
		boolean copyImages = false;	
		
		if (copyImages) {
			Settings settings = new Settings();
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;
			TexturePacker2.process(settings, "images", "../FruitCatcher-android/assets", "game");
			TexturePacker2.process(settings, "text-images", "../FruitCatcher-android/assets", "text_images");
			TexturePacker2.process(settings, "text-images-de", "../FruitCatcher-android/assets", "text_images_de");
		}
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "FruitCatcher";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 480;
		
		new LwjglApplication(new FruitCatcherGame(null, "en"), cfg);
	}
}
