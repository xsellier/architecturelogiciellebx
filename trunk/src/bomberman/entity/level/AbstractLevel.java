package bomberman.entity.level;

import gameframework.base.Drawable;
import gameframework.game.GameEntity;

import java.awt.Point;
import java.awt.Rectangle;

import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;

public abstract class AbstractLevel implements Drawable, GameEntity {
	
	protected Point position;
	
	public Point getPosition() {
		return position;
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(position.x, position.y, SPRITE_SIZE_X, SPRITE_SIZE_Y));
	}
}
