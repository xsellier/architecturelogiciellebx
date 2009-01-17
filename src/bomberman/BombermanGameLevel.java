package bomberman;

import static bomberman.game.ConstantValues.NB_COLUMNS;
import static bomberman.game.ConstantValues.NB_ROWS;
import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.MoveStrategyKeyboard;
import gameframework.game.CanvasDefaultImpl;
import gameframework.game.Game;
import gameframework.game.GameEntity;
import gameframework.game.GameLevelDefaultImpl;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverseDefaultImpl;
import gameframework.game.GameUniverseViewPortDefaultImpl;
import gameframework.game.MoveBlockerChecker;
import gameframework.game.MoveBlockerCheckerDefaultImpl;
import gameframework.game.OverlapProcessor;
import gameframework.game.OverlapProcessorDefaultImpl;
import gameframework.game.OverlapRuleApplier;

import java.awt.Canvas;
import java.awt.Point;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import bomberman.base.MoveStrategyKeyboardExt;
import bomberman.entity.Bomberman;
import bomberman.entity.Floor;
import bomberman.entity.item.BombItem;
import bomberman.entity.item.FireItem;
import bomberman.entity.level.BlocAround;
import bomberman.entity.level.SuperWall;
import bomberman.entity.level.Wall;
import bomberman.rule.BombermanMoveBlockers;
import bomberman.rule.BombermanOverlaps;

public class BombermanGameLevel extends GameLevelDefaultImpl implements
		GameEntityObserver {
	Canvas canvas;
	// 0 : Empty; 1 : Blocs Around; 2 : Super Walls; 3 : Walls; 4 : Bombs; 5 :
	// Flames;
	static int[][] tab = {
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1 },
			{ 1, 0, 2, 3, 2, 3, 0, 2, 3, 2, 2, 2, 0, 2, 2, 0, 2, 2, 2, 1 },
			{ 1, 5, 3, 3, 3, 3, 0, 3, 3, 3, 0, 3, 0, 2, 2, 0, 2, 2, 2, 1 },
			{ 1, 4, 2, 3, 2, 3, 0, 2, 3, 2, 2, 2, 0, 2, 2, 0, 2, 2, 2, 1 },
			{ 1, 5, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 4, 2, 3, 2, 3, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1 },
			{ 1, 0, 0, 3, 3, 3, 0, 2, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 0, 1 },
			{ 1, 0, 2, 3, 2, 3, 0, 3, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 1 },
			{ 1, 2, 0, 3, 3, 3, 0, 2, 0, 0, 0, 0, 0, 2, 2, 0, 2, 2, 2, 1 },
			{ 1, 2, 2, 3, 2, 3, 0, 2, 0, 0, 0, 0, 0, 2, 2, 0, 2, 2, 2, 1 },
			{ 1, 2, 0, 3, 3, 3, 0, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 1 },
			{ 1, 2, 2, 3, 2, 3, 0, 2, 2, 0, 2, 2, 2, 3, 3, 2, 2, 2, 0, 1 },
			{ 1, 2, 0, 3, 3, 3, 0, 2, 2, 0, 2, 4, 4, 4, 4, 4, 4, 2, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 4, 4, 4, 4, 4, 4, 2, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	@Override
	protected void init() {
		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();
		OverlapRuleApplier overlapRules = new BombermanOverlaps(new Point(
				1 * SPRITE_SIZE_X, 1 * SPRITE_SIZE_Y), life[0], score[0]);
		overlapProcessor.setOverlapRules(overlapRules);

		MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		moveBlockerChecker.setMoveBlockerRules(new BombermanMoveBlockers());

		universe = new GameUniverseDefaultImpl(moveBlockerChecker,
				overlapProcessor);
		overlapRules.setUniverse(universe);

		gameBoard = new GameUniverseViewPortDefaultImpl(canvas, universe);
		((GameUniverseViewPortDefaultImpl) gameBoard)
				.setBackground("images/Level/LevelSnow/Floor.gif");

		((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);

		// Universe loading
		for (int i = 0; i < NB_ROWS; ++i) {
			for (int j = 0; j < NB_COLUMNS; ++j) {

				if (tab[i][j] == 1) {
					universe.addGameEntity(new BlocAround(canvas, new Point(j
							* SPRITE_SIZE_X, i * SPRITE_SIZE_Y)));
				} else if (tab[i][j] == 2) {
					universe.addGameEntity(new SuperWall(canvas, new Point(j
							* SPRITE_SIZE_X, i * SPRITE_SIZE_Y)));
				} else {

					universe.addGameEntity(new Floor(canvas, new Point(j
							* SPRITE_SIZE_X, i * SPRITE_SIZE_Y)));

					if (tab[i][j] == 3) {
						universe.addGameEntity(new Wall(canvas, new Point(j
								* SPRITE_SIZE_X, i * SPRITE_SIZE_Y), universe));
					}
					if (tab[i][j] == 4) {
						universe.addGameEntity(new BombItem(canvas, new Point(j
								* SPRITE_SIZE_X, i * SPRITE_SIZE_Y), universe));
					}
					if (tab[i][j] == 5) {
						universe.addGameEntity(new FireItem(canvas, new Point(j
								* SPRITE_SIZE_X, i * SPRITE_SIZE_Y), universe));
					}
				}
			}
		}

		// Bomberman creation and universe loading
		/*
		 * Player choice : - Bomberman - Link - KitKat - PetitGros - PetitMaigre
		 */
		// Player 1
		Bomberman bm = new Bomberman(canvas, "Bomberman",
				(GameEntityObserver) this);
		GameMovableDriverDefaultImpl bmDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboard keyStrBm = new MoveStrategyKeyboardExt(bm, 1);
		bmDriver.setStrategy(keyStrBm);
		bmDriver.setmoveBlockerChecker(moveBlockerChecker);
		canvas.addKeyListener(keyStrBm);
		bm.setDriver(bmDriver);
		bm.setPosition(new Point(1 * SPRITE_SIZE_X, 1 * SPRITE_SIZE_Y));
		universe.addGameEntity(bm);

		// Player 2
		Bomberman link = new Bomberman(canvas, "Link",
				(GameEntityObserver) this);
		GameMovableDriverDefaultImpl linkDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboard keyStrLink = new MoveStrategyKeyboardExt(link, 2);
		linkDriver.setStrategy(keyStrLink);
		linkDriver.setmoveBlockerChecker(moveBlockerChecker);
		canvas.addKeyListener(keyStrLink);
		link.setDriver(linkDriver);
		link.setPosition(new Point(18 * SPRITE_SIZE_X, 14 * SPRITE_SIZE_Y));
		universe.addGameEntity(link);

	}

	public BombermanGameLevel(Game g) {
		super(g);
		canvas = g.getCanvas();
	}

	private boolean stopGameLoop;
	private static final int MINIMUM_DELAY_BETWEEN_GAME_CYCLES = 100;
	private boolean isAvailable = false;

	public void run() {
		stopGameLoop = false;
		// main game loop :
		long start;
		while (!stopGameLoop && !this.isInterrupted()) {
			start = new Date().getTime();
			gameBoard.paint();
			universe.allOneStepMoves();
			universe.processAllOverlaps();
			updateEntity();
			try {
				long sleepTime = MINIMUM_DELAY_BETWEEN_GAME_CYCLES
						- (new Date().getTime() - start);
				if (sleepTime > 0) {
					Thread.sleep(sleepTime);
				}
			} catch (Exception e) {
			}
		}
	}

	public void updateEntity() {
		if (isAvailable) {
			while (entities.size() != 0) {
				l.lock();
				String operation = entitiesOperation.poll();
				GameEntity entity = entities.poll();

				try {
					if (operation.compareTo("add") == 0) {
						System.out.println("add ");
						universe.addGameEntity(entity);

					} else if (operation.compareTo("r") == 0) {
						System.out.println("remove ");
						universe.removeGameEntity(entity);
					}
					isAvailable = false;

					System.out.println("Sortie size = " + entities.size());
				} finally {
					l.unlock();
				}
			}
		}
	}

	private Lock l = new ReentrantLock();
	private ArrayBlockingQueue<GameEntity> entities = new ArrayBlockingQueue<GameEntity>(
			50);

	private ArrayBlockingQueue<String> entitiesOperation = new ArrayBlockingQueue<String>(
			50);

	public void update(GameEntity entity, String operation) {
		isAvailable = true;
		entities.add(entity);
		entitiesOperation.add(operation);
		System.out.println("Entrée : size = " + entities.size());
	}

	@Override
	public void evolution() {
	}

}
