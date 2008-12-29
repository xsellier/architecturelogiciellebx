package bomberman.entity;

import static gameframework.game.ConstantValues.SPRITE_SIZE;
import static gameframework.game.ConstantValues.SPRITE_SIZE_X;
import static gameframework.game.ConstantValues.SPRITE_SIZE_Y;
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

public class Fire implements Drawable, GameEntity, Overlappable {
	protected static DrawableImage image0 = null;
	protected static DrawableImage image1 = null;
	protected static DrawableImage image2 = null;
	protected static DrawableImage image3 = null;

	protected static ArrayList<DrawableImage> imgList = null;
	
	protected Point position;

	private GameUniverse universe;
	private Timer timer;
	private int spriteNumber = -1;
	private int firePower;

	public Fire(Canvas defaultCanvas, Point pos, GameUniverse universe,
			int firePower) {
		if (image0 == null || image1 == null || image2 == null
				|| image3 == null) {
			image0 = new DrawableImage("images/Sprite/Fire/Center/Center0.gif",
					defaultCanvas);
			image1 = new DrawableImage("images/Sprite/Fire/Center/Center1.gif",
					defaultCanvas);
			image2 = new DrawableImage("images/Sprite/Fire/Center/Center2.gif",
					defaultCanvas);
			image3 = new DrawableImage("images/Sprite/Fire/Center/Center3.gif",
					defaultCanvas);
			imgList = new ArrayList<DrawableImage>(3);
			imgList.add(image0);
			imgList.add(image1);
			imgList.add(image2);
			imgList.add(image3);
		}
		position = pos;
		this.universe = universe;
		this.firePower = firePower;
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTaskExt(4), 0, 100);
	}

	private class TimerTaskExt extends TimerTask {
		int maxCycle;
		private int cycle = 0;

		public TimerTaskExt(int maxCycle) {
			this.maxCycle = maxCycle;
		}

		public void run() {
			spriteNumber++;
			spriteNumber = spriteNumber % 4;
			cycle++;
			if (cycle > maxCycle) {
				universe.removeGameEntity(Fire.this);
				fireExpansion();
				this.cancel();
			}
		}
	};

	private void fireExpansion() {
		System.out.println("position = (" + getPosition().x / SPRITE_SIZE_X + ", " + getPosition().y / SPRITE_SIZE_Y + ")");
	}
	
	public Point getPosition() {
		return position;
	}

	public void draw(Graphics g) {
		g.drawImage(imgList.get(spriteNumber).getImage(), (int) getPosition().getX(),
				(int) getPosition().getY(), SPRITE_SIZE_X, SPRITE_SIZE_Y, null);
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				SPRITE_SIZE, SPRITE_SIZE));
	}
}
