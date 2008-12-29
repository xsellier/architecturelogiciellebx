package gameframework.base;

import gameframework.game.GameUniverse;

import java.awt.Canvas;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import bomberman.entity.Bomb;
import bomberman.entity.Bomberman;

public class MoveStrategyKeyboardExt extends KeyAdapter implements MoveStrategy {

	private SpeedVector currentMove = new SpeedVectorDefaultImpl(
			new Point(0, 0));

	private GameUniverse universe;
	private Canvas canvas;
	private Bomberman bm;

	public MoveStrategyKeyboardExt(Canvas canvas, GameUniverse universe,
			Bomberman bm) {
		this.canvas = canvas;
		this.universe = universe;
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
			Point tmp = getSpeedVector().getDir();

			int x = bm.getPosition().x;
			int y = bm.getPosition().y;

			if (tmp.getX() == 1) {
				x--;
				;
			} else if (tmp.getX() == -1) {
				x++;
			} else if (tmp.getY() == 1) {
				y--;
			} else if (tmp.getY() == -1) {
				y++;
			} else {
				x--;
			}

			System.out.println("Ajout de bomb sur la carte à la position (" + x
					+ ", " + y + ")");
			universe.addGameEntity(new Bomb(canvas, new Point(x, y), universe));
			break;
		}
	}

}
