package com.dropTheBox.game;


import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.dropTheBox.game.entity.EntityData;
import com.dropTheBox.game.entity.Platform;
import com.dropTheBox.game.entity.platform.NormalPlatform;
import com.dropTheBox.game.layer.ActorLayer;


public class PlatformFactory {
	private final ActorLayer layer;

	public PlatformFactory(ActorLayer _layer){
		layer = _layer;

		Pools.set(NormalPlatform.class, new Pool<NormalPlatform>(){
			@Override
			protected NormalPlatform newObject() {
				return new NormalPlatform(layer);
			}
		});
	}




	public Platform generate(EntityData data){
		Platform platform = null;
		switch(data.name){
		case "normalPlatform":
			platform = buildNormalPlatform(data);
			break;
		}
		return platform;
	}
	
	public NormalPlatform buildNormalPlatform(EntityData data){
		NormalPlatform platform = Pools.obtain(NormalPlatform.class);
		platform.init(data.xPos, data.yPos, data.width);
		if(data.properties.size > 0) runThroughProps(platform, data.properties);
		return platform;
	}
	
	protected void runThroughProps(Platform platform, ObjectMap<String, Object> map){
		if(map.get("rotation") != null) platform.setRotation((float) map.get("rotation"));
		if(map.get("scaleX") != null) platform.setScaleX((float) map.get("scaleX"));
		if(map.get("scaleY") != null) platform.setScaleY((float) map.get("scaleY"));
		if(map.get("scaleXY") != null) platform.setScale((float) map.get("scaleXY"));
		if(map.get("xVel") != null) platform.setXLinearVelocity((float) map.get("xVel"));
		if(map.get("yVel") != null) platform.setYLinearVelocity((float) map.get("yVel"));
		if(map.get("aVel") != null) platform.setAngularVelocity((float) map.get("aVel"));
	}









}


