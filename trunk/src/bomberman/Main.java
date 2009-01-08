package bomberman;

import gameframework.game.GameDefaultImpl;
import gameframework.game.GameLevel;

import java.util.ArrayList;

import bomberman.game.GameDefaultImplProxy;

public class Main {
	public static void main(String[] args) {
		GameDefaultImplProxy g = new GameDefaultImplProxy();
		ArrayList<GameLevel> levels = new ArrayList<GameLevel>();

		levels.add(new BombermanGameLevel(g));

		g.setLevels(levels);
		g.start();
//		g.pause();
	}
}
