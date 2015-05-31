package com.dropTheBox.game.actor.platform;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.dropTheBox.game.actor.Base;
import com.dropTheBox.game.layer.ActorLayer;

public class Platform extends Base {
	private Texture texture;
	public void init(ActorLayer layer, float x, float y, float w, float h){
		super.init(layer, x, y, w, h);
		getFixture().getFilterData().groupIndex = 3;
		texture = layer.getAssets().get("platform.png", Texture.class);
	}


	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(),
				getRotation(), 0, 0, (int)getWidth(), (int)getHeight(), false, false);

	}

	@Override
	public void act(float dt) {
		super.act(dt);
	}
	
	@Override
	protected Shape createShape() {
		PolygonShape shape = new PolygonShape();
	    shape.setAsBox(getWidth()/ 2f,getHeight() /2f);
	    return shape;
	}

	@Override
	public void setSize(float w, float h) {
		PolygonShape shape = (PolygonShape) getFixture().getShape();
		shape.setAsBox(w/ 2f, h /2f);
	}


	@Override
	public void setHeight(float height) {
		setSize(getWidth(), height);
	}


	@Override
	public void setWidth(float w) {
		setSize(w, getHeight());
	}


	@Override
	public void sizeBy(float x, float y) {
		setSize(getWidth() + x, getHeight() + y);
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
		bodyDef.type = BodyType.KinematicBody;
		return bodyDef;
	}

}
