package gameframework.base;

import java.util.Iterator;

public abstract class DrawableCompositeLeaf implements Drawable {

	public void add(Drawable d) {
		throw new InvalidDrawableException("LeafDrawable is not composite");
	}

	public void remove(Drawable d) {
		throw new InvalidDrawableException("LeafDrawable is not composite");
	}

	public Iterator<Drawable> elements() {
		return new Iterator<Drawable>() {
			public boolean hasNext() {
				return false;
			}

			public Drawable next() {
				return null;
			}

			public void remove() {
			}
		};
	}


}
