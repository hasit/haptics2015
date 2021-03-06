package uw.haptic2015.screens.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import uw.haptic2015.Main;

/**
 * Created by jerrychen on 6/13/15.
 */

public class SlopeScene extends GameScreen {
    Body slope;
    float angle; //In degrees. In range [1,45]

    //Coordinates
    Vector2 tr1, tr2, tr3;

    //For rendering the slope
    ShapeRenderer shapeRenderer;

    public SlopeScene(Main main){
        super(main);
    }

    public void create(){
        super.create();

        angle = main.config.getSlopeAngle();

        //Slope
        BodyDef slopeDef = new BodyDef();
        slopeDef.type = BodyDef.BodyType.StaticBody;
        slopeDef.position.set(0,0);

        slope = super.world.createBody(slopeDef);

        PolygonShape slopeShape = new PolygonShape();
        tr1 = getXY(screenWidth/2, screenHeight-MARGIN); tr1.x /= SCALE; tr1.y /= SCALE;
        tr2 = getXY(screenWidth-MARGIN, screenHeight-MARGIN); tr2.x /= SCALE; tr2.y /= SCALE;

        //Compute tr3 with angle
        float d = (float)((tr2.x - tr1.x)*Math.tan(angle * Math.PI / 180));
        tr3 = new Vector2(tr2.x, tr2.y + d);

        Vector2[] triangle = {tr1, tr2, tr3};
        slopeShape.set(triangle);

        FixtureDef triangleFixtureDef = new FixtureDef();
        triangleFixtureDef.shape = slopeShape;
        triangleFixtureDef.friction = 0.2f;
        triangleFixtureDef.density = 0.1f;
        triangleFixtureDef.restitution = 0.2f;

        slope.createFixture(triangleFixtureDef);
        slopeShape.dispose();

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void drawConfigValues() {
        super.drawConfigValues();
        font.draw(batch, String.format("Slope: %.0f %s", main.config.slopeAngle, main.config.slopeUnit), -screenWidth/8, 2*screenHeight/12);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.triangle(tr1.x*SCALE, tr1.y*SCALE, tr2.x*SCALE, tr2.y*SCALE, tr3.x*SCALE, tr3.y*SCALE);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
    }
}