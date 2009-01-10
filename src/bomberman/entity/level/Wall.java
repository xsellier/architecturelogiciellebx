package bomberman.entity.level;

import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameUniverse;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import bomberman.utility.LoadImage;

public class Wall extends AbstractLevel implements Overlappable {
	protected static DrawableImage image = null;
	protected static ArrayList<DrawableImage> imgBurnWallList = null;

	private boolean isActive = true;
	private int spriteNumber = -1;
	protected GameUniverse universe;

	public Wall(Canvas defaultCanvas, Point position, GameUniverse universe) {
		if (image == null) {
			image = LoadImage.getImgLevel(defaultCanvas).get("Wall");
			imgBurnWallList = LoadImage.getImgBurnWallList(defaultCanvas);
		}
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
			System.out.println(spriteNumber);
			spriteNumber = spriteNumber % 5;
			cycle++;
			if (cycle == maxCycle) {
				universe.removeGameEntity(Wall.this);
				this.cancel();
			}
		}
	};

}
