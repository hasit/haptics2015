package uw.haptic2015.screens.scenes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import uw.haptic2015.Main;

/**
 * Created by jerrychen on 6/13/15.
 */
public class SpringScene extends GameScreen{

    Sprite springSprite;

    Vector2 springLeft;
    Vector2 springRestPoint;

    public SpringScene(Main main) {
        super(main);
    }

    @Override
    public void create() {
        super.create();
        springSprite = new Sprite(new Texture("spring.png"));

        springLeft = getXY(MARGIN, screenHeight - MARGIN);
        springRestPoint = new Vector2(0, springLeft.y);

        springSprite.setPosition(springLeft.x - 10, springLeft.y);
    }

    @Override
    public Vector2 getJointTarget(int screenX, int screenY) {
        Vector2 newPos = getXY(screenX, screenY);

        //Stay on the ground
        newPos.x /= SCALE;
        newPos.y = boxBody.getWorldCenter().y;

        return newPos;
    }

    public float getSpringForce() {
        float d = boxBody.getWorldCenter().x;
        float force = - main.config.getSpringCoefficient() * d;

        return force;
    }

    @Override
    public float getResistance() {
        float res = main.config.getDensity() * main.config.getFrictionCoefficient();
        res += Math.abs(getSpringForce());
        res /= 2*(main.config.getSpringCoefficient() * screenWidth/2/SCALE);
        res += 0.5f;
        return res;
    }

    public float getSpringWidth() {
        float restWidth = Math.abs(springRestPoint.x - springLeft.x);
        return restWidth + boxBody.getWorldCenter().x * SCALE - sprite.getWidth()/2 + 30;
    }

    @Override
    public void drawSprites() {
        super.drawSprites();

        springSprite.setSize(getSpringWidth(), springSprite.getHeight());
        batch.draw(springSprite, springSprite.getX(), springSprite.getY(), springSprite.getWidth(), springSprite.getHeight());
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        boxBody.applyForceToCenter(getSpringForce(), 0, true);
    }
}
