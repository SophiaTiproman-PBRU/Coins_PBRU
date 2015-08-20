package appewtc.porntip;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.SortedMap;

public class MyGdxGame extends ApplicationAdapter {

	//Explicit
	private SpriteBatch batch;
	//	private Texture img;
	private Texture wallpaperTexture, cloudTexture, objTexture;
	private OrthographicCamera objOrthographicCamera;
	private BitmapFont nameBitmapFont;
	private int xCloudAnInt, yCloudAnInt = 600;
	private boolean cloudABoolean = true;
	private Rectangle objRectangle;
	private Vector3 objVector3;
	private Sound objSound;

	
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

		//setup pig
		objTexture = new Texture("coins.png");

		//Setup Rectangle Pig
		objRectangle = new Rectangle();
		objRectangle.x = 568;
		objRectangle.y = 100;
		objRectangle.width = 64;
		objRectangle.height = 64;

		//Setup Sound
		objSound = Gdx.audio.newSound(Gdx.files.internal("coins_drop.wav"));


	}	//create  เอาไว้กำหนดค่า

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

		batch.end();

		//Move Cloud
		moveCloud();

		//Active When Touch Screen
		activeTouchScreen();

	}	//render >> it is loop

	private void activeTouchScreen() {
		if (Gdx.input.isTouched()) {
			//Sound Effect
			objSound.play();

			objVector3 = new Vector3();
			objVector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);

			if (objVector3.x < 600) {
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

