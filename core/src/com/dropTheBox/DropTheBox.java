package com.dropTheBox;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.dropTheBox.actor.player.PlayerData;
import com.dropTheBox.scene.LogoScene;
import com.dropTheBox.scene.Scene;
import com.dropTheBox.utils.SettingData;

public class DropTheBox extends ApplicationAdapter {
	private Scene scene;
	private AssetManager commonAsset;
	private AssetManager gameAsset;
	private SettingData  setting;
	private PlayerData   playerData;
	
	@Override
	public void create () {
		scene = new LogoScene(this);
		commonAsset = new AssetManager();
		gameAsset   = new AssetManager();
		setting     = new SettingData();
		playerData  = new PlayerData();
	}

	@Override
	public void render () {
		scene.update();
		scene.draw();
	}
	
	@Override
	public void resize(int width, int height){
		scene.resize(width, height);
	}

	@Override
	public void resume(){
		scene.resume();
	}

	@Override
	public void pause(){
		scene.pause();
	}

	@Override
	public void dispose(){
		scene.dispose();
		commonAsset.dispose();
		gameAsset.dispose();
	}
	
	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public AssetManager getCommonAsset() {
		return commonAsset;
	}

	public AssetManager getGameAsset() {
		return gameAsset;
	}

	public SettingData getSetting() {
		return setting;
	}

	public PlayerData getPlayerData() {
		return playerData;
	}


}
