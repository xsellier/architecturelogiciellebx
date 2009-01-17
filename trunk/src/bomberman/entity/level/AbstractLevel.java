package bomberman.entity.level;

import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.Drawable;
import gameframework.game.GameEntity;
import gameframework.game.MoveBlocker;

import java.awt.Point;
import java.awt.Rectangle;

public abstract class AbstractLevel implements Drawable, GameEntity, MoveBlocker {

	protected Point position;

	public Point getPosition() {
		return position;
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(position.x, position.y, SPRITE_SIZE_X,
				SPRITE_SIZE_Y));
	}
}
