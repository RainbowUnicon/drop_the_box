package com.dropTheBox.game.layer;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.dropTheBox.game.ActorFactory;
import com.dropTheBox.game.Camera;
import com.dropTheBox.game.ItemFactory;
import com.dropTheBox.game.LevelManager;
import com.dropTheBox.game.PlatformFactory;
import com.dropTheBox.game.entity.EntityData;
import com.dropTheBox.game.entity.Platform;
import com.dropTheBox.game.entity.player.Player;
import com.dropTheBox.scene.GameScene;
import com.dropTheBox.scene.GameScene.GameState;
import com.dropTheBox.utils.Base;

public class ActorLayer extends Layer {
	public static final float PLATFORMGAP = 80; 
	public static final float HOLESIZE = 60;
	public static final float WIDTHBUFFER = 80;
	public static final float DESTRUCTIONBUFFER = 100;
	public static final float CREATIONBUFFER = 100;
	
	public final World world;	
	private Camera camera;
	private Player player;

	private final PlatformFactory platformFactory;
	private final ItemFactory itemFactory;
	private final ActorFactory entityFactory;

	private final Array<Base> entityList;
	private final Array<EntityData> entityDataQueue;
	
	boolean goNext = false;
	int yPos = 300;
	
	private boolean Box2DDebugRender = true;
	private Box2DDebugRenderer box2DDebugRenderer;
	private Matrix4 box2DDebugProjection;
	
	public ActorLayer(GameScene scene, Batch batch){
		super(scene, batch);
		
		Gdx.app.log("Drop The Box", "Loading asset for game");

		Gdx.app.log("Drop The Box", "Creating Stage");
		camera = new Camera(getWidth(), getHeight());
		stage.getViewport().setCamera(camera);

		Gdx.app.log("Drop The Box", "Creating World");
		world = new World(new Vector2(0, -30), true);
		world.setContactListener(new MyContactListener());
		


		Gdx.app.log("Drop The Box", "Creating Player");
		player = new Player(this);
		player.init(182, 500);
		
		Gdx.app.log("Drop The Box", "Setting camera moving speed");
		camera.setYVelocity(-175); 
	
		
		Pools.set(EntityData.class, new Pool<EntityData>(){
			@Override
			protected EntityData newObject() {
				return new EntityData();
			}
		});
		
		
		Gdx.app.log("Drop The Box", "Creating Factories");
		entityFactory = new ActorFactory(this);
		platformFactory = new PlatformFactory(this);
		itemFactory = new ItemFactory(this);
		
		Gdx.app.log("Drop The Box", "Finishing up");
		entityList = new Array<Base>();
		entityDataQueue = new Array<EntityData>();
		
		next();
		
		Gdx.app.debug("Drop The Box","Setting Debuggers for game");
		box2DDebugRenderer = new Box2DDebugRenderer();
		box2DDebugProjection = new Matrix4();
		Vector3 scale = new Vector3();
		scale.x = 1 / (getWidth() / 2) * Base.WORLDSCALE;
		scale.y = 1 / (getHeight() / 2) * Base.WORLDSCALE;
		box2DDebugProjection.setToScaling(scale);
	}

	@Override
	public void act(float dt) {
		if(getStartPoint() > calCreationPoint()){
			while(entityDataQueue.size >0){
				EntityData data = entityDataQueue.removeIndex(0);
				Platform platform = platformFactory.generate(data);
				Pools.free(data);
				entityList.add(platform);
			}
			next();
		}

		float desPoint = this.calDestructionPoint();
		int size = entityList.size;
		for(int i = size - 1; i >=0; i --){
			if(entityList.get(i).getY() > desPoint){
				Pools.free(entityList.get(i));
				entityList.removeIndex(i);
			}
		}
		if(getState() == GameState.Running){
			camera.update(dt);
			world.step(dt, 6, 2);
			stage.act(dt);
		}
	}
	
	@Override
	public void draw() {	
		stage.draw();
		if(Box2DDebugRender){
			box2DDebugProjection.setToOrtho2D(camera.getX() / Base.WORLDSCALE, camera.getY() / Base.WORLDSCALE, getWidth() / Base.WORLDSCALE, getHeight() / Base.WORLDSCALE);
			box2DDebugRenderer.render(world, box2DDebugProjection);
		}
	}



	protected float calDestructionPoint(){
		return camera.getY() + this.getHeight() + DESTRUCTIONBUFFER;
	}

	private float calCreationPoint(){
		return camera.getY() - CREATIONBUFFER;
	}


	public void next(){
		yPos -= PLATFORMGAP;
		createPlatforms();
	}

	public float getStartPoint(){
		return yPos;
	}

	Random r = new Random(System.currentTimeMillis());
	public void createPlatforms(){
		
		EntityData data = Pools.obtain(EntityData.class);
		int holePos = (int)(r.nextGaussian() * 10);
		if(holePos < 0 ) holePos = -holePos;
		System.out.println(holePos);
		data.type = EntityData.Type.PLATFORM;
		data.name = "normalPlatform";
		data.xPos = (float) holePos * 20;
		data.yPos = (float) yPos;
		data.width =  280f;
		data.height = 20f;
		entityDataQueue.add(data);
		
//		if(MathUtils.random(4) == 0){
//			EntityData data1 = Pools.obtain(EntityData.class);
//			EntityData data2 = Pools.obtain(EntityData.class);
//			int holePos = MathUtils.random(0,18);
//			int width = MathUtils.random(3, 7);
//			data1.type = EntityData.Type.PLATFORM;
//			data1.name = "normalPlatform";
//			data1.xPos = (float) holePos * 20;
//			data1.yPos = (float) yPos;
//			data1.width =  width * 20f;
//			data1.height = 20f;
//			entityDataQueue.add(data1);
//			
//			data2.set(data1);
//			data2.xPos = (float) holePos * 20 + width * 20f + 80;
//			data2.width = (float) 200 - width * 20f;
//			entityDataQueue.add(data2);
//
//		}
//		else{
//			EntityData data = Pools.obtain(EntityData.class);
//			int holePos = MathUtils.random(0,18); 
//			data.type = EntityData.Type.PLATFORM;
//			data.name = "normalPlatform";
//			data.xPos = (float) holePos * 20;
//			data.yPos = (float) yPos;
//			data.width =  280f;
//			data.height = 20f;
//			entityDataQueue.add(data);
//		}
	}

	@Override
	public InputProcessor getProcessor() {
		InputMultiplexer m = new InputMultiplexer();
		m.addProcessor(stage);
		m.addProcessor(player);
		return m;
	}

	public Camera getCamera(){
		return camera;
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