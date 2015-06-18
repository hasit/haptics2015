package uw.haptic2015.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import uw.haptic2015.Main;
import uw.haptic2015.screens.scenes.SlopeScene;

/**
 * Created by hasit on 6/16/15.
 */

public class SettingScreen implements Screen {

    Main main;

    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;

    public SettingScreen(Main main) {
        this.main = main;
    }

    public void create() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        stage = new Stage();

        final TextButton applyButton = new TextButton("Back", skin, "default");

        applyButton.setWidth(256f);
        applyButton.setHeight(128f);
        applyButton.setPosition(Gdx.graphics.getWidth() / 2 - 128f, Gdx.graphics.getHeight() / 2 - 64f);

        applyButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                main.setScreen(main.slopeScene);
            }
        });

        stage.addActor(applyButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }


    @Override
    public void show() {
        create();
    }


    @Override
    public void hide() {
        // called when current screen changes from this to a different screen
        this.dispose();
    }


    @Override
    public void pause() {
    }


    @Override
    public void resume() {
    }


    @Override
    public void dispose() {
        batch.dispose();
        skin.dispose();
    }
}
