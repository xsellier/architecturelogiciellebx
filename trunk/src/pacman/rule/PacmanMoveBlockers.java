package pacman.rule;

import gameframework.game.IllegalMoveException;
import gameframework.game.MoveBlockerRuleApplierDefaultImpl;
import pacman.entity.Ghost;
import pacman.entity.Wall;

public class PacmanMoveBlockers extends MoveBlockerRuleApplierDefaultImpl {

	public void moveBlockerRule(Ghost g, Wall w) throws IllegalMoveException {
		// The default case is when a ghost is active. He is not able to cross a
		// wall.
		if (g.isActive()) {
			throw new IllegalMoveException();
		// When a ghost is not active he can cross the walls
		}

	}


}
