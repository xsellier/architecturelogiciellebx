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

import bomberman.utility.LoadImage;

public abstract class AbstractItem implements Drawable, GameEntity, Overlappable {
	protected Point position;
	protected int spriteNumber = -1;
	protected GameUniverse universe;
	
	protected boolean isActive = true;
	
	protected static ArrayList<DrawableImage> imgBurnList = null;
	
	public AbstractItem(Canvas canvas, GameUniverse universe) {
		if (imgBurnList == null) {
			imgBurnList = LoadImage.getImgBurnList(canvas);
		}
		this.universe = universe;
	}
	
	public Point getPosition() {
		return position;
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				SPRITE_SIZE_X, SPRITE_SIZE_Y));
	}
	
	public abstract void burnItem();
}
