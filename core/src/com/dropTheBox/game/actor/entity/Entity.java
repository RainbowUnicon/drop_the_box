package com.dropTheBox.game.actor.entity;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.dropTheBox.game.actor.Base2;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.utils.ShapeTransformer;

public abstract class Entity extends Base2 {
	public static float WHEEL_RAIDUS = 20;
	private Fixture leftDownWheelFixture , rightDownWheelFixture, leftUpWheelFixture, rightUpWheelFixture;
	private float xD, yD;
	
	public Entity(ActorLayer _layer) {
		super(_layer);
		CircleShape shape = new CircleShape();
		shape.setRadius(WHEEL_RAIDUS / WORLDSCALE / 2f);
		FixtureDef def = createWheelFixtureDef(shape);
		leftDownWheelFixture = createFixture(def);
		rightDownWheelFixture = createFixture(def);
		leftDownWheelFixture.setDensity(10f);
		rightDownWheelFixture.setDensity(10f);
		leftUpWheelFixture = createFixture(def);
		rightUpWheelFixture = createFixture(def);
		shape.dispose();
	}
	
	@Override
	public void setSize(float w, float h){
		moveWheel(-xD, -yD);
		super.setSize(w, h);
		float diameter = w / WORLDSCALE / 2f;
		ShapeTransformer.resize(leftDownWheelFixture.getShape(), diameter );
		ShapeTransformer.resize(rightDownWheelFixture.getShape(),  diameter);
		ShapeTransformer.resize(leftUpWheelFixture.getShape(), diameter );
		ShapeTransformer.resize(rightUpWheelFixture.getShape(),  diameter);
		moveWheel(w / WORLDSCALE / 4f, -h/ WORLDSCALE /4f);
	}
	

	

	
	
	
	
	private void moveWheel(float xPos, float yPos){
		xD = xPos;
		yD = yPos;
		ShapeTransformer.translate(leftDownWheelFixture.getShape(), -xPos, yPos);
		ShapeTransformer.translate(rightDownWheelFixture.getShape(), xPos, yPos);
		ShapeTransformer.translate(leftUpWheelFixture.getShape(), -xPos, -yPos);
		ShapeTransformer.translate(rightUpWheelFixture.getShape(), xPos, -yPos);
	}
	
	private void rotateWheel(float d){
		ShapeTransformer.translate(leftDownWheelFixture.getShape(), (xD) / WORLDSCALE, (yD) / WORLDSCALE);
		ShapeTransformer.translate(rightDownWheelFixture.getShape(), -(xD) / WORLDSCALE, -(yD) / WORLDSCALE);
		ShapeTransformer.translate(leftUpWheelFixture.getShape(), (xD) / WORLDSCALE, (yD) / WORLDSCALE);
		ShapeTransformer.translate(rightUpWheelFixture.getShape(), -(xD) / WORLDSCALE, -(yD) / WORLDSCALE);
		xD =  (float)(xD * Math.cos(Math.toRadians(getRotation())) + yD * Math.sin(Math.toRadians(getRotation())));
		yD = -(float)(-xD * Math.sin(Math.toRadians(getRotation())) + yD * Math.sin(Math.toRadians(getRotation())));
		ShapeTransformer.translate(leftDownWheelFixture.getShape(), -xD /WORLDSCALE, -yD / WORLDSCALE);
		ShapeTransformer.translate(rightDownWheelFixture.getShape(), xD /WORLDSCALE, yD / WORLDSCALE);
		ShapeTransformer.translate(leftUpWheelFixture.getShape(), -xD /WORLDSCALE, -yD / WORLDSCALE);
		ShapeTransformer.translate(rightUpWheelFixture.getShape(), xD /WORLDSCALE, yD / WORLDSCALE);
	}
	
	
	protected FixtureDef createWheelFixtureDef(Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0f; 
		fixtureDef.friction = 0f;
		fixtureDef.filter.categoryBits = CATEGORY_WHEEL;
		fixtureDef.filter.maskBits = CATEGORY_PLATFORM;
		return fixtureDef;
	}

}
