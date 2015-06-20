package com.dropTheBox.game.entity.platform;

import com.dropTheBox.game.entity.Platform;
import com.dropTheBox.game.layer.ActorLayer;

public class NormalPlatform extends Platform {
	public static final float HEIGHT = 20;
	
	public NormalPlatform(ActorLayer layer) {
		super(layer);
		this.setName("normalPlatform");
	}
	

	public void init(float xPos, float yPos, float width){
		super.init(xPos, yPos, width, HEIGHT);
	}

}
