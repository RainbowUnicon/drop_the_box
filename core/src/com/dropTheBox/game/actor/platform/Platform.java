package com.dropTheBox.game.actor.platform;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.dropTheBox.game.actor.Base;
import com.dropTheBox.game.layer.ActorLayer;

public class Platform extends Base {
	public void init(ActorLayer layer, float x, float y, float w, float h){
		super.init(layer, x, y, w, h);
		getFixture().getFilterData().groupIndex = -3;
		
		Pixmap pixmap = new Pixmap( 64, 64, Pixmap.Format.RGBA8888 );
		pixmap.setColor( .5f, .5f, .5f, 1f );
		pixmap.fillRectangle(0, 0, (int) w, (int) h);
		pixmap.setColor(1,1,1,1);
		pixmap.fillRectangle(2, 2,(int) w-2,(int) h-2);
		this.setImage(new Texture( pixmap ));
		pixmap.dispose();
	}


	
	
	@Override
	protected Shape createShape() {
		PolygonShape shape = new PolygonShape();
	    shape.setAsBox(getWidth() / WORLDSCALE / 2f,getHeight() / WORLDSCALE /2f);
	    return shape;
	}

	@Override
	protected FixtureDef createFixtureDef(Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f; 
		fixtureDef.friction = 0f;
		return fixtureDef;
	}

	@Override
	protected BodyDef createBodyDef() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		return bodyDef;
	}

}
