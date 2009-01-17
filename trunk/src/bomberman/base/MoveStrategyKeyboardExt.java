package bomberman.base;

import static bomberman.game.ConstantValues.SPRITE_SIZE_X;
import static bomberman.game.ConstantValues.SPRITE_SIZE_Y;
import gameframework.base.MoveStrategyKeyboard;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;

import java.awt.Point;
import java.awt.event.KeyEvent;

import bomberman.entity.Bomberman;

public class MoveStrategyKeyboardExt extends MoveStrategyKeyboard {

	private SpeedVector currentMove = new SpeedVectorDefaultImpl(
			new Point(0, 0));

	private Bomberman bm;
	private Point currentPosition;
	private Point nextPosition = new Point(-1, -1);
	private MoveStrategyKeyboardMethodFactory player;

	public MoveStrategyKeyboardExt(Bomberman bm, int numberPlayer) {
		this.bm = bm;
		currentPosition = bm.getPosition();
		
		if (numberPlayer == 1) {
			player = new MoveStrategyKeyboardPlayer1();
		} else if (numberPlayer == 2) {
			player = new MoveStrategyKeyboardPlayer2();
		}
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

		if (keycode == player.right()) {
			currentMove.setDir(new Point(1, 0));
			nextPosition = new Point(currentPosition.x + SPRITE_SIZE_X, -1);
		} else if (keycode == player.left()) {
			currentMove.setDir(new Point(-1, 0));
			nextPosition = new Point(currentPosition.x - SPRITE_SIZE_X, -1);
		} else if (keycode == player.down()) {
			currentMove.setDir(new Point(0, 1));
			nextPosition = new Point(-1, currentPosition.y + SPRITE_SIZE_Y);
		} else if (keycode == player.up()) {
			currentMove.setDir(new Point(0, -1));
			nextPosition = new Point(-1, currentPosition.y - SPRITE_SIZE_Y);
		} else if (keycode == player.action()) {
			bm.putBomb();
		}
	}
}
