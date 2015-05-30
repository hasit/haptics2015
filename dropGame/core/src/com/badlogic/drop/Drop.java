package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import org.w3c.dom.css.Rect;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.Renderer;

public class Drop extends ApplicationAdapter {
	private Texture dropImage;
	private Texture bucketImage;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle bucket;
	private Array<Rectangle> rainDrops;
	private long lastDropTime;

	@Override
	public void create () {
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		//make sure the camera always show area of game what is 8000x480 wide
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		rainDrops = new Array<Rectangle>();
		spawnRainDrop();
	}

	private void spawnRainDrop(){
		Rectangle rainDrop = new Rectangle();
		rainDrop.x = MathUtils.random(0, 800-64);
		rainDrop.y = 480;
		rainDrop.width = 64;
		rainDrop.height = 64;
		rainDrops.add(rainDrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render () {
		//set the clear color to blue, red, green, blue, alpha
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		//clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		//render bucket
		batch.setProjectionMatrix(camera.combined); //use coordinate system specified by camera
		//record all drawing commands between begin and end, and submit all drawing requests at once
		batch.begin();
		batch.draw(bucketImage, bucket.x, bucket.y);
		for(Rectangle raindrop: rainDrops) {
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		batch.end();
		//making bucket move(touch/mouse)
		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3();
			//vector3 holding touch coordinates
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			//transform coordinates to camera's coordinate
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}

		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800 -64) bucket.x = 800 - 64;

		if(TimeUtils.nanoTime() - lastDropTime > 100000000) spawnRainDrop();

		Iterator<Rectangle> iter = rainDrops.iterator();
		while(iter.hasNext()){
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0) iter.remove();
			if (raindrop.overlaps(bucket)) {
				iter.remove();
			}
		}
	}

	@Override
	public void dispose(){
		dropImage.dispose();
		bucketImage.dispose();
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
