package bomberman.base;

import gameframework.base.MoveStrategy;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import bomberman.entity.Bomberman;

import static bomberman.game.ConstantValues.*;

public class MoveStrategyKeyboardExt extends KeyAdapter implements MoveStrategy {

	private SpeedVector currentMove = new SpeedVectorDefaultImpl(
			new Point(0, 0));

	private Bomberman bm;
	private Point currentPosition;
	private Point nextPosition = new Point(-1, -1);

	public MoveStrategyKeyboardExt(Bomberman bm) {
		this.bm = bm;
		currentPosition = bm.getPosition();
	}

	public SpeedVector getSpeedVector() {
		if (currentPosition.x == nextPosition.x
				|| currentPosition.y == nextPosition.y) {
			return SpeedVectorDefaultImpl.createNullVector();
		}

		return currentMove;
	}

	public void keyPressed(KeyEvent event) {
		int keycode = event.getKeyCode();
		currentPosition = bm.getPosition();
		
		System.out.println("x = " + currentPosition.x + ", y = " + currentPosition.y);
		
		switch (keycode) {
		
		case KeyEvent.VK_RIGHT:
			currentMove.setDir(new Point(1, 0));
			nextPosition = new Point(currentPosition.x + SPRITE_SIZE_X, -1);
			break;
		case KeyEvent.VK_LEFT:
			currentMove.setDir(new Point(-1, 0));
			nextPosition = new Point(currentPosition.x - SPRITE_SIZE_X, -1);
			break;
		case KeyEvent.VK_UP:
			currentMove.setDir(new Point(0, -1));
			nextPosition = new Point(-1, currentPosition.y - SPRITE_SIZE_Y);
			break;
		case KeyEvent.VK_DOWN:
			currentMove.setDir(new Point(0, 1));
			nextPosition = new Point(-1, currentPosition.y + SPRITE_SIZE_Y);
			break;
		case KeyEvent.VK_SPACE:
			bm.putBomb();
			break;
		}
	}
}
