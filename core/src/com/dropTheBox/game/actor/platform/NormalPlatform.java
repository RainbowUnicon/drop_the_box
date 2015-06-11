package com.dropTheBox.game.actor.platform;

import com.dropTheBox.game.layer.ActorLayer;

public class NormalPlatform extends Platform{
	public static final float HEIGHT = 25;
	
	public NormalPlatform(ActorLayer _layer) {
		super(_layer);
	}
	
	
	public void init(float x, float y, float w){
		super.init(x, y, w, HEIGHT);
	}


}
