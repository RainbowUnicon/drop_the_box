package com.dropTheBox.game.actor;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dropTheBox.game.layer.ActorLayer;

public abstract class Base extends com.badlogic.gdx.scenes.scene2d.Actor implements Poolable, ContactListener{
	protected boolean initialized;

	private Body body;
	private Fixture fixture;
	private SafeBody sBody;
	private SafeFixture sFixture;
	
	public Base (){	
		initialized = false;
	}
	
	protected void init(ActorLayer layer,float x, float y, float w, float h){
		super.setWidth(w);
		super.setHeight(h);
		
		if(initialized){
			setSize(w,h);
		} else{
			World world = layer.world;
		
			body = world.createBody(createBodyDef());

			//create the default shape of the object 
			Shape shapeToBeDisposed = createShape();
			
			//create the fixture 
			fixture = body.createFixture(createFixtureDef(shapeToBeDisposed));
			fixture.setUserData(this);

			//dispose the shape
			shapeToBeDisposed.dispose();
			
			sBody = new SafeBody(body);
			sFixture = new SafeFixture(fixture);
			
			initialized = true;
		}
		setPosition(x,y);
	}
	
	@Override
	public void act(float dt){
		super.setX(body.getPosition().x - getWidth()/2);
		super.setY(body.getPosition().y - getHeight()/2);
		super.setRotation((float)Math.toDegrees(body.getAngle())); 
	}

	@Override
	public void moveBy(float x, float y){
		setPosition(getX() + x, getY() + y);
	}
	
	@Override
	public boolean remove(){
		throw new UnsupportedOperationException();
	}
	
	public void remove(World world){
		super.remove();
		world.destroyBody(body);
		body = null;
		this.dispose();
	}
	
	@Override
	public void rotateBy(float d){
		super.rotateBy(d);
		body.setTransform(getX(), getY(), body.getAngle() + (float) Math.toRadians(d));
	}
	
	@Override
	public void scaleBy(float scale){
		super.scaleBy(scale);
		//TODO
	}
	
	@Override
	public void scaleBy(float scaleX, float scaleY){
		super.scaleBy(scaleX, scaleY);
		//TODO
	}
	
	@Override
	public void setBounds(float x, float y, float w, float h){
		setPosition(x,y);
		setSize(w,h);
	}
	
	@Override
	public abstract void setHeight(float height);
	
	@Override
	public void setPosition(float x, float y){
		super.setPosition(x, y);
		body.setTransform(x + getWidth()/2, y + getHeight()/2, body.getAngle());
	}
	
	@Override
	public void setRotation(float d){
		super.setRotation(d);
		body.setTransform(body.getPosition(), (float) Math.toRadians(d));
	}
	
	@Override 
	public abstract void setSize(float w, float h);
	
	@Override
	public abstract void setWidth(float w);
	
	@Override
	public abstract void sizeBy(float x, float y);
	
	public SafeFixture getFixture(){
		return sFixture;
	}
	
	public SafeBody getBody(){
		return sBody;
	}

	

	@Override
	public void reset() {
		body.setAwake(false);
		body.setLinearVelocity(0,0);
		body.setAngularVelocity(0);
		setSize(0,0);
		setPosition(0,0);
		setRotation(0);
	}
	
	protected abstract FixtureDef createFixtureDef(Shape shape);
	
	protected abstract Shape createShape();
	
	protected abstract BodyDef createBodyDef();

	@Override
	public void beginContact (Contact contact){};

	@Override
	public void endContact (Contact contact){};

	@Override
	public void preSolve (Contact contact, Manifold oldManifold){};

	@Override
	public void postSolve (Contact contact, ContactImpulse impulse){};

	//This method will be called, when this object is disposed
	public void dispose(){}
}
