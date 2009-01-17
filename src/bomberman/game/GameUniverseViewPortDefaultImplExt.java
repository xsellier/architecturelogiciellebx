package bomberman.game;

import gameframework.base.BackgroundImage;
import gameframework.base.Drawable;
import gameframework.game.GameEntity;
import gameframework.game.GameUniverse;
import gameframework.game.GameUniverseViewPortDefaultImpl;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

public class GameUniverseViewPortDefaultImplExt extends GameUniverseViewPortDefaultImpl {
	private Image buffer;
	private Graphics bufferGraphics;

	public GameUniverseViewPortDefaultImplExt(Canvas canvas, GameUniverse universe) {
		super(canvas, universe);
		this.canvas = canvas;
		buffer = canvas.createImage(canvas.getWidth(), canvas.getHeight());
		bufferGraphics = buffer.getGraphics();
		background = new BackgroundImage("images/background_image.gif", canvas);
		this.universe = universe;
	}

	public void paint() {
		background.draw(bufferGraphics);
		Iterator<GameEntity> gt = universe.gameEntities();
		ArrayList<GameEntity> gtCopy = new ArrayList<GameEntity>();
		for (; gt.hasNext();) {
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

	public void refresh() {
		canvas.getGraphics().drawImage(buffer, 0, 0, canvas.getWidth(),
				canvas.getHeight(), canvas);
	}
}
