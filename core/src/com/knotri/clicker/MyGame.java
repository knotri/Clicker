package com.knotri.clicker;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.knotri.clicker.screen.GameScreen;
import com.knotri.clicker.screen.SkinScreen;
import com.knotri.clicker.screen.TopScreen;


public class MyGame extends Game {

	public interface RequestHandler {
		//void confirm(ConfirmInterface confirmInterface);
		//void loadAds();
		//void showAds();
		//void share();
		//void shareRecord(int score);
		void saveRecord(int score);
		String getTopRecord(Array<TopScreen.ItemRecord> recordArray);
	}

	public RequestHandler requestHandler;
	public MyGame(RequestHandler requestHandler){
		this.requestHandler = requestHandler;
	}

	public static final float DESIGN_WIDTH = 720;
	public static Texture blackTexture;


	public static Array<ItemUpgrade> itemUpgrades = new Array<ItemUpgrade>();
	public static Array<TopScreen.ItemRecord> itemRecords = new Array<TopScreen.ItemRecord>();
	public static Array<SkinScreen.ItemSkin> itemSkins = new Array<SkinScreen.ItemSkin>();


	public static BitmapFont bigFont;
	public static BitmapFont middleFont;
	public static BitmapFont smallFont;
	public static int cps = 10;
	public static int score  = 0;


	GameScreen gameScreen;
//	SplashScreen splashScreen;
//	LevelScreen levelScreen;
//	EndScreen endScreen;

	public TextureRegion globalBackground;
	public static TextureRegion mainButton;
	public static TextureAtlas atlas;
	Music mp3Music;
	int flag = 0;

	Array<Integer> replace = new Array<Integer>();

	@Override
	public void create() {




//		Gdx.app.log("tag", "dfdfdfdfdfdfdfdf!!!!!!");
//		Gdx.app.log("tag", replace.toString());

//		Timer.schedule(new Timer.Task() {
//						   @Override
//						   public void run() {
//							   GdxAppodeal.getInstance().show(GdxAppodeal.INTERSTITIAL);
//						   }
//					   }
//				, 10    // delay
//				, 50    // seconds
//		);

		//mp3Music =  Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		//mp3Music.setLooping(true);
		//mp3Music.play();


		atlas = new TextureAtlas(Gdx.files.internal("skin/pack.atlas"));
		AbstractScreen.skin = new Skin();
		BitmapFont font = AbstractScreen.fontGeneration(Gdx.graphics.getWidth() / 16);
		AbstractScreen.skin.addRegions(atlas);
		AbstractScreen.skin.add("font", font, font.getClass());
		AbstractScreen.skin.load(Gdx.files.internal("skin/skin.json"));



		globalBackground = atlas.findRegion("12");
		mainButton = atlas.findRegion("YouTube-icon-full_color");

		Pixmap b = new Pixmap(1,1, Pixmap.Format.RGBA8888);
		b.setColor(Color.BLACK);
		b.fill();
		blackTexture = new Texture(b);

		itemUpgrades.add(new ItemUpgrade(atlas.findRegion("12"), "Снимать больше видео", 120, 3));
		itemUpgrades.add(new ItemUpgrade(atlas.findRegion("12"), "Снимать больше видео 2", 220, 5));
		itemUpgrades.add(new ItemUpgrade(atlas.findRegion("12"), "Снимать больше видео 3", 220, 5));
		itemUpgrades.add(new ItemUpgrade(atlas.findRegion("12"), "Снимать больше видео 4", 220, 5));

		itemUpgrades.add(new ItemUpgrade(atlas.findRegion("12"), "Снимать больше видео", 120, 3));
		itemUpgrades.add(new ItemUpgrade(atlas.findRegion("12"), "Снимать больше видео 2", 220, 5));
		itemUpgrades.add(new ItemUpgrade(atlas.findRegion("12"), "Снимать больше видео 3", 220, 5));
		itemUpgrades.add(new ItemUpgrade(atlas.findRegion("12"), "Снимать больше видео 4", 220, 5));

		itemSkins.add(new SkinScreen.ItemSkin(atlas.findRegion("121"), "1", 100));
		itemSkins.add(new SkinScreen.ItemSkin(atlas.findRegion("1212"), "2", 100));
		itemSkins.add(new SkinScreen.ItemSkin(atlas.findRegion("1222"), "3", 100));

		itemSkins.add(new SkinScreen.ItemSkin(atlas.findRegion("YouTube-icon-full_color"), "1", 100));
		itemSkins.add(new SkinScreen.ItemSkin(atlas.findRegion("123456"), "2", 100));
		itemSkins.add(new SkinScreen.ItemSkin(atlas.findRegion("1234567"), "3", 100));
		itemSkins.add(new SkinScreen.ItemSkin(atlas.findRegion("skin2"), "3", 100));


		AbstractScreen.game = this;


		loadGame();
		gameScreen = new GameScreen();
		setScreen(gameScreen);
		AbstractScreen.inputMultiplexer.addProcessor(AbstractScreen.stage);
		Gdx.input.setInputProcessor(AbstractScreen.inputMultiplexer);
		Gdx.input.setCatchBackKey(true);
	}


	public void saveGame(){
		String itemUpgradesStr = "";
		for(int i = 0; i < itemUpgrades.size; i++){
			itemUpgradesStr += itemUpgrades.get(i).level;
			if(i != itemUpgrades.size - 1){
				itemUpgradesStr += ",";
			}
		}
		AbstractScreen.prefs.putString("itemUpgrades", itemUpgradesStr);
		Gdx.app.log("save Game", itemUpgradesStr);
		AbstractScreen.prefs.putInteger("score", score);
		AbstractScreen.prefs.flush();
	}

	public void loadGame(){
		try {


			MyGame.score = AbstractScreen.prefs.getInteger("score");
			String itemUpgradesStr = AbstractScreen.prefs.getString("itemUpgrades");
			Gdx.app.log("load Game", itemUpgradesStr);
			String[] itemUpgradeStrArray = itemUpgradesStr.split(",");
			for (int i = 0; i < itemUpgradeStrArray.length; i++) {
				if (i >= itemUpgradeStrArray.length) break;
				itemUpgrades.get(i).level = Integer.parseInt(itemUpgradeStrArray[i]);
				for (int j = 0; j < itemUpgrades.get(i).level; j++) {
					itemUpgrades.get(i).price *= 1.6;
					cps += itemUpgrades.get(i).cps;
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}




}