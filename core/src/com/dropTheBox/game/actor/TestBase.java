package com.dropTheBox.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.dropTheBox.game.layer.ActorLayer;

public class TestBase extends Base {
	public static final float WIDTH = 40, HEIGHT = 40;
	private Fixture cFixture;


	
	
	public TestBase(ActorLayer _layer) {
		super(_layer);
		
		Shape shape = createShape();
		FixtureDef def = createFixtureDef(shape);
		cFixture = createFixture(def);
		cFixture.setUserData(this);
		shape.dispose();
		setImage(new TextureRegion(_layer.getAssets().get("game/base.png", Texture.class)));
		this.setType(BodyType.DynamicBody);
	}
	
	public void init(float xPos, float yPos, float width, float height){
		super.init(xPos, yPos, width, height);
	}
	

	protected Shape createShape() {
		CircleShape shape = new CircleShape();
		shape.setRadius(WIDTH / WORLDSCALE / 2f);
		return shape;
	}


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
