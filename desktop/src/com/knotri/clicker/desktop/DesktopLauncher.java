package com.knotri.clicker.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.Array;
import com.knotri.clicker.MyGame;
import com.knotri.clicker.screen.TopScreen;

public class DesktopLauncher  implements MyGame.RequestHandler {
	private static DesktopLauncher application;
	public static void main (String[] arg) {
		if (application == null) {
			application = new DesktopLauncher();
		}
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 640;
		config.width = 480;

//        config.height = 960;
//        config.width = 540;
//
//        config.height = 320;
//        config.width = 240;
		//config.addIcon("../res/drawable-hdpi/ic_launcher.png", Files.FileType.Internal);
		new LwjglApplication(new MyGame(application), config);
	}


	@Override
	public void saveRecord(int score){

	}

	@Override
	public String getTopRecord(final Array<TopScreen.ItemRecord> recordArray){
		return null;
	}
}
