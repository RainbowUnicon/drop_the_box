package com.dropTheBox.game.actor.platform;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.dropTheBox.game.actor.Base;
import com.dropTheBox.game.actor.Base2;
import com.dropTheBox.game.actor.item.Item;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.utils.ShapeTransformer;

public class Platform extends Base2{
	public static final float BUFFER_WIDTH = 100;
	private Array<Item> itemList;
	private Array<Vector2> distanceList;
	
	
	public Platform(ActorLayer layer) {
		super(layer);
		this.setName("platform");
		itemList = new Array<Item>();
		distanceList = new Array<Vector2>();
		this.setType(BodyType.KinematicBody);
		this.setImage(new TextureRegion(getLayer().getAssets().get("game/platform.png",Texture.class)));
		
		
		
	}
	
	@Override
	public void init(float x, float y, float w, float h){
		this.getImage().setRegion(0, 0, (int) w, (int) h);
		super.init(x, y, w, h);
	}
	
	@Override
	public void reset(){
		super.reset();
		itemList.clear();
		distanceList.clear();
	}
	
	@Override
	public void act(float dt){
		super.act(dt);
		if(!isAwake()) return;
		arrangeItem();
	}
	
	@Override
	public void setSize(float w, float h){
		super.setSize(w,h);

	}
	
	
	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	
	public void attachItem(Item item){
		itemList.add(item);
		distanceList.add(new Vector2(this.getCenterX() - item.getCenterX(), this.getCenterY() - item.getCenterY()));
	}
	
	public void detachItem(Item item){
		int index = itemList.indexOf(item, false);
		if(index == -1) return;
		distanceList.removeIndex(index);
		itemList.removeValue(item, false);
	}
	
	protected void arrangeItem(){
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

	protected Shape createShape() {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(10 / WORLDSCALE, 10 / WORLDSCALE);
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
