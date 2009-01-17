package gameframework.game;

import gameframework.base.IntegerObservable;
import java.awt.Canvas;

public interface Game {
	public void createGUI();

	public Canvas getCanvas();

	public void start();

	public void restore();

	public void save();

	public void pause();

	public void resume();

	public IntegerObservable[] score();

	public IntegerObservable[] life();
}
