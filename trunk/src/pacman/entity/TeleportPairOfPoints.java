package pacman.entity;

import gameframework.base.Overlappable;
import gameframework.game.GameEntity;

import java.awt.Point;
import java.awt.Rectangle;

public class TeleportPairOfPoints implements GameEntity, Overlappable {
	public static final int SPRITE_SIZE = 16;

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