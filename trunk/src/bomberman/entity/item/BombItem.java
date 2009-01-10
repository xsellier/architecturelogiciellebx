package bomberman.entity.item;

import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.DrawableImage;
import gameframework.game.GameUniverse;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import bomberman.utility.LoadImage;

public class BombItem extends AbstractItem {
	protected static DrawableImage image = null;

	public BombItem(Canvas defaultCanvas, Point pos, GameUniverse universe) {
		super(defaultCanvas, universe);
		if (image == null) {
			image = LoadImage.getImgItem(defaultCanvas).get("Bomb");
		}
		position = pos;
	}

	public void draw(Graphics g) {
		if (isActive) {
			g.drawImage(image.getImage(), (int) getPosition().getX(),
					(int) getPosition().getY(), SPRITE_SIZE_X, SPRITE_SIZE_Y,
					null);
		}
	}

	public void burnItem() {
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
			spriteNumber = spriteNumber % 7;
			cycle++;
			if (cycle == maxCycle) {
				universe.removeGameEntity(BombItem.this);
				this.cancel();
			}
		}
	};
}
