package com.RobotechWar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * �̳�Thread��дrun()������ʹ���߳�ʹ����ӵ����Զ�öͬʱ���䲢�ƶ�
 * 
 * @author �����
 * @author English name Frank
 */
public class JThread extends Thread implements airplane {

	/*
	 * define variable
	 */
	int n = 0;// i��ball����-1��i1��bullet����-1��n��Ϊ�м��������ball��bullet��ʧ�ĸ���
	int anotherI = 0, anotherI1 = 0;// anotherI�Ǽ�¼����ball�ĸ����ģ�anotherI1�Ǽ�¼����bullet�ĸ�����
	int d = 9, b = 10, c = b;// d��bulletֱ����b��ball�ĸ�����c��bullet�ĸ���
	int ballD = 50;// ball�İ뾶
	int wide = 0;
	int number = 0;// number��¼��ը�ĸ���
	private int universeN, universeTurn;
	private int universeX = 1920;
	private String[] imageName = { "Image/meteorolite.gif",
			"Image/meteorolite1.gif", "Image/meteorolite2.gif",
			"Image/meteorolite3.gif", "Image/meteorolite4.gif" };// ����ͼƬ·�����ַ�������
	private Graphics graf;// �����ȡ�Ļ���
	Image background = new ImageIcon("Image/background.jpg").getImage();
	Image airplaneImage = new ImageIcon("Image/airplane.png").getImage();
	private Image boomImage[] = new Image[3];
	private Image universe[] = new Image[33];
	JPanel centerPanel;
	private drawCircle drawCircle = new drawCircle();

	BoomSound bs;

	// pressInformationָʾball�Ƿ��䣬pressInformation2ָʾbullet�Ƿ���
	// ballInformationʹִ��ball��ش��룬bulletInformationʹִ��bullet��ش���
	boolean pressInformation, pressInformation2, ballInformation,
			bulletInformation, shootInformation, backgroundInformation = true;

	MyArrayList<ball> ball = new MyArrayList<>();// ��������ball��Ϣ������
	MyArrayList<bullet> bullet = new MyArrayList<>();// ��������bullet��Ϣ������
	MyArrayList<boom> boom = new MyArrayList<>();// ��������boom��Ϣ������

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
	 * ��bulletInformation��ֵ����Ϊ�ж��Ƿ�����ball
	 */
	public void ball(boolean ballInformation) {
		this.ballInformation = ballInformation;
	}

	/*
	 * ��ball�е����ݸ�ֵ
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
	 * ��bulletInformation��ֵ
	 */
	public void bullet(boolean bulletInformation) {
		this.bulletInformation = bulletInformation;
	}

	/*
	 * ��bullet�е����ݸ�ֵ
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
				// ���ж��Ƿ�Ӧ���ó�ʼ��һ���µ�ball
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
			// �ж��Ƿ�Ӧ���ó�ʼ��һ���µ�bullet
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
			// �����ƶ��ķ���
			if (bulletInformation || ballInformation)
				move();
		}
	}

	/*
	 * move the ball and bullet
	 */
	public void move() {
		{
			sleep();// ����sleep����
			// �ж�ball��bullet�Ƿ�Ӧ����ʧ
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
			// ɾ������ӵ�
			if (shootInformation) {
				for (int i = 0; i < ball.getSize();) {
					if (ball.getElement(i).shootInformation) {
						boom.addElement(new boom());
						boom.getElement(this.number).img = boomImage;
						boom.getElement(this.number).x = ball.getElement(i).x;
						boom.getElement(this.number).y = ball.getElement(i).y;
						// TODO ����playBoom
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
			// ��ɵ���
			for (int i = 0; i < ball.getSize(); i++) {
				for (int n = i + 1; n < ball.getSize(); n++) {
					flick(i, n);
				}
			}
			// ���������
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
			// TODO ���Ʊ���
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
			// ���Ʒɻ�
			g.drawImage(airplaneImage, airplane[0], airplane[1], airplane[0]
					+ airplaneWidth, airplane[1] + airplaneHeight, 0, 0, width,
					height, null);
			// ���»����һ��ball��bullet
			if (ballInformation) {
				drawBall(g);
			}
			if (bulletInformation) {
				drawBullet(g);
			}
			// ���Ʊ�ըЧ��
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
			// ʹճ����С�����
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
			// ��bullet
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
			// ��ball
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
	 * ������Ϸ����
	 * 
	 * @param g
	 *            ����bufferedImage�л�ȡ�Ļ���
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
	 * ������ը�����Ķ���
	 */
	public void setBoomSound() {
		bs = new BoomSound();
		bs.boomInput();
	}

	/**
	 * ������ը����
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
 *            ���Ʊ�ը��ͼƬ����
 * @param number
 *            �����ÿһ֡��ը��ӳͼƬ���±�
 * @param retrunnumber
 *            ��¼��ը��ͼƬ���ֵĴ���
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
