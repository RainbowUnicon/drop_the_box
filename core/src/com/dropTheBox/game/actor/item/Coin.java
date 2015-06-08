package com.dropTheBox.game.actor.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction;
import com.badlogic.gdx.utils.Array;
import com.dropTheBox.game.actor.player.Player;
import com.dropTheBox.game.layer.ActorLayer;

public class Coin extends Item {
	public Coin(ActorLayer _layer) {
		super(_layer);
		// TODO Auto-generated constructor stub
	}

	public static final int DIAMETER = 50;

	public static final int GOLD = 300;
	public static final int SILVER = 200;
	public static final int BRONZE = 100;

	private final int numFrame = 6;
	private Animation animation;
	private float stateTime;
	private int coinType;



	public void init(float x, float y, int c){
		super.init(x, y, 50,50);
		coinType = c;

		Texture texture = getTexture(getLayer(),c);
		Array<TextureRegion> regions = new Array<TextureRegion>(numFrame);
		for(int i = 0; i < numFrame; i ++)
			regions.add(new TextureRegion(texture, i * 50, 0, DIAMETER, DIAMETER));

		animation = new Animation(.10f, regions, Animation.PlayMode.LOOP);
	}

	@Override
	public void act(float dt){
		super.act(dt);
		stateTime += dt;
		setImage( animation.getKeyFrame(stateTime));
	}

	@Override
	public void draw(Batch batch, float a){
		super.draw(batch, a);
	}

	private Texture getTexture(ActorLayer layer, int c){
		if(c == GOLD)
			return layer.getAssets().get("game/goldCoin.png", Texture.class);
		else if(c == SILVER)
			return layer.getAssets().get("game/silverCoin.png", Texture.class);
		else if(c == BRONZE)
			return layer.getAssets().get("game/bronzeCoin.png", Texture.class);
		throw new RuntimeException("Not valid coin type");
	}

	@Override
	public void beginContact (Contact contact){
		if(!isActivated() && contact.getFixtureA().getUserData() instanceof Player ||
				contact.getFixtureB().getUserData() instanceof Player){
			
			RemoveActorAction action = new RemoveActorAction();
			action.setTarget(this);
			getStage().addAction(action);
			this.activate();
		}
	};

}
