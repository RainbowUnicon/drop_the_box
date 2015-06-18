package com.dropTheBox.game.layer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pools;
import com.dropTheBox.game.Camera;
import com.dropTheBox.game.LevelManager;
import com.dropTheBox.game.entity.player.Player;
import com.dropTheBox.scene.GameScene;
import com.dropTheBox.scene.GameScene.GameState;
import com.dropTheBox.utils.Base;

public class ActorLayer extends Layer {
	public final World world;	
	private Camera camera;
	private Player player;


	private final LevelManager levelManager;


	//TODO remove these
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	OrthographicCamera pcamera;

	public ActorLayer(GameScene scene){
		super(scene, new SpriteBatch());
		
		Gdx.app.log("Drop The Box", "Loading asset for game");
		Load.load(getAssets());

		Gdx.app.log("Drop The Box", "Creating Stage");
		camera = new Camera(getWidth(), getHeight());
		stage.getViewport().setCamera(camera);

		Gdx.app.log("Drop The Box", "Creating World");
		world = new World(new Vector2(0, -50), true);
		world.setContactListener(new MyContactListener());
		
		Gdx.app.log("Drop The Box", "Setting Level");
		levelManager = new LevelManager(this);
		
		Gdx.app.debug("Drop The Box","Setting Debuggers for game");
		pcamera = new OrthographicCamera(getWidth(),getHeight());
		pcamera.position.set(pcamera.viewportWidth / 2f / Base.WORLDSCALE, pcamera.viewportHeight / 2f / Base.WORLDSCALE, 0); 
		pcamera.zoom = 1f / Base.WORLDSCALE;
		pcamera.update();

		Gdx.app.log("Drop The Box", "Creating Player");
		player = new Player(this);
		player.init(182, 500);
		

		

	}

	@Override
	public void act(float dt) {
		if(getState() == GameState.Running){
			camera.update(dt);
			world.step(dt, 6, 2);
			levelManager.act(dt);
			pcamera.position.set(pcamera.viewportWidth / 2f / Base.WORLDSCALE, (pcamera.viewportHeight / 2f + camera.getY()) / Base.WORLDSCALE, 0); 
			pcamera.update();
			stage.act(dt);
		}
	}

	@Override
	public void draw() {
		GameState state = getState();	
		if(state == GameState.Running || state == GameState.CountDown || state == GameState.Pause)
			stage.draw();
		this.debugRenderer.render(world, pcamera.combined);
		stage.getBatch().flush();
	}



	@Override
	public InputProcessor getProcessor() {
		InputMultiplexer m = new InputMultiplexer();
		m.addProcessor(stage);
		m.addProcessor(player);
		return m;
	}

	@Override
	public void dispose(){
		super.dispose();
		levelManager.dispose();
	}

	public Camera getCamera(){
		return camera;
	}
}








class Load{
	public static void load(AssetManager am){
		TextureParameter param = new TextureParameter();
		param.genMipMaps = true; 
		param.magFilter = TextureFilter.Linear;
		param.minFilter = TextureFilter.Linear;
		am.load("background/test.png",Texture.class, param);
		am.load("game/base.png",Texture.class, param);
		am.load("game/crate.png",Texture.class, param);
		am.load("game/item.png", Texture.class, param);
		am.load("game/platform.png",Texture.class, param);
		am.load("game/mob_0.png", Texture.class, param);
		am.load("game/minion_1.png", Texture.class, param);
		am.load("game/bronzeCoin.png", Texture.class, param);
		am.load("game/silverCoin.png", Texture.class, param);
		am.load("game/goldCoin.png", Texture.class, param);
		am.load("game/platform.png", Texture.class, param);
		am.load("game/player.png", Texture.class, param);
		while(!am.update());
	}
}




class MyContactListener implements ContactListener{
	@Override
	public void beginContact(Contact contact) {
		Object a = contact.getFixtureA().getUserData();
		Object b = contact.getFixtureB().getUserData();
		if(a == null | b == null) return;
		((ContactListener)a).beginContact(contact);
		((ContactListener)b).beginContact(contact);
	}

	@Override
	public void endContact(Contact contact) {
		Object a = contact.getFixtureA().getUserData();
		Object b = contact.getFixtureB().getUserData();
		if(a == null | b == null) return;
		
		((ContactListener)a).endContact(contact);
		((ContactListener)b).endContact(contact);
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Object a = contact.getFixtureA().getUserData();
		Object b = contact.getFixtureB().getUserData();
		if(a == null | b == null) return;
		
		((ContactListener)a).preSolve(contact, oldManifold);
		((ContactListener)b).preSolve(contact, oldManifold);
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		Object a = contact.getFixtureA().getUserData();
		Object b = contact.getFixtureB().getUserData();
		if(a == null | b == null) return;
		((ContactListener)a).postSolve(contact, impulse);
		((ContactListener)b).postSolve(contact, impulse);
	}
}