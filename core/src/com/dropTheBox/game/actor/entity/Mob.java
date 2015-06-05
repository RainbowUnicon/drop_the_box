package com.dropTheBox.game.actor.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.dropTheBox.game.layer.ActorLayer;

public class Mob extends Entity {
	private Texture t1, t2, t3, t4, t5;
	public void init(ActorLayer layer, float x, float y){
		super.init(layer, x, y, 50,50);
		getFixture().getFilterData().groupIndex = -4;
		t1 = layer.getAssets().get("game/mob_0.png",Texture.class);
		t2 = layer.getAssets().get("game/mob_1.png",Texture.class);
		t3 = layer.getAssets().get("game/mob_2.png",Texture.class);
		t4 = layer.getAssets().get("game/mob_3.png",Texture.class);
		t5 = layer.getAssets().get("game/mob_4.png",Texture.class);
		setImage(t1);
	}

	@Override
	protected Shape createShape() {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(getWidth()/ WORLDSCALE / 2f,getHeight() / WORLDSCALE /2f);
		return shape;
	}

	@Override
	protected FixtureDef createFixtureDef(Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f; 
		fixtureDef.friction = 0f;
		return fixtureDef;
	}



	@Override
	protected BodyDef createBodyDef() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		return bodyDef;
	}

}
