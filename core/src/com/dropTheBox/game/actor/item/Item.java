package com.dropTheBox.game.actor.item;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.dropTheBox.game.actor.Base2;
import com.dropTheBox.game.layer.ActorLayer;

public abstract class Item extends Base2 {
	private boolean isActivated;
	
	public Item(ActorLayer _layer) {
		super(_layer);
		this.setName("item");
	}
	
	protected void init(float x, float y,float d){
		super.init(x, y, d, d);
		setType(BodyType.KinematicBody);
		isActivated = false;
	}


	@Override
	protected FixtureDef createFixtureDef(Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f; 
		fixtureDef.friction = 0f;
		fixtureDef.isSensor = true;
		fixtureDef.filter.categoryBits = CATEGORY_ITEM;
		fixtureDef.filter.maskBits = CATEGORY_PLAYER | CATEGORY_PLATFORM;
		return fixtureDef;
	}
	
	public void activate(){
		isActivated = true;
	}
	
	public boolean isActivated(){
		return isActivated;
	}
}
