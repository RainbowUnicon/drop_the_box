package com.dropTheBox.game.layer;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dropTheBox.scene.GameScene;
import com.dropTheBox.scene.Scene;

abstract class Layer implements Disposable{
	private final int width, height;
	private final GameScene gs;
	public final Stage stage;

	public Layer(GameScene _scene, Batch _batch){
		gs = _scene;	
		width = Scene.WIDTH;
		height = Scene.HEIGHT;
		stage = new Stage(new StretchViewport(width, height), _batch);
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
	
	public final ActorLayer getActorLayer() {
		return gs.getActorLayer();
	}
	
	public final BackgroundLayer getBackgroundLayer(){
		return gs.getBackgroundLayer();
	}
	
	public final DisplayLayer getDisplayLayer(){
		return gs.getDisplayLayer();
	}
	
	public final GlassLayer getGlassLayer(){
		return gs.getGlassLayer();
	}

	public float getWidth(){
		return width;
	}

	public float getHeight(){
		return height;
	}

	@Override
	public void dispose(){
		stage.dispose();
	}
}
