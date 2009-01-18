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

import bomberman.utility.LoadImage;

public class Bomb extends AbstractEntity {
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
		if (imgList == null) {
			imgList = LoadImage.getImgBomb(defaultCanvas);
		}

		canvas = defaultCanvas;
		position = pos;
		this.bm = bm;
		this.firePower = firePower;

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTaskExt(9, imgList.size()), 0, 250);
	}

	private class TimerTaskExt extends TimerTask {
		int maxCycle;
		private int cycle = 0;
		private int imgNumber;
		
		public TimerTaskExt(int maxCycle, int imgNumber) {
			this.maxCycle = maxCycle;
			this.imgNumber = imgNumber;
		}

		public void run() {
			spriteNumber++;
			spriteNumber = spriteNumber % imgNumber;
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
