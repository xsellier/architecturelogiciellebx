package bomberman.entity;

import gameframework.base.Overlappable;
import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.game.GameEntity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import static gameframework.game.ConstantValues.*;

public class SuperPacgum implements Drawable, GameEntity, Overlappable {
	protected static DrawableImage image = null;
	protected Point position;

	public SuperPacgum(Canvas defaultCanvas, Point pos) {
		if (image == null) {
			image = new DrawableImage("images/superpacgum.gif", defaultCanvas);
		}
		position = pos;
	}

	public Point getPosition() {
		return position;
	}

	public void draw(Graphics g) {
		g.drawImage(image.getImage(), (int) getPosition().getX(),
				(int) getPosition().getY(), SPRITE_SIZE, SPRITE_SIZE, null);

	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				SPRITE_SIZE, SPRITE_SIZE));
	}
}
