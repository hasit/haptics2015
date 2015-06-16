package uw.haptic2015;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import uw.haptic2015.screens.LandingScreen;
import uw.haptic2015.screens.scenes.GameScreen;
import uw.haptic2015.screens.scenes.SlopeScene;

public class Main extends Game {
	public GameScreen slopeScene;
	public LandingScreen landingScreen;
	
	@Override
	public void create () {
		slopeScene = new SlopeScene(this);
		landingScreen = new LandingScreen(this);
        setScreen(landingScreen);
	}
}
