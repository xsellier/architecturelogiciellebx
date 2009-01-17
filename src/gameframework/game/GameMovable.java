package gameframework.game;

import gameframework.base.Movable;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;

import java.awt.Point;

public abstract class GameMovable implements Movable {
	GameMovableDriver moveDriver = new GameMovableDriverDefaultImpl();
	Point position = new Point(50, 50);
	SpeedVector direction = SpeedVectorDefaultImpl.createNullVector();

	public void setPosition(Point p) {
		position = (Point) p.clone();
	}

	public Point getPosition() {
		return position;
	}

	public void setSpeedVector(SpeedVector speedVector) {
		direction = (SpeedVector) speedVector.clone();
	}

	public SpeedVector getSpeedVector() {
		return (SpeedVector) direction.clone();
	}

	public void setDriver(GameMovableDriver drv) {
		moveDriver = drv;
	}

	public GameMovableDriver getDriver() {
		return moveDriver;
	}

	public void oneStepMove() {
		
		//
		SpeedVector m = moveDriver.getSpeedVector(this);
		
		direction.setDir(m.getDir());
		direction.setSpeed(m.getSpeed());
		position.translate((int) direction.getDir().getX()
				* direction.getSpeed(), (int) direction.getDir().getY()
				* direction.getSpeed());
		oneStepMoveHandler();
	}

	/**
	 * Add some behavior to oneStepMove
	 */
	public abstract void oneStepMoveHandler();
}
