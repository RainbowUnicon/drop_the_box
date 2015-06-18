package com.dropTheBox.game.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Pools;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.utils.Base;
import com.dropTheBox.utils.ShapeTransformer;

public abstract class Entity extends Base {
	public static final short CATEGORY_PLAYER = 0x0001;
	public static final short CATEGORY_PLATFORM = 0x0002;
	public static final short CATEGORY_ITEM = 0x0004;
	public static final short CATEGORY_ENTITY = 0x0008;
	public static final short CATEGORY_WHATEVER = 0x0010;
	public static final short CATEGORY_WHEEL = (short)0x8000;
	
	private final ActorLayer layer;
	private final Fixture lFixture, cFixture, rFixture;
	private float prevGapX, prevGapY;

	public Entity(ActorLayer layer) {
		super(layer.world);
		this.layer = layer;
		this.setName("entity");
		
		Shape shape = createShape();
		FixtureDef def = createFixtureDef(shape);
		lFixture = createFixture(def);
		cFixture = createFixture(def);
		rFixture = createFixture(def);
		cFixture.setUserData(this);
		Pools.free(shape);
		
		moveBuffer(layer.getWidth(), 0);
	}
	
	
	
	@Override
	protected void init(float xPos, float yPos, float width, float height) {
		super.init(xPos, yPos, width, height);
		layer.stage.addActor(this);
	}



	@Override
	public void act(float dt){
		super.act(dt);
		if(!isAwake()) return;
		if(isTouchingLeftWall())
			touchingLeftWall();
		else if(isTouchingRightWall())
			touchingRightWall();
	} 
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(isTouchingLeftWall())
			drawImageAt(batch,getX() + getLayer().getWidth(), getY());
		else if(isTouchingRightWall())
			drawImageAt(batch, getX() - getLayer().getWidth(), getY());
	}

	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


	protected boolean isTouchingLeftWall(){
		return (getScaledX() < 0);
	}

	protected boolean isTouchingRightWall(){
		return (getScaledX() + getScaledWidth() > getLayer().getWidth());
	}
	
	protected void touchingLeftWall(){
		if(getScaledX() <= -getScaledWidth())
			this.setPosition(getLayer().getWidth() + getX(), getY());
	}

	protected void touchingRightWall(){
		if(getScaledX() >= getLayer().getWidth())
			this.setPosition(getX() - getLayer().getWidth(), getY());
	}
	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


	@Override
	protected void sizeChanged(){
		moveBuffer(-prevGapX, -prevGapY);
		super.sizeChanged();
		moveBuffer(-prevGapX, -prevGapY);
	}
	
	@Override
	protected void scaleChanged(){
		moveBuffer(-prevGapX, -prevGapY);
		super.scaleChanged();
		moveBuffer(-prevGapX, -prevGapY);
	}
	
	@Override
	protected void rotationChanged(){
		super.rotationChanged();
		ShapeTransformer.setPosition(lFixture.getShape(), (prevGapX) / WORLDSCALE, (prevGapY) / WORLDSCALE);
		ShapeTransformer.setPosition(rFixture.getShape(), -(prevGapX) / WORLDSCALE, -(prevGapY) / WORLDSCALE);
		float distance = getLayer().getWidth();
		prevGapX =  (float)(distance * Math.cos(Math.toRadians(getRotation())));
		prevGapY = -(float)(distance * Math.sin(Math.toRadians(getRotation())));
		ShapeTransformer.setPosition(lFixture.getShape(), -prevGapX /WORLDSCALE, -prevGapY / WORLDSCALE);
		ShapeTransformer.setPosition(rFixture.getShape(), prevGapX /WORLDSCALE, prevGapY / WORLDSCALE);
	}

	private void moveBuffer(float x, float y){
		prevGapX = x;
		prevGapY = y;
		ShapeTransformer.setPosition(lFixture.getShape(), -(prevGapX) / WORLDSCALE, -(prevGapY) / WORLDSCALE);
		ShapeTransformer.setPosition(rFixture.getShape(), (prevGapX) / WORLDSCALE, (prevGapY) / WORLDSCALE);
	}
	
	protected Fixture getCenterFixture(){
		return cFixture;
	}
	
	protected Fixture getLeftFixture(){
		return lFixture;
	}
	
	protected Fixture getRightFixture(){
		return rFixture;
	}
	
	public ActorLayer getLayer(){
		return layer;
	}

	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	protected Platform extractPlatform(Contact contact){
		return extract(contact, Platform.class);
	}
	
	protected Actor extractActor(Contact contact){
		return extract(contact, Actor.class);
	}
	
	protected Item extractItem(Contact contact){
		return extract(contact, Item.class);
	}
	
	protected <T> T extract(Contact contact, Class<T> clazz){
		if(contact == null || clazz == null) throw new NullPointerException();
		
		Object objectA = contact.getFixtureA().getUserData();
		if(clazz.isInstance(objectA) && this != objectA) return (T) objectA;
		Object objectB = contact.getFixtureB().getUserData();
		if(clazz.isInstance(objectB) && this != objectB) return (T) objectB;
		return null;
	}
	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	
	protected abstract Shape createShape();
	protected abstract FixtureDef createFixtureDef(Shape shape);
}





//@Override
//public void sizeBy(float x, float y) {
//	float tempX = prevGapX;
//	float tempY = prevGapY;
//	moveBuffer(-tempX, -tempY);
//	super.sizeBy(x, y);
//	moveBuffer(tempX, tempY);
//}
//
//
//@Override
//public void setSize(float w, float h){
//	float tempX = prevGapX;
//	float tempY = prevGapY;
//	moveBuffer(-tempX, -tempY);
//	super.setSize(w, h);
//	moveBuffer(tempX, tempY);
//}
//
//@Override
//public void setWidth(float w){
//	float tempX = prevGapX;
//	float tempY = prevGapY;
//	moveBuffer(-tempX, -tempY);
//	super.setWidth(w);
//	moveBuffer(tempX, tempY);
//}
//
//@Override
//public void setHeight(float h){
//	float tempX = prevGapX;
//	float tempY = prevGapY;
//	moveBuffer(-tempX, -tempY);
//	super.setHeight(h);
//	moveBuffer(tempX, tempY);
//}
