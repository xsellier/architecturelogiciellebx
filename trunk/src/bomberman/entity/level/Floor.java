package bomberman.entity.level;

import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import bomberman.utility.LoadImage;

public class Floor implements Overlappable, Drawable, GameEntity {
	protected static DrawableImage image = null;

	protected Point position;
	
	public Floor(Canvas defaultCanvas, Point pos) {
		if (image == null) {
			image = LoadImage.getImgLevel(defaultCanvas).get("Floor");
		}
		position = pos;
	}

	public void draw(Graphics g) {
		g.drawImage(image.getImage(), (int) getPosition().getX(),
				(int) getPosition().getY(), SPRITE_SIZE_X, SPRITE_SIZE_Y, null);
	}

	public Point getPosition() {
		return position;
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(position.x, position.y, SPRITE_SIZE_X,
				SPRITE_SIZE_Y));
	}
}
