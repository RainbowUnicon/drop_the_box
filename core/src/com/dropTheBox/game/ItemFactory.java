package com.dropTheBox.game;


import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.dropTheBox.game.entity.item.Coin;
import com.dropTheBox.game.layer.ActorLayer;

public class ItemFactory{
	private final ActorLayer layer;

	
	public ItemFactory(ActorLayer _layer){
		layer = _layer;	
		
		Pools.set(Coin.class, new Pool<Coin>(){
			@Override
			protected Coin newObject() {
				return new Coin(layer);
			}
		});
	}
	


//	public void createItems(Floor floor){
//		if(MathUtils.random(4) == 0){
//			Coin coin = coinPool.obtain();
//			coin.init(floor.getPlatforms().first().getY() + floor.getPlatforms().first().getHeight() + 10, 8, Coin.SILVER);
//			floor.addItem(coin);
//		}
//	}
}
