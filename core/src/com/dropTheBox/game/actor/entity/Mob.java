package com.dropTheBox.game.actor.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.dropTheBox.game.layer.ActorLayer;

public class Mob extends Entity {
	public Mob(ActorLayer _layer) {
		super(_layer);
		// TODO Auto-generated constructor stub
	}

	private TextureRegion t1, t2, t3, t4, t5, t0;
	private TextureRegion currRegion;
	private boolean find = false;
	private Body sensor;
	
	public void init(float x, float y){
		super.init(x, y, 50,50);
		Texture tt = getLayer().getAssets().get("game/minion_1.png",Texture.class); 
		t0 = new TextureRegion(tt, 0 , 0, 50, 50);
		t1 = new TextureRegion(tt, 50 , 0, 50, 50);
		t2 = new TextureRegion(tt, 100 , 0, 50, 50);
		t3 = new TextureRegion(tt, 150 , 0, 50, 50);
		t4 = new TextureRegion(tt, 200 , 0, 50, 50);
		t5 = new TextureRegion(tt, 250 , 0, 50, 50);
		currRegion = t4;
		body.applyForceToCenter(-100, 0, true);

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		batch.draw(currRegion, getX(), getY() + gap, 25,25, 50,50, getScaleX(), getScaleY(), getRotation());
	}

	float gap = 0;
	float clock = 1f;
	float aaa = 0f;
	float bbb = 5f;
	boolean baba = false;
	@Override
	public void act(float dt){
		super.act(dt);
		clock -= dt;
		
		if(clock < 0){
			if(currRegion == t4)
				currRegion = t0;
			else if(currRegion == t0)
				currRegion = t1;
			else if(currRegion == t1)
				currRegion = t2;
			else if(currRegion == t2)
				currRegion = t3;
			else if(currRegion == t3)
				currRegion = t4;
			clock = 1f;
		}
		
		
	}
	
	private void find(){
		
		find = true;
		currRegion = t0;
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
