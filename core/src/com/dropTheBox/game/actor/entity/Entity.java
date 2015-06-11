package com.dropTheBox.game.actor.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Shape;
import com.dropTheBox.game.actor.Base;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.scene.Scene;
import com.dropTheBox.utils.ShapeTransformer;

public abstract class Entity extends Base {
	private Fixture ninjaFixture;
	private NinjaLoc currNinjaLoc;
	
	public Entity(ActorLayer _layer) {
		super(_layer);
	}

	protected void init( float x, float y, float w, float h){ 
		super.init(x, y, w, h);

		if(isInitialized())
			setSize(w / WORLDSCALE, h / WORLDSCALE);
		else {
			Shape ninjaShape = createShape();
			ninjaFixture = body.createFixture(createFixtureDef(ninjaShape));
			ninjaFixture.setUserData(this);
			ninjaShape.dispose();
		}

		currNinjaLoc = isAtLeftCorner() ? NinjaLoc.RIGHT : NinjaLoc.CENTER;
		currNinjaLoc = isAtRightCorner() ? NinjaLoc.LEFT : NinjaLoc.CENTER;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(currNinjaLoc == NinjaLoc.RIGHT)
			drawImageAt(batch,getX() + getLayer().getWidth(), getY());
		else if(currNinjaLoc == NinjaLoc.LEFT)
			drawImageAt(batch, getX() - getLayer().getWidth(), getY());
	}

	@Override
	public void act(float dt) {
		super.act(dt);

		float xPos = getX();
		float yPos = getY();

		if(isAtLeftCorner()){
			if(currNinjaLoc != NinjaLoc.RIGHT)
				updateNinja(NinjaLoc.RIGHT);
			if(xPos <= -getWidth())
				this.setPosition(getLayer().getWidth() - getWidth(), yPos);
		}
		else if(isAtRightCorner()){
			if(currNinjaLoc != NinjaLoc.LEFT)
				updateNinja(NinjaLoc.LEFT);
			if(xPos >= getLayer().getWidth() )
				this.setPosition(0, yPos);
		}
		else if(currNinjaLoc != NinjaLoc.CENTER){
			updateNinja(NinjaLoc.LEFT);
		}
	} 

	private void updateNinja(NinjaLoc loc){
		int gap =  loc.pos - currNinjaLoc.pos;
		ShapeTransformer.translate(ninjaFixture.getShape(), gap * Scene.WIDTH, 0);
		currNinjaLoc = loc;
	}

	protected boolean isAtLeftCorner(){
		return (getX() <= 0);
	}

	protected boolean isAtRightCorner(){
		return (getX() + getWidth() >= getLayer().getWidth());
	}



	private enum NinjaLoc{
		LEFT(-1), RIGHT(1), CENTER(0);
		public int pos;
		NinjaLoc(int p){
			pos = p;
		}
	}

}
