package com.dropTheBox.game.actor.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.dropTheBox.game.actor.Base;
import com.dropTheBox.game.layer.ActorLayer;

public class Item extends Base {
	
	public void init(ActorLayer layer, float x, float y,float d){
		super.init(layer, x, y, d, d);
		getFixture().getFilterData().groupIndex = -3;

		this.setImage(layer.getAssets().get("game/item.png",Texture.class));
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
		return fixtureDef;
	}

	@Override
	protected BodyDef createBodyDef() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		return bodyDef;
	}

}
