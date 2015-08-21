package appewtc.porntip;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;
import java.util.SortedMap;

public class MyGdxGame extends ApplicationAdapter {

	//Explicit
	private SpriteBatch batch;
	//	private Texture img;
	private Texture wallpaperTexture, cloudTexture, objTexture, coinsTexture, rainTexture ;
	private OrthographicCamera objOrthographicCamera;
	private BitmapFont nameBitmapFont, scoreBitmapFont, showScoreBitmapFont;	//variables for display text
	private int xCloudAnInt, yCloudAnInt = 600;
	private boolean cloudABoolean = true, finishABoolean = false;
	private Rectangle objRectangle, coinsRectangle, rainRectangle;
	private Vector3 objVector3;
	private Sound objSound, waterDropSound, coinsDropSound;
	private Array<Rectangle> coinsArray, rainArray;
	private long lastDropCoins, lastDropRain;
	private Iterator<Rectangle> coinsIterator, rainIterator;	// >> Java.util
	private int scoreAnInt = 0, falseAnInt = 0, finalScoreAnInt;  //automatic set to zero
	private Music rainMusic;
	private Music backgroundMusic;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		//	img = new Texture("badlogic.jpg");

		//Setup Screen
		objOrthographicCamera = new OrthographicCamera();
		objOrthographicCamera.setToOrtho(false, 1200, 800);

		//Setup Wallpaper
		wallpaperTexture = new Texture("wallpapers_a_01.png");

		//Setup BitMapFont
		nameBitmapFont = new BitmapFont();
		nameBitmapFont.setColor(Color.PURPLE);
		nameBitmapFont.setScale(4);

		//Setup Cloud
		cloudTexture = new Texture("cloud.png");

		//setup object
		objTexture = new Texture("bucket.png");

		//Setup Rectangle object
		objRectangle = new Rectangle();
		objRectangle.x = 568;
		objRectangle.y = 100;
		objRectangle.width = 64;
		objRectangle.height = 64;

		//Setup object Sound
	//	objSound = Gdx.audio.newSound(Gdx.files.internal("mosquito.wav"));

		//Setup coins
		coinsTexture = new Texture("coins.png");

		//Create coinsArray
		coinsArray = new Array<Rectangle>();
		coinsRandomDrop();

		//Setup Water Drop Sound
		waterDropSound = Gdx.audio.newSound(Gdx.files.internal("water_drop.wav"));

		//Setup Coins Drop Sound
		coinsDropSound = Gdx.audio.newSound(Gdx.files.internal("coins_drop.wav"));

		//Setup scoreBitMapFont
		scoreBitmapFont = new BitmapFont();
		scoreBitmapFont.setColor(Color.CYAN);
		scoreBitmapFont.setScale(4);

		//Setup showScoreBitMapFont
		showScoreBitmapFont = new BitmapFont();
		//showScoreBitmapFont.setColor(Color.GREEN);
		showScoreBitmapFont.setColor(230,28,223,255);
		showScoreBitmapFont.setScale(4);

		 //Setup rainTexture
		rainTexture = new Texture("droplet.png");

		//Create rainArray
		rainArray = new Array<Rectangle>();
		rainRandomDrop();

		//Setup rainMusic
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		//Setup backgroundMusic
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bggame.mp3"));

	}	//create  >> for setup ot initial

	private void rainRandomDrop() {
		rainRectangle = new Rectangle();
		rainRectangle.x = MathUtils.random(0, 1136);
		rainRectangle.y = 800;
		rainRectangle.width = 64;
		rainRectangle.height = 64;

		rainArray.add(rainRectangle);
		lastDropRain = TimeUtils.nanoTime();

	}

	private void coinsRandomDrop() {

		coinsRectangle = new Rectangle();
		coinsRectangle.x = MathUtils.random(0, 1136);	//1200-64 = 1136
		coinsRectangle.y = 800; // drop from the top
		coinsRectangle.width = 64;
		coinsRectangle.height = 64;
		coinsArray.add(coinsRectangle);
		lastDropCoins = TimeUtils.nanoTime();	//don't drop from the same position

	}	//coinRandomDrop

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Setup Screen
		objOrthographicCamera.update();
		batch.setProjectionMatrix(objOrthographicCamera.combined);

		// for draw object
		batch.begin();
		//	batch.draw(img, 0, 0);

		//Drawable Wallpaper
		batch.draw(wallpaperTexture, 0, 0);

		//Drawable Cloud
		batch.draw(cloudTexture, xCloudAnInt, yCloudAnInt);

		//Drawable BitMapFont
		nameBitmapFont.draw(batch, "Coins PBRU", 50, 750);

		//Drawable object >> picture
		batch.draw(objTexture, objRectangle.x, objRectangle.y);

		//Drawable Coins
		for (Rectangle forCoins : coinsArray) {
			batch.draw(coinsTexture, forCoins.x, forCoins.y);
		}	//for

		//Drawable Score
		scoreBitmapFont.draw(batch, "Score = " + Integer.toString(scoreAnInt), 800, 750);

		//Drawable Rain
		for (Rectangle forRain : rainArray) {
			batch.draw(rainTexture, forRain.x, forRain.y);
		}	//for


		if (finishABoolean) {

			batch.draw(wallpaperTexture, 0, 0);

			showScoreBitmapFont.draw(batch, "Your Score = " + Integer.toString(finalScoreAnInt), 500, 750);

		}


		batch.end();

		//Move Cloud
		moveCloud();

		//Active When Touch Screen
		activeTouchScreen();

		//Random Drop Coins
		randomDropCoins();

		//Random Drop Rain
		randomDropRain();

		//Play rainMusic
		rainMusic.play();

		//Play background
		backgroundMusic.play();

	}	//render >> it is loop

	private void randomDropRain() {
		if (TimeUtils.nanoTime() - lastDropRain > 1E9) {
			rainRandomDrop();
		} 	//if
		rainIterator = rainArray.iterator();
		while (rainIterator.hasNext()) {
			Rectangle myrainRectangle = rainIterator.next();
			myrainRectangle.y -= 50 * Gdx.graphics.getDeltaTime();

			//When Rain drop into floor
			if (myrainRectangle.y + 64 < 0) {
				waterDropSound.play();
				rainIterator.remove();	//Deallocate Memory
			}	//if

			//When Rain overlap Object
			if (myrainRectangle.overlaps(objRectangle)) {
				scoreAnInt--;
				waterDropSound.play();
				rainIterator.remove();	//Deallocate Memory
			}


		}	//while
	}	//randomDropRain

	private void randomDropCoins() {
		if (TimeUtils.nanoTime() - lastDropCoins > 1E9) {
			coinsRandomDrop();
		} 	//if

		coinsIterator = coinsArray.iterator();

		while (coinsIterator.hasNext()) {
			Rectangle myCoinsRectangle = coinsIterator.next();
			myCoinsRectangle.y -= 50 * Gdx.graphics.getDeltaTime();

			//When Coins into Floor >> remove from memory
			if (myCoinsRectangle.y + 64 < 0) {
				falseAnInt++;
				waterDropSound.play();
				coinsIterator.remove();  //Deallocate Memory
				checkFalse();

			}	//if

			//When Coins Overlap Object
			if (myCoinsRectangle.overlaps(objRectangle)) {
				coinsDropSound.play();
				coinsIterator.remove();
				scoreAnInt += 2;
			}	//if


		} 	//while

	}	//RandomDropCoins

	private void checkFalse() {
		if (falseAnInt > 100) {
			dispose();

			if (!finishABoolean) {
				finalScoreAnInt = scoreAnInt;
			}

			finishABoolean = true;

		}	//if
	}	//checkFalse

	@Override
	public void dispose() {
		super.dispose();

		backgroundMusic.dispose();
		rainMusic.dispose();
	//	objSound.dispose();
		waterDropSound.dispose();
		coinsDropSound.dispose();

	}	//dispose

	private void activeTouchScreen() {
		if (Gdx.input.isTouched()) {
			//Sound Effect
		//	objSound.play();

			objVector3 = new Vector3();
			objVector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);

			//Gdx.graphics.getWidth()/2 >> 600
			if (objVector3.x < Gdx.graphics.getWidth()/2) {
				if (objRectangle.x < 0) {
					objRectangle.x = 0;
				} else {
					objRectangle.x -= 10;
				}
			} else {
				if (objRectangle.x > 1136) {
					objRectangle.x = 1136;
				} else {
					objRectangle.x += 10;
				}
			}
		}    //if
	}

	private void moveCloud() {

		if (cloudABoolean) {
			if (xCloudAnInt < 1200-263) {
				xCloudAnInt += 100 * Gdx.graphics.getDeltaTime();
			} else {
				cloudABoolean = !cloudABoolean;
			}
		} else {
			if (xCloudAnInt > 0) {
				xCloudAnInt -= 100 * Gdx.graphics.getDeltaTime();
			} else {
				cloudABoolean = !cloudABoolean;
			}
		}

	}	//movecloud

}	//Main class

