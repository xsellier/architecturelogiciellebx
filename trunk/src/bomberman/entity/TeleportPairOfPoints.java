package bomberman.entity;

import gameframework.base.Overlappable;
import gameframework.game.GameEntity;

import java.awt.Point;
import java.awt.Rectangle;

import static gameframework.game.ConstantValues.*;

public class TeleportPairOfPoints implements GameEntity, Overlappable {

	protected Point position;
	protected Point destination;

	public TeleportPairOfPoints(Point pos, Point destination) {
		position = pos;
		this.destination = destination;
	}

	public Point getDestination() {
		return destination;
	}

	public Point getPosition() {
		return position;
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				SPRITE_SIZE, SPRITE_SIZE));
	}
}