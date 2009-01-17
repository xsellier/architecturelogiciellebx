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

public class Pacman extends GameMovable implements Drawable, GameEntity,
		Overlappable {
	public static final int SPRITE_SIZE = 16;
	protected static DrawableImage image = null;
	protected Canvas defaultCanvas;
	protected int spriteNumber = 0;
	protected int spriteType = 0;
	protected boolean movable = true;
	protected boolean vulnerable = false;
	protected int vulnerableTimer = 0;

	public void setInvulnerable(int timer) {
		vulnerableTimer = timer;
	}

	public boolean isVulnerable() {
		return (vulnerableTimer <= 0);
	}

	public Pacman(Canvas defaultCanvas) {
		if (image == null) {
			image = new DrawableImage("images/pac1.gif", defaultCanvas);
		}
	}

	public void draw(Graphics g) {
		Point tmp = getSpeedVector().getDir();
		movable = true;
		if (tmp.getX() == 1) {
			spriteType = (isVulnerable() ? 0 : 4);
		} else if (tmp.getX() == -1) {
			spriteType = (isVulnerable() ? 1 : 5);
		} else if (tmp.getY() == 1) {
			spriteType = (isVulnerable() ? 3 : 7);
		} else if (tmp.getY() == -1) {
			spriteType = (isVulnerable() ? 2 : 6);
		} else {
			spriteType = 9;
			spriteNumber = 0;
			movable = false;
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
			if (!isVulnerable()) {
				vulnerableTimer--;
			}
		}
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, SPRITE_SIZE, SPRITE_SIZE));
	}
}
