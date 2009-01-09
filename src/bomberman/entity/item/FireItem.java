package bomberman.entity.item;

import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.DrawableImage;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;

import bomberman.utility.LoadImage;

public class FireItem extends AbstractItem {
	protected static DrawableImage image = null;

	public FireItem(Canvas defaultCanvas, Point pos) {
		super();
		if (image == null) {
			image = LoadImage.getImgItem(defaultCanvas).get("Fire");
		}
		position = pos;
	}

	public void draw(Graphics g) {
			g.drawImage(image.getImage(), (int) getPosition().getX(),
					(int) getPosition().getY(), SPRITE_SIZE_X, SPRITE_SIZE_Y, null);
	}

}
