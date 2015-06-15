package com.dropTheBox.game.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.utils.ShapeTransformer;

public abstract class Base2 extends Base {
	private final Fixture lFixture, cFixture, rFixture;
	private float xGap, yGap;

	public Base2(ActorLayer _layer) {
		super(_layer);
		this.setName("entity");
		Shape shape = createShape();
		FixtureDef def = createFixtureDef(shape);
		lFixture = createFixture(def);
		cFixture = createFixture(def);
		rFixture = createFixture(def);
		cFixture.setUserData(this);
		shape.dispose();
		moveBuffer(getLayer().getWidth(), 0);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(isTouchingLeftWall())
			drawImageAt(batch,getX() + getLayer().getWidth(), getY());
		else if(isTouchingRightWall())
			drawImageAt(batch, getX() - getLayer().getWidth(), getY());
	}

	
	
	@Override
	public void act(float dt){
		super.act(dt);
		if(!isAwake()) return;
		if(isTouchingLeftWall())
			touchingLeftWall();
		else if(isTouchingRightWall())
			touchingRightWall();
		rotateBuffer(getRotation());
	} 

	protected boolean isTouchingLeftWall(){
		return (getScaledX() < 0);
	}

	protected boolean isTouchingRightWall(){
		return (getScaledX() + getScaledWidth() > getLayer().getWidth());
	}
	
	protected void touchingLeftWall(){
		if(getScaledX() <= -getScaledWidth()){
			this.setPosition(getLayer().getWidth() + getX(), getY());
		}
	}

	protected void touchingRightWall(){
		if(getScaledX() >= getLayer().getWidth())
			this.setPosition(getX() - getLayer().getWidth(), getY());
	}
	

	@Override
	public void sizeBy(float x, float y) {
		float tempX = xGap;
		float tempY = yGap;
		moveBuffer(-tempX, -tempY);
		super.sizeBy(x, y);
		moveBuffer(tempX, tempY);
	}

	
	@Override
	public void setSize(float w, float h){
		float tempX = xGap;
		float tempY = yGap;
		moveBuffer(-tempX, -tempY);
		super.setSize(w, h);
		moveBuffer(tempX, tempY);
	}
	
	@Override
	public void setWidth(float w){
		float tempX = xGap;
		float tempY = yGap;
		moveBuffer(-tempX, -tempY);
		super.setWidth(w);
		moveBuffer(tempX, tempY);
	}
	
	@Override
	public void setHeight(float h){
		float tempX = xGap;
		float tempY = yGap;
		moveBuffer(-tempX, -tempY);
		super.setHeight(h);
		moveBuffer(tempX, tempY);
	}
	
	@Override
	public void rotateBy(float d){
		rotateBuffer(getRotation() + d);
		super.setRotation(d);
	}
	
	@Override
	public void setRotation(float d){
		super.setRotation(d);
		rotateBuffer(d);
	}
	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	
	@Override 
	public void setScale(float a){
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void scaleBy(float scale) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void scaleBy(float scaleX, float scaleY) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setScale(float scaleX, float scaleY) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setScaleX(float scaleX) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setScaleY(float scaleY) {
		throw new UnsupportedOperationException();
	}

	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


	private void moveBuffer(float x, float y){
		xGap = x;
		yGap = y;
		ShapeTransformer.translate(lFixture.getShape(), -(xGap) / WORLDSCALE, -(yGap) / WORLDSCALE);
		ShapeTransformer.translate(rFixture.getShape(), (xGap) / WORLDSCALE, (yGap) / WORLDSCALE);
	}
	
	private void rotateBuffer(float d){
		ShapeTransformer.translate(lFixture.getShape(), (xGap) / WORLDSCALE, (yGap) / WORLDSCALE);
		ShapeTransformer.translate(rFixture.getShape(), -(xGap) / WORLDSCALE, -(yGap) / WORLDSCALE);
		float distance = getLayer().getWidth();
		xGap =  (float)(distance * Math.cos(Math.toRadians(getRotation())));
		yGap = -(float)(distance * Math.sin(Math.toRadians(getRotation())));
		ShapeTransformer.translate(lFixture.getShape(), -xGap /WORLDSCALE, -yGap / WORLDSCALE);
		ShapeTransformer.translate(rFixture.getShape(), xGap /WORLDSCALE, yGap / WORLDSCALE);
	}
	
	protected abstract Shape createShape();
	protected abstract FixtureDef createFixtureDef(Shape shape);

}
