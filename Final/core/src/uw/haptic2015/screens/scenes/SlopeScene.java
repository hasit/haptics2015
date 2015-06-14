package uw.haptic2015.screens.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import uw.haptic2015.Main;

/**
 * Created by jerrychen on 6/13/15.
 */
public class SlopeScene extends GameScreen {
    SpriteBatch batch;
    Texture img;

    public SlopeScene(Main main){
        super(main);
        create();
    }

    public void create(){
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
    }
    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        batch.end();
    }
}
