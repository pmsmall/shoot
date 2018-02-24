package com.RobotechWar;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Gesture;

public class MyThread extends Thread {
	public listener listener;
	public Controller controller;

	public MyThread(listener listener, Controller controller) {
		this.listener = listener;
		this.controller = controller;
	}

	public void run() {
		while (true) {
			controller.addListener(listener);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
