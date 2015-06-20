package com.dropTheBox.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.dropTheBox.game.entity.EntityData;
import com.dropTheBox.game.entity.Platform;
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
	private final ActorFactory entityFactory;

	private final Array<Base> entityList;
	private final Array<EntityData> entityDataQueue;

	private float cameraYVelocity;


	boolean goNext = false;
	int yPos = 300;


	public LevelManager(ActorLayer _layer){
		layer = _layer;

		int fallingSpeed = -150;
		Gdx.app.log("Drop The Box", "Setting camera moving speed " + fallingSpeed);
		layer.getCamera().setYVelocity(fallingSpeed); //TODO remove magic number


		Pools.set(EntityData.class, new Pool<EntityData>(){
			@Override
			protected EntityData newObject() {
				return new EntityData();
			}
		});


		Gdx.app.log("Drop The Box", "Creating Factories");
		entityFactory = new ActorFactory(layer);
		platformFactory = new PlatformFactory(layer);
		itemFactory = new ItemFactory(layer);



		entityList = new Array<Base>();
		entityDataQueue = new Array<EntityData>();

		next();
		this.act(0);
	}


	public void act(float dt){
		if(getStartPoint() > calCreationPoint()){
			while(entityDataQueue.size >0){
				EntityData data = entityDataQueue.removeIndex(0);
				Platform platform = platformFactory.generate(data);
				Pools.free(data);
				entityList.add(platform);
			}
			next();
		}


		float desPoint = this.calDestructionPoint();
		int size = entityList.size;
		for(int i = size - 1; i >=0; i --){
			if(entityList.get(i).getY() > desPoint){
				Pools.free(entityList.get(i));
				entityList.removeIndex(i);
			}
		}
	}

	private float calDestructionPoint(){
		return layer.getCamera().getY() + layer.getHeight() + DESTRUCTIONBUFFER;
	}

	private float calCreationPoint(){
		return layer.getCamera().getY() - CREATIONBUFFER;
	}







	public void next(){
		yPos -= PLATFORMGAP;
		createPlatforms();
	}

	public float getStartPoint(){
		return yPos;
	}


	public void createPlatforms(){

		if(MathUtils.random(9) == 0){
			EntityData data1 = Pools.obtain(EntityData.class);
			EntityData data2 = Pools.obtain(EntityData.class);
			int holePos = MathUtils.random(0,18);
			int width = MathUtils.random(4, 6);
			data1.type = EntityData.Type.PLATFORM;
			data1.name = "normalPlatform";
			data1.xPos = (float) holePos * 20;
			data1.yPos = (float) yPos;
			data1.width =  width * 20f;
			data1.height = 20f;
			entityDataQueue.add(data1);
			
			data2.set(data1);
			data2.xPos = (float) holePos * 20 + width * 20f;
			data2.width = (float) 200 - width * 20f;
			entityDataQueue.add(data2);

		}
//		else{
//			EntityData data = Pools.obtain(EntityData.class);
//			int holePos = MathUtils.random(0,18); 
//			data.type = EntityData.Type.PLATFORM;
//			data.name = "normalPlatform";
//			data.xPos = (float) holePos * 20;
//			data.yPos = (float) yPos;
//			data.width =  280f;
//			data.height = 20f;
//			entityDataQueue.add(data);
//		}
	}


	@Override
	public void dispose() {
	}
}
