package com.dropTheBox.game.actor.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.dropTheBox.game.actor.item.Item;
import com.dropTheBox.game.layer.ActorLayer;

public class EntityManager implements Disposable{
	private final ActorLayer layer;
	private final Pool<Entity> entityPool;

	private final Array<Entity> entityList;
	
	public EntityManager(ActorLayer _layer){
		layer = _layer;

		entityPool = new Pool<Entity>(){
			@Override
			protected Entity newObject() {
				return null; //TODO fix
			}};
		
			entityList = new Array<Entity>();
	}
	
	public void destroyAbove(float desPoint){
		if(entityList.size == 0) return;
		while(entityList.first().getY() > desPoint){
			entityPool.free(entityList.first());
			entityList.removeIndex(0);
		}
	}


	@Override
	public void dispose() {
		for(Entity entity: entityList)
			entity.dispose();
	}
}
