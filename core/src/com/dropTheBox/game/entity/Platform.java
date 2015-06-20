package com.dropTheBox.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.Pools;
import com.dropTheBox.game.layer.ActorLayer;

public abstract class Platform extends Entity{
	public static final float BUFFER_WIDTH = 100;
	private final Array<Item> itemList;
	private final ObjectSet<Actor> contactingActorList; // actors that are on this platform



	public Platform(ActorLayer layer) {
		super(layer);
		this.setName("platform");
		this.setType(BodyType.KinematicBody);

		this.setImage(new TextureRegion(getLayer().getAssets().get("game/platform.png",Texture.class)));
		contactingActorList = new ObjectSet<Actor>();
		itemList = new Array<Item>();
		getLeftFixture().setUserData(this);
		getRightFixture().setUserData(this);
	}



	@Override
	protected void init(float x, float y, float w, float h){
		super.init(x, y, w, h);
		this.getImage().setRegion(0, 0, (int) w, (int) h);
	}

	@Override
	public void reset(){
		super.reset();
		clearItems();
		contactingActorList.clear();
	}



	@Override
	public void act(float dt){
		super.act(dt);
		if(!isAwake()) return;
		int size = itemList.size;
		for(int i = 0; i < size; i++){
			Item item = itemList.get(i);
			double r = (float) Math.toRadians(this.getRotation());
			float cx = (float)(item.xGap * Math.cos(r) - item.yGap * Math.sin(r));
			float cy = (float)(item.xGap * Math.sin(r) + item.yGap * Math.cos(r));
			item.setPosition(this.getCenterX() - cx - item.getWidth()/2, this.getCenterY() - cy - item.getHeight()/2);
			item.setRotation(this.getRotation());
		}
	}	



	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


	public void attachItem(Item item){
		if(item == null) throw new NullPointerException("item can't be null");
		item.setPlatform(this);
		itemList.add(item);
		item.xGap = this.getCenterX() - item.getCenterX();
		item.yGap = this.getCenterY() - item.getCenterY();
	}

	public void detachItem(Item item){
		if(item == null) throw new NullPointerException("item can't be null");
		int index = itemList.indexOf(item, true);
		if(index == -1) return;
		item.setPlatform(null);
		itemList.removeValue(item, false);
	}

	public Array<Item> getItems(){
		return itemList;
	}

	public void clearItems(){
		for(Item item : itemList)
			item.setPlatform(null);
		itemList.clear();
	}


	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@



	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	@Override
	public void beginContact (Contact contact){
		Actor actor = extractActor(contact);
		if(actor == null) return;
		contactingActorList.add(actor);
		actor.getContactingPlatformListAsObjectSet().add(this);
	}

	@Override
	public void endContact (Contact contact){
		Actor actor = extractActor(contact);
		if(actor == null) return;
		contactingActorList.remove(actor);
		actor.getContactingPlatformListAsObjectSet().remove(this);
	}

	public Actor[] getContactingActorList(){
		int size = contactingActorList.size;
		int index = 0;
		Actor[] actors = new Actor[size];
		for(Actor actor : contactingActorList)
			actors[index++] = actor;
		return actors;
	}
	
	ObjectSet getContactingActorListAsObjectSet(){
		return contactingActorList;
	}

	public boolean isContacting(Actor actor){
		return contactingActorList.contains(actor);
	}


	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	protected Shape createShape() {
		return Pools.obtain(PolygonShape.class);
	}


	protected FixtureDef createFixtureDef(Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f; 
		fixtureDef.friction = 0f;
		fixtureDef.filter.categoryBits = CATEGORY_PLATFORM;
		fixtureDef.filter.maskBits = ~CATEGORY_PLATFORM;
		return fixtureDef;
	}
}
