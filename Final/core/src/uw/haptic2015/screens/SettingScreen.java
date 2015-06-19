package uw.haptic2015.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import uw.haptic2015.Main;

/**
 * Created by hasit on 6/16/15.
 */

public class SettingScreen implements Screen {

    Main main;

    private Skin skin;
    private Table table;
    private Stage stage;
    private TextButton applyButton, backButton;
    private Slider frictionCoefficientSlider, densitySlider, gravitySlider, slopeAngleSlider, springCoefficientSlider;
    private Label titleLabel;
    private Label frictionCoefficientLabel, densityLabel, gravityLabel, slopeAngleLabel, springCoefficientLabel;
    private Label frictionCoefficientValueLabel, densityValueLabel, gravityValueLabel, slopeAngleValueLabel, springCoefficientValueLabel;

    public SettingScreen(Main main) {
        this.main = main;
    }

    public void create() {
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        stage = new Stage(new FillViewport(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3));

        // title
        titleLabel = new Label("Settings", skin, "title");

        // gravity
        gravityLabel = new Label("Gravity", skin, "default");
        gravitySlider = new Slider(0f, 10f, 0.1f, false, skin, "default-horizontal");
        gravityValueLabel = new Label(String.format("%.1f %s", -main.config.gravity, main.config.gravityUnit), skin);
        gravitySlider.setValue(-main.config.gravity);

        // density
        densityLabel = new Label("Mass", skin, "default");
        densitySlider = new Slider(1f, 20f, 1f, false, skin, "default-horizontal");
        densityValueLabel = new Label(String.format("%.0f %s", main.config.density, main.config.densityUnit), skin);
        densitySlider.setValue(main.config.density);

        // friction
        frictionCoefficientLabel = new Label("Friction", skin, "default");
        frictionCoefficientSlider = new Slider(0f, 1f, 0.1f, false, skin, "default-horizontal");
        frictionCoefficientValueLabel = new Label(String.format("%.1f %s", main.config.frictionCoefficient, main.config.frictionUnit), skin);
        frictionCoefficientSlider.setValue(main.config.frictionCoefficient);

        // apply button
        applyButton = new TextButton("Apply", skin, "default");

        // back button
        backButton = new TextButton("Back", skin, "default");

        //applyButton.setWidth(256f);
        //applyButton.setHeight(128f);
        //applyButton.setPosition(Gdx.graphics.getWidth() / 2 - (256f + 128f), Gdx.graphics.getHeight() / 4 - 128f);

        //backButton.setWidth(256f);
        //backButton.setHeight(128f);
        //backButton.setPosition(Gdx.graphics.getWidth() / 2 + (256f + 128f), Gdx.graphics.getHeight() / 4 - 128f);

        //stage.addActor(applyButton);
        //stage.addActor(backButton);

        // -----
        // table
        // -----
        table = new Table();
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        float scrW = Gdx.graphics.getWidth();
        float scrH = Gdx.graphics.getHeight();
        table.add(gravityLabel).expandY();//.size(scrW/3, scrH/3);
        table.add(gravitySlider);
        table.add(gravityValueLabel).width(scrW / 32);
        table.row();
        table.add(densityLabel).expandY();//.size(scrW/3, scrH/3);
        table.add(densitySlider);
        table.add(densityValueLabel).width(scrW / 32);
        table.row();
        table.add(frictionCoefficientLabel).expandY();//.size(scrW/3, scrH/3);
        table.add(frictionCoefficientSlider);
        table.add(frictionCoefficientValueLabel).width(scrW / 32);
        table.row();
        table.add(applyButton).expandY();
        table.add(backButton);

        table.setFillParent(true);
        table.pack();

        stage.addActor(table);

        gravitySlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float val = gravitySlider.getValue();
                gravityValueLabel.setText(String.format("%.1f %s", val, main.config.gravityUnit));
                //gravityValueLabel.invalidate();
            }
        });

        densitySlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float val = densitySlider.getValue();
                densityValueLabel.setText(String.format("%.0f %s", val, main.config.densityUnit));
            }
        });

        frictionCoefficientSlider.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float val = frictionCoefficientSlider.getValue();
                frictionCoefficientValueLabel.setText(String.format("%.1f %s", val, main.config.frictionUnit));
            }
        });

        applyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.config.gravity = -gravitySlider.getValue();
                main.config.density = densitySlider.getValue();
                main.config.frictionCoefficient = frictionCoefficientSlider.getValue();
                main.setScreen(main.activeScreen);

            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(main.activeScreen);
            }
        });

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
        //viewport.update(width,height);
        //stage.getViewport().update(width, height, false);
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
        skin.dispose();
    }
}
