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
	
	private final ActorLayer actorLayer;
	private final BackgroundLayer backgroundLayer;
	private final DisplayLayer displayLayer;
	private final GlassLayer glassLayer;
	
	private GameState currState;
	
	public GameScene(DropTheBox _dtb, LevelData data){
		dtb = _dtb;
		levelData = data;
		levelAsset = new AssetManager();
		currState = GameState.Running; //TODO change it to CountDown
		
		SpriteBatch batch = new SpriteBatch();
		actorLayer = new ActorLayer(this);
		backgroundLayer = new BackgroundLayer(this, batch);
		displayLayer = new DisplayLayer(this, batch);
		glassLayer = new GlassLayer(this, batch);
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(actorLayer.getProcessor());
		multiplexer.addProcessor(displayLayer.getProcessor());
		multiplexer.addProcessor(backgroundLayer.getProcessor());
		multiplexer.addProcessor(glassLayer.getProcessor());
		Gdx.input.setInputProcessor(multiplexer);
		Gdx.input.setCatchBackKey(true); 
	}
	
	@Override
	public void update() {
		float dt = Gdx.graphics.getDeltaTime();
		actorLayer.act(dt);
		backgroundLayer.act(dt);
		displayLayer.act(dt);
		glassLayer.act(dt);
	}

	@Override
	public void draw() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		backgroundLayer.draw();
		actorLayer.draw();
		displayLayer.draw();
		glassLayer.draw();
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
		actorLayer.dispose();
		backgroundLayer.dispose();
		displayLayer.dispose();
		glassLayer.dispose();
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
	
	public ActorLayer getActorLayer() {
		return actorLayer;
	}
	
	public BackgroundLayer getBackgroundLayer(){
		return backgroundLayer;
	}
	
	public DisplayLayer getDisplayLayer(){
		return displayLayer;
	}
	
	public GlassLayer getGlassLayer(){
		return glassLayer;
	}
	
	public enum GameState{
		CountDown, Running, Win, Lose, Pause;
	}

}
