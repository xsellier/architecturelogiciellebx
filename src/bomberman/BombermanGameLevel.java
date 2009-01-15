package bomberman;

import static bomberman.game.ConstantValues.NB_COLUMNS;
import static bomberman.game.ConstantValues.NB_ROWS;
import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.game.CanvasDefaultImpl;
import gameframework.game.Game;
import gameframework.game.GameLevelDefaultImpl;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverseDefaultImpl;
import gameframework.game.MoveBlockerChecker;
import gameframework.game.MoveBlockerCheckerDefaultImpl;
import gameframework.game.OverlapProcessor;
import gameframework.game.OverlapRuleApplier;

import java.awt.Canvas;
import java.awt.Point;

import bomberman.base.MoveStrategyKeyboardExt;
import bomberman.base.MoveStrategyKeyboardExtP2;
import bomberman.entity.Bomberman;
import bomberman.entity.item.BombItem;
import bomberman.entity.item.FireItem;
import bomberman.entity.level.BlocAround;
import bomberman.entity.level.Floor;
import bomberman.entity.level.SuperWall;
import bomberman.entity.level.Wall;
import bomberman.game.GameUniverseViewPortDefaultImplExt;
import bomberman.game.OverlapProcessorDefaultImplExt;
import bomberman.rule.BombermanMoveBlockers;
import bomberman.rule.BombermanOverlaps;

public class BombermanGameLevel extends GameLevelDefaultImpl {
	Canvas canvas;
	// 0 : Empty; 1 : Blocs Around; 2 : Super Walls; 3 : Walls; 4 : Bombs; 5 :
	// Flames;
	static int[][] tab = {
		{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		{ 1, 0, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1 },
		{ 1, 0, 2, 3, 2, 3, 0, 2, 3, 2, 2, 2, 0, 2, 2, 0, 2, 2, 2, 1 },
		{ 1, 3, 3, 3, 3, 3, 0, 3, 3, 3, 0, 3, 0, 2, 2, 0, 2, 2, 2, 1 },
		{ 1, 5, 2, 3, 2, 3, 0, 2, 3, 2, 2, 2, 0, 2, 2, 0, 2, 2, 2, 1 },
		{ 1, 5, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 5, 2, 3, 2, 3, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1 },
		{ 1, 5, 0, 3, 3, 3, 0, 2, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 0, 1 },
		{ 1, 5, 2, 3, 2, 3, 0, 3, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 1 },
		{ 1, 2, 0, 3, 3, 3, 0, 2, 0, 0, 0, 0, 0, 2, 2, 5, 2, 2, 2, 1 },
		{ 1, 2, 2, 3, 2, 3, 0, 2, 0, 0, 0, 0, 0, 2, 2, 5, 2, 2, 2, 1 },
		{ 1, 2, 0, 3, 3, 3, 0, 2, 2, 2, 2, 2, 2, 2, 5, 5, 5, 5, 5, 1 },
		{ 1, 2, 2, 3, 2, 3, 0, 2, 2, 5, 2, 2, 2, 3, 3, 2, 2, 2, 5, 1 },
		{ 1, 2, 0, 3, 3, 3, 0, 2, 2, 5, 2, 4, 4, 4, 4, 4, 4, 2, 5, 1 },
		{ 1, 5, 5, 5, 5, 5, 0, 5, 5, 5, 2, 4, 4, 4, 4, 4, 4, 2, 0, 1 },
		{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }};
		

	@Override
	protected void init() {
		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImplExt();
		OverlapRuleApplier overlapRules = new BombermanOverlaps(new Point(
				1 * SPRITE_SIZE_X, 1 * SPRITE_SIZE_Y), life[0], score[0]);
		overlapProcessor.setOverlapRules(overlapRules);

		MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		moveBlockerChecker.setMoveBlockerRules(new BombermanMoveBlockers());

		universe = new GameUniverseDefaultImpl(moveBlockerChecker,
				overlapProcessor);
		overlapRules.setUniverse(universe);

		gameBoard = new GameUniverseViewPortDefaultImplExt(canvas, universe);
		((GameUniverseViewPortDefaultImplExt) gameBoard).setBackground("images/Level/LevelSnow/Floor.gif");
		
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
//				} else if (tab[i][j] == 3) {
//					universe.addGameEntity(new Wall(canvas, new Point(j * SPRITE_SIZE_X,
//							i * SPRITE_SIZE_Y), universe));
				} else {
				
					universe.addGameEntity(new Floor(canvas, new Point(j
							* SPRITE_SIZE_X, i * SPRITE_SIZE_Y)));
					
					if (tab[i][j] == 3) {
						universe.addGameEntity(new Wall(canvas, new Point(j * SPRITE_SIZE_X,
								i * SPRITE_SIZE_Y), universe));
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
		/* Player choice :
		 * 	- Bomberman
		 * 	- Link
		 *  - KitKat
		 *  - PetitGros
		 *  - PetitMaigre
		 */
		Bomberman bm = new Bomberman(canvas, universe, "Bomberman");
		GameMovableDriverDefaultImpl bmDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboardExt keyStrBm = new MoveStrategyKeyboardExt(bm);
		bmDriver.setStrategy(keyStrBm);
		bmDriver.setmoveBlockerChecker(moveBlockerChecker);
		canvas.addKeyListener(keyStrBm);
		bm.setDriver(bmDriver);
		bm.setPosition(new Point(1 * SPRITE_SIZE_X, 1 * SPRITE_SIZE_Y));
		universe.addGameEntity(bm);
		
		// Player 2
		Bomberman link = new Bomberman(canvas, universe, "Link");
		GameMovableDriverDefaultImpl linkDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboardExtP2 keyStrLink = new MoveStrategyKeyboardExtP2(link);
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

	@Override
	public void evolution() {
	}

}
