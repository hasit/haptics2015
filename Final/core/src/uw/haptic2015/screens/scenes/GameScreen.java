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
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import uw.haptic2015.Main;

/**
 * Created by jerrychen on 6/13/15.
 */
public class GameScreen implements Screen {

    Main main;
    SpriteBatch batch;
    Texture img;
    DetectCollision collisionDetection;
    World world;
    Body boxBody;

    Sprite playButton, settingsButton;

    Body ground;
    Body leftEdge;
    Body rightEdge;
    Body topEdge;

    Sprite sprite;

    Camera camera;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;

    boolean physicsEnabled = false;

    final float SCALE = 100f;
    final float MARGIN = 10f;
    float screenWidth;
    float screenHeight;

    public GameScreen(Main main){
        this.main = main;

    }

    Vector2 getXY(float x, float y) {
        Vector3 v3 = camera.unproject(new Vector3(x, y, 0));
        return new Vector2(v3.x, v3.y);
    }

    boolean playTouched(int screenX, int screenY) {
        Vector2 touchPoint = getXY(screenX, screenY);

        System.out.println("Touched: " + screenX + ", " + screenY);
        System.out.println("Translated: " + touchPoint.x + ", " + touchPoint.y);
        System.out.println("Play at: " + playButton.getX() + ", " + playButton.getY());

        if(playButton.getBoundingRectangle().contains(touchPoint)) {
            return true;
        }
        return false;
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

        world = new World(new Vector2(0, -5f), true);

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        System.out.println("W: " + screenWidth + ", H: " + screenHeight);

        camera = new OrthographicCamera(screenWidth, screenHeight);

        //Box
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,0);

        boxBody = world.createBody(bodyDef);

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(sprite.getWidth()/2 / SCALE, sprite.getHeight()/2 / SCALE);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.friction = 0.2f;
        fixtureDef.density = 0.1f;
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

        playButton = new Sprite(new Texture("play.png"));
        xy1 = getXY(150, 250);
        playButton.setPosition(xy1.x, xy1.y);

        collisionDetection = new DetectCollision(this);
        //Touch events
        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                if (playTouched(screenX, screenY)) {
                    physicsEnabled = ! physicsEnabled;
                }

                if (isTouched(screenX, screenY)) {
                    boxBody.setAngularVelocity(0);
                    boxBody.setLinearVelocity(0, 0);
                    boxBody.setActive(false);
                }

                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                boxBody.setActive(true);
                main.mtpad.turnOff();
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isTouched(screenX, screenY)) {

                    Vector2 newPos = getXY(screenX, screenY);
                    newPos.x /= SCALE;
                    newPos.y /= SCALE;

                    float lowerX = newPos.x - sprite.getWidth() / 2 / SCALE;
                    float lowerY = newPos.y - sprite.getHeight() / 2 / SCALE;
                    float upperX = newPos.x + sprite.getWidth() / 2 / SCALE;
                    float upperY = newPos.y + sprite.getHeight() / 2 / SCALE;

                    collisionDetection.collision = false;
                    collisionDetection.setPoint(newPos);
                    world.QueryAABB(collisionDetection, lowerX, lowerY, upperX, upperY);

                    if (!collisionDetection.collision)
                        boxBody.setTransform(newPos.x, newPos.y, 0);
                }

                main.mtpad.sendFriction(1);
                return true;
            }

        });
    }

    @Override
    public void render (float delta) {
        camera.update();
        if (physicsEnabled) {
            world.step(1f / 60f, 6, 2);
        }

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(SCALE, SCALE, 0);

        sprite.setPosition(boxBody.getPosition().x * SCALE - img.getWidth() / 2, boxBody.getPosition().y * SCALE - img.getHeight() / 2);
        sprite.setRotation((float) Math.toDegrees(boxBody.getAngle()));

        batch.begin();
        batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
        batch.draw(playButton, playButton.getX(), playButton.getY());
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

class DetectCollision implements QueryCallback {
    GameScreen pbox;
    Boolean collision;
    Vector2 touchPoint;

    public DetectCollision(GameScreen pbox) {
        this.pbox = pbox;
        collision = false;
    }

    public void setPoint(Vector2 point) {
        touchPoint = point;
    }

    @Override
    public boolean reportFixture(Fixture fixture) {
        if(fixture.getBody() != pbox.boxBody) {
            float w = pbox.sprite.getWidth()/2/pbox.SCALE;
            float h = pbox.sprite.getHeight()/2/pbox.SCALE;

            if(fixture.getBody() == pbox.ground || fixture.getBody() == pbox.leftEdge
                    || fixture.getBody() == pbox.rightEdge
                    || fixture.getBody() == pbox.topEdge) {
                collision = true;
            } else if (
                    fixture.testPoint(touchPoint.x - w, touchPoint.y - h)
                            || fixture.testPoint(touchPoint.x - w, touchPoint.y + h)
                            || fixture.testPoint(touchPoint.x + w, touchPoint.y - h)
                            || fixture.testPoint(touchPoint.x + w, touchPoint.y + h)
                    ) {
                collision = true;
            }
        }
        return collision;
    }
}
