package com.dropTheBox.game.layer;

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
import com.dropTheBox.game.actor.Base;
import com.dropTheBox.game.actor.TestBase;
import com.dropTheBox.game.actor.entity.Crate;
import com.dropTheBox.game.actor.item.Coin;
import com.dropTheBox.game.actor.platform.Platform;
import com.dropTheBox.game.actor.player.Player;
import com.dropTheBox.scene.GameScene;
import com.dropTheBox.scene.GameScene.GameState;

public class ActorLayer extends Layer {
	public final World world;	
	private Camera camera;
	private Player player;


	//private final FloorManager floorManager;


	//TODO remove these
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	OrthographicCamera pcamera;
	Platform p;

	public ActorLayer(GameScene scene){
		super(scene, new SpriteBatch());
		Load.load(getAssets());

		camera = new Camera(getWidth(), getHeight());

		stage.getViewport().setCamera(camera);
		world = new World(new Vector2(0, -25), true);
		world.setContactListener(new MyContactListener());


		//floorManager = new FloorManager(this);


		createBorder();
		pcamera = new OrthographicCamera(getWidth(),getHeight());
		pcamera.position.set(pcamera.viewportWidth / 2f / Base.WORLDSCALE, pcamera.viewportHeight / 2f / Base.WORLDSCALE, 0); 
		pcamera.zoom = 4f / Base.WORLDSCALE;
		pcamera.update();

		player = new Player(this);
		player.init(182, 500);
		player.setRotation(45);

		
		p = new Platform(this);
		p.init(0,0,360f, 20f);

		Platform p1 = new Platform(this);
		p1.init(30,80,300f, 20f);
		
		Platform p2 = new Platform(this);
		p2.init(90,160,300f, 20f);
		
		Platform p3 = new Platform(this);
		p3.init(150,240,300f, 20f);
		
		Platform p4 = new Platform(this);
		p4.init(210,320,300f, 20f);
		
		Platform p5 = new Platform(this);
		p5.init(270,400,300f, 20f);
		
		Platform p6 = new Platform(this);
		p6.init(330,480,300f, 20f);
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

	protected void createBorder(){
		Body border = world.createBody(new BodyDef());
		ChainShape shape = new ChainShape();
		shape.createLoop(new float[]{0,0, getWidth() / Base.WORLDSCALE, 0, getWidth() / Base.WORLDSCALE, getHeight() / Base.WORLDSCALE, 0, getHeight() / Base.WORLDSCALE});
		Fixture fixture = border.createFixture(shape, 1);
		fixture.setSensor(true);
		fixture.getFilterData().categoryBits = 0x0000; //TODO no idea
		fixture.getFilterData().maskBits = ~0x0000;
		shape.dispose();
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
		am.load("game/player_f.png", Texture.class, param);
		am.load("game/player_l.png", Texture.class, param);
		am.load("game/player_r.png", Texture.class, param);
		am.load("game/mob_0.png", Texture.class, param);
		am.load("game/minion_1.png", Texture.class, param);
		am.load("game/bronzeCoin.png", Texture.class, param);
		am.load("game/silverCoin.png", Texture.class, param);
		am.load("game/goldCoin.png", Texture.class, param);
		am.load("game/platform.png", Texture.class, param);
		while(!am.update());
	}
}




class MyContactListener implements ContactListener{
	@Override
	public void beginContact(Contact contact) {
		Object a = contact.getFixtureA().getUserData();
		Object b = contact.getFixtureB().getUserData();
		if(a == null | b == null) return;
		((Base)a).beginContact(contact);
		((Base)b).beginContact(contact);
	}

	@Override
	public void endContact(Contact contact) {
		Object a = contact.getFixtureA().getUserData();
		Object b = contact.getFixtureB().getUserData();
		if(a == null | b == null) return;
		
		((Base)a).endContact(contact);
		((Base)b).endContact(contact);
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Object a = contact.getFixtureA().getUserData();
		Object b = contact.getFixtureB().getUserData();
		if(a == null | b == null) return;
		
		((Base)a).preSolve(contact, oldManifold);
		((Base)b).preSolve(contact, oldManifold);
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		Object a = contact.getFixtureA().getUserData();
		Object b = contact.getFixtureB().getUserData();
		if(a == null | b == null) return;
		((Base)a).postSolve(contact, impulse);
		((Base)b).postSolve(contact, impulse);
	}
}