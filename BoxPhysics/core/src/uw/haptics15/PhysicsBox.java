package uw.haptics15;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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

	Vector2 getXY(float x, float y) {
		Vector3 v3 = camera.unproject(new Vector3(x, y, 0));
		return new Vector2(v3.x, v3.y);
	}
	@Override
	public void create () {
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(screenWidth, screenHeight);

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
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
