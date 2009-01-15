package bomberman.entity;

import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;
import gameframework.game.GameUniverse;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import bomberman.utility.LoadImage;

public class Bomberman extends GameMovable implements Drawable, GameEntity,
		Overlappable {
	protected Canvas defaultCanvas;
	private int spriteNumber = -1;
	protected String spriteType;
	private int spriteMax;
	protected boolean movable = true;
	private GameUniverse universe;
	private boolean isDead = false;
	private Timer timer;
	private int firePower = 1;
	private int nbBomb = 1;
	private String playerType;

	private HashMap<String, ArrayList<DrawableImage>> imgMap = null;

	public Bomberman(Canvas defaultCanvas, GameUniverse universe, String playerType) {
		this.defaultCanvas = defaultCanvas;
		this.universe = universe;
		this.playerType = playerType;
		
		if (imgMap == null) {
			imgMap = LoadImage.loadImagePlayer(defaultCanvas, playerType);
		}
	}

	private class TimerTaskExt extends TimerTask {
		int maxCycle;
		private int cycle = 0;

		public TimerTaskExt(int maxCycle) {
			this.maxCycle = maxCycle;
			spriteNumber = -1;
		}

		public void run() {
			spriteNumber++;
			spriteNumber = spriteNumber % 4;
			System.out.println("spriteNumberDead = " + spriteNumber);
			cycle++;
			if (cycle > maxCycle) {
				universe.removeGameEntity(Bomberman.this);
				this.cancel();
			}
		}
	};

	public void draw(Graphics g) {
		Point tmp = getSpeedVector().getDir();
		movable = true;
		if (!isDead) {
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
				g.drawImage(
						imgMap.get(spriteType).get(spriteNumber).getImage(),
						(int) getPosition().getX(), (int) getPosition().getY(),
						SPRITE_SIZE_X, SPRITE_SIZE_Y, null);
			}
		} else {
			g.drawImage(imgMap.get("Die").get(spriteNumber).getImage(),
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
		return (new Rectangle(getPosition().x, getPosition().y, SPRITE_SIZE_X,
				SPRITE_SIZE_Y));
	}
	
	public void updateNbBomb() {
		nbBomb++;
	}

	public void updateFirePower() {
		firePower++;
	}
	
	public void putBomb() {
		if (nbBomb > 0) {
			nbBomb--;
			universe.addGameEntity(new Bomb(defaultCanvas, new Point(
					getPosition()), this, firePower, universe));
		}
	}

	public void die() {
		isDead = true;
		movable = false;
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTaskExt(4), 0, 300);
	}
}
