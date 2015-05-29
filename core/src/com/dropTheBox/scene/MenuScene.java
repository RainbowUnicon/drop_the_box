package com.dropTheBox.scene;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.dropTheBox.DropTheBox;
import com.dropTheBox.scene.utils.LevelButton;

public class MenuScene extends StagedScene {
	private final DropTheBox dtb;
	private final ImageButton settingsButton;
	
	public MenuScene(DropTheBox _dtb){
		dtb = _dtb;
		
		ButtonListener listener = new ButtonListener();
		getTable().defaults().pad(5);
		
		Table buttonTable = new Table();
		ScrollPane scrollPane = new ScrollPane(buttonTable);
		buttonTable.defaults().pad(8);
		for(int i = 0; i < 50; i ++){
			buttonTable.add(createLevelButton(i +1, dtb.getAssMan())).row();
			
		}
		
		Table table = new Table();
		
		settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(dtb.getAssMan().get("settingButton.png", Texture.class))), new TextureRegionDrawable(new TextureRegion(dtb.getAssMan().get("settingButton_down.png", Texture.class))));
		settingsButton.addListener(listener);
		table.add(settingsButton).right();
		
		getTable().add(scrollPane);
		getTable().row();
		getTable().add(settingsButton);
	}
	
	@Override
	public void draw(){
		super.draw();
	}
	
	private class ButtonListener extends ClickListener{
		@Override
		public void clicked(InputEvent event, float x, float y){
		
			if(event.getListenerActor() == settingsButton){
				dtb.setScene(new SettingsScene(dtb));
			}
		}
	}
	
	private LevelButton createLevelButton(int level, AssetManager assMan){
		String[] names = new String[] {"Break Boxes", "RUN!!!", "Time Run", "Get them all", "Avoid them", "Do Something", "You can't catch me", "BOSS FIGHT"};
		return new LevelButton(level, MathUtils.random(5),names[MathUtils.random(7)], MathUtils.random(1000) * 10,assMan);
	}
}
