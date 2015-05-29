package com.dropTheBox.scene;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.dropTheBox.DropTheBox;
import com.dropTheBox.scene.utils.MySlider;
import com.dropTheBox.utils.GenSettings;

public class SettingsScene extends StagedScene {
	private final DropTheBox dtb;
	
	private final MySlider accSlider, sndSlider, mscSlider;
	private final ImageTextButton backButton;
	
	public SettingsScene(DropTheBox _dtb){
		dtb = _dtb;
		AssetManager assMan = dtb.getAssMan();
		GenSettings settings = dtb.getSettings();
		
		//Listener
		Listener listener = new Listener();
		
		
		//create Styles
		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = assMan.get("Rg24.ttf", BitmapFont.class);
		
		Slider.SliderStyle sStyle = new Slider.SliderStyle();
		sStyle.background = new TextureRegionDrawable(new TextureRegion(assMan.get("slider.png",Texture.class)));
		sStyle.knob = new TextureRegionDrawable(new TextureRegion(assMan.get("knob.png",Texture.class)));
		
		ImageTextButton.ImageTextButtonStyle bStyle = new ImageTextButton.ImageTextButtonStyle();
		bStyle.down = new TextureRegionDrawable(new TextureRegion(assMan.get("button_down.png",Texture.class)));
		bStyle.up = new TextureRegionDrawable(new TextureRegion(assMan.get("button.png",Texture.class)));
		bStyle.font = assMan.get("Rg24.ttf", BitmapFont.class);
		
		
		//create objects
		accSlider = new MySlider(0, 100, 1, false, sStyle);
		accSlider.setValue(settings.getAccelSen());
		accSlider.addListener(listener);
		
		sndSlider = new MySlider(0, 100, 1, false, sStyle);
		sndSlider.setValue(settings.getSoundVol());
		sndSlider.addListener(listener);
		
		mscSlider = new MySlider(0, 100, 1, false, sStyle);
		mscSlider.setValue(settings.getMusicVol());
		mscSlider.addListener(listener);
		
		backButton = new ImageTextButton("Back",bStyle);
		backButton.addListener(listener);
		
		//set them on table
		Table table = getTable();
		table.defaults().pad(5);
		
		table.add(new Label("Accelormeter", labelStyle));
		table.row();
		table.add(accSlider);
		table.row();
		table.add(new Label("Sound", labelStyle));
		table.row();
		table.add(sndSlider);
		table.row();
		table.add(new Label("Music", labelStyle));
		table.row();
		table.add(mscSlider);
		table.row();
		table.add(backButton).bottom();
	}
	
	private class Listener extends ClickListener{
		@Override
		public void clicked(InputEvent event, float x, float y){
			Actor actor = event.getListenerActor();
			GenSettings settings = dtb.getSettings();

			if(actor == accSlider)
				settings.setAccelSen((int)accSlider.getValue());
			else if(actor == sndSlider)
				settings.setSoundVol((int)sndSlider.getValue());
			else if(actor == mscSlider)
				settings.setMusicVol((int)mscSlider.getValue());
			else if(actor == backButton){
				settings.flush();
				dtb.setScene(new MenuScene(dtb));
			}
		}
	}
}

////create CheckBox Style
//CheckBox.CheckBoxStyle style = new CheckBox.CheckBoxStyle();
//style.font = assMan.get("Rg24.ttf", BitmapFont.class);
//style.checkboxOn = new TextureRegionDrawable(new TextureRegion(assMan.get("checked.png",Texture.class)));
//style.checkboxOnDisabled = new TextureRegionDrawable(new TextureRegion(assMan.get("checked_disabled.png",Texture.class)));
//style.checkboxOff = new TextureRegionDrawable(new TextureRegion(assMan.get("unchecked.png",Texture.class)));
//style.checkboxOnDisabled = new TextureRegionDrawable(new TextureRegion(assMan.get("unchecked_disabled.png",Texture.class)));
