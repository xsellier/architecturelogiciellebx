package gameframework.game;

import gameframework.base.IntegerObservable;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class GameLevelDefaultImpl extends Thread implements GameLevel {
	private static final int MINIMUM_DELAY_BETWEEN_GAME_CYCLES = 100;
	protected GameUniverse universe;
	protected GameUniverseViewPort gameBoard;
	protected IntegerObservable score[];
	protected IntegerObservable life[];

	boolean stopGameLoop;

	/**
	 * To be implemented with respect to a specific game Initialize universe and
	 * gameBoard
	 */
	protected abstract void init();

	public GameLevelDefaultImpl(Game g) {
		this.score = g.score();
		this.life = g.life();
	}

	@Override
	public void start() {
		init();
		super.start();
		try {
			super.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static Lock l = new ReentrantLock();

	@Override
	public void run() {
		stopGameLoop = false;
		// main game loop :
		long start;

		while (!stopGameLoop && !this.isInterrupted()) {
			start = new Date().getTime();
			gameBoard.paint();
			universe.allOneStepMoves();
			universe.processAllOverlaps();
			try {
				long sleepTime = MINIMUM_DELAY_BETWEEN_GAME_CYCLES
						- (new Date().getTime() - start);
				if (sleepTime > 0) {
					Thread.sleep(sleepTime);
				}
			} catch (Exception e) {
			}
		}
	}

	public void end() {
		stopGameLoop = true;
	}

	protected void evolution() {
	}

	protected void overlap_handler() {
	}

}
