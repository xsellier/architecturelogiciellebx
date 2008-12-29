package gameframework.base;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import bomberman.entity.Bomberman;

public class MoveStrategyKeyboardExt extends KeyAdapter implements MoveStrategy {

	private SpeedVector currentMove = new SpeedVectorDefaultImpl(
			new Point(0, 0));

	private Bomberman bm;

	public MoveStrategyKeyboardExt(Bomberman bm) {
		this.bm = bm;
	}

	public SpeedVector getSpeedVector() {
		// TODO Auto-generated method stub
		return currentMove;
	}

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
		case KeyEvent.VK_SPACE:
			bm.putBomb();
			break;
		}
	}

}
