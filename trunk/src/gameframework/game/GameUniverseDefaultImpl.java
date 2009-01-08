package gameframework.game;

import gameframework.base.Overlappable;
import gameframework.base.Movable;

import java.util.Iterator;
import java.util.Vector;

public class GameUniverseDefaultImpl implements GameUniverse {
	private Vector<GameEntity> gameEntities = new Vector<GameEntity>();
	private OverlapProcessor overlapProcessor;
	private MoveBlockerChecker moveBlockerChecker;

	public Iterator<GameEntity> gameEntities() {
		return gameEntities.iterator();
	}

	public GameUniverseDefaultImpl(MoveBlockerChecker obs, OverlapProcessor col) {
		overlapProcessor = col;
		moveBlockerChecker = obs;
	}

	public void addGameEntity(GameEntity gameEntity) {
		gameEntities.add(gameEntity);
		if (gameEntity instanceof Overlappable) {
			overlapProcessor.addOverlappable((Overlappable) gameEntity);
		}
		if (gameEntity instanceof MoveBlocker) {
			moveBlockerChecker.addMoveBlocker((MoveBlocker) gameEntity);
		}
	}

	public void removeGameEntity(GameEntity gameEntity) {
		gameEntities.remove(gameEntity);
		if (gameEntity instanceof Overlappable) {
			overlapProcessor.removeOverlappable((Overlappable) gameEntity);
		}
		if (gameEntity instanceof MoveBlocker) {
			moveBlockerChecker.removeMoveBlocker((MoveBlocker) gameEntity);
		}
	}

	public void allOneStepMoves() {
		for (GameEntity entity : gameEntities) {
			if (entity instanceof Movable) {
				((Movable) entity).oneStepMove();
			}
		}
	}

	public void processAllOverlaps() {
		overlapProcessor.processOverlapsAll();
	}

}
