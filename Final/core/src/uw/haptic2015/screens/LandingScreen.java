package uw.haptic2015.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import uw.haptic2015.Main;
import uw.haptic2015.screens.scenes.SlopeScene;

/**
 * Created by jerrychen on 6/13/15.
 */
public class LandingScreen implements Screen {

    SpriteBatch batch;
    Texture img;
    Main main;

    public LandingScreen(Main main){
        this.main = main;
        create();
    }

    public void create(){
        batch = new SpriteBatch();
        //img = new Texture("badlogic.jpg");
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        /*
        batch.begin();
        batch.draw(img, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        batch.end();

        if(Gdx.input.isTouched()){
            main.setScreen(main.slopeScene);
        }
        */
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
