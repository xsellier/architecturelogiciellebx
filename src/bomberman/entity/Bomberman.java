package bomberman.entity;

import static bomberman.game.ConstantValues.*;
import gameframework.base.DrawableImage;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import bomberman.GameEntityObserver;
import bomberman.utility.LoadImage;

public class Bomberman extends AbstractEntity {
	protected Canvas defaultCanvas;
	private int spriteNumber = -1;
	protected String spriteType;
	private int spriteMax;
	protected boolean movable = true;
	private boolean isDead = false;
	private Timer timer;
	private int firePower = DEFAULT_FIRE_POWER;
	private int nbBomb = DEFAULT_NB_BOMB;

	private HashMap<String, ArrayList<DrawableImage>> imgMap = null;

	public Bomberman(Canvas defaultCanvas, String playerType,
			GameEntityObserver o) {
		super(o);
		this.defaultCanvas = defaultCanvas;

		if (imgMap == null) {
			imgMap = LoadImage.getImgPlayer(defaultCanvas, playerType);
		}
	}

	private class TimerTaskExt extends TimerTask {
		int maxCycle;
		private int cycle = 0;
		private int imgNumber;
		
		public TimerTaskExt(int maxCycle, int imgNumber) {
			this.maxCycle = maxCycle;
			spriteNumber = -1;
			this.imgNumber = imgNumber;
		}

		public void run() {
			spriteNumber++;
			spriteNumber = spriteNumber % imgNumber;
			cycle++;
			if (cycle > maxCycle) {
				operation.removeGameEntity(Bomberman.this);
				this.cancel();
			}
		}
	};

	public void draw(Graphics g) {
		Point tmp = getSpeedVector().getDir();
		if (!isDead) {
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
		if (nbBomb > 0 && !isDead) {
			nbBomb--;
			operation.addGameEntity(new Bomb(defaultCanvas, new Point(
					getPosition()), this, firePower));
		}
	}

	public void die() {
		isDead = true;
		movable = false;
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTaskExt(1, imgMap.get("Die").size()), 0, 700);
	}
	
	public EntityOperation getOperation() {
		return operation;
	}
}
