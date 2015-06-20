package com.dropTheBox.game.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.utils.Pools;
import com.dropTheBox.game.entity.Actor;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.scene.GameScene.GameState;

public class Player extends Actor implements InputProcessor{
	public static final int WIDTH = 40, HEIGHT = 40;
	private TextureRegion tR, tL, tF;

	private boolean leftMove, rightMove;
	private float maxSpeed, brakeForce, accelForce;


	public Player(ActorLayer layer) {
		super(layer);
		this.setName("player");
		this.setType(BodyType.DynamicBody);
	}

	public void init(float x, float y){
		super.init(x, y, WIDTH, HEIGHT);

		tR = new TextureRegion(getLayer().getAssets().get("game/player.png", Texture.class),0,0,40,40);
		tF = new TextureRegion(getLayer().getAssets().get("game/player.png", Texture.class),40,0,40,40);
		tL = new TextureRegion(getLayer().getAssets().get("game/player.png", Texture.class),80,0,40,40);
		setImage(tR);

		brakeForce = 600;
		accelForce = 200;
		maxSpeed = 400;
	}


	@Override
	public void act(float dt) {
		super.act(dt);
		float xVel = getXVelocity();
		float yVel = getYVelocity();

		//TODO remove this
		this.setZIndex(Integer.MAX_VALUE);


		if(isFlying()){
			if(xVel > 0)
				this.applyForceToCenter(-150, 0, true);
			else if (xVel < 0)
			this.applyForceToCenter(150, 0, true);

		}
		else{
			if(leftMove){
				if(xVel <0)
					this.applyForceToCenter(-accelForce, 0, true);
				else
					this.applyForceToCenter(-brakeForce, 0, true);
			}else if( rightMove ){
				if(xVel >0)
					this.applyForceToCenter(accelForce, 0, true);
				else
					this.applyForceToCenter(brakeForce, 0, true);
			}
		}

		if(xVel > maxSpeed)
			this.setLinearVelocity(maxSpeed, yVel);
		else if(xVel < -maxSpeed)
			this.setLinearVelocity(-maxSpeed, yVel);



		if(xVel > 0){
			setImage(tR);
		}
		else if(xVel < 0){
			setImage(tL);
		}
		else{
			setImage(tF);
		}
		
		
		if(getY() <= getLayer().getCamera().getY())
			this.setY(getLayer().getCamera().getY());
		//		if(getRotation() > 30){
		//			setRotation(30);
		//		}
		//		if(getRotation() < -30)
		//			setRotation(-30);
	}



	public void setLeftMove(boolean m){
		leftMove = m;
	}

	public void setRightMove(boolean m){
		rightMove = m;
	}

	@Override
	public boolean keyDown(int keycode) {
		if(getLayer().getState() == GameState.Running){if(keycode == Keys.A)
			Player.this.setLeftMove(true);
		else if(keycode ==Keys.D)
			Player.this.setRightMove(true);
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(getLayer().getState() == GameState.Running){
			if(keycode == Keys.A)
				Player.this.setLeftMove(false);
			else if(keycode ==Keys.D)
				Player.this.setRightMove(false);
		}
		return true;
	}


	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(getLayer().getState() == GameState.Running){
			if(screenX < Gdx.graphics.getWidth()/2)
				this.setLeftMove(true);
			else 
				this.setRightMove(true);
		}

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(getLayer().getState() == GameState.Running){
			if(screenX < Gdx.graphics.getWidth()/2)
				this.setLeftMove(false);
			else 
				this.setRightMove(false);
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	protected Shape createShape() {
		return Pools.obtain(CircleShape.class);
	}

	@Override
	protected FixtureDef createFixtureDef(Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 10f; 
		fixtureDef.friction = 0f;
		fixtureDef.filter.categoryBits = CATEGORY_PLAYER;
		fixtureDef.filter.maskBits = ~CATEGORY_PLAYER;
		return fixtureDef;
	}
}
