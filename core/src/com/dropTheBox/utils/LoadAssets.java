package com.dropTheBox.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;

public final class LoadAssets {
	
	public static void loadAsset(AssetManager assMan){
		assMan.load("logo.png", Texture.class);
		assMan.load("common/playButton.png",Texture.class);
		assMan.load("common/playButton_down.png",Texture.class);
		assMan.load("common/levelButton.png",Texture.class);
		assMan.load("common/levelButton_down.png",Texture.class);
		assMan.load("common/settingButton.png",Texture.class);
		assMan.load("common/settingButton_down.png",Texture.class);
		
		assMan.load("common/checked.png",Texture.class);
		assMan.load("common/checked_disabled.png",Texture.class);
		assMan.load("common/unchecked.png",Texture.class);
		assMan.load("common/unchecked_disabled.png",Texture.class);
		
		assMan.load("common/knob.png",Texture.class);
		assMan.load("common/slider.png",Texture.class);
		
		assMan.load("common/button.png",Texture.class);
		assMan.load("common/button_down.png",Texture.class);
		
		FileHandleResolver resolver = new InternalFileHandleResolver();
		assMan.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assMan.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
		
		assMan.load("Rlg24.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Regular.ttf", 24, Color.LIGHT_GRAY));
		assMan.load("Blg24.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Bold.ttf", 24, Color.LIGHT_GRAY));
		assMan.load("Ilg24.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Italic.ttf", 24, Color.LIGHT_GRAY));
		assMan.load("Rg24.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Regular.ttf", 24, Color.GRAY));
		assMan.load("Bg24.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Bold.ttf", 24, Color.GRAY));
		assMan.load("Ig24.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Italic.ttf", 24, Color.GRAY));
		
		assMan.load("Rlg32.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Regular.ttf", 32, Color.LIGHT_GRAY));
		assMan.load("Blg32.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Bold.ttf", 32, Color.LIGHT_GRAY));
		assMan.load("Ilg32.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Italic.ttf", 32, Color.LIGHT_GRAY));
		assMan.load("Rg32.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Regular.ttf", 32, Color.GRAY));
		assMan.load("Bg32.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Bold.ttf", 32, Color.GRAY));
		assMan.load("Ig32.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Italic.ttf", 32, Color.GRAY));
		
		assMan.load("Rlg40.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Regular.ttf", 40, Color.LIGHT_GRAY));
		assMan.load("Blg40.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Bold.ttf", 40, Color.LIGHT_GRAY));
		assMan.load("Ilg40.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Italic.ttf", 40, Color.LIGHT_GRAY));
		assMan.load("Rg40.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Regular.ttf", 40, Color.GRAY));
		assMan.load("Bg40.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Bold.ttf", 40, Color.GRAY));
		assMan.load("Ig40.ttf", BitmapFont.class, createFreeTypeFontParameter("arimo/Arimo-Italic.ttf", 40, Color.GRAY));
	}

	private static FreeTypeFontLoaderParameter createFreeTypeFontParameter(String fileName, int size, Color color){
		FreeTypeFontLoaderParameter param = new FreeTypeFontLoaderParameter();
		param.fontFileName = fileName;
		param.fontParameters.size = size;
		param.fontParameters.color = color;
		return param;
	}
}
