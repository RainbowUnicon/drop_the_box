package com.dropTheBox.game.layer;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.dropTheBox.game.display.ScoreBoard;
import com.dropTheBox.scene.GameScene;

public class DisplayLayer extends Layer {
	private final ScoreBoard scoreBoard;
	
	public DisplayLayer(GameScene scene, Batch batch){
		super(scene, batch);
		
		scoreBoard = new ScoreBoard(this);
	}

	@Override
	public void draw() {
		stage.draw();
		
	}

	@Override
	public void act(float dt) {
		stage.act();
		
	}

	@Override
	public InputProcessor getProcessor() {
		return stage;
	}
	
	public ScoreBoard getScoreBoard(){
		return scoreBoard;
	}
}
