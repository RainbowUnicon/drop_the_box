package com.dropTheBox.game.layer;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.dropTheBox.scene.GameScene;

public class DisplayLayer extends Layer {
	
	public DisplayLayer(GameScene scene, Batch batch){
		super(scene, batch);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void act(float dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputProcessor getProcessor() {
		// TODO Auto-generated method stub
		return stage;
	}
}
