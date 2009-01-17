package bomberman.entity;

import static bomberman.game.ConstantValues.SPRITE_SIZE;
import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.DrawableImage;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import bomberman.utility.LoadImage;

public class Fire extends AbstractEntity {

	protected static HashMap<String, ArrayList<DrawableImage>> imgMap = null;

	protected Point position;

	private Timer timer;
	private int spriteNumber = -1;
	private int firePower;
	private int firePowerLeft;
	private Canvas defaultCanvas;
	private String fireType;

	private boolean isBurnable = false;

	protected static volatile HashMap<String, Boolean> fireExpansion = null;

	public Fire(Canvas defaultCanvas, Point pos, int firePower,
			int firePowerLeft, String fireType) {
		super(null);
		if (imgMap == null) {
			imgMap = LoadImage.loadImageFire(defaultCanvas);
		}

		position = pos;
		this.firePower = firePower;
		this.defaultCanvas = defaultCanvas;
		this.fireType = fireType;
		this.firePowerLeft = firePowerLeft;

		if (fireType.compareTo("Center") == 0) {
			fireExpansion = new HashMap<String, Boolean>();
			fireExpansion.put("Up", true);
			fireExpansion.put("Down", true);
			fireExpansion.put("Left", true);
			fireExpansion.put("Right", true);
		}

		if ((fireType.compareTo("Center") == 0)) {
			fireStartExpansion();
		}

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTaskExt(4), 0, 100);
	}

	private class TimerTaskExt extends TimerTask {
		int maxCycle;
		private int cycle = 0;

		public TimerTaskExt(int maxCycle) {
			this.maxCycle = maxCycle;
		}

		public void run() {
			spriteNumber++;
			spriteNumber = spriteNumber % 4;
			cycle++;
			if (cycle > maxCycle) {
				operation.removeGameEntity(Fire.this);
				this.cancel();
			}
		}
	};

	private void fireStartExpansion() {
		int x = getPosition().x;
		int y = getPosition().y;

		if (firePower == 1) {
			operation.addGameEntity(new Fire(defaultCanvas, new Point(x, y
					- SPRITE_SIZE_Y), 1, 0, "UpExt"));
			operation.addGameEntity(new Fire(defaultCanvas, new Point(x, y
					+ SPRITE_SIZE_Y), 1, 0, "DownExt"));
			operation.addGameEntity(new Fire(defaultCanvas, new Point(x
					- SPRITE_SIZE_X, y), 1, 0, "LeftExt"));
			operation.addGameEntity(new Fire(defaultCanvas, new Point(x
					+ SPRITE_SIZE_X, y), 1, 0, "RightExt"));
		} else {
			firePowerLeft -= 2;
			operation.addGameEntity(new Fire(defaultCanvas, new Point(x, y
					- SPRITE_SIZE_Y), firePower, firePowerLeft, "Up"));
			operation.addGameEntity(new Fire(defaultCanvas, new Point(x, y
					+ SPRITE_SIZE_Y), firePower, firePowerLeft, "Down"));
			operation.addGameEntity(new Fire(defaultCanvas, new Point(x
					- SPRITE_SIZE_X, y), firePower, firePowerLeft, "Left"));
			operation.addGameEntity(new Fire(defaultCanvas, new Point(x
					+ SPRITE_SIZE_X, y), firePower, firePowerLeft, "Right"));
		}
	}

	public void stopExpansion(String fireType) {
		fireExpansion.put(fireType, false);
	}

	private int doOneTime = 0;

	public void fireExpansion(String fireType, int firePowerLeft) {
		if (doOneTime++ < 1) {
			int x = getPosition().x;
			int y = getPosition().y;

			if (firePowerLeft > 0) {
				firePowerLeft--;
				if (fireType.compareTo("Up") == 0
						&& fireExpansion.get("Up").booleanValue()) {
					operation.addGameEntity(new Fire(defaultCanvas, new Point(
							x, y - SPRITE_SIZE_Y), firePower, firePowerLeft,
							"Up"));
				} else if (fireType.compareTo("Down") == 0
						&& fireExpansion.get("Down").booleanValue()) {
					System.out.println(fireType + "BIS="
							+ fireExpansion.get(fireType));
					operation.addGameEntity(new Fire(defaultCanvas, new Point(
							x, y + SPRITE_SIZE_X), firePower, firePowerLeft,
							"Down"));
				} else if (fireType.compareTo("Left") == 0
						&& fireExpansion.get("Left").booleanValue()) {
					operation.addGameEntity(new Fire(defaultCanvas, new Point(x
							- SPRITE_SIZE_X, y), firePower, firePowerLeft,
							"Left"));
				} else if (fireType.compareTo("Right") == 0
						&& fireExpansion.get("Right").booleanValue()) {
					operation.addGameEntity(new Fire(defaultCanvas, new Point(x
							+ SPRITE_SIZE_X, y), firePower, firePowerLeft,
							"Right"));
				}
			} else if (firePowerLeft == 0) {
				if (fireType.compareTo("Up") == 0) {
					operation.addGameEntity(new Fire(defaultCanvas, new Point(
							x, y - SPRITE_SIZE_Y), 1, 0, "UpExt"));
				} else if (fireType.compareTo("Down") == 0) {
					operation.addGameEntity(new Fire(defaultCanvas, new Point(
							x, y + SPRITE_SIZE_Y), 1, 0, "DownExt"));
				} else if (fireType.compareTo("Left") == 0) {
					operation.addGameEntity(new Fire(defaultCanvas, new Point(x
							- SPRITE_SIZE_X, y), 1, 0, "LeftExt"));
				} else if (fireType.compareTo("Right") == 0) {
					operation.addGameEntity(new Fire(defaultCanvas, new Point(x
							+ SPRITE_SIZE_X, y), 1, -1, "RightExt"));
				}
			}
		}
	}

	public String getType() {
		return fireType;
	}

	public int getPower() {
		return firePowerLeft;
	}

	public static HashMap<String, Boolean> getFireExpansion() {
		return fireExpansion;
	}

	public Point getPosition() {
		return position;
	}

	public void setBurnable() {
		isBurnable = true;
	}

	public void draw(Graphics g) {
		if (isBurnable) {
			g.drawImage(imgMap.get(fireType).get(spriteNumber).getImage(),
					(int) getPosition().getX(), (int) getPosition().getY(),
					SPRITE_SIZE_X, SPRITE_SIZE_Y, null);
		} else {
			g.drawImage(null, getPosition().x, getPosition().y, SPRITE_SIZE_X,
					SPRITE_SIZE_Y, null);
		}
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				SPRITE_SIZE, SPRITE_SIZE));
	}

	@Override
	public void oneStepMoveHandler() {
		// TODO Auto-generated method stub

	}

}
