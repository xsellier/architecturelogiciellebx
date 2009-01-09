package bomberman.entity.level;

import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.DrawableImage;
import gameframework.game.MoveBlocker;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;

import bomberman.utility.LoadImage;

public class SuperWall extends AbstractLevel implements MoveBlocker {
	protected static DrawableImage image = null;

	public SuperWall(Canvas defaultCanvas, Point position) {
		if (image == null) {
			image = LoadImage.getImgLevel(defaultCanvas).get("SuperWall");
		}
		this.position = position;
	}

	public void draw(Graphics g) {
		g.drawImage(image.getImage(), position.x, position.y, SPRITE_SIZE_X,
				SPRITE_SIZE_Y, null);
	}

}
