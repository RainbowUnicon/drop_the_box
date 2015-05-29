package com.dropTheBox.game.layer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dropTheBox.game.EventHandler;
import com.dropTheBox.scene.GameScene;

public class ActorLayer extends Layer {
	private final GameScene gs;
	private final World world;
	
	private Viewport viewport;
	private OrthographicCamera camera;
	
	public final static int WORLD_WIDTH = 120, WORLD_HEIGHT = 220;
	
	public ActorLayer(GameScene scene){
		gs = scene;
		world = new World(new Vector2(0, -10), true); 
		world.setContactListener(new MyContactListener());
		
		//set up camera and viewport
		camera = new OrthographicCamera();
		viewport = new StretchViewport(WORLD_WIDTH,WORLD_HEIGHT, camera);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.translate(WORLD_WIDTH/2, WORLD_HEIGHT/2);
	}
	@Override
	public void update() {
		float dt = Gdx.graphics.getDeltaTime();
		world.step(dt, 6, 2);
	}

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}
	public EventHandler getHandler() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	private class MyContactListener implements ContactListener{

		@Override
		public void beginContact(Contact contact) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void endContact(Contact contact) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void preSolve(Contact contact, Manifold oldManifold) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void postSolve(Contact contact, ContactImpulse impulse) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
