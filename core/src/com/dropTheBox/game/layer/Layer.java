package com.dropTheBox.game.layer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract class Layer implements Disposable{

	public abstract void update();
	
	public abstract void draw(SpriteBatch batch);
	
	public abstract void resume();
	
	public abstract void pause();
	
	@Override
	public void dispose() {}

}
