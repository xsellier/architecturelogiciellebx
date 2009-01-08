package bomberman.entity;

import static bomberman.game.ConstantValues.*;
import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class FireItem implements Drawable, GameEntity, Overlappable {
	protected static DrawableImage image = null;
	
	protected Point position;

	public FireItem(Canvas defaultCanvas, Point pos) {
		if (image == null) {
			image = new DrawableImage("images/Item/Flame.gif", defaultCanvas);
		}
		position = pos;
	}

	public Point getPosition() {
		return position;
	}

	public void draw(Graphics g) {
			g.drawImage(image.getImage(), (int) getPosition().getX(),
					(int) getPosition().getY(), SPRITE_SIZE_X, SPRITE_SIZE_Y, null);
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				SPRITE_SIZE, SPRITE_SIZE));
	}
}
