package gameframework.game;

import gameframework.base.BackgroundImage;
import gameframework.base.Drawable;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

public class GameUniverseViewPortDefaultImpl implements GameUniverseViewPort {
	private Image buffer;
	private Graphics bufferGraphics;
	protected Canvas canvas;
	protected BackgroundImage background;
	protected GameUniverse universe;

	public GameUniverseViewPortDefaultImpl(Canvas canvas, GameUniverse universe) {
		this.canvas = canvas;
		buffer = canvas.createImage(canvas.getWidth(), canvas.getHeight());
		bufferGraphics = buffer.getGraphics();
		background = new BackgroundImage("images/background_image.gif", canvas);
		this.universe = universe;
	}

	public void setBackground(String filename) {
		background = new BackgroundImage(filename, canvas);
	}

	public void paint() {
		// Modification qui ne fait que repousser l'echeance de l'exception
		background.draw(bufferGraphics);
		synchronized (universe) {
//			Iterator<GameEntity> gt = universe.gameEntities();
//			
//			for (; gt.hasNext();) {
//				GameEntity tmp = gt.next();
//				if (tmp instanceof Drawable) {
//					((Drawable) tmp).draw(bufferGraphics);
//				}
//			}
//			refresh();
			
			Iterator<GameEntity> gt = universe.gameEntities();
			ArrayList<GameEntity> gtCopy = new ArrayList<GameEntity>();
			
			for(; gt.hasNext();) {
				GameEntity tmp = gt.next();
				gtCopy.add(tmp);
			}
			
			for(GameEntity elem : gtCopy) {
				if (elem instanceof Drawable) {
					((Drawable) elem).draw(bufferGraphics);
				}
			}
			
			refresh();
		}
	}

	public void refresh() {
		canvas.getGraphics().drawImage(buffer, 0, 0, canvas.getWidth(),
				canvas.getHeight(), canvas);
	}
}
