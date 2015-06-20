package com.dropTheBox.game.entity;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool.Poolable;

public class EntityData implements Poolable{
	public Type type;
	public String name;
	public float xPos, yPos, width, height;
	public final ObjectMap<String, Object> properties;
	
	
	public EntityData() {
		properties = new ObjectMap<String, Object>();
	}

	@Override
	public void reset() {
		name = "";
		xPos = 0;
		yPos = 0;
		width = 10;
		height = 10;
		properties.clear();
	}
	
	public void set(EntityData data){
		this.type = data.type;
		this.name = data.name;
		this.xPos = data.xPos;
		this.yPos = data.yPos;
		this.width = data.width;
		this.height = data.height;
		this.properties.putAll(data.properties);
	}
	
	public enum Type{
		ITEM , PLATFORM, SHAPE;
	}

}
