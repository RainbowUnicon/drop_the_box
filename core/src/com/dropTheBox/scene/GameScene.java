package com.dropTheBox.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dropTheBox.DropTheBox;
import com.dropTheBox.game.LevelData;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.game.layer.BackgroundLayer;
import com.dropTheBox.game.layer.DisplayLayer;
import com.dropTheBox.game.layer.GlassLayer;

public class GameScene implements Scene {
	private final DropTheBox dtb;

	private final LevelData levelData;
	private final AssetManager levelAsset;
	
	private final ActorLayer aLayer;
	private final BackgroundLayer bLayer;
	private final DisplayLayer dLayer;
	private final GlassLayer gLayer;
	
	private GameState state;
	
	public GameScene(DropTheBox _dtb, LevelData data){
		dtb = _dtb;
		levelData = data;
		levelAsset = new AssetManager();
		state = GameState.Running; //TODO change it to CountDown
		
		aLayer = new ActorLayer(this);
		bLayer = new BackgroundLayer(this);
		dLayer = new DisplayLayer(this);
		gLayer = new GlassLayer(this);
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(bLayer.getStage());
		multiplexer.addProcessor(aLayer.getStage());
		multiplexer.addProcessor(dLayer.getStage());
		multiplexer.addProcessor(gLayer.getStage());
		Gdx.input.setInputProcessor(multiplexer);
		Gdx.input.setCatchBackKey(true); 
	}
	
	@Override
	public void update() {
		float dt = Gdx.graphics.getDeltaTime();
		aLayer.act(dt);
		bLayer.act(dt);
		dLayer.act(dt);
		gLayer.act(dt);
	}

	@Override
	public void draw() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		aLayer.draw();
		bLayer.draw();
		dLayer.draw();
		gLayer.draw();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void resume() {
		state = GameState.Pause;
	}

	@Override
	public void pause() {
		state = GameState.Running;
	}

	@Override
	public void dispose() {
		Gdx.input.setCatchBackKey(false); 
		aLayer.dispose();
		bLayer.dispose();
		dLayer.dispose();
		gLayer.dispose();
		levelAsset.dispose();
	}
	
	public void reset(){
		dtb.setScene(new GameScene(dtb,levelData));
	}
	
	public GameState getState() {
		return state;
	}
	public void setState(GameState state) {
		this.state = state;
	}
	public LevelData getLevelData() {
		return levelData;
	}
	public AssetManager getLevelAsset() {
		return levelAsset;
	}
	public ActorLayer getaLayer() {
		return aLayer;
	}
	public BackgroundLayer getbLayer() {
		return bLayer;
	}
	public DisplayLayer getdLayer() {
		return dLayer;
	}
	public GlassLayer getgLayer() {
		return gLayer;
	}
	
	public enum GameState{
		CountDown, Running, Win, Lose, Pause;
	}

}
