package com.mygdx.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.mygdx.drop.Bucket;
import com.mygdx.drop.Constants;
import com.mygdx.drop.DropGame;
import com.mygdx.drop.Raindrop;
import com.mygdx.drop.RaindropPool;

public class GameScreen implements Screen {

	final DropGame game;

	Texture dropImage;
	Texture bucketImage;
	Sound dropSound;
	Music rainMusic;
	OrthographicCamera camera;
	SpriteBatch batch;
	Bucket bucket;
	Array<Raindrop> raindrops = new Array<>();

	RaindropPool raindropPool;

	int dropsGathered;

	public GameScreen(final DropGame game) {
		this.game = game;

		// load the images for the droplet and the bucket, 64x64 pixels each
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		// load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		// start the playback of the background music immediately
		rainMusic.setLooping(true);
		rainMusic.play();

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		// create a Rectangle to logically represent the bucket
		bucket = new Bucket();

		// create raindrops
		raindropPool = new RaindropPool(5);
		spawnRaindrop();
	}

	private void spawnRaindrop() {
		Raindrop raindrop = raindropPool.obtain();
		raindrop.init(dropImage.getWidth(), dropImage.getHeight());
		raindrops.add(raindrop);
	}

	@Override
	public void render(float delta) {
		// clear screen
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// draw background
		batch.begin();
		batch.draw(background, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		batch.end();

		// spawn raindrops
		spawnTimer += delta;
		if (spawnTimer > SPAWN_INTERVAL) {
			spawnTimer = 0;
			if (raindropPool.size() < MAX_DROPS) {
				Raindrop raindrop = raindropPool.obtain();
				raindrop.reset(MathUtils.random(Constants.RAINDROP_WIDTH, Constants.SCREEN_WIDTH - Constants.RAINDROP_WIDTH),
						Constants.SCREEN_HEIGHT);
			}
		}

		// handle input
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			bucket.x -= Constants.BUCKET_SPEED * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			bucket.x += Constants.BUCKET_SPEED * Gdx.graphics.getDeltaTime();
		}

		// ensure bucket stays within screen bounds
		if (bucket.x < 0) {
			bucket.x = 0;
		}
		if (bucket.x > Constants.SCREEN_WIDTH - Constants.BUCKET_WIDTH) {
			bucket.x = Constants.SCREEN_WIDTH - Constants.BUCKET_WIDTH;
		}

		// update raindrops and check for collision with bucket
		for (Raindrop raindrop : raindropPool.getActiveItems()) {
			raindrop.update(delta);
			if (raindrop.getBoundingBox().overlaps(bucketBoundingBox())) {
				dropsGathered++;
				raindropPool.free(raindrop);
			}
		}

		// draw bucket and raindrops
		batch.begin();
		batch.draw(bucketImage, bucket.x, bucket.y, Constants.BUCKET_WIDTH, Constants.BUCKET_HEIGHT);
		for (Raindrop raindrop : raindropPool.getActiveItems()) {
			batch.draw(dropImage, raindrop.getPosition().x - Constants.RAINDROP_WIDTH / 2,
					raindrop.getPosition().y - Constants.RAINDROP_HEIGHT / 2, Constants.RAINDROP_WIDTH,
					Constants.RAINDROP_HEIGHT);
		}

		// draw score
		font.draw(batch, "Drops Collected: " + dropsGathered, Constants.SCORE_X_OFFSET, Constants.SCORE_Y_OFFSET);
		batch.end();
	}

	private Rectangle bucketBoundingBox() {
		return new Rectangle(bucket.x, bucket.y, Constants.BUCKET_WIDTH, Constants.BUCKET_HEIGHT);
	}

	// unused interface methods
	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		dropImage.dispose();
		bucketImage.dispose();
		background.dispose();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void show() {}
}