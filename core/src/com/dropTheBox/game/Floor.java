package com.dropTheBox.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dropTheBox.game.actor.entity.Entity;
import com.dropTheBox.game.actor.item.Item;
import com.dropTheBox.game.actor.platform.Platform;
import com.dropTheBox.game.layer.ActorLayer;

/*
 * This class is composed of platform, items and entity that make a sense of a floor
 */
public class Floor extends Actor implements Poolable{
	private int floorNumber;
	
	private final ActorLayer layer;
	private final Array <Platform> platforms; 
	private final Array <Item> items; 
	private final Array <Entity> entities;
	
	public Floor(ActorLayer _layer){
		layer = _layer;
		platforms = new Array<Platform>(3);
		items = new Array<Item>();
		entities = new Array<Entity>();
	}
	
	public void init(int _floorNumber, float _yPos){
		floorNumber = _floorNumber;
	}
	
	@Override
	public boolean remove(){
		boolean success = super.remove();
		clearItems();
		clearPlatforms();
		clearEntities();
		return success;
	}
	
	public void addItem(Item item){
		items.add(item);
	}
	public void addPlatform(Platform platform){
		platforms.add(platform);
	}
	public void addEntity(Entity entity){
		entities.add(entity);
	}
	
	public void removeItem(Item item, boolean complete){
		items.removeValue(item, false);
		if(complete) item.remove();
	}
	public void removePlatform(Platform platform, boolean complete){
		platforms.removeValue(platform, false);
		if(complete) platform.remove();
	}
	public void removeEntity(Entity entity, boolean complete){
		entities.removeValue(entity, false);
		if(complete) entity.remove();
	}
	
	public void clearItems(){
		for(int i = 0; i < items.size; i++){
			items.get(0).remove();
			items.removeIndex(0);
		}
	}
	public void clearPlatforms(){
		for(int i = 0; i < platforms.size; i++){
			platforms.get(0).remove();
			platforms.removeIndex(0);
		}
	}
	public void clearEntities(){
		for(int i = 0; i < entities.size; i++){
			entities.get(0).remove();
			entities.removeIndex(0);
		}
	}
	
	
	public Array<Platform> getPlatforms(){
		return platforms;
	}
	public Array<Item> getItems(){
		return items;
	}
	public Array<Entity> getEntity(){
		return entities;
	}
	public int getFloorNumber(){
		return floorNumber;
	}

	@Override
	public void reset() {
		floorNumber = -1;
	}
}
