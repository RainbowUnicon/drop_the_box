package com.dropTheBox.game.actor.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Shape;
import com.dropTheBox.game.actor.Base;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.scene.Scene;
import com.dropTheBox.utils.ShapeTransformer;



public abstract class Entity extends Base {
	private Fixture lFixture, rFixture;
	private boolean lBuffer, rBuffer;

	protected void init(ActorLayer l, float x, float y, float w, float h){ 
		super.init(l, x, y, w, h);

		Shape rightBufferShape = createShape();
		Shape leftBufferShape = createShape();
		
		rFixture = body.createFixture(createFixtureDef(rightBufferShape));
		lFixture = body.createFixture(createFixtureDef(leftBufferShape));
		rFixture.setUserData(this);
		lFixture.setUserData(this);
		
		rightBufferShape.dispose();
		leftBufferShape.dispose();

	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(rBuffer){
			batch.draw(getImage(), getX() + getLayer().getWidth(), getY(), getWidth() / 2f,getHeight() / 2f, getWidth(), getHeight(), getScaleX(), getScaleY(),
					getRotation(), 0, 0, (int)getWidth(), (int)getHeight(), false, false);
		}
		else if(lBuffer){
			batch.draw(getImage(), getX() - getLayer().getWidth(), getY(),  getWidth() / 2f,getHeight() / 2f, getWidth(), getHeight(), getScaleX(), getScaleY(),
					getRotation(), 0, 0, (int)getWidth(), (int)getHeight(), false, false);
		}
	}

	@Override
	public void act(float dt) {
		super.act(dt);

		float xPos = getX();
		float yPos = getY();
		float xVel = getXVelocity();
		float yVel = getYVelocity();
		
		if(xPos <= 0 && xVel < 0){
			if(!rBuffer)
				this.setRightBuffer(true);
			if(xPos <= -getWidth()){
				this.setRightBuffer(false);
				this.setPosition(getLayer().getWidth() - getWidth(), yPos);
			}
		}
		else if(xPos + getWidth() >= getLayer().getWidth() && xVel > 0){
			if(!lBuffer)
				this.setLeftBuffer(true);
			if(xPos >= getLayer().getWidth() ){
				this.setLeftBuffer(false);
				this.setPosition(0, yPos);
			}
		}
	}



	
	protected void setLeftBuffer(boolean flag){
		lBuffer = flag;
		if(flag){
			ShapeTransformer.translate(lFixture.getShape(), -Scene.WIDTH, 0);
		}
		else{
			ShapeTransformer.translate(lFixture.getShape(), Scene.WIDTH, 0);
		}
	}
	
	protected void setRightBuffer(boolean flag){
		rBuffer = flag;
		if(flag){
			ShapeTransformer.translate(rFixture.getShape(), Scene.WIDTH, 0);
		}
		else{
			ShapeTransformer.translate(rFixture.getShape(), -Scene.WIDTH, 0);
		}
	}
}
