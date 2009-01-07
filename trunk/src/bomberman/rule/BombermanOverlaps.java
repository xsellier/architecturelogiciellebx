package bomberman.rule;

import gameframework.base.IntegerObservable;
import gameframework.base.Overlap;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRuleApplierDefaultImpl;

import java.awt.Point;
import java.util.Vector;

import bomberman.entity.BombItem;
import bomberman.entity.Bomberman;
import bomberman.entity.Fire;
import bomberman.entity.FireItem;


public class BombermanOverlaps extends OverlapRuleApplierDefaultImpl {
	protected GameUniverse universe;
//	protected Vector<Ghost> vGhosts = new Vector<Ghost>();

	// Delay during which pacman is invulnerable and during which ghosts can be
	// eaten (in number of cycles)
	protected Point pacManStartPos;
	protected Point ghostStartPos;
	protected boolean managePacmanDeath;
	private IntegerObservable score;
	private IntegerObservable life;

	public BombermanOverlaps(Point pacPos, Point gPos,
			IntegerObservable life, IntegerObservable score) {
		pacManStartPos = (Point) pacPos.clone();
		ghostStartPos = (Point) gPos.clone();
		this.life = life;
		this.score = score;
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}

//	public void addGhost(Ghost g) {
//		vGhosts.addElement(g);
//	}

	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables) {
		managePacmanDeath = true;
		super.applyOverlapRules(overlappables);
	}

	public void overlapRule(Bomberman bm, BombItem b) {
		score.setValue(score.getValue() + 1);
 		universe.removeGameEntity(b);
	}

	public void overlapRule(Fire f, Bomberman bm) {
		System.out.println("Mort");
	}
	
	public void overlapRule(Bomberman bm, Fire f) {
		bm.die();
	}
	
	public void overlapRule(Bomberman bm, FireItem f) {
		universe.removeGameEntity(f);
	}
}
