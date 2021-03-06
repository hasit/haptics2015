package uw.haptic2015;

import com.badlogic.gdx.Game;

import uw.haptic2015.screens.LandingScreen;
import uw.haptic2015.screens.SettingScreen;
import uw.haptic2015.screens.scenes.GameScreen;
import uw.haptic2015.screens.scenes.SlopeScene;
import uw.haptic2015.screens.scenes.SpringScene;
import uw.tpad.TPad;

public class Main extends Game {
	public GameScreen slopeScene;
	public GameScreen springScene;
	public GameScreen activeScreen;

	public LandingScreen landingScreen;
	public SettingScreen settingScreen;

	public Config config;

	public TPad mtpad;

	public Main(TPad mtpad) {
		this.mtpad = mtpad;
	}

	@Override
	public void create () {
		config = new Config();

		landingScreen = new LandingScreen(this);
		settingScreen = new SettingScreen(this);

		springScene = new SpringScene(this);
		slopeScene = new SlopeScene(this);

		setScreen(landingScreen);

	}
}