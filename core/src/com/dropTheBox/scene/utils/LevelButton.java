package com.dropTheBox.scene.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LevelButton extends ImageButton{
	private final static float WIDTH = 320f, HEIGHT = 70f;
	private final static float UP_BUFFER = 6f, DOWN_BUFFER = 10f;
	private final static float RIGHT_BUFFER = 10f, LEFT_BUFFER = 7f;
	
	
	
	private final GlyphLayout level, starFrame, levelName, bestScore, star;
	private final BitmapFont fontRg40, fonrRlg24, fontRg32, fontIg24, fontRg24;
	
	public LevelButton(int _level, int _nStar, String _levelName, int _bestScore, AssetManager assMan) {
		super(new TextureRegionDrawable(new TextureRegion(assMan.get("levelButton.png", Texture.class))), 
			  new TextureRegionDrawable(new TextureRegion(assMan.get("levelButton_down.png", Texture.class))));
		
		if(_level < 0)
			throw new IllegalArgumentException("level has to be more than zero");

		if(_bestScore < 0)
			throw new IllegalArgumentException("best score has to be more than zero");
		if(_nStar < 0 || _nStar > 5)
			throw new IllegalArgumentException("star has to be between 0 to 5");
		
		
		
		fontRg40 = assMan.get("Rg40.ttf", BitmapFont.class);
		fonrRlg24 = assMan.get("Rlg24.ttf", BitmapFont.class);
		fontRg32 = assMan.get("Rg32.ttf", BitmapFont.class);
		fontIg24 = assMan.get("Ig24.ttf", BitmapFont.class);
		fontRg24 = assMan.get("Rg24.ttf", BitmapFont.class);
		
		level     = new GlyphLayout(fontRg32, "Level " + _level + "");
		starFrame = new GlyphLayout(fonrRlg24, createStar(5 - _nStar) );
		star      = new GlyphLayout(fontRg40, createStar(_nStar));
		levelName = new GlyphLayout(fontIg24, _levelName);
		bestScore = new GlyphLayout(fontRg24, _bestScore + "");

	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		super.draw(batch,parentAlpha);
		float x = getX();
		float y = getY();
		
		fontRg32.draw(batch, level, x + LEFT_BUFFER, y + HEIGHT - UP_BUFFER );
		fonrRlg24.draw(batch, starFrame, x + WIDTH - RIGHT_BUFFER - star.width - starFrame.width, y + HEIGHT - UP_BUFFER - (star.height - starFrame.height)/2);
		fontRg40.draw(batch, star, x + WIDTH - RIGHT_BUFFER - star.width, y + HEIGHT - UP_BUFFER);
		fontIg24.draw(batch, levelName, x + LEFT_BUFFER + 5, y + DOWN_BUFFER + levelName.height);
		fontRg24.draw(batch, bestScore, x + WIDTH - RIGHT_BUFFER - bestScore.width, y + DOWN_BUFFER + levelName.height);
	}
	
	private String createStar(int nStar){
		String s = "";
		for(int i = 0; i < nStar; i++)
			s +="*";
		return s;
	}

}
