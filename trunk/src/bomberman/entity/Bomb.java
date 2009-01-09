package bomberman.entity;

import static bomberman.game.ConstantValues.DEFAULT_FIRE_POWER;
import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameUniverse;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Bomb implements Drawable, GameEntity, Overlappable {
	protected static DrawableImage image0 = null;
	protected static DrawableImage image1 = null;
	protected static DrawableImage image2 = null;

	protected static ArrayList<DrawableImage> imgList = null;

	private Canvas canvas;
	protected Point position;
	private Timer timer;
	private int spriteNumber = -1;
	private GameUniverse universe;

	public Bomb(Canvas defaultCanvas, Point pos, GameUniverse universe) {
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
		this.universe = universe;
		
		universe.addGameEntity(this);
		
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
//				universe.removeGameEntity(Bomb.this);
//				universe.addGameEntity(new Fire(canvas, getPosition(),
//						universe, DEFAULT_FIRE_POWER, "Center"));
				this.cancel();
			}
		}
	};

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
				SPRITE_SIZE_X / 2 * 3, SPRITE_SIZE_Y / 2 * 3));
	}
}
