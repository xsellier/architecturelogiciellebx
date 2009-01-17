package bomberman.entity;

import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.DrawableImage;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends AbstractEntity {
	protected static DrawableImage image0 = null;
	protected static DrawableImage image1 = null;
	protected static DrawableImage image2 = null;

	protected static ArrayList<DrawableImage> imgList = null;

	private Canvas canvas;
	protected Point position;
	private Timer timer;
	private int spriteNumber = -1;
	private int firePower;
	private Bomberman bm;
	private boolean isActive = true;

	public Bomb(Canvas defaultCanvas, Point pos, Bomberman bm, int firePower) {
		super(null);
		if (image0 == null || image1 == null || image2 == null) {
			image0 = new DrawableImage("images/Sprite/Bomb/Bomb0.gif",
					defaultCanvas);
			image1 = new DrawableImage("images/Sprite/Bomb/Bomb1.gif",
					defaultCanvas);
			image2 = new DrawableImage("images/Sprite/Bomb/Bomb2.gif",
					defaultCanvas);
			imgList = new ArrayList<DrawableImage>(3);
			imgList.add(image0);
			imgList.add(image1);
			imgList.add(image2);
		}

		canvas = defaultCanvas;
		position = pos;
		this.bm = bm;
		this.firePower = firePower;

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTaskExt(9), 0, 250);
	}

	private class TimerTaskExt extends TimerTask {
		int maxCycle;
		private int cycle = 0;

		public TimerTaskExt(int maxCycle) {
			this.maxCycle = maxCycle;
		}

		public void run() {
			spriteNumber++;
			spriteNumber = spriteNumber % 3;
			cycle++;
			if (cycle == maxCycle) {
				if (isActive) {
					exploseBomb();
				}
			}
		}
	};

	public void exploseBomb() {
		isActive = false;
		operation.removeGameEntity(Bomb.this);
		bm.updateNbBomb();
		operation.addGameEntity(new Fire(canvas, getPosition(), firePower,
				firePower, "Center"));
		timer.cancel();
	}

	public Point getPosition() {
		return position;
	}

	public void draw(Graphics g) {
		g.drawImage(imgList.get(spriteNumber).getImage(), (int) getPosition()
				.getX(), (int) getPosition().getY(), SPRITE_SIZE_X,
				SPRITE_SIZE_Y, null);
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				SPRITE_SIZE_X, SPRITE_SIZE_Y));
	}

	@Override
	public void oneStepMoveHandler() throws UnsupportedOperationException {

	}
}
