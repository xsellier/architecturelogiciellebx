package gameframework.base;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * {@link MoveStrategy} which listens to the keyboard and answers new
 * {@link SpeedVector speed vectors} based on what the user typed.
 */
public class MoveStrategyKeyboard extends KeyAdapter implements MoveStrategy {
	private SpeedVector currentMove = new SpeedVectorDefaultImpl(
			new Point(0, 0));

	public SpeedVector getSpeedVector() {
		return currentMove;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int keycode = event.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_RIGHT:
			currentMove.setDir(new Point(1, 0));
			break;
		case KeyEvent.VK_LEFT:
			currentMove.setDir(new Point(-1, 0));
			break;
		case KeyEvent.VK_UP:
			currentMove.setDir(new Point(0, -1));
			break;
		case KeyEvent.VK_DOWN:
			currentMove.setDir(new Point(0, 1));
			break;
		}
	}
}
