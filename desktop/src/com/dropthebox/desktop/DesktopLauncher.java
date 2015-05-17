package com.dropthebox.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dropTheBox.DropTheBox;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//screen ratio is 9:16
		config.width =360;
		config.height = 660;
		new LwjglApplication(new DropTheBox(), config);
	}
}
