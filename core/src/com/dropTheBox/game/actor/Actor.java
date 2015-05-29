package com.dropTheBox.game.actor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dropTheBox.game.EventHandler;
import com.dropTheBox.game.event.GameEvent;
import com.dropTheBox.game.layer.ActorLayer;

public abstract class Actor implements Poolable, ContactListener{
	private EventHandler handler;
	protected boolean initialized;

	protected Shape shape;
	protected Body body;
	protected Fixture fixture;

	protected float width, height;

	public Actor (){	
		initialized = false;
	}


	//This method create the default shape of actor, which is a rectangle
	protected abstract Shape createShape();

	public abstract void setSize(float w, float h);

	//This method will be called for drawing this actor. By default, it doesn't contain any codes.
	public abstract void draw(SpriteBatch batch);

	//This method will be called for each frame to update the status of this object. dt is the time difference from each frame 
	public abstract void update(float dt);

	//destroy this platform
	public void destroy(World world){
		this.dispose();
		if(body != null){
			body.destroyFixture(fixture);
			world.destroyBody(body);
		}
		body = null;
	}

	//GETTERs
	public Body getBody(){
		return body;
	}
	public Fixture getFixture(){
		return fixture;
	}
	public float getWidth(){
		return width;
	}	
	public float getHeight(){
		return height;
	}	
	public float getX(){
		return body.getPosition().x - width/2;
	}
	public float getY(){
		return body.getPosition().y - height/2;
	}

	//SETTERs
	public void setPosition(float xPos, float yPos){
		body.setTransform(xPos + width/2, yPos -height/2, body.getAngle());
	}
	public void setTransform(float xPos, float yPos, float angle){
		body.setTransform(xPos + width/2,yPos - height/2, angle);
	}
	public void setRotation(float angle){
		body.setTransform(body.getPosition(), angle);
	}
	public void setLinearVelocity(float vx,float vy){
		body.setLinearVelocity(vx, vy);
	}
	public void setAngularVelocity(float va){
		body.setAngularVelocity(va);
	}

	@Override
	public void reset() {
		body.setAwake(false);
		setSize(0,0);
		setTransform(0,0,0);
		setLinearVelocity(0,0);
		setAngularVelocity(0);
	}

	public void init(ActorLayer layer, BodyType bt,float x, float y, float w, float h){
		if(initialized){
			setSize(w,h);
		} else{
			World world = layer.getWorld();
			handler = layer.getHandler();

			body = world.createBody(new BodyDef());

			//create the default shape of the object 
			Shape shapeToBeDisposed = createShape();

			//create the fixture 
			fixture = body.createFixture(createFixtureDef(shapeToBeDisposed));
			fixture.setUserData(this);

			//dispose the shape
			shapeToBeDisposed.dispose();
		}

		width = w;
		height = h;
		body.setTransform(x + width/2, y - height, 0);
	}
	
	public void fireEvent(GameEvent event){
		handler.add(event);
	}

	//This method create the default fixture definition of actor
	protected FixtureDef createFixtureDef(Shape shape){
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f; 
		fixtureDef.friction = 0f;
		return fixtureDef;
	}

	public void beginContact (Contact contact){};

	public void endContact (Contact contact){};

	public void preSolve (Contact contact, Manifold oldManifold){};

	public void postSolve (Contact contact, ContactImpulse impulse){};

	//This method will be called, when this object is disposed
	public void dispose(){}
}
