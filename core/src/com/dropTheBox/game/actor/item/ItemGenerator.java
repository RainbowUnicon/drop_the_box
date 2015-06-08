package com.dropTheBox.game.actor.item;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.dropTheBox.game.Floor;
import com.dropTheBox.game.layer.ActorLayer;

public class ItemGenerator {
	private final ActorLayer layer;
	private final Pool<Coin> coinPool;

	public ItemGenerator(ActorLayer _layer){
		layer = _layer;

		coinPool = new Pool<Coin>(){
			@Override
			protected Coin newObject() {
				return new Coin(layer);
			}};
	}

	public void createItems(Floor floor){
		if(MathUtils.random(4) == 0){
			Coin coin = coinPool.obtain();
			coin.init(floor.getPlatforms().first().getY() + floor.getPlatforms().first().getHeight() + 10, 8, Coin.SILVER);
			floor.addItem(coin);
		}
	}
}
