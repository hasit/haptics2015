package uw.haptic2015.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import uw.haptic2015.Main;

/**
 * Created by jerrychen on 6/13/15.
 */
public class LandingScreen implements Screen {

    float screenWidth, screenHeight;
    Stage stage;
    Texture title, subtitle1, subtitle1_down, subtitle2, subtitle2_down, subtitle3;;
    TextureRegion title_r, subtitle1_r, subtitle1_down_r, subtitle2_r, subtitle2_down_r, subtitle3_r;
    TextureRegionDrawable title_dr, subtitle1_dr, subtitle1_down_dr, subtitle2_dr, subtitle2_down_dr, subtitle3_dr;
    TextButton.TextButtonStyle title_style, subtitle1_style, subtitle2_style, subtitle3_style;
    BitmapFont default_font;
    Main main;

    Texture background;

    public LandingScreen(Main main) {
        this.main = main;
    }

    public void create(){
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        stage = new Stage();

        //Creating Textures
        title = new Texture("title.png");
        subtitle1 = new Texture("subtitle1.png");
        subtitle1_down = new Texture("subtitle1_down.png");
        subtitle2 = new Texture("subtitle2.png");
        subtitle2_down = new Texture("subtitle2_down.png");
        subtitle3 = new Texture("subtitle3.png");

        //Creating TextureRegions
        title_r = new TextureRegion(title);
        subtitle1_r = new TextureRegion(subtitle1);
        subtitle1_down_r = new TextureRegion(subtitle1_down);
        subtitle2_r = new TextureRegion(subtitle2);
        subtitle2_down_r = new TextureRegion(subtitle2_down);
        subtitle3_r = new TextureRegion(subtitle3);

        //Creating TextureRegionDrawables
        title_dr = new TextureRegionDrawable(title_r);
        subtitle1_dr = new TextureRegionDrawable(subtitle1_r);
        subtitle1_down_dr = new TextureRegionDrawable(subtitle1_down_r);
        subtitle2_dr = new TextureRegionDrawable(subtitle2_r);
        subtitle2_down_dr = new TextureRegionDrawable(subtitle2_down_r);
        subtitle3_dr = new TextureRegionDrawable(subtitle3_r);

        //Creating TextButtonStyles
        default_font = new BitmapFont();
        title_style = new TextButton.TextButtonStyle(title_dr, title_dr, title_dr, default_font);
        subtitle1_style = new TextButton.TextButtonStyle(subtitle1_dr, subtitle1_down_dr, subtitle1_dr, default_font);
        subtitle2_style = new TextButton.TextButtonStyle(subtitle2_dr, subtitle2_down_dr, subtitle2_dr, default_font);
        subtitle3_style = new TextButton.TextButtonStyle(subtitle3_dr, subtitle3_dr, subtitle3_dr, default_font);

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
        subtitle3_b.setPosition((screenWidth - 400f), (screenHeight - 500f));

        subtitle1_b.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(main.slopeScene);
            }
        });

        subtitle2_b.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(main.springScene);
            }
        });

        //Background
        Image background = new Image(new Texture("grey_grid_landscape.png"));
        background.setFillParent(true);
        stage.addActor(background);

        //Add to Stage
        stage.addActor(title_b);
        stage.addActor(subtitle1_b);
        stage.addActor(subtitle2_b);
        stage.addActor(subtitle3_b);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
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
        stage.dispose();
    }
}
