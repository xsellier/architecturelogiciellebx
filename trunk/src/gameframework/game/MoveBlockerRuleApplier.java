package gameframework.game;

import gameframework.base.Movable;

import java.util.Vector;

public interface MoveBlockerRuleApplier {
	public boolean moveValidationProcessing(Vector<MoveBlocker> obs, Movable m);
}
