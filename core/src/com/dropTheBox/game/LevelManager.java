package com.dropTheBox.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.dropTheBox.game.entity.Actor;
import com.dropTheBox.game.entity.Item;
import com.dropTheBox.game.entity.Platform;
import com.dropTheBox.game.entity.item.Coin;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.utils.Base;

public class LevelManager implements Disposable{
	public static final float PLATFORMGAP = 80; 
	public static final float HOLESIZE = 60;
	public static final float WIDTHBUFFER = 80;
	public static final float DESTRUCTIONBUFFER = 100;
	public static final float CREATIONBUFFER = 100;

	private final ActorLayer layer;

	private final PlatformFactory platformFactory;
	private final ItemFactory itemFactory;
	private final EntityFactory entityFactory;

	private final Array<Base> actorList;


	boolean goNext = false;
	int yPos = 300;


	public LevelManager(ActorLayer _layer){
		layer = _layer;

		layer.getCamera().setYVelocity(-180); //TODO remove magic number

		entityFactory = new EntityFactory(layer);
		platformFactory = new PlatformFactory(layer);
		itemFactory = new ItemFactory(layer);

		actorList = new Array<Base>();

		pData = new Array<Float[]>(); //TODO remove thiss

		next();
		this.act(0);
	}


	public void act(float dt){
		if(getStartPoint() > getCreationPoint()){
			while(pData.size >0){
				Float[] w = pData.removeIndex(0);
				Platform platform = platformFactory.generate("platform", w[0], w[1], w[2], w[3]);
				actorList.add(platform);
			}
			next();
		}
		actorList.add(Pools.obtain(Coin.class));


		float desPoint = this.getDestructionPoint();
		int size = actorList.size;
		for(int i = size - 1; i >=0; i --){
			if(actorList.get(i).getY() > desPoint){
				Pools.free(actorList.get(i));
				actorList.removeIndex(i);
			}
		}
	}




	private float getDestructionPoint(){
		return layer.getCamera().getY() + layer.getHeight() + DESTRUCTIONBUFFER;
	}


	private float getCreationPoint(){
		return layer.getCamera().getY() - CREATIONBUFFER;
	}

	@Override
	public void dispose() {
	}




	public void next(){
		yPos -= PLATFORMGAP;
		createPlatforms();
	}

	public float getStartPoint(){
		return yPos;
	}

	Array<Float[]> pData;

	public void createPlatforms(){
		int holePos = MathUtils.random(0,((int) (layer.getWidth() - HOLESIZE))); //decide the position of the hole
		pData.add(new Float[]{ (float) holePos, (float) yPos, 280f, 20f});
	}
}
