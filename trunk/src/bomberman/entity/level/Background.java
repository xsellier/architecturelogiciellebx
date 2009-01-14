package bomberman.entity.level;

import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;

import bomberman.utility.LoadImage;

import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;

public class Background extends AbstractLevel implements Overlappable {

	protected static DrawableImage image = null;

	public Background(Canvas defaultCanvas, Point pos) {
		if (image == null) {
			image = LoadImage.getImgLevel(defaultCanvas).get("Floor");
		}
		position = pos;
	}

	public void draw(Graphics g) {
		g.drawImage(image.getImage(), (int) getPosition().getX(),
				(int) getPosition().getY(), SPRITE_SIZE_X, SPRITE_SIZE_Y, null);
	}

	public Object getClassAtPoint(Point p) {
		if (p == getPosition())
			return getClass();
		return null;
	}
}