package uw.haptic2015.screens.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

import uw.haptic2015.Main;

/**
 * Created by jerrychen on 6/13/15.
 */
public class GameScreen extends InputAdapter implements Screen {

    Main main;
    SpriteBatch batch;
    Texture img;

    World world;
    Body boxBody;

    //Sprite settingsButton;

    Body ground;
    Body leftEdge;
    Body rightEdge;
    Body topEdge;

    Sprite sprite;

    Camera camera;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;

    final float SCALE = 100f;
    final float MARGIN = 10f;
    float screenWidth;
    float screenHeight;

    MouseJointDef jointDef;
    MouseJoint joint;

    public GameScreen(Main main){
        this.main = main;

    }

    Vector2 getXY(float x, float y) {
        Vector3 v3 = camera.unproject(new Vector3(x, y, 0));
        return new Vector2(v3.x, v3.y);
    }

    boolean isTouched(int screenX, int screenY) {
        Vector2 touchPoint = getXY(screenX, screenY);
        touchPoint.x /= SCALE;
        touchPoint.y /= SCALE;

        Vector2 relativePos = boxBody.getLocalPoint(new Vector2(touchPoint.x, touchPoint.y));

        if (Math.abs(relativePos.x) <= sprite.getWidth()/2/SCALE && Math.abs(relativePos.y) <= sprite.getHeight()/2/SCALE) {
            return true;
        }
        return false;
    }

    public void create(){
        batch = new SpriteBatch();
        Pixmap pmap = new Pixmap(128,128,Pixmap.Format.RGBA8888);
        pmap.setColor(Color.BLUE);
        pmap.fillRectangle(0, 0, 128, 128);
        img = new Texture(pmap);
        img.draw(pmap, 0, 0);
        img.bind();

        sprite = new Sprite(img);

        world = new World(new Vector2(0, main.config.getGravity()), true);

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(screenWidth, screenHeight);

        //Box
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(-1,1);

        boxBody = world.createBody(bodyDef);

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(sprite.getWidth()/2 / SCALE, sprite.getHeight()/2 / SCALE);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.friction = main.config.getFrictionCoefficient();
        fixtureDef.density = main.config.getDensity();
        fixtureDef.restitution = 0.2f;

        boxBody.createFixture(fixtureDef);

        bodyShape.dispose();

        //Ground edge
        BodyDef edgeDef = new BodyDef();
        edgeDef.type = BodyDef.BodyType.StaticBody;
        edgeDef.position.set(0,0);
        FixtureDef edgeFixture = new FixtureDef();
        EdgeShape edge = new EdgeShape();
        Vector2 xy1 = getXY(MARGIN, screenHeight - MARGIN);
        Vector2 xy2 = getXY(screenWidth - MARGIN, screenHeight - MARGIN);
        edge.set(xy1.x/SCALE, xy1.y/SCALE, xy2.x/SCALE, xy2.y/SCALE);
        edgeFixture.shape = edge;
        ground = world.createBody(edgeDef);
        ground.createFixture(edgeFixture);
        edge.dispose();

        //Left edge
        edge = new EdgeShape();
        xy1 = getXY(MARGIN, screenHeight - MARGIN);
        xy2 = getXY(MARGIN, MARGIN);
        edge.set(xy1.x/SCALE, xy1.y/SCALE, xy2.x/SCALE, xy2.y/SCALE);
        edgeFixture.shape = edge;
        leftEdge = world.createBody(edgeDef);
        leftEdge.createFixture(edgeFixture);
        edge.dispose();

        //Right edge
        edge = new EdgeShape();
        xy1 = getXY(screenWidth - MARGIN, screenHeight - MARGIN);
        xy2 = getXY(screenWidth - MARGIN, MARGIN);
        edge.set(xy1.x/SCALE, xy1.y/SCALE, xy2.x/SCALE, xy2.y/SCALE);
        edgeFixture.shape = edge;
        rightEdge = world.createBody(edgeDef);
        rightEdge.createFixture(edgeFixture);
        edge.dispose();

        //Top edge
        edge = new EdgeShape();
        xy1 = getXY(MARGIN, MARGIN);
        xy2 = getXY(screenWidth - MARGIN, MARGIN);
        edge.set(xy1.x/SCALE, xy1.y/SCALE, xy2.x/SCALE, xy2.y/SCALE);
        edgeFixture.shape = edge;
        topEdge = world.createBody(edgeDef);
        topEdge.createFixture(edgeFixture);
        edge.dispose();

        debugRenderer = new Box2DDebugRenderer();

        //Touch events
        jointDef = new MouseJointDef();
        jointDef.bodyA = ground;
        jointDef.bodyB = boxBody;
        jointDef.collideConnected = true;
        jointDef.maxForce = 100;

        Gdx.input.setInputProcessor(this);
    }

    public Vector2 getJointTarget(int screenX, int screenY) {
        Vector2 newPos = getXY(screenX, screenY);
        newPos.x /= SCALE;
        newPos.y /= SCALE;

        return newPos;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (isTouched(screenX, screenY)) {
            jointDef.target.set(getJointTarget(screenX, screenY));
            joint = (MouseJoint) world.createJoint(jointDef);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        main.mtpad.turnOff();

        if(joint == null) return false;

        world.destroyJoint(joint);
        joint = null;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(joint == null) return false;

        joint.setTarget(getJointTarget(screenX, screenY));

        main.mtpad.sendFriction(1);
        return true;
    }

    @Override
    public void render (float delta) {
        camera.update();
        world.step(1f / 60f, 6, 2);

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(SCALE, SCALE, 0);

        sprite.setPosition(boxBody.getPosition().x * SCALE - img.getWidth() / 2, boxBody.getPosition().y * SCALE - img.getHeight() / 2);
        sprite.setRotation((float) Math.toDegrees(boxBody.getAngle()));

        batch.begin();
        batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
        batch.end();

        debugRenderer.render(world, debugMatrix);
    }
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // called when this screen is set as the screen with game.setScreen();
        create();
    }


    @Override
    public void hide() {
        // called when current screen changes from this to a different screen
    }


    @Override
    public void pause() {
    }


    @Override
    public void resume() {
    }


    @Override
    public void dispose() {
        // never called automatically
        img.dispose();
        world.dispose();
    }
}
