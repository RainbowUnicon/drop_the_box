package com.dropTheBox.scene;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dropTheBox.DropTheBox;
import com.dropTheBox.game.EventHandler;
import com.dropTheBox.game.LevelData;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.game.layer.BackgroundLayer;
import com.dropTheBox.game.layer.DisplayLayer;
import com.dropTheBox.game.layer.GlassLayer;

public class GameScene implements Scene {
	private final DropTheBox dtb;
	private final EventHandler handler;
	

	private final LevelData levelData;
	private final AssetManager levelAsset;
	
	private final ActorLayer aLayer;
	private final BackgroundLayer bLayer;
	private final DisplayLayer dLayer;
	private final GlassLayer gLayer;
	
	private GameState state;
	private SpriteBatch batch;
	
	public GameScene(DropTheBox _dtb, LevelData data){
		dtb = _dtb;
		handler = new EventHandler();
		levelData = data;
		levelAsset = new AssetManager();
		
		aLayer = new ActorLayer(this);
		bLayer = new BackgroundLayer(this);
		dLayer = new DisplayLayer(this);
		gLayer = new GlassLayer(this);
		
		state = GameState.CountDown;
		batch = new SpriteBatch();
		
	}
	@Override
	public void update() {
		aLayer.update();
		bLayer.update();
		dLayer.update();
		gLayer.update();
	}

	@Override
	public void draw() {
		batch.begin();
		aLayer.draw(batch);
		bLayer.draw(batch);
		dLayer.draw(batch);
		gLayer.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		aLayer.resume();
		bLayer.resume();
		dLayer.resume();
		gLayer.resume();
	}

	@Override
	public void pause() {
		aLayer.pause();
		bLayer.pause();
		dLayer.pause();
		gLayer.pause();
	}

	@Override
	public void dispose() {
		aLayer.dispose();
		bLayer.dispose();
		dLayer.dispose();
		gLayer.dispose();
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
	public EventHandler getHandler() {
		return handler;
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
