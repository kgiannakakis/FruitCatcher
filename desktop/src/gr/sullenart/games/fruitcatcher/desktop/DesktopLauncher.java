package gr.sullenart.games.fruitcatcher.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import gr.sullenart.games.fruitcatcher.FruitCatcherGame;

public class DesktopLauncher {

	public static void main (String[] arg) {
        // Create two run configurations
        // 1. For texture packing. Pass 'texturepacker' as argument and use desktop/src
        //    as working directory
        // 2. For playing game with android/assets as working directory
        if (arg.length == 1 && arg[0].equals("texturepacker")) {
            String outdir = "assets";
            TexturePacker.Settings settings = new TexturePacker.Settings();
            settings.maxWidth = 1024;
            settings.maxHeight = 1024;
            TexturePacker.process(settings, "images", outdir, "game");
            TexturePacker.process(settings, "text-images", outdir, "text_images");
            TexturePacker.process(settings, "text-images-de", outdir, "text_images_de");
        }
        else {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.title = "FruitCatcher";
            config.width = 800;
            config.height = 480;
            new LwjglApplication(new FruitCatcherGame(null, "en"), config);
        }
	}
}
