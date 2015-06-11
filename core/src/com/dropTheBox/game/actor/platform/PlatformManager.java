package com.dropTheBox.game.actor.platform;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.dropTheBox.game.Camera;
import com.dropTheBox.game.layer.ActorLayer;


public class PlatformManager implements Disposable {
	private final ActorLayer layer;

	private final Array<Platform> platformList;
	private final Pool<Platform> pool;

	

	public PlatformManager(ActorLayer _layer){
		layer = _layer;

		platformList = new Array<Platform>();
		pool = new Pool<Platform>(){
			@Override
			protected Platform newObject() {
				return new NormalPlatform(layer);
			}
		};

	}

	public void destroyAbove(float desPoint){
		if(platformList.size == 0) return;
		while(platformList.first().getY() > desPoint){
			pool.free(platformList.first());
			platformList.removeIndex(0);
		}
	}


	public void generate(String name, float x, float y, float w){
		Platform platform = pool.obtain();
		platform.init(x, y, w, 25);
		System.out.println(x + " " + y + " " + w);
		platformList.add(platform);
	}



	@Override
	public void dispose() {
		for(Platform platform : platformList)
			platform.dispose();
	}
	
	
	

	
}


