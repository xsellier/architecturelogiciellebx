package gameframework.game;

import gameframework.base.Movable;
import gameframework.base.Overlappable;

import java.util.Iterator;
import java.util.Vector;

public class GameUniverseDefaultImpl implements GameUniverse {
	private volatile Vector<GameEntity> gameEntities = new Vector<GameEntity>();
	private volatile OverlapProcessor overlapProcessor;
	private volatile MoveBlockerChecker moveBlockerChecker;

	public synchronized Iterator<GameEntity> gameEntities() {
		Iterator<GameEntity> it;
		synchronized (gameEntities.iterator()) {
			it = gameEntities.iterator();
		}
		return it;
	}

	public GameUniverseDefaultImpl(MoveBlockerChecker obs, OverlapProcessor col) {
		overlapProcessor = col;
		moveBlockerChecker = obs;
	}

	public void addGameEntity(GameEntity gameEntity) {
		synchronized (gameEntities) {
			gameEntities.add(gameEntity);
			if (gameEntity instanceof Overlappable) {
				synchronized (overlapProcessor) {
					overlapProcessor.addOverlappable((Overlappable) gameEntity);
				}
			}
			if (gameEntity instanceof MoveBlocker) {
				synchronized (moveBlockerChecker) {
					moveBlockerChecker.addMoveBlocker((MoveBlocker) gameEntity);
				}
			}
		}
	}

	public void removeGameEntity(GameEntity gameEntity) {
		synchronized (gameEntities) {
			gameEntities.remove(gameEntity);
			if (gameEntity instanceof Overlappable) {
				synchronized (overlapProcessor) {
					overlapProcessor
							.removeOverlappable((Overlappable) gameEntity);
				}
			}
			if (gameEntity instanceof MoveBlocker) {
				synchronized (moveBlockerChecker) {
					moveBlockerChecker
							.removeMoveBlocker((MoveBlocker) gameEntity);
				}
			}
		}
	}

	public void allOneStepMoves() {
		synchronized (gameEntities) {
			for (GameEntity entity : gameEntities) {
				if (entity instanceof Movable) {
					((Movable) entity).oneStepMove();
				}
			}
		}
	}

	public void processAllOverlaps() {
		synchronized (overlapProcessor) {
			overlapProcessor.processOverlapsAll();
		}
	}

}
