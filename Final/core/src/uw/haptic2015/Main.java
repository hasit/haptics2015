package uw.haptic2015;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import uw.haptic2015.screens.LandingScreen;
import uw.haptic2015.screens.SettingScreen;
import uw.haptic2015.screens.scenes.GameScreen;
import uw.haptic2015.screens.scenes.SlopeScene;

public class Main extends Game {
	public GameScreen slopeScene;
	public LandingScreen landingScreen;
	public SettingScreen settingScreen;
	
	@Override
	public void create () {
		landingScreen = new LandingScreen(this);
		slopeScene = new SlopeScene(this);
		//settingScreen = new SettingScreen(this);
        setScreen(slopeScene);
	}
}
