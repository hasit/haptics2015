package uw.haptic2015;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import uw.haptic2015.screens.LandingScreen;
import uw.haptic2015.screens.scenes.SlopeScene;

public class Main extends Game {
	SpriteBatch batch;
	Texture img;
	SlopeScene slopeScene;
	LandingScreen landingScreen;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		slopeScene = new SlopeScene(this);
		landingScreen = new LandingScreen(this);
        setScreen(slopeScene);
	}
}
