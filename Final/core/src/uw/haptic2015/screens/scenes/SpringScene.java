package uw.haptic2015.screens.scenes;

import com.badlogic.gdx.math.Vector2;

import uw.haptic2015.Main;

/**
 * Created by jerrychen on 6/13/15.
 */
public class SpringScene extends GameScreen{

    public SpringScene(Main main){
        super(main);
    }

    @Override
    public Vector2 getJointTarget(int screenX, int screenY) {
        Vector2 newPos = getXY(screenX, screenY);

        //Stay on the ground
        newPos.x /= SCALE;
        newPos.y = boxBody.getWorldCenter().y;

        return newPos;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        float d = boxBody.getWorldCenter().x;
        float force = - main.config.getSpringCoefficient() * d;

        boxBody.applyForceToCenter(force, 0, true);
    }
}
