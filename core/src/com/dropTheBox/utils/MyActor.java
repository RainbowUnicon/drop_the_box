package com.dropTheBox.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyActor extends Actor {
	@Override
	public void rotateBy(float degrees){
		if(degrees != 0){
			super.rotateBy(degrees);
			rotationChanged();
		}
	}

	@Override
	public void setRotation(float degrees){
		if(getRotation() != degrees){
			super.setRotation(degrees);
			rotationChanged();
		}
	}
	
	protected void rotationChanged(){}
	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	
	@Override
	public void sizeBy(float size){
		if(size != 0)
			super.sizeBy(size);
	}
	
	@Override
	public void sizeBy(float width, float height){
		if(width != 0 || height != 0)
			super.sizeBy(width, height);
	}
	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	
	@Override
	public void setScaleX(float scaleX) {
		if(getScaleX() != scaleX){
			super.setScaleX(scaleX);
			scaleChanged();
		}
	}

	@Override
	public void setScaleY(float scaleY) {
		if(getScaleY() != scaleY){
			super.setScaleY(scaleY);
			scaleChanged();
		}
	}

	@Override
	public void setScale(float scaleXY) {
		if(getScaleX() != scaleXY || getScaleY() != scaleXY){
			super.setScale(scaleXY);
			scaleChanged();
		}
	}

	@Override
	public void setScale(float scaleX, float scaleY) {
		if(getScaleX() != scaleX || getScaleY() != scaleY){
			super.setScale(scaleX, scaleY);
			scaleChanged();
		}
	}

	@Override
	public void scaleBy(float scale) {
		if(scale != 0){
			super.scaleBy(scale);
			scaleChanged();
		}
	}

	@Override
	public void scaleBy(float scaleX, float scaleY) {
		if(scaleX != 0 || scaleY != 0){
			super.scaleBy(scaleX, scaleY);
			scaleChanged();
		}
	}
	
	protected void scaleChanged(){}
}



//@Override
//public void sizeBy(float x, float y){
//	float newWidth = getWidth() + x;
//	float newHeight = getHeight() + y;
//	super.setSize(newWidth, newHeight);
//	super.setOrigin(newWidth /2f, newHeight /2f);
//	for(Fixture fixture : body.getFixtureList())
//		ShapeTransformer.setSize(fixture.getShape(), newWidth / WORLDSCALE, newHeight / WORLDSCALE);
//}
//
//@Override 
//public void setSize(float w, float h){
//	super.setSize(w, h);
//	for(Fixture fixture : body.getFixtureList())
//		ShapeTransformer.setSize(fixture.getShape(), w / WORLDSCALE, h / WORLDSCALE);
//}
//
//@Override
//public void setWidth(float width){
//	super.setWidth(width);
//	for(Fixture fixture : body.getFixtureList())
//		ShapeTransformer.setSize(fixture.getShape(), width / WORLDSCALE, getHeight() / WORLDSCALE);
//}
//
//@Override
//public void setHeight(float height){
//	super.setHeight(height);
//	for(Fixture fixture : body.getFixtureList())
//		ShapeTransformer.setSize(fixture.getShape(), getWidth()/ WORLDSCALE, height / WORLDSCALE);
//}


//@Override
//public void setPosition(float x, float y){
//	super.setPosition(x, y);
//		body.setTransform((x + getWidth()/2) / WORLDSCALE, (y + getHeight()/2) /  WORLDSCALE , body.getAngle());
//}
//
//@Override
//public void moveBy(float x, float y){
//	super.moveBy(x, y);
//	Vector2 v = body.getPosition();
//	body.setTransform(v.x + x / WORLDSCALE, v.y + y / WORLDSCALE, body.getAngle());
//}
//
//@Override
//public void setX(float x){
//	super.setX(x);
//	body.setTransform((x + getWidth()/2) / WORLDSCALE, getCenterY() /  WORLDSCALE , body.getAngle());
//}
//
//@Override
//public void setY(float y){
//	super.setY(y);
//	body.setTransform(getCenterX() / WORLDSCALE, (y + getHeight()/2) /  WORLDSCALE , body.getAngle());
//}
//@Override
//public void scaleBy(float scale){
//	setScale(getScaleX() + scale, getScaleY() + scale);
//}
//
//@Override
//public void scaleBy(float scaleX, float scaleY){
//	setScale(getScaleX() + scaleX, getScaleY() + scaleY);
//}
//
//@Override
//public void setScale(float scaleXY) {
//	setScale(scaleXY, scaleXY);
//}
//
//@Override
//public void setScale(float scaleX, float scaleY) {
//	for(Fixture fixture : body.getFixtureList())
//		ShapeTransformer.setScale(fixture.getShape(), scaleX / getScaleX(), scaleY /getScaleY());
//	super.setScale(scaleX, scaleY);
//}
//
//@Override
//public void setScaleX(float scaleX){
//	setScale(scaleX, getScaleY());
//}
//
//@Override
//public void setScaleY(float scaleY){
//	setScale(getScaleX(), scaleY);
//}
