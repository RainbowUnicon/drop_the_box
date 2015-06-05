package com.dropTheBox.game.layer;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dropTheBox.scene.GameScene;
import com.dropTheBox.scene.Scene;

public abstract class Layer implements Disposable{
	public static final int WIDTH  = Scene.WIDTH;
	public static final int HEIGHT = Scene.HEIGHT;

	protected final GameScene gs;
	protected final Stage stage;

	public Layer(GameScene scene,int width, int height, Batch batch){
		gs = scene;	
		stage = new Stage(new StretchViewport(width, height), batch);
	}	

	public abstract void draw();

	public abstract void update(float dt);

	public abstract InputProcessor getProcessor();
	
	public AssetManager getAssets(){
		return gs.getLevelAsset();
	}

	public GameScene.GameState getState(){
		return gs.getState();
	}

	public float getWidth(){
		return stage.getViewport().getWorldWidth();
	}

	public float getHeight(){
		return stage.getViewport().getWorldHeight();
	}

	@Override
	public void dispose(){
		stage.dispose();
	}
}
