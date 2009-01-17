package bomberman.entity.item;

import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.DrawableImage;
import gameframework.game.GameUniverse;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;

import bomberman.utility.LoadImage;

public class FireItem extends AbstractItem {
	protected static DrawableImage image = null;

	public FireItem(Canvas defaultCanvas, Point pos, GameUniverse universe) {
		super(defaultCanvas, universe);
		if (image == null) {
			image = LoadImage.getImgItem(defaultCanvas).get("Fire");
		}
		position = pos;
	}

	public void draw(Graphics g) {
		if (isActive) {
			g.drawImage(image.getImage(), (int) getPosition().getX(),
					(int) getPosition().getY(), SPRITE_SIZE_X, SPRITE_SIZE_Y, null);
		} else {
			g.drawImage(imgBurnList.get(spriteNumber).getImage(), (int) getPosition()
					.getX(), (int) getPosition().getY(), SPRITE_SIZE_X,
					SPRITE_SIZE_Y, null);
		}
	}
}
