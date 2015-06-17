package uw.haptic2015.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.awt.Button;

import uw.haptic2015.Main;

/**
 * Created by hasit on 6/13/15.
 */

public class SettingScreen implements Screen {

    Main main;

    Stage stage;

    TextButton applyButton, cancelButton;
    TextButton.TextButtonStyle commonButtonStyle;

    BitmapFont font;

    Skin skin;

    TextureAtlas buttonAtlas;

    public SettingScreen(Main main){
        this.main = main;
    }

    public void create(){
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
        skin.addRegions(buttonAtlas);

        commonButtonStyle = new TextButton.TextButtonStyle();
        commonButtonStyle.font = font;
        commonButtonStyle.up = skin.getDrawable("up-button");
        commonButtonStyle.down = skin.getDrawable("down-button");
        commonButtonStyle.checked = skin.getDrawable("checked-button");

        applyButton = new TextButton("Apply", commonButtonStyle);
        stage.addActor(applyButton);

        applyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Button pressed");
            }
        });
    }


    @Override
    public void render(float delta) {
        stage.draw();
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
