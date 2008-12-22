package gameframework.base;

import java.util.Observable;

public class IntegerObservable extends Observable {
	protected int val;

	public int getValue() {
		return val;
	}

	public void setValue(int val) {
		this.val = val;
		setChanged();
		notifyObservers();
	}
}
