package bomberman.entity;

import static gameframework.game.ConstantValues.SPRITE_SIZE_X;
import static gameframework.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;
import gameframework.game.GameUniverse;
import gameframework.utility.LoadImage;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

public class Bomberman extends GameMovable implements Drawable, GameEntity,
		Overlappable {
	protected Canvas defaultCanvas;
	protected int spriteNumber = -1;
	protected String spriteType;
	private int spriteMax;
	protected boolean movable = true;
	private GameUniverse universe;

	private static HashMap<String, ArrayList<DrawableImage>> imgMap = null;

	public Bomberman(Canvas defaultCanvas, GameUniverse universe) {
		this.defaultCanvas = defaultCanvas;
		this.universe = universe;

		if (imgMap == null) {
			imgMap = LoadImage.loadImagePlayer(defaultCanvas);
		}
	}

	public void draw(Graphics g) {
		Point tmp = getSpeedVector().getDir();
		movable = true;
		if (tmp.getX() == 1) {
			spriteType = "Right";
		} else if (tmp.getX() == -1) {
			spriteType = "Left";
		} else if (tmp.getY() == 1) {
			spriteType = "Down";
		} else if (tmp.getY() == -1) {
			spriteType = "Up";
		} else {
			spriteType = "Down";
			spriteNumber = 0;
			movable = false;
		}

		spriteMax = imgMap.get(spriteType).size();
		if (spriteNumber < spriteMax) {
			g.drawImage(imgMap.get(spriteType).get(spriteNumber).getImage(),
					(int) getPosition().getX(), (int) getPosition().getY(),
					SPRITE_SIZE_X, SPRITE_SIZE_Y, null);
		}

	}

	@Override
	public void oneStepMoveHandler() {
		if (movable) {
			spriteNumber++;
			spriteNumber = spriteNumber % spriteMax;
		}
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, SPRITE_SIZE_X, SPRITE_SIZE_Y));
	}

	public void putBomb() {
		int x = getPosition().x;
		int y = getPosition().y;

		double x1 = getPosition().getX();
		double x2 = getPosition().getY();
		
		System.out.println("x = " + x + " y = " + y);

		System.out.println("Ajout de bomb sur la carte à la position (" + x1
				/ SPRITE_SIZE_X + ", " + x2 / SPRITE_SIZE_Y + ")");

		double tmp1 = x1 / SPRITE_SIZE_X;
		double tmp2 = x2 / SPRITE_SIZE_Y;

		
		
		System.out.println("Ajout bomb position (" + tmp1 + ", " + tmp2 + ")");

		universe.addGameEntity(new Bomb(defaultCanvas, new Point(x, y),
				universe));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gameframework.game.GameEntityExt#canStopFire()
	 */
	public boolean canStopFire() {
		// TODO Auto-generated method stub
		return false;
	}
}
