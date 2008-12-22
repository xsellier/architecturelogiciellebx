package bomberman.entity;

import gameframework.base.Overlappable;
import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import static gameframework.game.ConstantValues.*;

public class Bomberman extends GameMovable implements Drawable, GameEntity,
		Overlappable {
	protected static DrawableImage image = null;
	protected Canvas defaultCanvas;
	protected int spriteNumber = 0;
	protected int spriteType = 0;
	protected boolean movable = true;
	protected boolean vulnerable = false;
	protected int vulnerableTimer = 0;
	private static int NB_SPRITE = 3;

	public void setInvulnerable(int timer) {
		vulnerableTimer = timer;
	}

	public boolean isVulnerable() {
		return (vulnerableTimer <= 0);
	}

	public Bomberman(Canvas defaultCanvas) {
		if (image == null) {
			image = new DrawableImage("images/bombermanblack.png",
					defaultCanvas);
		}
	}

	public void draw(Graphics g) {
		Point tmp = getSpeedVector().getDir();
		movable = true;
		if (tmp.getX() == 1) {
			spriteType = (isVulnerable() ? 5 : 4);
			spriteNumber += 6;
		} else if (tmp.getX() == -1) {
			spriteType = (isVulnerable() ? 4 : 5);
			spriteNumber += 6;
		} else if (tmp.getY() == 1) {
			spriteType = (isVulnerable() ? 4 : 7);
		} else if (tmp.getY() == -1) {
			spriteType = (isVulnerable() ? 5 : 6);
		} else {
			spriteType = 4;
			spriteNumber = 0;
			movable = false;
		}

		/* 3 : Magic number which set center the bomberman */
		g.drawImage(image.getImage(), 
				(int) getPosition().getX() + 3,
				(int) getPosition().getY() + 3,  
				(int) getPosition().getX() + SPRITE_SIZE_X + 3,
				(int) getPosition().getY() + SPRITE_SIZE_Y + 3, 
				spriteNumber * SPRITE_SIZE_X,
				spriteType * SPRITE_SIZE_Y, 
				(spriteNumber + 1) * SPRITE_SIZE_X,
				(spriteType + 1) * SPRITE_SIZE_Y, 
				null);

	}

	@Override
	public void oneStepMoveHandler() {
		if (movable) {
			spriteNumber++;
			spriteNumber = spriteNumber % NB_SPRITE;
			if (!isVulnerable()) {
				vulnerableTimer--;
			}
		}
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, SPRITE_SIZE_X, SPRITE_SIZE_Y));
	}
}
