package bomberman.entity;

import gameframework.base.Drawable;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;
import bomberman.GameEntityObserver;

public abstract class AbstractEntity extends GameMovable implements Drawable,
		GameEntity, Overlappable {

	protected static EntityOperation operation = null;

	public AbstractEntity(GameEntityObserver o) {
		if (operation == null) {
			operation = new EntityOperation(o);
		}
	}

}
