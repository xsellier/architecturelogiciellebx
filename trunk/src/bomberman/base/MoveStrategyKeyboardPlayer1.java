package bomberman.base;

import java.awt.event.KeyEvent;

public class MoveStrategyKeyboardPlayer1 implements MoveStrategyKeyboardMethodFactory {

	public int right() {
		return KeyEvent.VK_D;
	}
	
	public int left() {
		return KeyEvent.VK_Q;
	}
	
	public int down() {
		return KeyEvent.VK_S;
	}
	
	public int up() {
		return KeyEvent.VK_Z;
	}
	
	public int action() {
		return KeyEvent.VK_SPACE;
	}

}
