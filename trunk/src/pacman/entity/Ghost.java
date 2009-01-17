package pacman.entity;

import gameframework.base.Overlappable;
import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Ghost extends GameMovable implements Drawable, GameEntity,
		Overlappable {
	protected static DrawableImage image = null;
	protected int spriteNumber = 0;
	protected int spriteType = 0;
	protected boolean movable = true;
	protected int afraidTimer = 0;
	protected int maxAfraidTimer = 0;
	protected boolean active = true;
	public static final int SPRITE_SIZE = 16;

	public boolean isAfraid() {
		return afraidTimer > 0;
	}

	public void setAfraid(int timer) {
		maxAfraidTimer = afraidTimer = timer;
	}

	public boolean isActive() {
		return active;
	}

	public void setAlive(boolean aliveState) {
		active = aliveState;
	}

	public Ghost(Canvas defaultCanvas) {
		if (image == null) {
			image = new DrawableImage("images/ghost.gif", defaultCanvas);
		}
	}

	public void draw(Graphics g) {
		Point tmp = getSpeedVector().getDir();
		movable = true;
		spriteType = 0;

		if (!isActive()) {
			spriteType = 12;
		} else if (afraidTimer > maxAfraidTimer / 2) {
			spriteType = 4;
		} else if (isAfraid()) {
			spriteType = 8;
		}

		if (tmp.getX() == -1) {
			spriteType += 1;
		} else if (tmp.getY() == 1) {
			spriteType += 3;
		} else if (tmp.getY() == -1) {
			spriteType += 2;
		}

		g.drawImage(image.getImage(), (int) getPosition().getX(),
				(int) getPosition().getY(), (int) getPosition().getX()
						+ SPRITE_SIZE,
				(int) getPosition().getY() + SPRITE_SIZE, spriteNumber * 32,
				spriteType * 32, (spriteNumber + 1) * 32,
				(spriteType + 1) * 32, null);
	}

	@Override
	public void oneStepMoveHandler() {
		if (movable) {
			spriteNumber++;
			spriteNumber = spriteNumber % 6;
			if (isAfraid()) {
				afraidTimer--;
			}
		}
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, SPRITE_SIZE, SPRITE_SIZE));
	}
}
