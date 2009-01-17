package gameframework.game;

import gameframework.base.Overlap;

import java.util.Vector;

public interface OverlapRuleApplier {
	public void setUniverse(GameUniverse universe);

	/**
	 * Modify the Universe depending on all the overlaps in parameter.
	 */
	public void applyOverlapRules(Vector<Overlap> overlaps);
}
