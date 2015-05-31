package com.dropTheBox.game.layer;

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
import com.dropTheBox.game.actor.platform.Platform;
import com.dropTheBox.game.actor.player.Player;
import com.dropTheBox.scene.GameScene;
import com.dropTheBox.scene.GameScene.GameState;

public class ActorLayer extends Layer {
	public static final float scale = 3f;
	public final World world;
	
	
	//TODO remove these
	Platform p;
	Player i;
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	OrthographicCamera camera;
	
	public ActorLayer(GameScene scene, Batch batch){
		super(scene, Layer.WIDTH, Layer.HEIGHT, batch);
		world = new World(new Vector2(0, -10), true); 

		Load.load(getAssets());
		
		camera = new OrthographicCamera(this.getViewport().getWorldWidth(),this.getViewport().getWorldHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        
		p = new Platform(); //TODO remove these
		p.init(this, 0, 0, 360, 25);
		this.addActor(p);
		
		i = new Player();
		i.init(this,0, 620);
		this.addActor(i);
	}
	
	
	
	@Override
	public void act(float dt) {
		if(gs.getState() == GameState.Running){
			world.step(dt, 6, 2);
			super.act(dt);
		}
	}

	@Override
	public void draw() {
		GameState state = gs.getState();		
		if(state == GameState.Running || state == GameState.CountDown || state == GameState.Pause)
			super.draw();
		debugRenderer.render(world, camera.combined);
	}
}



class Load{
	public static void load(AssetManager am){
		am.load("platform.png",Texture.class);
		am.load("player.png", Texture.class);
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