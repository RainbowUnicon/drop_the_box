package com.dropTheBox.game.entity.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Pools;
import com.dropTheBox.game.entity.Entity;
import com.dropTheBox.game.layer.ActorLayer;

public class Crate extends Entity {
	public static final float WIDTH = 50, HEIGHT =50;
	public Crate(ActorLayer _layer) {
		super(_layer);
		this.setType(BodyType.DynamicBody);
	}

	public void init(float x, float y){
		super.init(x, y, WIDTH, HEIGHT);
		setImage(new TextureRegion(getLayer().getAssets().get("game/crate.png",Texture.class)));
	}

	@Override
	protected Shape createShape() {
		return Pools.obtain(CircleShape.class);
	}

	@Override
	protected FixtureDef createFixtureDef(Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f; 
		fixtureDef.friction = 0f;
		fixtureDef.filter.categoryBits = CATEGORY_ENTITY;
		fixtureDef.filter.maskBits = CATEGORY_PLATFORM | CATEGORY_PLAYER;
		return fixtureDef;
	}


}
