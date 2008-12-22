package gameframework.game;

import gameframework.base.Movable;

import java.lang.reflect.Method;
import java.util.Vector;

public class MoveBlockerRuleApplierDefaultImpl implements MoveBlockerRuleApplier {

	public boolean moveValidationProcessing(Vector<MoveBlocker> moveBlockers,
			Movable m) {
		for (MoveBlocker moveBlocker : moveBlockers) {
			try {
				moveBlockerRuleApply(m, moveBlocker);
			} catch (Exception e) {
				// by default the moveBlocker implies the invalidation of the move
				//(in particular, if no method has been found by moveBlockerRuleApply)
				return false;
			}
		}
		return true;
	}

	private void moveBlockerRuleApply(Movable e1, MoveBlocker e2) throws Exception {
		Object[] param = new Object[2];
		Class<?>[] paramClass = new Class[2];
		Class<?> receiverClass = this.getClass();

		param[0] = e1;
		paramClass[0] = e1.getClass();
		param[1] = e2;
		paramClass[1] = e2.getClass();

		Method m = null;
		m = receiverClass.getMethod("moveBlockerRule", paramClass);
		m.invoke(this, param);
	}
}
