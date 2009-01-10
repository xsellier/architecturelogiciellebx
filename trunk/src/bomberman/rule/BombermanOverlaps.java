package bomberman.rule;

import gameframework.base.IntegerObservable;
import gameframework.base.Overlap;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRuleApplierDefaultImpl;

import java.awt.Point;
import java.util.Vector;

import bomberman.entity.Bomberman;
import bomberman.entity.Fire;
import bomberman.entity.item.AbstractItem;
import bomberman.entity.item.BombItem;
import bomberman.entity.item.FireItem;
import bomberman.entity.level.Floor;

public class BombermanOverlaps extends OverlapRuleApplierDefaultImpl {
	protected GameUniverse universe;
	protected Point BombermanStartPos;
	protected boolean managePacmanDeath;
	private IntegerObservable score;
	private IntegerObservable life;

	public BombermanOverlaps(Point pacPos, IntegerObservable life,
			IntegerObservable score) {
		BombermanStartPos = (Point) pacPos.clone();
		this.life = life;
		this.score = score;
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}

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
	
	public void overlapRule(Fire f, FireItem fi) {
		fi.burnItem();
	}
	
	public void overlapRule(Fire f, BombItem bi) {
		bi.burnItem();
	}

	public void overlapRule(Bomberman bm, Fire f) {
		// bm.die();
	}

	public void overlapRule(Bomberman bm, FireItem f) {
		life.setValue(life.getValue() + 1);
		universe.removeGameEntity(f);
	}

	public void overlapRule(Bomberman bm, Floor f) {
//		bm.setPosition(f.getPosition());
//		MoveStrategyKeyboardExt strat = new MoveStrategyKeyboardExt(bm);
//		GameMovableDriverDefaultImpl bomberDriver = (GameMovableDriverDefaultImpl) bm
//				.getDriver();
//		bomberDriver.setStrategy(strat);
//		bm.setPosition(f.getPosition());
	}
}
