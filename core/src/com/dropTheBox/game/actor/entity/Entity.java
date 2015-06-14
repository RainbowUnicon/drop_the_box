package com.dropTheBox.game.actor.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.dropTheBox.game.actor.Base;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.utils.ShapeTransformer;

public abstract class Entity extends Base {
	protected Fixture fixture1, fixture2;

	public Entity(ActorLayer _layer) {
		super(_layer);
		this.setName("entity");
		Shape shape = createShape();
		fixture1 = createFixture(createFixtureDef(shape));
		fixture2 = createFixture(createFixtureDef(shape));
		fixture1.setUserData(this);
		fixture2.setUserData(this);
		shape.dispose();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(isTouchingLeftWall())
			drawImageAt(batch,getX() + getLayer().getWidth(), getY());
		else if(isTouchingRightWall())
			drawImageAt(batch, getX() - getLayer().getWidth(), getY());
	}

	
	
	@Override
	public void act(float dt){
		super.act(dt);
		if(!isAwake()) return;
		if(isTouchingLeftWall())
			touchingLeftWall();
		else if(isTouchingRightWall())
			touchingRightWall();
		else if (xD != 0 || yD != 0)
			touchingNone();
	} 

	protected boolean isTouchingLeftWall(){
		return (getScaledX() < 0);
	}

	protected boolean isTouchingRightWall(){
		return (getScaledX() + getScaledWidth() > getLayer().getWidth());
	}
	
	protected void touchingLeftWall(){
		translateFixture(1);
		if(getScaledX() <= -getScaledWidth()){
			this.setPosition(getLayer().getWidth() + getX(), getY());
		}
	}
	
	protected void touchingRightWall(){
		translateFixture(-1);
		if(getScaledX() >= getLayer().getWidth())
			this.setPosition(getX() - getLayer().getWidth(), getY());
	}
	
	protected void touchingNone(){
		translateFixture(0);
	}

	private float xD, yD;
	private void translateFixture(int gap){
		ShapeTransformer.translate(fixture2.getShape(), -xD / WORLDSCALE, -yD / WORLDSCALE);
		float distance = gap * getLayer().getWidth();
		xD =  (float)(distance * Math.cos(Math.toRadians(getRotation())));
		yD = -(float)(distance * Math.sin(Math.toRadians(getRotation())));
		ShapeTransformer.translate(fixture2.getShape(), xD /WORLDSCALE, yD / WORLDSCALE);
	}
	
	
	protected abstract Shape createShape();
	protected abstract FixtureDef createFixtureDef(Shape shape);

}
