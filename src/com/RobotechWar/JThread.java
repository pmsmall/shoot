package com.RobotechWar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 继承Thread重写run()方法以使用线程使球和子弹可以多枚同时发射并移动
 * 
 * @author 康天楠
 * @author English name Frank
 */
public class JThread extends Thread implements airplane {

	/*
	 * define variable
	 */
	int n = 0;// i是ball个数-1，i1是bullet个数-1，n作为中间变量代表ball和bullet消失的个数
	int anotherI = 0, anotherI1 = 0;// anotherI是记录覆盖ball的个数的，anotherI1是记录覆盖bullet的个数的
	int d = 9, b = 10, c = b;// d是bullet直径，b是ball的个数，c是bullet的个数
	int ballD = 50;// ball的半径
	int wide = 0;
	int number = 0;// number记录爆炸的个数
	private int universeN, universeTurn;
	private int universeX = 1920;
	private String[] imageName = { "Image/meteorolite.gif",
			"Image/meteorolite1.gif", "Image/meteorolite2.gif",
			"Image/meteorolite3.gif", "Image/meteorolite4.gif" };// 储存图片路径的字符串数组
	private Graphics graf;// 储存获取的画笔
	Image background = new ImageIcon("Image/background.jpg").getImage();
	Image airplaneImage = new ImageIcon("Image/airplane.png").getImage();
	private Image boomImage[] = new Image[3];
	private Image universe[] = new Image[33];
	JPanel centerPanel;
	private drawCircle drawCircle = new drawCircle();

	BoomSound bs;

	// pressInformation指示ball是否发射，pressInformation2指示bullet是否发射
	// ballInformation使执行ball相关代码，bulletInformation使执行bullet相关代码
	boolean pressInformation, pressInformation2, ballInformation,
			bulletInformation, shootInformation, backgroundInformation = true;

	MyArrayList<ball> ball = new MyArrayList<>();// 建立储存ball信息的数组
	MyArrayList<bullet> bullet = new MyArrayList<>();// 建立储存bullet信息的数组
	MyArrayList<boom> boom = new MyArrayList<>();// 建立储存boom信息的数组

	BufferedImage bufferedImage;
	Graphics g;

	/*
	 * set graf
	 */
	public JThread(Graphics graf, JPanel centerPanel,drawCircle drawCircle) {
		// TODO Auto-generated constructor stub
		for (int i = 0; i < universe.length; i++) {
			universe[i] = new ImageIcon("Image/universe_" + (i + 1) + ".png")
					.getImage();
		}
		for (int i = 0; i < boomImage.length; i++) {
			boomImage[i] = new ImageIcon("Image/boom" + (i + 1) + ".png")
					.getImage();
		}
		this.graf = graf;
		this.centerPanel = centerPanel;
		this.drawCircle=drawCircle;
	}

	public void bufferedImage() {
		bufferedImage = new BufferedImage(centerPanel.getWidth(),
				centerPanel.getHeight(), 3);
		g = bufferedImage.createGraphics();
	}

	/*
	 * 给bulletInformation赋值，作为判断是否引发ball
	 */
	public void ball(boolean ballInformation) {
		this.ballInformation = ballInformation;
	}

	/*
	 * 给ball中的数据赋值
	 */
	public void ball(int i) {
		ball.getElement(i).x = (int) (Math.random() * (centerPanel.getWidth() - ballD));
		ball.getElement(i).y = (int) (Math.random() * (centerPanel.getHeight() - ballD)) / 4;
		ball.getElement(i).img = new ImageIcon(
				imageName[(int) (Math.random() * 5)]).getImage();
		graf.drawImage(ball.getElement(i).img, ball.getElement(i).x,
				ball.getElement(i).y, ball.getElement(i).x + ballD,
				ball.getElement(i).y + ballD, 0, 0, ballwidth, ballheight, null);
		ball.getElement(i).dx = (int) (Math.random() * 5 + 1);
		ball.getElement(i).dy = (int) (Math.random() * 5 + 1);

		if (Math.random() >= 0.5)
			ball.getElement(i).horInformation = true;
		else
			ball.getElement(i).horInformation = false;
		if (Math.random() >= 0.5)
			ball.getElement(i).verInformation = true;
		else
			ball.getElement(i).verInformation = false;
	}

	/*
	 * 给bulletInformation赋值
	 */
	public void bullet(boolean bulletInformation) {
		this.bulletInformation = bulletInformation;
	}

	/*
	 * 给bullet中的数据赋值
	 */
	public void bullet(int i1) {
		bullet.getElement(i1).x = airplane[0] + (airplaneWidth - d) / 2 - 2;
		bullet.getElement(i1).y = airplane[1] - d;
		graf.setColor(Color.RED);
		graf.fillOval(bullet.getElement(i1).x, bullet.getElement(i1).y, d, d);
	}

	/*
	 * overriding run()(non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
	//	drawCircle dc = new drawCircle();
		while (true) {
			this.centerPanel = drawCircle.getCenter();
			if (ballInformation) {
				// 先判断是否应该用初始化一个新的ball
				if (// getN()
				5 > ball.getSize())
					pressInformation = true;
				else
					pressInformation = false;
				if (pressInformation) {
					ball.addElement(new ball());
					ball(ball.getSize() - 1);
				}
			}
			// 判断是否应该用初始化一个新的bullet
			if (bulletInformation) {
				if (getI1() > bullet.getSize())
					pressInformation2 = true;
				else
					pressInformation2 = false;
				if (pressInformation2) {
					bullet.addElement(new bullet());
					bullet(bullet.getSize() - 1);
				}
			}
			// 调用移动的方法
			if (bulletInformation || ballInformation)
				move();
		}
	}

	/*
	 * move the ball and bullet
	 */
	public void move() {
		{
			sleep();// 调用sleep方法
			// 判断ball和bullet是否应该消失
			n = 0;
			for (int i = 0; i < ball.getSize(); i++) {
				for (int i1 = 0; i1 < bullet.getSize(); i1++) {
					int x = Math.abs((ball.getElement(i).x + ballD / 2)
							- (bullet.getElement(i1).x + d / 2));
					int y = Math.abs((ball.getElement(i).y + ballD / 2)
							- (bullet.getElement(i1).y + d / 2));
					int r = (ballD + d) / 2;
					if (x * x + y * y <= r * r) {
						ball.getElement(i).shootInformation = true;
						bullet.getElement(i1).shootInformation = true;
						shootInformation = true;
					}
				}
			}
			// 删除球和子弹
			if (shootInformation) {
				for (int i = 0; i < ball.getSize();) {
					if (ball.getElement(i).shootInformation) {
						boom.addElement(new boom());
						boom.getElement(this.number).img = boomImage;
						boom.getElement(this.number).x = ball.getElement(i).x;
						boom.getElement(this.number).y = ball.getElement(i).y;
						// TODO 调用playBoom
						playBoom();
						this.number++;
						ball.delete(i);
						listener.n--;
					} else
						i++;
				}
				for (int i = 0; i < bullet.getSize();) {
					if (bullet.getElement(i).shootInformation) {
						bullet.delete(i);
						listener.n1--;
					} else
						i++;
				}
				shootInformation = false;
			}
			// 完成弹球
			for (int i = 0; i < ball.getSize(); i++) {
				for (int n = i + 1; n < ball.getSize(); n++) {
					flick(i, n);
				}
			}
			// 处理背景相关
			int x0 = universeX + 120 - centerPanel.getHeight();
			if (x0 <= 800 && x0 >= 50) {
				universeTurn++;
				if (universeTurn % 5 == 0)
					universeN++;
			}
			if (x0 < 50)
				universeN = 0;
			universeX -= 5;
			if (universeN == 32)
				universeN--;

			if (universeX <= 0) {
				universeX = 1920;
				universeTurn = 0;
			}
			// TODO 绘制背景
			drawBackgrond(g);
			// g.drawImage(background, 0, 0, centerPanel.getWidth(),
			// centerPanel.getHeight(), 0, 100 + wide, 1920,
			// 1080 - 100 + wide, null);
			if (wide >= 100)
				backgroundInformation = false;
			if (wide <= 0)
				backgroundInformation = true;
			if (backgroundInformation)
				wide++;
			else
				wide--;
			// 绘制飞机
			g.drawImage(airplaneImage, airplane[0], airplane[1], airplane[0]
					+ airplaneWidth, airplane[1] + airplaneHeight, 0, 0, width,
					height, null);
			// 重新绘出下一个ball和bullet
			if (ballInformation) {
				drawBall(g);
			}
			if (bulletInformation) {
				drawBullet(g);
			}
			// 绘制爆炸效果
			// TODO Auto-generated constructor stub
			for (int i = 0; i < boom.getSize(); i++) {
				if (boom.getElement(i).trun == 2) {
					boom.getElement(i).trun = 0;
					boom.getElement(i).retrunnumber++;
					boom.getElement(i).number++;
					if (boom.getElement(i).number == 3) {
						boom.getElement(i).number = 0;
					}
				}
				g.drawImage(boom.getElement(i).img[boom.getElement(i).number],
						boom.getElement(i).x, boom.getElement(i).y,
						boom.getElement(i).x + boomSize[0],
						boom.getElement(i).y + boomSize[1], 0, 0, boomD, boomD,
						null);
				boom.getElement(i).trun++;
				boom.getElement(i).x -= 4;
				boom.getElement(i).y -= 4;
				boomSize[0] += 8;
				boomSize[1] += 8;
				if (boom.getElement(i).retrunnumber == 6) {
					boomSize[0] = boomsize;
					boomSize[1] = boomsize;
					boom.delete(i);
					if (i >= 1) {
						i--;
					}
					this.number--;
				}
			}
			graf.drawImage(bufferedImage, 0, 0, centerPanel.getWidth(),
					centerPanel.getHeight(), 0, 0, centerPanel.getWidth(),
					centerPanel.getHeight(), null);
		}
	}

	private void flick(int i, int n) {
		// TODO Auto-generated method stub
		int x = Math.abs(ball.getElement(i).x - ball.getElement(n).x);
		int y = Math.abs(ball.getElement(i).y - ball.getElement(n).y);
		if (x * x + y * y <= ballD * ballD) {
			// 使粘连的小球分离
			while (x * x + y * y < ballD * ballD) {
				if (ball.getElement(i).x > ball.getElement(n).x) {
					ball.getElement(i).x++;
					ball.getElement(n).x--;
				} else {
					ball.getElement(i).x--;
					ball.getElement(n).x++;
				}
				if (ball.getElement(i).y > ball.getElement(n).y) {
					ball.getElement(i).y++;
					ball.getElement(n).y--;
				} else {
					ball.getElement(i).y--;
					ball.getElement(n).y++;
				}
				x = Math.abs(ball.getElement(i).x - ball.getElement(n).x);
				y = Math.abs(ball.getElement(i).y - ball.getElement(n).y);
			}
			int temper = ball.getElement(i).dx;
			ball.getElement(i).dx = ball.getElement(n).dx;
			ball.getElement(n).dx = temper;
			temper = ball.getElement(i).dy;
			ball.getElement(i).dy = ball.getElement(n).dy;
			ball.getElement(n).dy = temper;
			boolean temperInformation = ball.getElement(i).horInformation;
			ball.getElement(i).horInformation = ball.getElement(n).horInformation;
			ball.getElement(n).horInformation = temperInformation;
			temperInformation = ball.getElement(i).verInformation;
			ball.getElement(i).verInformation = ball.getElement(n).verInformation;
			ball.getElement(n).verInformation = temperInformation;
		}
	}

	private void drawBullet(Graphics g) {
		// TODO Auto-generated method stub
		for (int i = 0; i < bullet.getSize(); i++) {
			// 绘bullet
			g.setColor(Color.RED);
			g.fillOval(bullet.getElement(i).x, bullet.getElement(i).y -= 5, d,
					d);
			g.fillOval(bullet.getElement(i).x - 2 * d,
					bullet.getElement(i).y + 10, d, d);
			g.fillOval(bullet.getElement(i).x + 2 * d,
					bullet.getElement(i).y + 10, d, d);
			// g.fillOval(bullet.getElement(i).x - 3 * d,
			// bullet.getElement(i).y + 15, d, d);
			// g.fillOval(bullet.getElement(i).x + 3 * d,
			// bullet.getElement(i).y + 15, d, d);
			if (bullet.getElement(i).y <= 0) {
				bullet.delete(i);
				listener.n1--;
			}
		}
	}

	private void drawBall(Graphics g) {
		// TODO Auto-generated method stub
		for (int i = 0; i < ball.getSize(); i++) {
			// 绘ball
			if (centerPanel.getWidth() <= ball.getElement(i).x
					|| (ball.getElement(i).x + ballD) <= 0
					|| centerPanel.getHeight() <= ball.getElement(i).y
					|| (ball.getElement(i).y + ballD) <= 0) {
				ball.delete(i);
				i--;
			} else {
				if (ball.getElement(i).horInformation
						&& ball.getElement(i).verInformation) {
					g.drawImage(ball.getElement(i).img,
							ball.getElement(i).x += ball.getElement(i).dx,
							ball.getElement(i).y += ball.getElement(i).dy,
							ball.getElement(i).x + ballD, ball.getElement(i).y
									+ ballD, 0, 0, ballwidth, ballheight, null);
				} else if (ball.getElement(i).horInformation
						&& !ball.getElement(i).verInformation) {
					g.drawImage(ball.getElement(i).img,
							ball.getElement(i).x += ball.getElement(i).dx,
							ball.getElement(i).y -= ball.getElement(i).dy,
							ball.getElement(i).x + ballD, ball.getElement(i).y
									+ ballD, 0, 0, ballwidth, ballheight, null);
				} else if (!ball.getElement(i).horInformation
						&& ball.getElement(i).verInformation) {
					g.drawImage(ball.getElement(i).img,
							ball.getElement(i).x -= ball.getElement(i).dx,
							ball.getElement(i).y += ball.getElement(i).dy,
							ball.getElement(i).x + ballD, ball.getElement(i).y
									+ ballD, 0, 0, ballwidth, ballheight, null);
				} else {
					g.drawImage(ball.getElement(i).img,
							ball.getElement(i).x -= ball.getElement(i).dx,
							ball.getElement(i).y -= ball.getElement(i).dy,
							ball.getElement(i).x + ballD, ball.getElement(i).y
									+ ballD, 0, 0, ballwidth, ballheight, null);
				}
			}
		}
	}

	/*
	 * the methord of sleeping
	 */
	public void sleep() {
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 绘制游戏背景
	 * 
	 * @param g
	 *            传入bufferedImage中获取的画笔
	 */
	public void drawBackgrond(Graphics g) {
		g.drawImage(universe[universeN], 0, 0, centerPanel.getWidth(),
				centerPanel.getHeight(), 0,
				universeX + 120 - centerPanel.getHeight(), 1080, universeX,
				null);
		g.drawImage(universe[universeN], 0, 0, centerPanel.getWidth(),
				centerPanel.getHeight(), 0, universeX + 1920 + 120
						- centerPanel.getHeight(), 1080, universeX + 1920, null);
	}

	/**
	 * the number of n in class listener
	 */
	public int getN() {
		return listener.n;

	}

	/**
	 * get the number of n1 in class listener
	 */
	public int getI1() {
		return listener.n1;
	}

	/**
	 * 创建爆炸声音的对象
	 */
	public void setBoomSound() {
		bs = new BoomSound();
		bs.boomInput();
	}

	/**
	 * 引发爆炸声音
	 */
	public void playBoom() {
		bs.playBoom();
	}

	public drawCircle getDrawCircle() {
		return drawCircle;
	}

	public void setDrawCircle(drawCircle drawCircle) {
		this.drawCircle = drawCircle;
	}

}

/**
 * fine a class to contain all the information of every ball
 * 
 * @param x
 *            which this class includes is the x coordinate of each ball
 * @param y
 *            which this class includes is the y coordinate of each ball
 * @param dx
 *            which this class includes is in charge of the change of the x
 *            coordinate of each ball
 * @param dy
 *            which this class includes is in charge of the change of the y
 *            coordinate of each ball
 * @param horInformation
 *            which this class includes records the horiz motion direction
 * @param verInformation
 *            which this class includes records the vertical motion direction
 * @param shootInformation
 *            which this class includes records that whether the ball is shooted
 * @param img
 *            which this class includes records the img of each ball
 */
class ball {
	int x, y, dx, dy;
	boolean horInformation, verInformation, shootInformation = false;
	Image img;
}

/**
 * define a class to contain all the information of every bullet
 * 
 * @param x
 *            which this class includes is the x coordinate of each bullet
 * @param y
 *            which this class includes is the y coordinate of each bullet
 * @param dx
 *            which this class includes is in charge of the change of the x
 *            coordinate of each bullet
 * @param dy
 *            which this class includes is in charge of the change of the y
 *            coordinate of each bullet
 * @param shootInformation
 *            which this class includes records that whether the bullet shoots
 * 
 * @param img
 *            which this class includes records the img of each bullet
 */
class bullet {
	int x, y;
	boolean shootInformation = false;
	Image img;
}

/**
 * define a class to contain all the information of every boom
 * 
 * @param x
 *            which this class includes is the x coordinate of each boom
 * @param y
 *            which this class includes is the y coordinate of each boom
 * @param turn
 *            控制爆炸的图片交替
 * @param number
 *            这个是每一帧爆炸对映图片的下标
 * @param retrunnumber
 *            记录爆炸的图片出现的次数
 * @param img
 *            which this class includes records the img of each bullet
 */
class boom {
	int x, y;
	int trun;
	int number;
	int retrunnumber;
	boolean showInformation = false;
	Image img[];
}
