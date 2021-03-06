package com.spbox2d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

public class SimplePhysicBox2d extends ApplicationAdapter {
	SpriteBatch batch;
	Sprite sprite;
	Texture img;
	World world;
	Body body;
    Body bodyEdgeScreen;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    OrthographicCamera camera;
    //BitmapFont font;

    private MouseJointDef jointDef;
    private MouseJoint joint;

    //float torque = 0.0f;
    boolean drawSprite = true;

    final float PIXELS_TO_METERS = 100f;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		//using the default image with movement enable
		img = new Texture("badlogic.jpg");
		sprite = new Sprite(img);

		//center the sprite in the top/middle of the screen
		sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);

		//create a physic world that holds all physics calculation
		world = new World(new Vector2(0, -20f), true);

		//create bodyDefinition that defines the physics objects type and position in the simulation
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;

		//1 in physics engine is 1 pixel, set our body to the same position as our sprite
		bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) / PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2) / PIXELS_TO_METERS);

		//create a body in the world with our definition
		body = world.createBody(bodyDef);

		//define dimension of physics shape
		PolygonShape shape = new PolygonShape();

		//set the physics polygon to a box with same dimensions as sprite
		shape.setAsBox(sprite.getWidth()/2 / PIXELS_TO_METERS, sprite.getHeight()/2 / PIXELS_TO_METERS);

		//fixture def holds density, restitution and others
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.1f;
        fixtureDef.restitution = 0.5f;

		body.createFixture(fixtureDef);
        shape.dispose();

        //bodyDef for edge
        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS - 50/PIXELS_TO_METERS;
        float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS - 50/PIXELS_TO_METERS;

        bodyDef2.position.set(0,0);
        FixtureDef fixtureDef2 = new FixtureDef();

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(-w/2, -h/2, w/2, -h/2);
        fixtureDef2.shape = edgeShape;

        bodyEdgeScreen = world.createBody(bodyDef2);
        bodyEdgeScreen.createFixture(fixtureDef2);
        edgeShape.dispose();

        jointDef = new MouseJointDef();
        jointDef.bodyB = body;
        jointDef.collideConnected = true;
        jointDef.maxForce = 500;

        Gdx.input.setInputProcessor(new InputAdapter() {
            private Vector3 tmp = new Vector3();
            private Vector2 tmp2 = new Vector2();

            private QueryCallback queryCallback = new QueryCallback() {

                @Override
                public boolean reportFixture(Fixture fixture) {
                    if(!fixture.testPoint(tmp.x, tmp.y))
                        return true;

                    jointDef.bodyB = fixture.getBody();
                    jointDef.target.set(tmp.x, tmp.y);
                    joint = (MouseJoint) world.createJoint(jointDef);
                    return false;
                }

            };
            //on touch we apply force from the direction of the users touch
            public boolean touchDown(int screenX, int screenY, int pointer, int button){
                System.out.println("TouchDown!");
                camera.unproject(tmp.set(screenX, screenY, 0));
                world.QueryAABB(queryCallback, tmp.x, tmp.y, tmp.x, tmp.y);
                return true;
            }

            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                System.out.println("TouchUp!");
                if(joint == null)
                    return false;

                world.destroyJoint(joint);
                joint = null;
                return true;
            }

            public boolean touchDragged(int screenX, int screenY, int pointer) {
                System.out.println("TouchDragged!");
                if(joint == null)
                    return false;

                camera.unproject(tmp.set(screenX, screenY, 0));
                joint.setTarget(tmp2.set(tmp.x, tmp.y));
                return true;
            }
        });

        debugRenderer = new Box2DDebugRenderer();
        //font = new BitmapFont();
        //font.setColor(Color.BLACK);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

    private float elapsed = 0;

	@Override
	public void render () {
        camera.update();
		//advance the world, by the amount of time that has elapsed since the last frame
		//upate rate to the frame rate, and vice versa
		world.step(1f / 60f, 6, 2);

        /*float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        if(sprite.getX() > w){
            sprite.setX(w - 50);
        } else if(sprite.getX() < 0){
            sprite.setX(50);
        }

        if(sprite.getY() > h){
            sprite.setY(h - 50);
        }*/


        /*if(Gdx.input.isTouched()) {
            //Vector3 touchPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            //camera.unproject(touchPosition);
            float x = -Gdx.input.getX();
            float y = -Gdx.input.getY();

            float w = sprite.getWidth();
            float h = sprite.getHeight();

            float newX = (-x - 1.5f * w) / PIXELS_TO_METERS;
            float newY = (y + 2.5f * h) / PIXELS_TO_METERS;

            float sX = body.getPosition().x - 1.5f*w;
            float sY = body.getPosition().y - 2.5f*h;

            System.out.println("Touched: " + x + ", " + y);
            System.out.println("Body at: " + sX + ", " + sY);
            //System.out.println("Body size: " + w + ", " + h);

            if(x > sX - w/2 && x < sX + w/2 && y > sY - h/2 && y < sY + h/2)
                body.setTransform(newX,newY, 0);

        }*/
		//update sprite position accordingly
		sprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) - sprite.getWidth()/2, (body.getPosition().y * PIXELS_TO_METERS) - sprite.getHeight()/2);

        sprite.setRotation((float) Math.toDegrees(body.getAngle()));

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 0);

		batch.begin();

        if(drawSprite)
		    batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(),sprite.getOriginY(),sprite.getWidth(),sprite.getHeight(),sprite.getScaleX(),sprite.getScaleY(),sprite.getRotation());

        //font.draw(batch, "Restitution: " + body.getFixtureList().first().getRestitution(), -Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

		batch.end();

        debugRenderer.render(world, debugMatrix);
	}

	@Override
	public void dispose(){
		img.dispose();
		world.dispose();
	}
}
