package com.the8team.dragonboatrace.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.the8team.dragonboatrace.DragonBoatRace;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Dragon Boat Race";
		config.foregroundFPS = 60;
		config.height = 720;
		config.width = 1280;
		new LwjglApplication(new DragonBoatRace(), config);
	}
}
