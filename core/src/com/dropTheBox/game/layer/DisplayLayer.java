package com.dropTheBox.game.layer;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.dropTheBox.scene.GameScene;

public class DisplayLayer extends Layer {
	
	public DisplayLayer(GameScene scene, Batch batch){
		super(scene,Layer.WIDTH, Layer.HEIGHT, batch);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputProcessor getProcessor() {
		// TODO Auto-generated method stub
		return stage;
	}
}
