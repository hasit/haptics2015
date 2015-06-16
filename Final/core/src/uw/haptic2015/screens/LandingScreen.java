package uw.haptic2015.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import uw.haptic2015.Main;

/**
 * Created by jerrychen on 6/13/15.
 */
public class LandingScreen implements Screen {

    OrthographicCamera camera;
    SpriteBatch batch;
    Sprite sprite;
    Texture img;
    TextureRegion region;
    float screenWidth, screenHeight;
    Main main;

    public LandingScreen(Main main){
        this.main = main;
        create();
    }

    public void create(){
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);

        batch = new SpriteBatch();
        img = new Texture("grey_grid_landscape.png");
        //img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        //region = new TextureRegion(img, 0, 0, 800, 420);

        //sprite = new Sprite(region);
        sprite = new Sprite(img);
        //sprite.setSize(1f, 1f * sprite.getHeight()/sprite.getWidth());
        //sprite.scale(1f);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        //sprite.draw(batch);
        batch.draw(sprite, 0, 0);

        batch.end();

    }

    @Override
    public void resize(int width, int height) {
    }


    @Override
    public void show() {
        // called when this screen is set as the screen with game.setScreen();
    }


    @Override
    public void hide() {
        // called when current screen changes from this to a different screen
    }


    @Override
    public void pause() {
    }


    @Override
    public void resume() {
    }


    @Override
    public void dispose() {
        // never called automatically
    }
}
