package com.RobotechWar;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;

public class listener extends Listener implements ActionListener,
		MouseListener, KeyListener, airplane, MouseMotionListener {
	private Graphics graf;
	static int n = 0, n1 = 0, jfX, jfY, jfX0, jfY0;

	@SuppressWarnings("unused")
	private boolean startInformation, mouseInformation, keyInformation;
	private JPanel centerPanel;
	private JThread jthread;
	private drawCircle dc;
	private Frame frame;
	private double handX0, handY0, handZ0, handX, handY, handZ;
	private drawCircle drawCircle = new drawCircle();

	public listener(drawCircle drawCircle) {
		this.drawCircle = drawCircle;
		setJThread();
	}

	public void setJThread() {
		dc = new drawCircle();
		setCenterPanel();
		jthread = new JThread(graf, centerPanel, drawCircle);
		jthread.bufferedImage();
		jthread.setBoomSound();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		centerPanel.requestFocus();
		JButton button = (JButton) e.getSource();
		if (button.getActionCommand().equals("draw")) {
			if (n == 0 && jthread != null) {
				if (!startInformation) {
					jthread.start();
					startInformation = true;
				}
				jthread.ball(true);
			}
			n++;
		}
		if (button.getActionCommand().equals("mouse")) {
			mouseInformation = true;
			button.setText("键盘模式");
			button.setActionCommand("key");
		} else if (button.getActionCommand().equals("key")) {
			mouseInformation = false;
			button.setText("鼠标模式");
			button.setActionCommand("mouse");
		} else if (button.getActionCommand().equals("exit")) {
			System.exit(0);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource().equals(drawCircle.getjf())) {
			jfX = e.getX();
			jfY = e.getY();
		}
		setCenterPanel();
		System.out.println(e.getX());
		System.out.println(e.getY());
		if (e.getX() - airplaneWidth / 2 >= 0
				&& e.getX() + airplaneWidth / 2 <= centerPanel.getWidth()
				&& e.getY() + airplaneHeight / 2 <= centerPanel.getHeight()
				&& e.getY() - airplaneHeight / 2 >= 0) {
			airplane[0] = e.getX() - airplaneWidth / 2;
			airplane[1] = e.getY() - airplaneHeight / 2;
		}
		int x = e.getX(), y = e.getY();
		if (x >= airplane[0] && y >= airplane[1] && x <= airplane[0] + 44
				&& y <= airplane[1] + 46) {
			if (n1 == 0) {
				jthread.bullet(true);
			}
			n1++;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	/*
	 * set graf
	 */
	public void setCenterPanel(JPanel centerPanel) {
		this.centerPanel = centerPanel;
	}

	public void setGraf(Graphics graf) {
		this.graf = graf;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		setCenterPanel();
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			if (airplane[0] >= 3)
				airplane[0] -= 3;
			break;
		case KeyEvent.VK_RIGHT:
			if (airplane[0] <= centerPanel.getWidth() - airplaneWidth)
				airplane[0] += 3;
			break;
		case KeyEvent.VK_UP:
			if (airplane[1] >= 3)
				airplane[1] -= 3;
			break;
		case KeyEvent.VK_DOWN:
			if (airplane[1] <= centerPanel.getHeight() - airplaneHeight - 2)
				airplane[1] += 3;
			break;
		case KeyEvent.VK_SPACE:
			// TODO bullet
			if (n1 == 0) {
				if (!startInformation) {
					jthread.start();
					startInformation = true;
					jthread.setBoomSound();
				}
				jthread.bullet(true);
			}
			n1++;
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated meth
		if (e.getSource().equals(drawCircle.getjf())) {
			jfX0 = e.getX();
			jfY0 = e.getY();
			int x = drawCircle.getjf().getLocation().x, y = drawCircle.getjf()
					.getLocation().y;
			drawCircle.getjf().setLocation(x + jfX0 - jfX, y + jfY0 - jfY);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (mouseInformation) {
			if (e.getX() - airplaneWidth / 2 >= 0
					&& e.getX() + airplaneWidth / 2 <= centerPanel.getWidth()
					&& e.getY() + airplaneHeight / 2 <= centerPanel.getHeight()
					&& e.getY() - airplaneHeight / 2 >= 0) {
				airplane[0] = e.getX() - airplaneWidth / 2;
				airplane[1] = e.getY() - airplaneHeight / 2;
			}
		}
	}

	/**
	 * 设置centerPanel
	 */
	public void setCenterPanel() {
		this.centerPanel = dc.getCenter();
	}

	/**
	 * 设置Splash Screen
	 */
	public void Splash() {
		SplashScreen splash = SplashScreen.getSplashScreen();
		Graphics2D g = (Graphics2D) splash.createGraphics();
		g.drawOval(0, 0, 500, 500);
	}

	public void onInit(Controller controller) {
		System.out.println("In");
	}

	public void onFrame(Controller controller) {
		onFocusGained(controller);
		frame = controller.frame();
		for (Hand hand : frame.hands()) {
			// System.out.println(hand.palmPosition().getX());
			if (handX0 == 0 && handX == 0) {
				handX0 = hand.palmPosition().getX();
			} else if (handX0 == handX) {
				handX0 = handX = 0;
			} else {
				handX = hand.palmPosition().getX();
				// System.out.println(handX - handX0);
				if (Math.abs(handX - handX0) < 0.5)
					handX = handX0 = 0;
				else if (handX - handX0 > 0) {
					// System.out.println(handX - handX0);
					airplane[0] = (int) Math.min(airplane[0] + (handX - handX0)
							* 20, centerPanel.getWidth() - airplaneWidth);
				} else
					airplane[0] = (int) Math.max(airplane[0] - (handX0 - handX)
							* 20, 0);
				handX0 = handX;

			}
			if (handY0 == 0 && handY == 0) {
				handY0 = hand.palmPosition().getY();
			} else if (handY0 == handY) {
				handY0 = handY = 0;
			} else {
				handY = hand.palmPosition().getY();
				if (Math.abs(handX - handX0) < 0.5)
					handX = handX0 = 0;
				else if (handY - handY0 < 0) {
					airplane[1] = (int) Math.min(
							airplane[1] + Math.abs(handY - handY0) * 20,
							centerPanel.getHeight() - airplaneHeight - 2);
				} else
					airplane[1] = (int) Math.max(
							airplane[1] - Math.abs(handY0 - handY) * 20, 0);
				handY0 = handY;
			}
			// String handType = hand.isLeft() ? "Left hand" : "Right hand";
			// System.out.println("  " + handType + ", id: " + hand.id()
			// + ", palm position: " + hand.palmPosition());

			// Get the hand's normal vector and direction
			// Vector normal = hand.palmNormal();
			// Vector direction = hand.direction();
			// System.out.println(hand.direction().getX());

			// Calculate the hand's pitch, roll, and yaw angles
			// System.out.println("  pitch: " +
			// Math.toDegrees(direction.pitch()) + " degrees, "
			// + "roll: " + Math.toDegrees(normal.roll()) + " degrees, "
			// + "yaw: " + Math.toDegrees(direction.yaw()) + " degrees");

			// Get arm bone
			// Arm arm = hand.arm();
			// System.out.println("  Arm direction: " + arm.direction()
			// + ", wrist position: " + arm.wristPosition()
			// + ", elbow position: " + arm.elbowPosition());

			// // Get fingers
			// for (Finger finger : hand.fingers()) {
			// System.out.println("    " + finger.type() + ", id: " +
			// finger.id()
			// + ", length: " + finger.length()
			// + "mm, width: " + finger.width() + "mm");
			//
			// //Get Bones
			// for(Bone.Type boneType : Bone.Type.values()) {
			// Bone bone = finger.bone(boneType);
			// System.out.println("      " + bone.type()
			// + " bone, start: " + bone.prevJoint()
			// + ", end: " + bone.nextJoint()
			// + ", direction: " + bone.direction());
			// }
			// }

			controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
			GestureList gestures = frame.gestures();
			for (int i = 0; i < gestures.count(); i++) {
				Gesture gesture = gestures.get(i);

				switch (gesture.type()) {
				case TYPE_CIRCLE:
					CircleGesture circle = new CircleGesture(gesture);

					// Calculate clock direction using the angle between circle
					// normal and pointable
					String clockwiseness;
					if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI / 2) {
						// Clockwise if angle is less than 90 degrees
						clockwiseness = "clockwise";
					} else {
						clockwiseness = "counterclockwise";
					}

					// Calculate angle swept since last frame
					double sweptAngle = 0;
					if (circle.state() != State.STATE_START) {
						CircleGesture previousUpdate = new CircleGesture(
								controller.frame(1).gesture(circle.id()));
						sweptAngle = (circle.progress() - previousUpdate
								.progress()) * 2 * Math.PI;
					}
					break;
				case TYPE_SWIPE:
					SwipeGesture swipe = new SwipeGesture(gesture);
					break;
				case TYPE_SCREEN_TAP:
					ScreenTapGesture screenTap = new ScreenTapGesture(gesture);
					break;
				case TYPE_KEY_TAP:
					KeyTapGesture keyTap = new KeyTapGesture(gesture);
					if (n1 == 0) {
						if (!startInformation) {
							jthread.start();
							startInformation = true;
							jthread.setBoomSound();
						}
						jthread.bullet(true);
					}
					if (startInformation)
						n1++;
					break;
				default:
					break;
				}
			}

			// if (!frame.hands().isEmpty() || !gestures.isEmpty()) {
			// System.out.println();
			// }
		}
	}
}
