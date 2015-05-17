package com.dropTheBox.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * DO NOT INITIALIZE THIS 
 * @author younglee
 *
 * This class will be used to create componenets for stages
 * The basic L&F is uiskin.json
 */
public class CompFactory {
	private static Skin skin;
	private CompFactory(){}
	
	
	static{
		// initialize default skin object that will be used for creating components 
		skin = new Skin(Gdx.files.internal("fonts/uiskin.json"));
	}
	
	/**
	 * Create a text button with the given word and listener
	 * @param text word that the button should have
	 * @param listener InputListener object for this button 
	 * @return button TextButton object with given text
	 */
	public static TextButton createTextButton(String text, InputListener listener){
		 TextButton button = new TextButton(text, skin, "default");
	     button.addListener(listener);
	     return button;
	}
	
	/**
	 * Create an image button with the image
	 * @param imageUp path to the image for normal state
	 * @param imageDown path to image when cursor is clicking the button
	 * @param imageChecked path to image when cursor is hovering the button
	 * @param listener InputListener object for this button
	 * @return button ImageButton with given image
	 */
	public static ImageButton createImageButton(String imageUp, String imageDown, String imageChecked, InputListener listener){
		ImageButton button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(imageUp))),new TextureRegionDrawable(new TextureRegion(new Texture(imageDown))),new TextureRegionDrawable(new TextureRegion(new Texture(imageChecked))));
		button.addListener(listener);
	    return button;
	}
	
	/**
	 * Create an image button with the image
	 * @param imageUp path to the image for normal state
	 * @param imageDown path to image when cursor is clicking the button
	 * @param listener InputListener object for this button
	 * @return button ImageButton with given image
	 */
	public static ImageButton createImageButton(String imageUp, String imageDown, InputListener listener){
		ImageButton button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(imageUp))),new TextureRegionDrawable(new TextureRegion(new Texture(imageDown))));
		button.addListener(listener);
	    return button;
	}
	
	/**
	 * Create an image button with the image
	 * @param imageUp path to the image for normal state
	 * @param listener InputListener object for this button
	 * @return button ImageButton with given image
	 */
	public static ImageButton createImageButton(String imageUp, InputListener listener){
		ImageButton button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(imageUp))));
		button.addListener(listener);
	    return button;
	}
	
	/**
	 * create a label with a given text
	 * @param text text to show in the label
	 * @return label that user wants
	 */
	public static Label createLabel(String text){
		return new Label(text, skin);
	}
	
	public static Image createImage(String path){
		return new Image(new TextureRegionDrawable(new TextureRegion(new Texture(path))));
	}
}
