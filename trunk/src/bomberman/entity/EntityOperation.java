package bomberman.entity;

import gameframework.game.GameEntity;

import java.util.ArrayList;

import bomberman.GameEntityObserver;

public class EntityOperation implements GameEntityObservable {

	private ArrayList<GameEntityObserver> observerList = new ArrayList<GameEntityObserver>();
	
	public EntityOperation(GameEntityObserver obs) {
		this.addObserver(obs);
	}
	
	public void addGameEntity(GameEntity entity) {
		notifie(entity, "add");
	}
	
	public void removeGameEntity(GameEntity entity) {
		notifie(entity, "r");
	}

	public void addObserver(GameEntityObserver observer) {
		observerList.add(observer);
	}

	public void deleteObserver(GameEntityObserver observer) {
		observerList.remove(observer);
	}

	public void notifie(GameEntity entity, String operation) {
		for(GameEntityObserver obs : observerList) {
			obs.update(entity, operation);
		}
	}
	
}
