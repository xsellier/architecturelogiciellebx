package gameframework.game;

import gameframework.base.Overlappable;
import gameframework.base.Overlap;

import java.lang.reflect.Method;
import java.util.Vector;

public abstract class OverlapRuleApplierDefaultImpl implements OverlapRuleApplier {

	public void applyOverlapRules(Vector<Overlap> overlaps) {
		for (Overlap col : overlaps) {
			try {
				applySpecificOverlapRule(col.getOverlappable1(), col.getOverlappable2());
			} catch (Exception e) {
			}
		}
	}

	private void applySpecificOverlapRule(Overlappable e1, Overlappable e2) throws Exception {
		Object[] param = new Object[2];
		Class<?>[] paramClass = new Class[2];
		Class<?> receiverClass = this.getClass();
		param[0] = e1;
		paramClass[0] = e1.getClass();
		param[1] = e2;
		paramClass[1] = e2.getClass();
		Method m = null;
		try {
			m = receiverClass.getMethod("overlapRule", paramClass);
			m.invoke(this, param);
		} catch (Exception e) {
			Class<?> tmpclass = paramClass[0];
			Object tmpobject = param[0];
			paramClass[0] = paramClass[1];
			paramClass[1] = tmpclass;
			param[0] = paramClass[1];
			param[1] = tmpobject;
			m = receiverClass.getMethod("overlapRule", paramClass);
			m.invoke(this, param);
		}
	}

}
