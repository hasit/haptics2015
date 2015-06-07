package uw.haptics15;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

public class PhysicsBox extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	World world;
	Body boxBody;

    Body ground;
    Body leftEdge;
    Body rightEdge;
    Body topEdge;
    Body slope;

    Body controller;

	Sprite sprite;

	Camera camera;
	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;

	final float SCALE = 100f;
	float screenWidth;
	float screenHeight;

    DetectCollision collisionDetection;

	Vector2 getXY(float x, float y) {
		Vector3 v3 = camera.unproject(new Vector3(x, y, 0));
		return new Vector2(v3.x, v3.y);
	}

	boolean isTouched(int screenX, int screenY) {
		Vector3 touchPoint = new Vector3(screenX, screenY, 0);
		camera.unproject(touchPoint);
        touchPoint.x /= SCALE;
        touchPoint.y /= SCALE;

		Vector2 relativePos = boxBody.getLocalPoint(new Vector2(touchPoint.x, touchPoint.y));

		if (Math.abs(relativePos.x) <= sprite.getWidth()/2/SCALE && Math.abs(relativePos.y) <= sprite.getHeight()/2/SCALE) {
			return true;
		}
		return false;
	}

	@Override
	public void create () {
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(screenWidth, screenHeight);

		batch = new SpriteBatch();
		img = new Texture("uw.jpg");
		sprite = new Sprite(img);

		world = new World(new Vector2(0, -5f), true);

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
		Vector2 xy1 = getXY(50, screenHeight - 50);
		Vector2 xy2 = getXY(screenWidth - 50, screenHeight - 50);
		edge.set(xy1.x/SCALE, xy1.y/SCALE, xy2.x/SCALE, xy2.y/SCALE);
		edgeFixture.shape = edge;
		ground = world.createBody(edgeDef);
		ground.createFixture(edgeFixture);
		edge.dispose();

        //Left edge
        edge = new EdgeShape();
        xy1 = getXY(50, screenHeight - 50);
        xy2 = getXY(50, 50);
        edge.set(xy1.x/SCALE, xy1.y/SCALE, xy2.x/SCALE, xy2.y/SCALE);
        edgeFixture.shape = edge;
        leftEdge = world.createBody(edgeDef);
        leftEdge.createFixture(edgeFixture);
        edge.dispose();

        //Right edge
        edge = new EdgeShape();
        xy1 = getXY(screenWidth - 50, screenHeight - 50);
        xy2 = getXY(screenWidth - 50, 50);
        edge.set(xy1.x/SCALE, xy1.y/SCALE, xy2.x/SCALE, xy2.y/SCALE);
        edgeFixture.shape = edge;
        rightEdge = world.createBody(edgeDef);
        rightEdge.createFixture(edgeFixture);
        edge.dispose();

        //Top edge
        edge = new EdgeShape();
        xy1 = getXY(50, 50);
        xy2 = getXY(screenWidth - 50, 50);
        edge.set(xy1.x/SCALE, xy1.y/SCALE, xy2.x/SCALE, xy2.y/SCALE);
        edgeFixture.shape = edge;
        topEdge = world.createBody(edgeDef);
        topEdge.createFixture(edgeFixture);
        edge.dispose();

        //Slope

        BodyDef slopeDef = new BodyDef();
        slopeDef.type = BodyDef.BodyType.StaticBody;
        slopeDef.position.set(0,0);

        slope = world.createBody(slopeDef);

        PolygonShape slopeShape = new PolygonShape();
        Vector2 tr1 = getXY(screenWidth/2, screenHeight-50); tr1.x /= SCALE; tr1.y /= SCALE;
        Vector2 tr2 = getXY(screenWidth-50, screenHeight-50); tr2.x /= SCALE; tr2.y /= SCALE;
        Vector2 tr3 = getXY(screenWidth-50, screenHeight/2); tr3.x /= SCALE; tr3.y /= SCALE;

        Vector2[] triangle = {tr1, tr2, tr3};
        slopeShape.set(triangle);

        FixtureDef triangleFixtureDef = new FixtureDef();
        triangleFixtureDef.shape = slopeShape;
        triangleFixtureDef.friction = 0.2f;
        triangleFixtureDef.density = 0.1f;
        triangleFixtureDef.restitution = 0.2f;

        slope.createFixture(triangleFixtureDef);
        slopeShape.dispose();

		debugRenderer = new Box2DDebugRenderer();

        collisionDetection = new DetectCollision(this);
		//Touch events
		Gdx.input.setInputProcessor(new InputAdapter() {

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				if(isTouched(screenX, screenY)) {
                    boxBody.setAngularVelocity(0);
                    boxBody.setLinearVelocity(0,0);
                    boxBody.setActive(false);
                }

				return true;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				boxBody.setActive(true);
				return true;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
                if(isTouched(screenX, screenY)) {

                    Vector2 newPos = getXY(screenX, screenY);
                    newPos.x /= SCALE; newPos.y /= SCALE;

                    float lowerX = newPos.x - sprite.getWidth()/2/SCALE;
                    float lowerY = newPos.y - sprite.getHeight()/2/SCALE;
                    float upperX = newPos.x + sprite.getWidth()/2/SCALE;
                    float upperY = newPos.y + sprite.getHeight()/2/SCALE;

                    collisionDetection.collision = false;
                    collisionDetection.setPoint(newPos);
                    world.QueryAABB(collisionDetection, lowerX, lowerY, upperX, upperY);

                    if (!collisionDetection.collision)
                        boxBody.setTransform(newPos.x, newPos.y, 0);
                }
				return true;
			}

		});
	}

	@Override
	public void render () {
		camera.update();
		world.step(1f / 60f, 6, 2);

		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		debugMatrix = batch.getProjectionMatrix().cpy().scale(SCALE, SCALE, 0);

		sprite.setPosition(boxBody.getPosition().x * SCALE - img.getWidth() / 2, boxBody.getPosition().y*SCALE - img.getHeight() / 2);
		sprite.setRotation((float) Math.toDegrees(boxBody.getAngle()));

		batch.begin();
		batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
		batch.end();

		debugRenderer.render(world, debugMatrix);
	}

	@Override
	public void dispose() {
		img.dispose();
		world.dispose();
	}
}

class DetectCollision implements QueryCallback {
    PhysicsBox pbox;
    Boolean collision;
    Vector2 touchPoint;

    public DetectCollision(PhysicsBox pbox) {
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