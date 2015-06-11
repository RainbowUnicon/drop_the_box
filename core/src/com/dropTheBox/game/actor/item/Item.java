package com.dropTheBox.game.actor.item;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.dropTheBox.game.actor.entity.Entity;
import com.dropTheBox.game.layer.ActorLayer;

public abstract class Item extends Entity {
	private boolean isActivated;
	
	public Item(ActorLayer _layer) {
		super(_layer);
	}
	
	protected void init(float x, float y,float d){
		super.init(x, y, d, d);
		isActivated = false;
	}
	

	
	@Override
	protected Shape createShape() {
		CircleShape shape = new CircleShape();
		shape.setRadius(getHeight() / WORLDSCALE / 2f);
		return shape;
	}

	@Override
	protected FixtureDef createFixtureDef(Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f; 
		fixtureDef.friction = 0f;
		fixtureDef.isSensor = true;
		fixtureDef.filter.groupIndex = 5;
		return fixtureDef;
	}

	@Override
	protected BodyDef createBodyDef() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.awake = false;
		return bodyDef;
	}
	
	public void activate(){
		isActivated = true;
	}
	
	public boolean isActivated(){
		return isActivated;
	}
}
