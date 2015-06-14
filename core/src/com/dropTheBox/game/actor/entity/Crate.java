package com.dropTheBox.game.actor.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.dropTheBox.game.layer.ActorLayer;

public class Crate extends Entity {
	public static final float WIDTH = 50, HEIGHT =50;
	public Crate(ActorLayer _layer) {
		super(_layer);
	}

	public void init(float x, float y){
		super.init(x, y, WIDTH, HEIGHT);
		setImage(new TextureRegion(getLayer().getAssets().get("game/crate.png",Texture.class)));
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


}
