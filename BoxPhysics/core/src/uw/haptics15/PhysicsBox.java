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

		world = new World(new Vector2(0, -10f), true);

		//Box
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(0,0);

		boxBody = world.createBody(bodyDef);

		PolygonShape bodyShape = new PolygonShape();
		bodyShape.setAsBox(sprite.getWidth()/2 / SCALE, sprite.getHeight()/2 / SCALE);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = bodyShape;
		fixtureDef.density = 0.1f;
		fixtureDef.restitution = 0.6f;

		boxBody.createFixture(fixtureDef);

		bodyShape.dispose();

		//Edges
		BodyDef groundDef = new BodyDef();
		groundDef.type = BodyDef.BodyType.StaticBody;
		groundDef.position.set(0,0);

		FixtureDef groundFixture = new FixtureDef();

		EdgeShape groundEdge = new EdgeShape();

		Vector2 xy1 = getXY(0, (screenHeight - 50));
		Vector2 xy2 = getXY(screenWidth, (screenHeight - 50));

		//float x1 = 0, y1 = (screenHeight - 50) / WORLD_SCALE;
		//float x2 = screenWidth / WORLD_SCALE, y2  = y1;

		groundEdge.set(xy1.x/SCALE, xy1.y/SCALE, xy2.x/SCALE, xy2.y/SCALE);

		groundFixture.shape = groundEdge;

		ground = world.createBody(groundDef);
		ground.createFixture(groundFixture);

		groundEdge.dispose();

		debugRenderer = new Box2DDebugRenderer();

        collisionDetection = new DetectCollision(this);
		//Touch events
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				if(isTouched(screenX, screenY))
					boxBody.setActive(false);

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
                    world.QueryAABB(collisionDetection, lowerX-0.1f, lowerY-0.1f, upperX+0.1f, upperY+0.1f);

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

    public DetectCollision(PhysicsBox pbox) {
        this.pbox = pbox;
        collision = false;
    }
    @Override
    public boolean reportFixture(Fixture fixture) {
        if(fixture.getBody() != pbox.boxBody) {
            //System.out.println("Collision!" + fixture.getBody().toString());
            collision = true;
            return true;
        }
        return false;
    }
}