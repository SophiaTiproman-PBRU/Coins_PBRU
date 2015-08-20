package appewtc.porntip;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {

	//Explicit
	private SpriteBatch batch;
	//	private Texture img;
	private Texture wallpaperTexture;
	private OrthographicCamera objOrthographicCamera;
	private BitmapFont nameBitmapFont;
	
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

		//Drawable BitMapFont
		nameBitmapFont.draw(batch, "Coins PBRU", 50, 600);

		batch.end();
	}	//render >> it is loop
}	//Main class

