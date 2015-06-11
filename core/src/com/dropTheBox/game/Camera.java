package com.dropTheBox.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class Camera extends OrthographicCamera {
	private float aVel, xVel, yVel, xPos, yPos, angle;

	public Camera() {
		super();
	}

	public Camera(float viewportWidth, float viewportHeight) {
		super(viewportWidth, viewportHeight);
		this.translate(viewportWidth/2, viewportHeight/2);
	}


	public void update(float dt) {
		super.update();
		this.translate(xVel * dt, yVel * dt);
		this.rotate(aVel * dt);
	}

	@Override
	public void rotate(float _angle) {
		angle += _angle;
		super.rotate(_angle);
	}

	@Override
	public void translate(float x, float y) {
		xPos += x;
		yPos += y;
		super.translate(x, y);
	}

	@Override
	public void translate(Vector2 vec) {
		xPos += vec.x;
		yPos += vec.y;
		super.translate(vec);
	}
	
	public void setXVelocity(float xv){
		xVel = xv;
	}
	
	public void setYVelocity(float yv){
		yVel = yv;
	}
	
	public void setAngularVelocity(float av){
		aVel = av;
	}
	
	public float getAngularVelocity() {
		return aVel;
	}

	public float getXVelocity() {
		return xVel;
	}

	public float getYVelocity() {
		return yVel;
	}

	public float getX() {
		return xPos - this.viewportWidth/2;
	}

	public void setX(float xPos) {
		translate(xPos + this.viewportWidth/2,0);
	}

	public float getY() {
		return yPos - this.viewportHeight/2;
	}

	public void setY(float yPos) {
		translate(0, yPos + this.viewportHeight/2);
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		rotate(angle);
	}
	
}
