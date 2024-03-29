package gameframework.base;

import java.awt.Point;

/**
 * Indicates a 2D direction and speed
 */
public interface SpeedVector extends Cloneable {
	public Point getDir();

	public int getSpeed();

	public void setDir(Point p);

	public void setSpeed(int i);

	public Object clone();
}
