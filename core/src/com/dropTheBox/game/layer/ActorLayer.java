package com.dropTheBox.game.layer;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.dropTheBox.game.Camera;
import com.dropTheBox.game.actor.Base;
import com.dropTheBox.game.actor.platform.NormalPlatform;
import com.dropTheBox.game.actor.player.Player;
import com.dropTheBox.scene.GameScene;
import com.dropTheBox.scene.GameScene.GameState;

public class ActorLayer extends Layer {
	public final World world;
	
	private Camera camera;
	

	//private final FloorManager floorManager;
	
	
	//TODO remove these
	Player player;
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	OrthographicCamera pcamera;
	
	
	public ActorLayer(GameScene scene){
		super(scene, new SpriteBatch());
		Load.load(getAssets());
		
		camera = new Camera(getWidth(), getHeight());

		stage.getViewport().setCamera(camera);
		world = new World(new Vector2(0, -40), true); //TODO Set gravity constant
		world.setContactListener(new MyContactListener());

		//floorManager = new FloorManager(this);
		
		
		pcamera = new OrthographicCamera(getWidth(),getHeight());
        pcamera.position.set(pcamera.viewportWidth / 2f, pcamera.viewportHeight / 2f, 0); 
        pcamera.update();
        
		
		player = new Player(this);
		player.init(320, 400);
		player.setRotation(90);
		

		
		NormalPlatform p = new NormalPlatform(this);
		p.init(-100,0,100f);
		

		NormalPlatform p1 = new NormalPlatform(this);
		p1.init(100f,0,260f);
	}

	@Override
	public void act(float dt) {
		if(getState() == GameState.Running){
			camera.update(dt);
			
			world.step(dt, 6, 2);
			
			//floorManager.update(dt);
			stage.act(dt);
		}
	}

	@Override
	public void draw() {
		GameState state = getState();		
		if(state == GameState.Running || state == GameState.CountDown || state == GameState.Pause)
			stage.draw();
		debugRenderer.render(world, pcamera.combined);
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
		//floorManager.dispose();
	}
	
	public Camera getCamera(){
		return camera;
	}
}








class Load{
	public static void load(AssetManager am){
		am.load("background/test.png",Texture.class);
		am.load("game/base.png",Texture.class);
		am.load("game/crate.png",Texture.class);
		am.load("game/item.png", Texture.class);
		am.load("game/platform.png",Texture.class);
		am.load("game/player_f.png", Texture.class);
		am.load("game/player_l.png", Texture.class);
		am.load("game/player_r.png", Texture.class);
		am.load("game/mob_0.png", Texture.class);
		am.load("game/minion_1.png", Texture.class);
		am.load("game/bronzeCoin.png", Texture.class);
		am.load("game/silverCoin.png", Texture.class);
		am.load("game/goldCoin.png", Texture.class);
		while(!am.update());
	}
}




class MyContactListener implements ContactListener{
	@Override
	public void beginContact(Contact contact) {
			((Base)contact.getFixtureA().getUserData()).beginContact(contact);
			((Base)contact.getFixtureB().getUserData()).beginContact(contact);
	}

	@Override
	public void endContact(Contact contact) {
		((Base)contact.getFixtureA().getUserData()).endContact(contact);
		((Base)contact.getFixtureB().getUserData()).endContact(contact);
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		((Base)contact.getFixtureA().getUserData()).preSolve(contact, oldManifold);
		((Base)contact.getFixtureB().getUserData()).preSolve(contact, oldManifold);
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		((Base)contact.getFixtureA().getUserData()).postSolve(contact, impulse);
		((Base)contact.getFixtureB().getUserData()).postSolve(contact, impulse);
	}
}