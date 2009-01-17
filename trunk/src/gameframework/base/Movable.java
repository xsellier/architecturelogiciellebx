package gameframework.base;

import java.awt.Point;

/**
 * Has a currentPosition, a {@link SpeedVector} and a bounding box.
 */
public interface Movable extends ObjectWithBoundedBox {
	public Point getPosition();

	public SpeedVector getSpeedVector();

	public void setSpeedVector(SpeedVector m);
 
	public void oneStepMove();
}
