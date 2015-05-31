package com.dropTheBox.game.layer;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dropTheBox.scene.GameScene;
import com.dropTheBox.scene.Scene;

public abstract class Layer implements Disposable {
	protected final GameScene gs;
	protected final Stage stage;
	
	public Layer(GameScene scene){
		gs = scene;
		stage = new Stage(new StretchViewport(Scene.WIDTH,Scene.HEIGHT));
	}	
	
	@Override
	public void dispose() {
		stage.dispose();
	}
	
	public Stage getStage(){
		return stage;
	}

	public AssetManager getAssets(){
		return gs.getLevelAsset();
	}
	
	public abstract void act(float dt);
	
	public abstract void draw();
}
