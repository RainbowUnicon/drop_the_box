package com.dropTheBox.game.layer;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dropTheBox.scene.GameScene;
import com.dropTheBox.scene.Scene;

public abstract class Layer extends Stage {
	public static final int WIDTH 	= Scene.WIDTH;
	public static final int HEIGHT = Scene.HEIGHT;
	
	protected final GameScene gs;
	
	public Layer(GameScene scene,int width, int height, Batch batch){
		super(new StretchViewport(width, height), batch);
		gs = scene;	
	}	
	
	
	public AssetManager getAssets(){
		return gs.getLevelAsset();
	}
}
