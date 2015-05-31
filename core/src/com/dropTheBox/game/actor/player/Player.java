package com.dropTheBox.game.actor.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.dropTheBox.game.actor.Base;
import com.dropTheBox.game.actor.SafeBody;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.scene.Scene;

public class Player extends Base {
	private Texture texture;
	private boolean leftMove, rightMove;
	
	public void init(ActorLayer layer, float x, float y){
		super.init(layer, x, y, 40,40);
		getFixture().getFilterData().groupIndex = -5;
		texture = layer.getAssets().get("player.png", Texture.class);
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
		
		SafeBody body = getBody();
		Vector2 p = body.getPosition(); //this p is the center point of the body
		Vector2 v = body.getLinearVelocity(); // v is the linear velocity of the body
		
		//respond to the directional input
		//Apply twice more force on the object, when the direction of input and that of player's velocity are different
		if(leftMove){
			System.out.println("Dadad");
			if(v.x <0)
				body.applyForceToCenter(-100000, 0, true);
			else
				body.applyForceToCenter(-200000, 0, true);
		}else if( rightMove && !leftMove){
			if(v.x >0)
				body.applyForceToCenter(100000, 0, true);
			else
				body.applyForceToCenter(200000, 0, true);
		}
		
		//transport player, if player reach the edge of the world 
		if(p.x - getWidth()< 0 && v.x < 0){ //if left corner of player's body reached the left edge of the world
			//transport the body to the point where, left corner of player's body meet the right edge of the world 
			body.setTransform(Scene.WIDTH + getWidth(),p.y,0);
		}
		else if(p.x + getWidth() > Scene.WIDTH && v.x > 0){ // if right corner of player's body reached the right edge of the world 
			//vice versa
			body.setTransform(-getWidth(),p.y,0);
		}
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
	
	public void setLeftMove(boolean m){
		leftMove = m;
	}
	
	public void setRightMove(boolean m){
		rightMove = m;
	}


	@Override
	protected BodyDef createBodyDef() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		return bodyDef;
	}

	
	private class PlayerListener extends InputListener{
		@Override
		public boolean keyDown(InputEvent event, int keycode){
			System.out.println("LEFT");
			if(keycode == Keys.A)
				Player.this.setLeftMove(true);
			else if(keycode ==Keys.D)
				Player.this.setRightMove(true);
			return true;
		}

		@Override
		public boolean keyUp(InputEvent event, int keycode){
			if(keycode == Keys.A)
				Player.this.setLeftMove(false);
			else if(keycode ==Keys.D)
				Player.this.setRightMove(false);
			return true;
		}
		//
		//			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//				//if pause button is clicked, bypass other lines
		//				switch(GameScene.this.gameState){
		//				case Paused:
		//					gamePauseMenu.processTouchDown(screenX, screenY);
		//					return false;
		//				case GameOver:
		//					gameOverScreen.processTouchDown(screenX, screenY);
		//					return false;
		//				case CountDown:
		//					break;
		//				case GameWin:
		//					break;
		//				case Running:
		//					break;
		//				}
		//				
		//				if(pauseButton.isClicked(screenX, screenY)){
		//					return false;
		//				}
		//				if(!useAcclerometer && GameScene.this.gameState == GameState.Running){
		//					if(screenX < Gdx.graphics.getWidth()/2)
		//						player.setLeftMove(true);
		//					else 
		//						player.setRightMove(true);
		//				}
		//
		//				return false;
		//			}
		//
		//			@Override
		//			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		//				switch(GameScene.this.gameState){
		//				case Paused:
		//					gamePauseMenu.processTouchUp(screenX, screenY);
		//					return false;
		//				case GameOver:
		//					gameOverScreen.processTouchUp(screenX, screenY);
		//					return false;
		//				}
		//				
		//				if(pauseButton.isClicked(screenX,screenY)){
		//					pauseButton.click();
		//					return false;
		//				}
		//				if(!useAcclerometer && GameScene.this.gameState == GameState.Running){
		//					if(screenX < Gdx.graphics.getWidth()/2)
		//						player.setLeftMove(false);
		//					else 
		//						player.setRightMove(false);
		//				}
		//				return false;
		//			}
		//			
		//			@Override
		//			public boolean keyTyped(char character) {
		//				return false;
		//			}
		//			@Override
		//			public boolean touchDragged(int screenX, int screenY, int pointer) {
		//				return false;
		//			}
		//			@Override
		//			public boolean mouseMoved(int screenX, int screenY) {
		//				return false;
		//			}
		//			@Override
		//			public boolean scrolled(int amount) {
		//				return false;
		//			}
	}
}
