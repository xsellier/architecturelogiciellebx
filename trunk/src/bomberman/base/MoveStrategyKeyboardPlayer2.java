package bomberman.base;

import java.awt.event.KeyEvent;

public class MoveStrategyKeyboardPlayer2 implements MoveStrategyKeyboardMethodFactory {

	public int right() {
		return KeyEvent.VK_RIGHT;
	}
	
	public int left() {
		return KeyEvent.VK_LEFT;
	}
	
	public int down() {
		return KeyEvent.VK_DOWN;
	}
	
	public int up() {
		return KeyEvent.VK_UP;
	}
	
	public int action() {
		return KeyEvent.VK_M;
	}

}
