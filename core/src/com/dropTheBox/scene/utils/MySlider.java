package com.dropTheBox.scene.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class MySlider extends Slider{
	private boolean vertical;
	
	public MySlider(float min, float max, float stepSize, boolean vertical, SliderStyle style) {
		super(min, max, stepSize, vertical, style);
		this.vertical = vertical;
	}

	public MySlider(float min, float max, float stepSize, boolean vertical, Skin skin, String styleName) {
		super(min, max, stepSize, vertical, skin, styleName);
		this.vertical = vertical;
	}

	public MySlider(float min, float max, float stepSize, boolean vertical, Skin skin) {
		super(min, max, stepSize, vertical, skin);
		this.vertical = vertical;
	}
	
	@Override
	public float getPrefHeight(){
		Slider.SliderStyle style = getStyle();
		boolean disabled = isDisabled();
		
		if(this.vertical){
			final Drawable knob = (disabled && style.disabledKnob != null) ? style.disabledKnob : style.knob;
			final Drawable bg = (disabled && style.disabledBackground != null) ? style.disabledBackground : style.background;
			return Math.max(knob == null ? 0 : knob.getMinHeight(), bg == null ? 0 : bg.getMinHeight());
		} else
			return super.getPrefHeight();
	}
	
	@Override
	public float getPrefWidth(){
		Slider.SliderStyle style = getStyle();
		boolean disabled = isDisabled();
		
		if(!this.vertical){
			final Drawable knob = (disabled && style.disabledKnob != null) ? style.disabledKnob : style.knob;
			final Drawable bg = (disabled && style.disabledBackground != null) ? style.disabledBackground : style.background;
			return Math.max(knob == null ? 0 : knob.getMinWidth(), bg == null ? 0 : bg.getMinWidth());
		} else
			return super.getPrefWidth();
	}
	
}
