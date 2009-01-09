package bomberman;

import gameframework.game.GameLevel;

import java.util.ArrayList;

import bomberman.game.GameDefaultImplExt;

public class Main {
	public static void main(String[] args) {
		GameDefaultImplExt g = new GameDefaultImplExt();
		ArrayList<GameLevel> levels = new ArrayList<GameLevel>();

		levels.add(new BombermanGameLevel(g));

		g.setLevels(levels);
		g.start();
//		g.pause();
	}
}
