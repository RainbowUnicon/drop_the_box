package com.dropTheBox.game.entity;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.Pools;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.utils.ShapeTransformer;

public abstract class Actor extends Entity {
	private final float WHEEL_RAIDUS = 20;
	
	private Fixture leftDownWheelFixture , rightDownWheelFixture, leftUpWheelFixture, rightUpWheelFixture;
	private Fixture feetFixture;
	private float prevGapX, prevGapY;
	private ActorFeet feet;
	private ObjectSet<Platform> contactingPlatformList;
	
	public Actor(ActorLayer _layer) {
		super(_layer);
		this.setName("actor");
		
		Shape shape = createWheelShape();
		FixtureDef def = createWheelFixtureDef(shape);
		leftDownWheelFixture = createFixture(def);
		rightDownWheelFixture = createFixture(def);
		leftUpWheelFixture = createFixture(def);
		rightUpWheelFixture = createFixture(def);
		
		feetFixture = createFixture(createFeetFixtureDef(shape));
		
		Pools.free(shape);
		
		feet = new ActorFeet(feetFixture);
		contactingPlatformList = new ObjectSet<Platform>();
	}
	
	@Override
	public final void init(float xPos, float yPos, float width, float height){
		super.init(xPos, yPos, width, height);
	}
		
	
	@Override
	public void reset(){
		super.reset();
		contactingPlatformList.clear();
		feet.reset();
	}

	
	@Override
	protected void sizeChanged(){
		moveWheel(-prevGapX, -prevGapY);
		super.sizeChanged();
		float diameter = Math.min(getWidth() / WORLDSCALE / 2f, getHeight() / WORLDSCALE / 2f);
		ShapeTransformer.setSize(leftDownWheelFixture.getShape(), diameter );
		ShapeTransformer.setSize(rightDownWheelFixture.getShape(),  diameter);
		ShapeTransformer.setSize(leftUpWheelFixture.getShape(), diameter );
		ShapeTransformer.setSize(rightUpWheelFixture.getShape(),  diameter);
		ShapeTransformer.setSize(feetFixture.getShape(), diameter);
		moveWheel(getWidth() / WORLDSCALE / 2f - diameter/2f, - getHeight()/ WORLDSCALE /2f + diameter / 2f);
	}
	
	@Override
	protected void scaleChanged(){
		moveWheel(-prevGapX, -prevGapY);
		super.scaleChanged();
		float diameter = Math.min(getWidth() * getScaleX() / WORLDSCALE / 2f, getHeight() * getScaleY() / WORLDSCALE / 2f);
		ShapeTransformer.setSize(leftDownWheelFixture.getShape(), diameter );
		ShapeTransformer.setSize(rightDownWheelFixture.getShape(),  diameter);
		ShapeTransformer.setSize(leftUpWheelFixture.getShape(), diameter );
		ShapeTransformer.setSize(rightUpWheelFixture.getShape(),  diameter);
		ShapeTransformer.setSize(feetFixture.getShape(), diameter);
		moveWheel(getWidth() * getScaleX() / WORLDSCALE / 2f - diameter/2f, - getHeight() * getScaleY()/ WORLDSCALE /2f + diameter / 2f);
	}
	
	private void moveWheel(float xPos, float yPos){
		prevGapX = xPos;
		prevGapY = yPos;
		ShapeTransformer.setPosition(leftDownWheelFixture.getShape(), -xPos, yPos);
		ShapeTransformer.setPosition(rightDownWheelFixture.getShape(), xPos, yPos);
		ShapeTransformer.setPosition(leftUpWheelFixture.getShape(), -xPos, -yPos);
		ShapeTransformer.setPosition(rightUpWheelFixture.getShape(), xPos, -yPos);
		ShapeTransformer.setPosition(feetFixture.getShape(), 0, yPos * 2);
	}
	
	private Shape createWheelShape(){
		CircleShape shape = Pools.obtain(CircleShape.class);
		shape.setRadius(WHEEL_RAIDUS / WORLDSCALE / 2f);
		return shape;
	}
	
	private FixtureDef createWheelFixtureDef(Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 10f; 
		fixtureDef.friction = 0f;
		fixtureDef.filter.categoryBits = CATEGORY_WHEEL;
		fixtureDef.filter.maskBits = CATEGORY_PLATFORM;
		return fixtureDef;
	}
	
	private FixtureDef createFeetFixtureDef(Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0f; 
		fixtureDef.isSensor = true;
		fixtureDef.filter.categoryBits = CATEGORY_WHEEL;
		fixtureDef.filter.maskBits = CATEGORY_PLATFORM;
		return fixtureDef;
	}
	
	public Platform[] getContactingPlatformList(){
		int size = contactingPlatformList.size;
		int index = 0;
		Platform[] platforms = new Platform[size];
		for(Platform platform : contactingPlatformList)
			platforms[index++] = platform;
		return platforms;
	}
	
	ObjectSet<Platform> getContactingPlatformListAsObjectSet(){
		return contactingPlatformList;
	}
	
	
	
	
	public boolean isFlying(){
		return feet.isFlying();
	}
	
	
	
	
	
	private class ActorFeet implements ContactListener{	
		private ObjectSet<Platform> contactingPlatformList;
		private Fixture feetFixture;
		
		
		private ActorFeet(Fixture feetFixture){
			contactingPlatformList = new ObjectSet<Platform>();
			this.feetFixture = feetFixture;
			this.feetFixture.setUserData(this);
			contactingPlatformList  = new ObjectSet<Platform>();
		}
		
		@Override
		public void beginContact(Contact contact) {
			Platform platform = Actor.this.extractPlatform(contact);
			if(platform == null) return;
			contactingPlatformList.add(platform);
		}

		@Override
		public void endContact(Contact contact) {
			Platform platform = Actor.this.extractPlatform(contact);
			if(platform == null) return;
			contactingPlatformList.remove(platform);
		}
		
		public boolean isFlying(){
			return contactingPlatformList.size == 0;
		}
		
		
		public void reset(){
			contactingPlatformList.clear();
		}
		
		@Override
		public void preSolve(Contact contact, Manifold oldManifold) {}

		@Override
		public void postSolve(Contact contact, ContactImpulse impulse) {}
	}
}