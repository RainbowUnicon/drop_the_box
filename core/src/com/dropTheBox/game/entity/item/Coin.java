package com.dropTheBox.game.entity.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.dropTheBox.game.entity.Item;
import com.dropTheBox.game.entity.player.Player;
import com.dropTheBox.game.layer.ActorLayer;

public class Coin extends Item {


	public static final int DIAMETER = 40;

	public static final int GOLD = 300;
	public static final int SILVER = 200;
	public static final int BRONZE = 100;

	private final int numFrame = 6;
	private Animation animation;
	private float stateTime;
	private int coinType;

	public Coin(ActorLayer _layer) {
		super(_layer);
		this.setName("coin");
	}

	public void init(float x, float y, int c){
		super.init(x, y, DIAMETER, DIAMETER);
		coinType = c;

		Texture texture = getTexture(getLayer(),c);
		Array<TextureRegion> regions = new Array<TextureRegion>(numFrame);
		for(int i = 0; i < numFrame; i ++)
			regions.add(new TextureRegion(texture, i * 50, 0, 50,50)); //TODO Fix this

		animation = new Animation(.10f, regions, Animation.PlayMode.LOOP);
		setImage( animation.getKeyFrame(stateTime));
	}

	@Override
	public void act(float dt){
		super.act(dt);
		stateTime += dt;
		setImage( animation.getKeyFrame(stateTime));
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
	public void activate() {
		getLayer().getDisplayLayer().getScoreBoard().addScore(coinType);
	}
	
	@Override
	protected Shape createShape() {
		return Pools.obtain(CircleShape.class);
	}

}
