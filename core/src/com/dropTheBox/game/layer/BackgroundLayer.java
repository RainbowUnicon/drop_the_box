package com.dropTheBox.game.layer;

import javafx.scene.layout.Background;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dropTheBox.scene.GameScene;

public class BackgroundLayer extends Layer{
	private Batch batch;
	private Texture image;
	
	public BackgroundLayer(GameScene scene, Batch batch){
		super(scene,Layer.WIDTH, Layer.HEIGHT, batch);
		this.batch = batch;
		image = scene.getLevelAsset().get("background/test.png",Texture.class);
	}

	@Override
	public void draw() {
		batch.begin();
		batch.draw(image, 0, 0, image.getWidth(), image.getHeight());
		batch.end();
	}

	@Override
	public void update(float dt) {
	}

	@Override
	public InputProcessor getProcessor() {
		return stage;
	}

}
