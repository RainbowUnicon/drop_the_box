package com.dropTheBox.game.actor.item;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.dropTheBox.game.layer.ActorLayer;

public class ItemManager implements Disposable{
	private final ActorLayer layer;
	private final Pool<Coin> coinPool;

	private final Array<Coin> itemList;
	
	public ItemManager(ActorLayer _layer){
		layer = _layer;

		coinPool = new Pool<Coin>(){
			@Override
			protected Coin newObject() {
				return new Coin(layer);
			}};
		
			itemList = new Array<Coin>();
	}
	
	public void destroyAbove(float desPoint){
		if(itemList.size == 0) return;
		while(itemList.first().getY() > desPoint){
			coinPool.free(itemList.first());
			itemList.removeIndex(0);
		}
	}

//	public void createItems(Floor floor){
//		if(MathUtils.random(4) == 0){
//			Coin coin = coinPool.obtain();
//			coin.init(floor.getPlatforms().first().getY() + floor.getPlatforms().first().getHeight() + 10, 8, Coin.SILVER);
//			floor.addItem(coin);
//		}
//	}

	@Override
	public void dispose() {
		for(Item item : itemList)
			item.dispose();
	}
}
