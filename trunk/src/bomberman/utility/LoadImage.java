package bomberman.utility;

import gameframework.base.DrawableImage;

import java.awt.Canvas;
import java.util.ArrayList;
import java.util.HashMap;

public class LoadImage {

	private static final String DIRECTORY_PLAYER = "images/Sprite/Player/";
	private static final String DIRECTORY_FIRE = "images/Sprite/Fire/";
	private static final String DIRECTORY_LEVEL = "images/Level/LevelSnow/";
	private static final String DIRECTORY_ITEM = "images/Item/";

	private static HashMap<String, DrawableImage> imgLevel = null;
	private static HashMap<String, DrawableImage> imgItem = null;
	private static HashMap<String, HashMap<String, Integer>> mapPlayer = null;

	public static void initMapPlayer() {
		mapPlayer = new HashMap<String, HashMap<String, Integer>>();

		HashMap<String, Integer> mapBomberman = new HashMap<String, Integer>();
		mapBomberman.put("Left", 10);
		mapBomberman.put("Right", 10);
		mapBomberman.put("Down", 8);
		mapBomberman.put("Up", 10);
		mapBomberman.put("Idle", 1);
		mapBomberman.put("Die", 4);

		mapPlayer.put("Bomberman", mapBomberman);

		HashMap<String, Integer> mapLink = new HashMap<String, Integer>();
		mapLink.put("Left", 8);
		mapLink.put("Right", 8);
		mapLink.put("Down", 7);
		mapLink.put("Up", 7);
		mapLink.put("Idle", 1);
		mapLink.put("Die", 4);

		mapPlayer.put("Link", mapLink);
		
		HashMap<String, Integer> mapKitKat = new HashMap<String, Integer>();
		mapKitKat.put("Left", 8);
		mapKitKat.put("Right", 8);
		mapKitKat.put("Down", 7);
		mapKitKat.put("Up", 7);
		mapKitKat.put("Idle", 1);
		mapKitKat.put("Die", 4);

		mapPlayer.put("KitKat", mapKitKat);
		
		HashMap<String, Integer> mapPetitGros = new HashMap<String, Integer>();
		mapPetitGros.put("Left", 10);
		mapPetitGros.put("Right", 10);
		mapPetitGros.put("Down", 10);
		mapPetitGros.put("Up", 10);
		mapPetitGros.put("Idle", 1);
		mapPetitGros.put("Die", 6);

		mapPlayer.put("PetitGros", mapPetitGros);
		
		HashMap<String, Integer> mapPetitMaigre = new HashMap<String, Integer>();
		mapPetitMaigre.put("Left", 8);
		mapPetitMaigre.put("Right", 8);
		mapPetitMaigre.put("Down", 10);
		mapPetitMaigre.put("Up", 10);
		mapPetitMaigre.put("Idle", 1);
		mapPetitMaigre.put("Die", 5);

		mapPlayer.put("PetitMaigre", mapPetitMaigre);

	}

	public static HashMap<String, ArrayList<DrawableImage>> loadImagePlayer(
			Canvas canvas, String playerType) {
		HashMap<String, ArrayList<DrawableImage>> imgMap = new HashMap<String, ArrayList<DrawableImage>>();

		if (mapPlayer == null) {
			initMapPlayer();
		}
		
		String path = DIRECTORY_PLAYER + playerType + "/";
		
		ArrayList<DrawableImage> imgListLeft = loadImg(canvas, path, "Left",
				"gif", mapPlayer.get(playerType).get("Left"));
		ArrayList<DrawableImage> imgListRight = loadImg(canvas, path, "Right",
				"gif", mapPlayer.get(playerType).get("Right"));
		ArrayList<DrawableImage> imgListDown = loadImg(canvas, path, "Down",
				"gif", mapPlayer.get(playerType).get("Down"));
		ArrayList<DrawableImage> imgListUp = loadImg(canvas, path, "Up", "gif",
				mapPlayer.get(playerType).get("Up"));
		ArrayList<DrawableImage> imgListIdle = loadImg(canvas, path, "Idle",
				"gif", mapPlayer.get(playerType).get("Idle"));
		ArrayList<DrawableImage> imgListDie = loadImg(canvas, path, "Die",
				"gif", mapPlayer.get(playerType).get("Die"));

		imgMap.put("Left", imgListLeft);
		imgMap.put("Right", imgListRight);
		imgMap.put("Down", imgListDown);
		imgMap.put("Up", imgListUp);
		imgMap.put("Idle", imgListIdle);
		imgMap.put("Die", imgListDie);

		return imgMap;

	}

	private static ArrayList<DrawableImage> loadImg(Canvas canvas, String dir,
			String name, String extension, int number) {
		ArrayList<DrawableImage> result = new ArrayList<DrawableImage>(number);

		for (int i = 0; i < number; i++) {
			String s = dir + name + i + "." + extension;
			DrawableImage tmp = new DrawableImage(s, canvas);
			result.add(tmp);
		}

		return result;
	}

	public static HashMap<String, ArrayList<DrawableImage>> loadImageFire(
			Canvas canvas) {
		HashMap<String, ArrayList<DrawableImage>> imgMap = new HashMap<String, ArrayList<DrawableImage>>();

		ArrayList<DrawableImage> imgListCenter = loadImg(canvas, DIRECTORY_FIRE
				+ "Center/", "Center", "gif", 4);
		ArrayList<DrawableImage> imgListLeft = loadImg(canvas, DIRECTORY_FIRE
				+ "Left/", "Left", "gif", 4);
		ArrayList<DrawableImage> imgListRight = loadImg(canvas, DIRECTORY_FIRE
				+ "Right/", "Right", "gif", 4);
		ArrayList<DrawableImage> imgListDown = loadImg(canvas, DIRECTORY_FIRE
				+ "Down/", "Down", "gif", 4);
		ArrayList<DrawableImage> imgListUp = loadImg(canvas, DIRECTORY_FIRE
				+ "Up/", "Up", "gif", 4);
		ArrayList<DrawableImage> imgListLeftExt = loadImg(canvas,
				DIRECTORY_FIRE + "LeftExt/", "LeftExt", "gif", 4);
		ArrayList<DrawableImage> imgListRightExt = loadImg(canvas,
				DIRECTORY_FIRE + "RightExt/", "RightExt", "gif", 4);
		ArrayList<DrawableImage> imgListUpExt = loadImg(canvas, DIRECTORY_FIRE
				+ "UpExt/", "UpExt", "gif", 4);
		ArrayList<DrawableImage> imgListDownExt = loadImg(canvas,
				DIRECTORY_FIRE + "DownExt/", "DownExt", "gif", 4);

		imgMap.put("Center", imgListCenter);
		imgMap.put("Left", imgListLeft);
		imgMap.put("Right", imgListRight);
		imgMap.put("Down", imgListDown);
		imgMap.put("Up", imgListUp);
		imgMap.put("LeftExt", imgListLeftExt);
		imgMap.put("RightExt", imgListRightExt);
		imgMap.put("UpExt", imgListUpExt);
		imgMap.put("DownExt", imgListDownExt);

		return imgMap;

	}

	public static HashMap<String, DrawableImage> getImgLevel(Canvas canvas) {
		if (imgLevel == null) {
			loadLevel(canvas);
		}
		return imgLevel;
	}

	private static void loadLevel(Canvas canvas) {
		imgLevel = new HashMap<String, DrawableImage>();

		imgLevel.put("Wall", new DrawableImage(DIRECTORY_LEVEL + "Wall.gif",
				canvas));
		imgLevel.put("SuperWall", new DrawableImage(DIRECTORY_LEVEL
				+ "SuperWall.gif", canvas));
		imgLevel.put("BlocAround", new DrawableImage(DIRECTORY_LEVEL
				+ "BlocAround.gif", canvas));
		imgLevel.put("Floor", new DrawableImage(DIRECTORY_LEVEL + "Floor.gif",
				canvas));
	}

	public static HashMap<String, DrawableImage> getImgItem(Canvas canvas) {
		if (imgItem == null) {
			loadItem(canvas);
		}
		return imgItem;
	}

	private static void loadItem(Canvas canvas) {
		imgItem = new HashMap<String, DrawableImage>();

		imgItem.put("Bomb", new DrawableImage(DIRECTORY_ITEM + "Bomb.gif",
				canvas));
		imgItem.put("Fire", new DrawableImage(DIRECTORY_ITEM + "Fire.gif",
				canvas));
	}

	public static ArrayList<DrawableImage> getImgBurnItemList(Canvas canvas) {
		return loadImg(canvas, DIRECTORY_ITEM + "ItemBurning/", "ItemBurning",
				"gif", 7);
	}

	public static ArrayList<DrawableImage> getImgBurnWallList(Canvas canvas) {
		return loadImg(canvas, DIRECTORY_LEVEL + "FireWall/", "FireWall",
				"gif", 5);
	}
}
