package com.dropTheBox.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.dropTheBox.game.entity.player.Player;
import com.dropTheBox.game.layer.ActorLayer;

public class Platform extends Entity{
	public static final float BUFFER_WIDTH = 100;
	private Array<Item> itemList;
	private Array<Vector2> distanceList;
	private boolean stepped;
	
	
	
	public Platform(ActorLayer layer) {
		super(layer);
		this.setName("platform");
		this.setType(BodyType.KinematicBody);
		
		this.setImage(new TextureRegion(getLayer().getAssets().get("game/platform.png",Texture.class)));
		itemList = new Array<Item>();
		distanceList = new Array<Vector2>();
		stepped = false;
	}
	
	
	
	@Override
	public void init(float x, float y, float w, float h){
		super.init(x, y, w, h);
		this.getImage().setRegion(0, 0, (int) w, (int) h);
	}
	
	@Override
	public void reset(){
		super.reset();
		clearItems();
	}
	
	
	
	@Override
	public void act(float dt){
		super.act(dt);
		if(!isAwake()) return;
		int size = itemList.size;
		for(int i = 0; i < size; i++){
			Vector2 v = distanceList.get(i);
			Item item = itemList.get(i);
			double r = (float) Math.toRadians(this.getRotation());
			float cx = (float)(v.x * Math.cos(r) - v.y * Math.sin(r));
			float cy = (float)(v.x * Math.sin(r) + v.y * Math.cos(r));
			item.setPosition(this.getCenterX() - cx - item.getWidth()/2, this.getCenterY() - cy - item.getHeight()/2);
			item.setRotation(this.getRotation());
		}
	}	
	

	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	
	
	public void removeItem(Item item){
		if(item == null) throw new NullPointerException();
		item.setPlatform(this);
		itemList.add(item);
		distanceList.add(new Vector2(this.getCenterX() - item.getCenterX(), this.getCenterY() - item.getCenterY()));
	}
	
	public void addItem(Item item){
		int index = itemList.indexOf(item, false);
		if(index == -1) return;
		item.setPlatform(null);
		itemList.removeValue(item, false);
		distanceList.removeIndex(index);
	}
	
	public Array<Item> getItems(){
		return itemList;
	}
	
	public void clearItems(){
		for(Item item : itemList)
			item.setPlatform(null);
		itemList.clear();
		distanceList.clear();
	}
	
	public void stepped(){
		getLayer().getDisplayLayer().getScoreBoard().addScore(50);
	}
	
	
	@Override
	public void beginContact (Contact contact){
		if(!stepped && (contact.getFixtureA().getUserData() instanceof Player ||
				contact.getFixtureB().getUserData() instanceof Player)){
			stepped();
			stepped = true;
		}
	}
	
	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	protected Shape createShape() {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(20 / WORLDSCALE, 20 / WORLDSCALE);
		return shape;
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
