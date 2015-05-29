package com.dropTheBox.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.dropTheBox.DropTheBox;
import com.dropTheBox.utils.LoadAssets;

public class LogoScene extends StagedScene {
	private final DropTheBox dtb;
	
	public LogoScene(DropTheBox _dtb){
		dtb = _dtb;
		getTable().add(new Image(new Texture(Gdx.files.internal("name.png"))));	
		
		LoadAssets.loadAsset(dtb.getAssMan());
	}
	
	float waitFor = .5f;
	
	@Override
	public void update(){
		waitFor -= Gdx.graphics.getDeltaTime();
		super.update();
		if(dtb.getAssMan().update() && waitFor < 0)
			dtb.setScene(new GameScene(dtb,null));//dtb.setScene(new StartScene(dtb));
	}
}

//FileHandleResolver resolver = new InternalFileHandleResolver(); 
//assMan.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
//assMan.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
//
//FreeTypeFontLoaderParameter param32 = new FreeTypeFontLoaderParameter();
//param32.fontFileName = "arimo/Arimo-Regular.ttf";
//param32.fontParameters.size = 32;
//param32.fontParameters.color = Color.LIGHT_GRAY;
//assMan.load("size32.ttf", BitmapFont.class, param32);
//
//FreeTypeFontLoaderParameter param321 = new FreeTypeFontLoaderParameter();
//param321.fontFileName = "arimo/Arimo-Bold.ttf";
//param321.fontParameters.size = 35;
//assMan.load("size321.ttf", BitmapFont.class, param321);
//
//FreeTypeFontLoaderParameter param24 = new FreeTypeFontLoaderParameter();
//param24.fontFileName = "arimo/Arimo-Regular.ttf";
//param24.fontParameters.size = 24;
//assMan.load("size24.ttf", BitmapFont.class, param24);
