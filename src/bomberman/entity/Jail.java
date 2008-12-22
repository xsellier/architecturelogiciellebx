package bomberman.entity;

import gameframework.base.Overlappable;
import gameframework.game.GameEntity;

import java.awt.Point;
import java.awt.Rectangle;

import static gameframework.game.ConstantValues.*;

public class Jail implements GameEntity, Overlappable {

	protected Point position;

	public Jail(Point pos) {
		position = pos;
	}

	public Point getPosition() {
		return position;
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				SPRITE_SIZE, SPRITE_SIZE));
	}
}
