package com.dropTheBox.scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.dropTheBox.DropTheBox;

public class StartScene extends StagedScene {
	private final DropTheBox dtb;
	private ImageButton playButton;
	
	public StartScene(DropTheBox _dtb){
		dtb = _dtb;
		playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(dtb.getAssMan().get("playButton.png", Texture.class))), new TextureRegionDrawable(new TextureRegion(dtb.getAssMan().get("playButton_down.png", Texture.class))));
		playButton.addListener(new ButtonListener());
		
		Table table = getTable();
		table.add(new Image(dtb.getAssMan().get("logo.png", Texture.class))).padBottom(200);
		table.row();
		table.add(playButton);
	}
	
	private class ButtonListener extends ClickListener{
		@Override
		public void clicked(InputEvent event, float x, float y){
		
			if(event.getListenerActor() == playButton){

				dtb.setScene(new MenuScene(dtb));
			}
		}
	}

}
