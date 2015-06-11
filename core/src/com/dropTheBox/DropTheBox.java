package com.dropTheBox;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.dropTheBox.game.actor.player.PlayerData;
import com.dropTheBox.scene.LogoScene;
import com.dropTheBox.scene.Scene;
import com.dropTheBox.utils.GenSettings;

public class DropTheBox extends ApplicationAdapter {
	private Scene scene;
	private AssetManager assMan;
	private GenSettings  setting;
	private PlayerData   playerData;
	
	@Override
	public void create () {
		assMan = new AssetManager();
		setting     = new GenSettings();
		playerData  = new PlayerData("AA");
		scene = new LogoScene(this);
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
		assMan.dispose();
	}
	
	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public AssetManager getAssMan() {
		return assMan;
	}


	public GenSettings getSettings() {
		return setting;
	}

	public PlayerData getPlayerData() {
		return playerData;
	}


}
