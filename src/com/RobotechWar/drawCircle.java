package com.RobotechWar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.leapmotion.leap.Controller;

/**
 * @author 康天楠
 * @author English name Frank
 */
public class drawCircle implements airplane {
	/**
	 * define variable
	 */
	JFrame jf;// define Frame
	JPanel northPanel, centerSouthPanel, drawPanel;// define all Panels
	static Panel centerPanel;
	JButton startButton, keyToMouseButton, exitButton, minButton;// define all
																	// buttons
	listener lis;// use listener
	int frameX, frameY;

	Image background = new ImageIcon("Image/background.jpg").getImage();
	Image airplaneImage = new ImageIcon("Image/airplane.png").getImage();

	/**
	 * build the frame
	 */
	public void frame() {
		setframeXandY();
		jf = new JFrame();
		jf.setSize(frameX, frameY);
		jf.setBackground(Color.white);

		BorderLayout borderLayout = new BorderLayout();
		jf.setLayout(borderLayout);
		centerPanel();
		northPanel();

		jf.setUndecorated(true);

		jf.setVisible(true);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(3);

		lis = new listener(this);
		Controller controller = new Controller();

		addListener();
		setcursors();

		System.out.println(centerPanel.getHeight());
		lis.setGraf(centerPanel.getGraphics());
		lis.setCenterPanel(centerPanel);
		centerPanel.repaint();

		lis.setJThread();
		MyThread mt = new MyThread(lis, controller);
		mt.start();
	}

	public void addListener() {
		centerPanel.addMouseListener(lis);
		centerPanel.addKeyListener(lis);
		centerPanel.addMouseMotionListener(lis);
		startButton.addActionListener(lis);
		keyToMouseButton.addActionListener(lis);
		exitButton.addActionListener(lis);
		jf.addMouseMotionListener(lis);
		jf.addMouseListener(lis);
	}

	public void setcursors() {
		startButton.setCursor(Cursor.getPredefinedCursor(12));
		keyToMouseButton.setCursor(Cursor.getPredefinedCursor(12));
		exitButton.setCursor(Cursor.getPredefinedCursor(12));
		northPanel.setCursor(Cursor.getPredefinedCursor(13));
		MyWindow mywindow = new MyWindow(jf);
		Cursor c = mywindow.createCursor(
				new ImageIcon("Image\\mousepoint.gif").getImage(), "无");
		centerPanel.setCursor(c);
	}

	public void setframeXandY() {
		Dimension scream = Toolkit.getDefaultToolkit().getScreenSize();
		frameX = scream.width;
		frameY = scream.height;
	}

	/**
	 * set the button
	 */
	public void button() {
		startButton = new JButton("戳我");
		northPanel.add(startButton);
		startButton.setActionCommand("draw");
	}

	/**
	 * set the button2
	 */
	public void button2() {
		keyToMouseButton = new JButton("鼠标模式");
		northPanel.add(keyToMouseButton);
		keyToMouseButton.setActionCommand("mouse");
	}

	public void setExitbutton() {
		exitButton = new JButton("退出");
		northPanel.add(exitButton);
		exitButton.setActionCommand("exit");
	}

	/**
	 * set the northPanel
	 */
	public void northPanel() {
		northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(0, 50));
		northPanel.setBackground(Color.GREEN);
		button();
		button2();
		setExitbutton();

		jf.add(northPanel, BorderLayout.NORTH);
	}

	/**
	 * set the centerPanel
	 */
	public void centerPanel() {
		centerPanel = new Panel();
		centerPanel.setBackground(background);
		centerPanel.setAirplaneImage(airplaneImage);

		centerPanel.setBackground(Color.WHITE);
		BorderLayout borderLayout = new BorderLayout();
		centerPanel.setLayout(borderLayout);
		drawPanel();

		jf.add(centerPanel, BorderLayout.CENTER);
	}

	/**
	 * 初始化中间的panel中间的panel
	 */
	public void drawPanel() {
		drawPanel = new JPanel();
		drawPanel.addMouseListener(lis);
		drawPanel.addMouseMotionListener(lis);
		drawPanel.setBackground(Color.WHITE);

		centerPanel.add(drawPanel, BorderLayout.CENTER);
	}

	/**
	 * 获取centerPanel
	 */
	public JPanel getCenter() {
		return centerPanel;
	}

	public drawCircle getDrawCircle() {
		return this;
	}

	public JFrame getjf() {
		return jf;
	}
}

@SuppressWarnings("serial")
class Panel extends JPanel implements airplane {
	private Image background;
	private Image airplaneImage;
	int d = 9;

	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), 0,
				100, 1920, 1080 - 100, null);
		g.drawImage(airplaneImage, airplane[0], airplane[1], airplane[0]
				+ airplaneWidth, airplane[1] + airplaneHeight, 0, 0, width,
				height, null);
	}

	public void setBackground(Image background) {
		this.background = background;
	}

	public void setAirplaneImage(Image airplaneImage) {
		this.airplaneImage = airplaneImage;
	}
}

@SuppressWarnings("serial")
class MyWindow extends Window {

	public MyWindow(Frame owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}

	public Cursor createCursor(Image img, String name) {
		return this.getToolkit().createCustomCursor(img, new Point(12, 15),
				name);
	}
}