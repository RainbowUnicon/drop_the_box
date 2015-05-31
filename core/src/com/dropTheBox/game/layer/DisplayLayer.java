package com.dropTheBox.game.layer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.dropTheBox.scene.GameScene;

public class DisplayLayer extends Layer {
	
	public DisplayLayer(GameScene scene, Batch batch){
		super(scene,Layer.WIDTH, Layer.HEIGHT, batch);
	}
}
