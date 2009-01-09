package bomberman.game;

import gameframework.base.Overlappable;
import gameframework.base.Overlap;
import gameframework.base.IntersectTools;
import gameframework.base.Movable;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;
import gameframework.game.OverlapProcessor;
import gameframework.game.OverlapRuleApplier;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class OverlapProcessorDefaultImplExt implements OverlapProcessor {

	/**
	 * These two lists contain all overlappables for which we want to compute
	 * overlaps. We distinguish between movable and non-movable because two
	 * non-movables never overlap.
	 */
	private List<Overlappable> overlappablesNonMovable;
	private List<Overlappable> overlappablesMovable;

	private OverlapRuleApplier overlapRules;

	public OverlapProcessorDefaultImplExt() {
		overlappablesNonMovable = new Vector<Overlappable>();
		overlappablesMovable = new Vector<Overlappable>();
	}

	public void addOverlappable(Overlappable p) {

		if (p instanceof Movable) {
			synchronized (overlappablesMovable) {
				overlappablesMovable.add(p);
			}
		} else {
			synchronized (overlappablesNonMovable) {
				overlappablesNonMovable.add(p);
			}
		}
	}

	public void removeOverlappable(Overlappable p) {
		if (p instanceof Movable) {
			synchronized (overlappablesMovable) {
				overlappablesMovable.remove(p);
			}
		} else {
			synchronized (overlappablesNonMovable) {
				overlappablesNonMovable.remove(p);
			}
		}
	}

	public void setOverlapRules(OverlapRuleApplier overlapRules) {
		synchronized (overlapRules) {
			this.overlapRules = overlapRules;
		}
	}

	// for optimization purpose : prevents to compute two times the overlaps
	private List<Overlappable> movablesTmp;

	public void processOverlapsAll() {
		Vector<Overlap> overlaps = new Vector<Overlap>();
		synchronized (overlappablesMovable) {
			movablesTmp = new Vector<Overlappable>(overlappablesMovable);
			for (Overlappable overlappable : overlappablesMovable) {
				movablesTmp.remove(overlappable);
				computeOneOverlap(overlappable, overlaps);
			}
		}

		synchronized (overlapRules) {
			overlapRules.applyOverlapRules(overlaps);
		}
	}

	private void computeOneOverlap(Overlappable overlappable,
			Vector<Overlap> overlaps) {
		Area overlappableArea, targetArea;
		Rectangle boundingBoxTarget, boundingBoxOverlappable;

		Shape intersectShape = intersectionComputation(overlappable);

		overlappableArea = new Area(intersectShape);
		boundingBoxOverlappable = intersectShape.getBounds();

		Iterator<Overlappable> iterator;
		synchronized (overlappablesNonMovable) {
			iterator = overlappablesNonMovable.iterator();
			while (iterator.hasNext()) {
				Overlappable targetOverlappable = iterator.next();
				if (targetOverlappable != overlappable) {
					Shape targetShape;
					targetShape = targetOverlappable.getBoundingBox();
					boundingBoxTarget = targetShape.getBounds();

					if (boundingBoxOverlappable.intersects(boundingBoxTarget)) {
						targetArea = new Area(targetShape);
						targetArea.intersect(overlappableArea);
						if (!targetArea.isEmpty()) {
							overlaps.add(new Overlap(overlappable,
									targetOverlappable));
						}
					}
				}
			}
		}

		synchronized (movablesTmp) {
			iterator = movablesTmp.iterator();

			while (iterator.hasNext()) {
				Overlappable targetOverlappable = iterator.next();
				if (targetOverlappable != overlappable) {
					Shape targetShape;
					targetShape = IntersectTools.getIntersectShape(
							(Movable) targetOverlappable,
							new SpeedVectorDefaultImpl(
									((Movable) targetOverlappable)
											.getSpeedVector().getDir(),
									-((Movable) targetOverlappable)
											.getSpeedVector().getSpeed()));
					boundingBoxTarget = targetShape.getBounds();

					if (boundingBoxOverlappable.intersects(boundingBoxTarget)) {
						targetArea = new Area(targetShape);
						targetArea.intersect(overlappableArea);
						if (!targetArea.isEmpty()) {
							overlaps.add(new Overlap(overlappable,
									targetOverlappable));
						}
					}
				}
			}
		}
	}

	private Shape intersectionComputation(Overlappable overlappable) {
		if (overlappable instanceof Movable) {
			Movable movable = (Movable) overlappable;
			SpeedVector speedVector = movable.getSpeedVector();
			SpeedVectorDefaultImpl oppositeSpeedVector = new SpeedVectorDefaultImpl(
					speedVector.getDir(), -1 * speedVector.getSpeed());
			return IntersectTools.getIntersectShape(movable,
					oppositeSpeedVector);
		} else {
			return overlappable.getBoundingBox();
		}
	}
}
