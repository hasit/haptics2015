package uw.haptic2015.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import uw.tpad.TPad;
import uw.tpad.TPadImpl;
import uw.haptic2015.Main;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		TPad mtpad = new TPadImpl(this.getContext());
		initialize(new Main(mtpad), config);
	}

}
