package com.dropTheBox.game.layer;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dropTheBox.scene.GameScene;
import com.dropTheBox.scene.Scene;

public abstract class Layer implements Disposable{
	private int width, height;
	
	private final GameScene gs;
	
	protected final Stage stage;
	
	
	public Layer(GameScene _scene,int _width, int _height, Batch _batch){
		gs = _scene;	
		stage = new Stage(new StretchViewport(_width, _height), _batch);
	}	

	public abstract void draw();

	public abstract void act(float dt);

	public abstract InputProcessor getProcessor();
	
	public AssetManager getAssets(){
		return gs.getLevelAsset();
	}

	public GameScene.GameState getState(){
		return gs.getState();
	}

	public float getWidth(){
		return width;
	}

	public float getHeight(){
		return height;
	}
	
	public Stage getStage(){
		return stage;
	}

	@Override
	public void dispose(){
		stage.dispose();
	}
}
