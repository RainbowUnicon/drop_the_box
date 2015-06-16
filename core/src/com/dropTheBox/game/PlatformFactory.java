package com.dropTheBox.game;


import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.dropTheBox.game.entity.Platform;
import com.dropTheBox.game.layer.ActorLayer;


public class PlatformFactory {
	private final ActorLayer layer;

	public PlatformFactory(ActorLayer _layer){
		layer = _layer;

		Pools.set(Platform.class, new Pool<Platform>(){
			@Override
			protected Platform newObject() {
				return new Platform(layer);
			}
		});
	}




	public Platform generate(String name, float x, float y, float w, float h){
		Platform platform = null;
		switch(name){
		case "platform":
			platform = Pools.get(Platform.class).obtain();
			platform.init(x, y, w, h);
			break;
		}
		return platform;
	}









}


