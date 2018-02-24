package com.RobotechWar;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author ¿µÌìéª
 * @author English name Frank
 */
public class BoomSound {

	private File file;
	private AudioClip ac;

	/**
	 * @param args
	 */
	public void boomInput() {
		file = new File("music/boom2.wav");
		try {
			@SuppressWarnings("deprecation")
			URL url = file.toURL();
			ac = Applet.newAudioClip(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playBoom() {
		if (ac != null)
			ac.play();
	}

}
