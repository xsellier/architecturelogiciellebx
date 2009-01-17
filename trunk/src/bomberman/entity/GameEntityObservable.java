package bomberman.entity;

import gameframework.game.GameEntity;
import bomberman.GameEntityObserver;

public interface GameEntityObservable {
	public void addObserver(GameEntityObserver observer);
	public void deleteObserver(GameEntityObserver observer);
	public void notifie(GameEntity entity, String operation);
}
