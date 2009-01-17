package bomberman.entity.level;

import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameUniverse;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import bomberman.entity.item.BombItem;
import bomberman.entity.item.FireItem;
import bomberman.utility.LoadImage;

public class Wall extends AbstractLevel implements Overlappable {
	protected static DrawableImage image = null;
	protected static ArrayList<DrawableImage> imgBurnWallList = null;

	private boolean isActive = true;
	private int spriteNumber = -1;
	protected GameUniverse universe;
	private Canvas canvas;

	public Wall(Canvas defaultCanvas, Point position, GameUniverse universe) {
		if (image == null) {
			image = LoadImage.getImgLevel(defaultCanvas).get("Wall");
			imgBurnWallList = LoadImage.getImgBurnWallList(defaultCanvas);
		}
		this.canvas = defaultCanvas;
		this.position = position;
		this.universe = universe;
	}

	public void draw(Graphics g) {
		if (isActive) {
			g.drawImage(image.getImage(), position.x, position.y,
					SPRITE_SIZE_X, SPRITE_SIZE_Y, null);
		} else {
			g.drawImage(imgBurnWallList.get(spriteNumber).getImage(),
					(int) getPosition().getX(), (int) getPosition().getY(),
					SPRITE_SIZE_X, SPRITE_SIZE_Y, null);
		}
	}

	public void burnWall() {
		isActive = false;
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTaskExt(4), 0, 250);
	}

	private class TimerTaskExt extends TimerTask {
		int maxCycle;
		private int cycle = 0;

		public TimerTaskExt(int maxCycle) {
			this.maxCycle = maxCycle;
		}

		public void run() {
			spriteNumber++;
			spriteNumber = spriteNumber % 5;
			cycle++;
			if (cycle == maxCycle) {
				universe.removeGameEntity(Wall.this);
				randSetItem();
				this.cancel();
			}
		}
	};
	
	private void randSetItem() {
		double rand = Math.random() * 100;
		
		if (rand < 15) {
			double randItem = Math.random() * 100;
			if (randItem < 50) {
				universe.addGameEntity(new BombItem(canvas, new Point(position.x, position.y), universe));
			} else {
				universe.addGameEntity(new FireItem(canvas, new Point(position.x, position.y), universe));
			}
		}
	}
}
