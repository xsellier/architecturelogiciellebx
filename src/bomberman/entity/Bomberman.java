package bomberman.entity;

import static gameframework.game.ConstantValues.SPRITE_SIZE_X;
import static gameframework.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

public class Bomberman extends GameMovable implements Drawable, GameEntity,
		Overlappable {
	protected Canvas defaultCanvas;
	protected int spriteNumber = 0;
	protected String spriteType;
	private int spriteMax;
	protected boolean movable = true;

	private static HashMap<String, ArrayList<DrawableImage>> imgMap = null;

	public Bomberman(Canvas defaultCanvas) {
		this.defaultCanvas = defaultCanvas;

		if (imgMap == null) {
			imgMap = new HashMap<String, ArrayList<DrawableImage>>();
			loadImage();
		}
	}

	private void loadImage() {
		ArrayList<DrawableImage> imgListLeft = loadImg(
				"images/Sprite/Player/Bomberman/", "Left", "gif", 10);
		ArrayList<DrawableImage> imgListRight = loadImg(
				"images/Sprite/Player/Bomberman/", "Right", "gif", 10);
		ArrayList<DrawableImage> imgListDown = loadImg(
				"images/Sprite/Player/Bomberman/", "Down", "gif", 8);
		ArrayList<DrawableImage> imgListUp = loadImg(
				"images/Sprite/Player/Bomberman/", "Up", "gif", 10);
		ArrayList<DrawableImage> imgListIdle = loadImg(
				"images/Sprite/Player/Bomberman/", "Idle", "gif", 6);

		imgMap.put("Left", imgListLeft);
		imgMap.put("Right", imgListRight);
		imgMap.put("Down", imgListDown);
		imgMap.put("Up", imgListUp);
		imgMap.put("Idle", imgListIdle);

	}

	private ArrayList<DrawableImage> loadImg(String path, String name,
			String extension, int number) {
		ArrayList<DrawableImage> result = new ArrayList<DrawableImage>(number);

		for (int i = 0; i < number; i++) {
			String s = path + name + i + "." + extension;
			DrawableImage tmp = new DrawableImage(s, defaultCanvas);
			result.add(tmp);
		}

		return result;
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
			spriteType = "Idle";
			spriteNumber = 0;
			movable = false;
		}

		spriteMax = imgMap.get(spriteType).size();
		
		g.drawImage(imgMap.get(spriteType).get(spriteNumber).getImage(),
				(int) getPosition().getX(), (int) getPosition().getY(),
				SPRITE_SIZE_X, SPRITE_SIZE_Y, null);

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
}
