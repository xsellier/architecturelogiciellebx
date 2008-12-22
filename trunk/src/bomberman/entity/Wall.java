package bomberman.entity;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.game.GameEntity;
import gameframework.game.MoveBlocker;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import static gameframework.game.ConstantValues.*;

public class Wall implements Drawable, MoveBlocker, GameEntity {
	protected static DrawableImage image = null;
	int x, y;

	public Wall(Canvas defaultCanvas, int xx, int yy) {
		if (image == null) {
			image = new DrawableImage("images/wall.gif", defaultCanvas);
		}
		x = xx;
		y = yy;
	}

	public void draw(Graphics g) {
		g.drawImage(image.getImage(), x, y, SPRITE_SIZE, SPRITE_SIZE, null);
	}

	public Point getPos() {
		return (new Point(x, y));
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(x, y, SPRITE_SIZE, SPRITE_SIZE));
	}
}
