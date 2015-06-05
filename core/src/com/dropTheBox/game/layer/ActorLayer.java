package com.dropTheBox.game.layer;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.dropTheBox.game.actor.Base;
import com.dropTheBox.game.actor.entity.Crate;
import com.dropTheBox.game.actor.entity.Entity;
import com.dropTheBox.game.actor.entity.Mob;
import com.dropTheBox.game.actor.item.Item;
import com.dropTheBox.game.actor.platform.Platform;
import com.dropTheBox.game.actor.player.Player;
import com.dropTheBox.scene.GameScene;
import com.dropTheBox.scene.GameScene.GameState;

public class ActorLayer extends Layer {

	public final World world;
	
	
	//TODO remove these
	Platform p;
	Item i;
	Player player;
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	OrthographicCamera camera;
	public Entity ea;
	
	public ActorLayer(GameScene scene, Batch batch){
		super(scene, Layer.WIDTH, Layer.HEIGHT, batch);
		world = new World(new Vector2(0, -10), true); 

		Load.load(getAssets());
		
		camera = new OrthographicCamera(getWidth(),getHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0); 
        camera.update();
        
		p = new Platform(); //TODO remove these
		stage.addActor(p);
		p.init(this, -150, 0, 660, 30);
		
//		i = new Item();
//		stage.addActor(i);
//		i.init(this, 0, 30, 50);
		
		player = new Player();
		player.init(this,200, 30);
		stage.addActor(player);
		
//		Crate crate = new Crate();
//		crate.init(this, 0, 30);
//		stage.addActor(crate);
		
		Mob mob = new Mob();
		mob.init(this, 100, 30);
		stage.addActor(mob);
	}

	@Override
	public void update(float dt) {
		if(gs.getState() == GameState.Running){
			world.step(dt, 6, 2);
			stage.act(dt);
			
		}
	}

	@Override
	public void draw() {
		GameState state = gs.getState();		
		if(state == GameState.Running || state == GameState.CountDown || state == GameState.Pause)
			stage.draw();
		debugRenderer.render(world, camera.combined);
	}



	@Override
	public InputProcessor getProcessor() {
		InputMultiplexer m = new InputMultiplexer();
		m.addProcessor(stage);
		m.addProcessor(player);
		return m;
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
		am.load("game/mob_1.png", Texture.class);
		am.load("game/mob_2.png", Texture.class);
		am.load("game/mob_3.png", Texture.class);
		am.load("game/mob_4.png", Texture.class);
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