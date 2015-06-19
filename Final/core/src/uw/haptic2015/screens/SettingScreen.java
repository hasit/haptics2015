package uw.haptic2015.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;

import uw.haptic2015.Main;
import uw.haptic2015.screens.scenes.SlopeScene;
import uw.haptic2015.screens.scenes.SpringScene;

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

        // slope
        slopeAngleLabel = new Label("Slope", skin, "default");
        slopeAngleSlider = new Slider(1f, 45f, 1f, false, skin, "default-horizontal");
        slopeAngleValueLabel = new Label(String.format("%.0f %s", main.config.slopeAngle, main.config.slopeUnit), skin);
        slopeAngleSlider.setValue(main.config.slopeAngle);

        // spring
        springCoefficientLabel = new Label("Spring Coeff", skin, "default");
        springCoefficientSlider = new Slider(5f, 50f, 1f, false, skin, "default-horizontal");
        springCoefficientValueLabel = new Label(String.format("%.0f %s", main.config.springCoefficient, main.config.springUnit), skin);
        springCoefficientSlider.setValue(main.config.springCoefficient);

        // apply button
        applyButton = new TextButton("Apply", skin, "default");

        // back button
        backButton = new TextButton("Back", skin, "default");

        //Background
        Image background = new Image(new Texture("grey_grid_landscape.png"));
        background.setFillParent(true);
        stage.addActor(background);

        // -----
        // table
        // -----
        table = new Table();
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        float scrW = Gdx.graphics.getWidth();
        float scrH = Gdx.graphics.getHeight();
        table.add(gravityLabel).expandY();
        table.add(gravitySlider);
        table.add(gravityValueLabel).width(scrW / 32);
        table.row();
        table.add(densityLabel).expandY();
        table.add(densitySlider);
        table.add(densityValueLabel).width(scrW / 32);
        table.row();
        table.add(frictionCoefficientLabel).expandY();
        table.add(frictionCoefficientSlider);
        table.add(frictionCoefficientValueLabel).width(scrW / 32);
        table.row();

        if(main.activeScreen instanceof SlopeScene) {
            table.add(slopeAngleLabel).expandY();
            table.add(slopeAngleSlider);
            table.add(slopeAngleValueLabel).width(scrW / 32);
            table.row();
        } else if(main.activeScreen instanceof SpringScene) {
            table.add(springCoefficientLabel).expandY();
            table.add(springCoefficientSlider);
            table.add(springCoefficientValueLabel).width(scrW / 32);
            table.row();
        }

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

        slopeAngleSlider.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float val = slopeAngleSlider.getValue();
                slopeAngleValueLabel.setText(String.format("%.0f %s", val, main.config.slopeUnit));
            }
        });

        springCoefficientSlider.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float val = springCoefficientSlider.getValue();
                springCoefficientValueLabel.setText(String.format("%.0f %s", val, main.config.springUnit));
            }
        });

        applyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.config.gravity = -gravitySlider.getValue();
                main.config.density = densitySlider.getValue();
                main.config.frictionCoefficient = frictionCoefficientSlider.getValue();
                main.config.slopeAngle = slopeAngleSlider.getValue();
                main.config.springCoefficient = springCoefficientSlider.getValue();
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
