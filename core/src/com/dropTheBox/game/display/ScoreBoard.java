package com.dropTheBox.game.display;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.dropTheBox.game.layer.DisplayLayer;

public class ScoreBoard extends Label{
	private final DisplayLayer layer;
	private int currentScore;
	
	public ScoreBoard(DisplayLayer layer) {
		super("", createStyle(layer.getAssets()));
		this.layer = layer;
		layer.stage.addActor(this);
		setScore(0);
	}
	
	public void setScore(int score){
		currentScore = score;
		this.setText(currentScore + "");
		this.validate();
		this.setPosition(layer.getWidth() - this.getPrefWidth() - 10, layer.getHeight() - this.getPrefHeight());
	}
	
	public void addScore(int score){
		setScore(currentScore + score);
	}
	
	private static final LabelStyle createStyle(AssetManager man){
		LabelStyle style = new LabelStyle();
		style.font = man.get("Rlg32.ttf", BitmapFont.class);
		return style;
	}


}
