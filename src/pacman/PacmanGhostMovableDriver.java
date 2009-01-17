package pacman;

import gameframework.base.Movable;
import gameframework.base.SpeedVector;
import gameframework.game.GameMovableDriverDefaultImpl;

public class PacmanGhostMovableDriver extends GameMovableDriverDefaultImpl {
	@Override
	public SpeedVector getSpeedVector(Movable m) {
		boolean continu = true;
		SpeedVector tmp, tmp2;
		tmp2 = m.getSpeedVector();
		tmp = super.getSpeedVector(m);
		int count = 10;
		while (continu) {
			count--;
			if ((tmp.getDir().getX() == tmp2.getDir().getX())
					&& (tmp.getDir().getY() != -tmp2.getDir().getY())) {
				continu = false;
				break;
			}
			if ((tmp.getDir().getX() != -tmp2.getDir().getX())
					&& (tmp.getDir().getY() == tmp2.getDir().getY())) {
				continu = false;
				break;
			}
			if ((tmp.getDir().getX() != tmp2.getDir().getX())
					&& (tmp.getDir().getY() != tmp2.getDir().getY())) {
				continu = false;
				break;
			}

			tmp = super.getSpeedVector(m);
			if (count < 1) {
				continu = false;
			}
		}
		return (tmp);
	}
}
