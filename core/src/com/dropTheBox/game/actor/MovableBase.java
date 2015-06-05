package com.dropTheBox.game.actor;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.game.layer.Layer;



public abstract class MovableBase extends Base{
	private Body ninjaBody;
	private Fixture ninjaFixture;

	protected void init(ActorLayer layer,float x, float y, float w, float h){
		super.init(layer, x, y, w, h);
		World world = layer.world;

		ninjaBody = world.createBody(createBodyDef());

		Shape shapeToBeDisposed = createShape();

		ninjaFixture = ninjaBody.createFixture(createFixtureDef(shapeToBeDisposed));
		ninjaFixture.setUserData(this);
		
		ninjaBody.setActive(false);

		shapeToBeDisposed.dispose();
	}
	
	@Override
	public void act(float dt){
		super.act(dt);
		float xPos = getX();
		float yPos = getY();
		float xVel = getXVelocity();
		float yVel = getYVelocity();
		
		if(xPos <= 0 && xVel < 0){
			System.out.println("DD");
			setPosition(Layer.WIDTH + xPos ,yPos);
		}
		else if(xPos + getWidth() >= Layer.WIDTH && xVel > 0){
			System.out.println("AA");
			setPosition(-getWidth() + xPos + getWidth() - Layer.WIDTH,yPos);
		}
	}
}
