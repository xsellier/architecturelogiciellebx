package bomberman.entity;

import static gameframework.game.ConstantValues.SPRITE_SIZE_X;
import static gameframework.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.game.GameEntity;
import gameframework.game.MoveBlocker;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class SuperWall implements Drawable, MoveBlocker, GameEntity {
	protected static DrawableImage image = null;
	int x, y;

	public SuperWall(Canvas defaultCanvas, int xx, int yy) {
		if (image == null) {
			image = new DrawableImage("images/superWall.gif", defaultCanvas);
		}
		x = xx;
		y = yy;
	}

	public void draw(Graphics g) {
		g.drawImage(image.getImage(), x, y, SPRITE_SIZE_X, SPRITE_SIZE_Y, null);
	}

	public Point getPos() {
		return (new Point(x, y));
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(x, y, SPRITE_SIZE_X, SPRITE_SIZE_Y));
	}
}
