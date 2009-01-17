package bomberman.entity.item;

import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameUniverse;

import java.awt.Canvas;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import bomberman.utility.LoadImage;

public abstract class AbstractItem implements Drawable, GameEntity, Overlappable {
	protected Point position;
	protected int spriteNumber = -1;
	protected GameUniverse universe;
	
	protected boolean isActive = true;
	
	protected static ArrayList<DrawableImage> imgBurnList = null;
	
	public AbstractItem(Canvas canvas, GameUniverse universe) {
		if (imgBurnList == null) {
			imgBurnList = LoadImage.getImgBurnItemList(canvas);
		}
		this.universe = universe;
	}
	
	public Point getPosition() {
		return position;
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				SPRITE_SIZE_X  / 5, SPRITE_SIZE_Y / 5));
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
			spriteNumber = spriteNumber % 7;
			cycle++;
			if (cycle == maxCycle) {
				removeItem();
				this.cancel();
			}
		}
	};
	
	private void removeItem() {
		universe.removeGameEntity(this);
	}

}
