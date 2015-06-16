package com.dropTheBox.game.entity;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.utils.ShapeTransformer;

public abstract class Actor extends Entity {
	private float WHEEL_RAIDUS = 20;
	private Fixture leftDownWheelFixture , rightDownWheelFixture, leftUpWheelFixture, rightUpWheelFixture;
	private float prevGapX, prevGapY;
	
	public Actor(ActorLayer _layer) {
		super(_layer);
		this.setName("actor");
		
		Shape shape = createWheelShape();
		FixtureDef def = createWheelFixtureDef(shape);
		leftDownWheelFixture = createFixture(def);
		rightDownWheelFixture = createFixture(def);
		leftUpWheelFixture = createFixture(def);
		rightUpWheelFixture = createFixture(def);
		shape.dispose();
	}
	
	@Override
	protected void sizeChanged(){
		moveWheel(-prevGapX, -prevGapY);
		super.sizeChanged();
		float diameter = Math.min(getWidth() / WORLDSCALE / 2f, getHeight() / WORLDSCALE / 2f);
		ShapeTransformer.setSize(leftDownWheelFixture.getShape(), diameter );
		ShapeTransformer.setSize(rightDownWheelFixture.getShape(),  diameter);
		ShapeTransformer.setSize(leftUpWheelFixture.getShape(), diameter );
		ShapeTransformer.setSize(rightUpWheelFixture.getShape(),  diameter);
		moveWheel(getWidth() / WORLDSCALE / 2f - diameter/2f, - getHeight()/ WORLDSCALE /2f + diameter / 2f);
	}
	
	private void moveWheel(float xPos, float yPos){
		prevGapX = xPos;
		prevGapY = yPos;
		ShapeTransformer.setPosition(leftDownWheelFixture.getShape(), -xPos, yPos);
		ShapeTransformer.setPosition(rightDownWheelFixture.getShape(), xPos, yPos);
		ShapeTransformer.setPosition(leftUpWheelFixture.getShape(), -xPos, -yPos);
		ShapeTransformer.setPosition(rightUpWheelFixture.getShape(), xPos, -yPos);
	}
	
	private Shape createWheelShape(){
		CircleShape shape = new CircleShape();
		shape.setRadius(WHEEL_RAIDUS / WORLDSCALE / 2f);
		return shape;
	}
	
	private FixtureDef createWheelFixtureDef(Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 10f; 
		fixtureDef.friction = 0f;
		fixtureDef.filter.categoryBits = CATEGORY_WHEEL;
		fixtureDef.filter.maskBits = CATEGORY_PLATFORM;
		return fixtureDef;
	}
}

//private void rotateWheel(float d){
//ShapeTransformer.setPosition(leftDownWheelFixture.getShape(), (xD) / WORLDSCALE, (yD) / WORLDSCALE);
//ShapeTransformer.setPosition(rightDownWheelFixture.getShape(), -(xD) / WORLDSCALE, -(yD) / WORLDSCALE);
//ShapeTransformer.setPosition(leftUpWheelFixture.getShape(), (xD) / WORLDSCALE, (yD) / WORLDSCALE);
//ShapeTransformer.setPosition(rightUpWheelFixture.getShape(), -(xD) / WORLDSCALE, -(yD) / WORLDSCALE);
//xD =  (float)(xD * Math.cos(Math.toRadians(getRotation())) + yD * Math.sin(Math.toRadians(getRotation())));
//yD = -(float)(-xD * Math.sin(Math.toRadians(getRotation())) + yD * Math.sin(Math.toRadians(getRotation())));
//ShapeTransformer.setPosition(leftDownWheelFixture.getShape(), -xD /WORLDSCALE, -yD / WORLDSCALE);
//ShapeTransformer.setPosition(rightDownWheelFixture.getShape(), xD /WORLDSCALE, yD / WORLDSCALE);
//ShapeTransformer.setPosition(leftUpWheelFixture.getShape(), -xD /WORLDSCALE, -yD / WORLDSCALE);
//ShapeTransformer.setPosition(rightUpWheelFixture.getShape(), xD /WORLDSCALE, yD / WORLDSCALE);
//}
