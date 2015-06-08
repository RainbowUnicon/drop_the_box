package com.dropTheBox.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	
	private GameState currState;
	
	public GameScene(DropTheBox _dtb, LevelData data){
		dtb = _dtb;
		levelData = data;
		levelAsset = new AssetManager();
		currState = GameState.Running; //TODO change it to CountDown
		
		SpriteBatch batch = new SpriteBatch();
		aLayer = new ActorLayer(this, batch);
		bLayer = new BackgroundLayer(this, batch);
		dLayer = new DisplayLayer(this, batch);
		gLayer = new GlassLayer(this, batch);
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(aLayer.getProcessor());
		multiplexer.addProcessor(dLayer.getProcessor());
		multiplexer.addProcessor(bLayer.getProcessor());
		multiplexer.addProcessor(gLayer.getProcessor());
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
		bLayer.draw();
		aLayer.draw();
		dLayer.draw();
		gLayer.draw();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void resume() {
		currState = GameState.Running;
	}

	@Override
	public void pause() {
		currState = GameState.Pause;
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
		return currState;
	}
	public void setState(GameState state) {
		this.currState = state;
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
