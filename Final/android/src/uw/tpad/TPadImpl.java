package uw.tpad;

import android.content.Context;

/**
 * Created by ashik on 6/17/15.
 */
public class TPadImpl implements TPad{
    private nxr.tpad.lib.TPad mtpad;

    public TPadImpl(Context context) {
        mtpad = new nxr.tpad.lib.TPadImpl(context);
    }

    public void turnOff() {
        mtpad.turnOff();
    }
    public void sendFriction(float f) {
        mtpad.sendFriction(f);
    }
}
