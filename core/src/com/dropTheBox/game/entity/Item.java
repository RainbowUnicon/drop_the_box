package com.dropTheBox.game.entity;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Pools;
import com.dropTheBox.game.entity.player.Player;
import com.dropTheBox.game.layer.ActorLayer;

public abstract class Item extends Entity {
	private boolean activated;
	private Platform platform;
	
	
	public Item(ActorLayer _layer) {
		super(_layer);
		this.setName("item");
		this.setType(BodyType.KinematicBody);
	}
	
	protected void init(float x, float y,float d){
		super.init(x, y, d, d);
		activated = false;
	}
	
	@Override
	public void reset(){
		super.reset();
		removeFromPlatform();
	}

	@Override
	public void act(float dt){
		super.act(dt);
		if(activated) Pools.free(this);
	}
	
	
	
	public abstract void activate();
	
	
	
	public void removeFromPlatform(){
		if(platform == null) return;
		platform.addItem(this);
	}
	
	public void addToPlatform(Platform platform){
		platform.removeItem(this);
	}
	
	protected void setPlatform(Platform platform){
		this.platform = platform;
	}
	
	
	
	@Override
	public void beginContact (Contact contact){
		if(!activated && (contact.getFixtureA().getUserData() instanceof Player || 
				contact.getFixtureB().getUserData() instanceof Player)){
			activate();
			activated = true;
		}
	}
	
	
	@Override
	protected Shape createShape() {
		CircleShape shape = new CircleShape();
		shape.setRadius(40 / WORLDSCALE / 2f);
		return shape;
	}
	
	@Override
	protected FixtureDef createFixtureDef(Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.isSensor = true;
		fixtureDef.filter.categoryBits = CATEGORY_ITEM;
		fixtureDef.filter.maskBits = CATEGORY_PLAYER | CATEGORY_PLATFORM;
		return fixtureDef;
	}
}
