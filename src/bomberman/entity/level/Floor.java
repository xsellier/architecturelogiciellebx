package bomberman.entity.level;

import static bomberman.game.ConstantValues.SPRITE_SIZE;
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

public class Floor implements Drawable, GameEntity, Overlappable {
	protected static DrawableImage image = null;

	protected Point position;

	public Floor(Canvas defaultCanvas, Point pos) {
		if (image == null) {
			image = LoadImage.getImgLevel(defaultCanvas).get("Floor");
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
