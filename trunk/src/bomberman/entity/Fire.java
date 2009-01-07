package bomberman.entity;

import static gameframework.game.ConstantValues.SPRITE_SIZE;
import static gameframework.game.ConstantValues.SPRITE_SIZE_X;
import static gameframework.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameUniverse;
import gameframework.utility.LoadImage;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Fire implements Drawable, GameEntity, Overlappable {
	protected static DrawableImage image0 = null;
	protected static DrawableImage image1 = null;
	protected static DrawableImage image2 = null;
	protected static DrawableImage image3 = null;

	protected static HashMap<String, ArrayList<DrawableImage>> imgMap = null;

	protected Point position;

	private GameUniverse universe;
	private Timer timer;
	private int spriteNumber = -1;
	private int firePower;
	private Canvas defaultCanvas;
	private String fireType;

	private int test = -1;

	public Fire(Canvas defaultCanvas, Point pos, GameUniverse universe,
			int firePower, String fireType) {
		if (imgMap == null) {
			imgMap = LoadImage.loadImageFire(defaultCanvas);
		}

		position = pos;
		this.universe = universe;
		this.firePower = firePower;
		this.defaultCanvas = defaultCanvas;
		this.fireType = fireType;

		if ((fireType.compareTo("Center") == 0) && (test <= firePower)) {
			fireExpansion();
		}
		
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
				this.cancel();
			}
		}
	};

	private void fireExpansion() {
		System.out.println("position = (" + getPosition().x / SPRITE_SIZE_X
				+ ", " + getPosition().y / SPRITE_SIZE_Y + ")");

		int x = getPosition().x;
		int y = getPosition().y;

		universe.addGameEntity(new Fire(defaultCanvas, new Point(x, y - SPRITE_SIZE_Y),
				universe, 1, "UpExt"));
		universe.addGameEntity(new Fire(defaultCanvas, new Point(x, y + SPRITE_SIZE_Y),
				universe, 1, "DownExt"));
		universe.addGameEntity(new Fire(defaultCanvas, new Point(x - SPRITE_SIZE_X, y),
				universe, 1, "LeftExt"));
		universe.addGameEntity(new Fire(defaultCanvas, new Point(x + SPRITE_SIZE_X, y),
				universe, 1, "RightExt"));

	}

	public Point getPosition() {
		return position;
	}

	public void draw(Graphics g) {
		g.drawImage(imgMap.get(fireType).get(spriteNumber).getImage(),
				(int) getPosition().getX(), (int) getPosition().getY(),
				SPRITE_SIZE_X, SPRITE_SIZE_Y, null);
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				SPRITE_SIZE, SPRITE_SIZE));
	}
}
