package bomberman;

import gameframework.game.GameEntity;

public interface GameEntityObserver {
	public void update(GameEntity entity, String operation);
}
