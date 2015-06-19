package uw.haptic2015.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import uw.haptic2015.Main;

/**
 * Created by jerrychen on 6/13/15.
 */
public class LandingScreen implements Screen {

    SpriteBatch batch;
    float screenWidth, screenHeight;
    Stage stage;
    Skin skin;
    ImageButton.ImageButtonStyle title_s;
    Texture title, subtitle1, subtitle2, subtitle3, shutdown;
    TextureRegion title_r, subtitle1_r, subtitle2_r, subtitle3_r, shutdown_r;
    TextureRegionDrawable title_dr, subtitle1_dr, subtitle2_dr, subtitle3_dr, shutdown_dr;
    TextButton.TextButtonStyle title_style, subtitle1_style, subtitle2_style, subtitle3_style, shutdown_style;
    BitmapFont default_font;
    Main main;

    public LandingScreen(Main main){
        this.main = main;
    }

    public void create(){
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        stage = new Stage();

        //Creating Textures
        title = new Texture("title.png");
        subtitle1 = new Texture("subtitle1.png");
        subtitle2 = new Texture("subtitle2.png");
        subtitle3 = new Texture("subtitle3.png");
        shutdown = new Texture("shutdown.png");

        //Creating TextureRegions
        title_r = new TextureRegion(title);
        subtitle1_r = new TextureRegion(subtitle1);
        subtitle2_r = new TextureRegion(subtitle2);
        subtitle3_r = new TextureRegion(subtitle3);
        shutdown_r = new TextureRegion(shutdown);

        //Creating TextureRegionDrawables
        title_dr = new TextureRegionDrawable(title_r);
        subtitle1_dr = new TextureRegionDrawable(subtitle1_r);
        subtitle2_dr = new TextureRegionDrawable(subtitle2_r);
        subtitle3_dr = new TextureRegionDrawable(subtitle3_r);
        shutdown_dr = new TextureRegionDrawable(shutdown_r);

        //Creating TextButtonStyles
        default_font = new BitmapFont();
        title_style = new TextButton.TextButtonStyle(title_dr, title_dr, title_dr, default_font);
        subtitle1_style = new TextButton.TextButtonStyle(subtitle1_dr, subtitle1_dr, subtitle1_dr, default_font);
        subtitle2_style = new TextButton.TextButtonStyle(subtitle2_dr, subtitle2_dr, subtitle2_dr, default_font);
        subtitle3_style = new TextButton.TextButtonStyle(subtitle3_dr, subtitle3_dr, subtitle3_dr, default_font);
        shutdown_style = new TextButton.TextButtonStyle(shutdown_dr, shutdown_dr, shutdown_dr, default_font);

        //Creating Title TextButton
        TextButton title_b = new TextButton(" ", title_style);
        title_b.setPosition((screenWidth - 750f), (screenHeight - 100f));

        //Creating Subtitle1 TextButton
        TextButton subtitle1_b = new TextButton(" ", subtitle1_style);
        subtitle1_b.setPosition((screenWidth - 1000f), (screenHeight - 500f) );

        //Creating Subtitle2 TextButton
        TextButton subtitle2_b = new TextButton(" ", subtitle2_style);
        subtitle2_b.setPosition((screenWidth - 700f), (screenHeight - 500f) );

        //Creating Subtitle3 TextButton
        TextButton subtitle3_b = new TextButton(" ", subtitle3_style);
        subtitle3_b.setPosition((screenWidth - 400f), (screenHeight - 500f) );

        //Creating Shutdown TextButton
        TextButton shutdown_b = new TextButton(" ", shutdown_style);
        shutdown_b.setPosition((screenWidth - 120f), (screenHeight - 700f));

        subtitle1_b.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(main.slopeScene);
            }
        });

        subtitle2_b.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                main.setScreen(main.springScene);
            }
        });

        shutdown_b.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                dispose();
                System.exit(0);
            }
        });

        //Add to Stage
        stage.addActor(title_b);
        stage.addActor(subtitle1_b);
        stage.addActor(subtitle2_b);
        stage.addActor(subtitle3_b);
        stage.addActor(shutdown_b);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render (float delta) {
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
