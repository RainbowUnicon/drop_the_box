package com.dropTheBox.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * This class will be used for base class that uses stage object in libgdx library.
 * This class is mainly used to simplify the codes.
 * Don't use this class for a scene that require heavy use of entities that doesn't extends Actor class in libgdx library (such as Game scene) 
 * @author younglee
 *
 */
public abstract class StagedScene implements Scene{
	public final int WORLDWIDTH  = 360;
	public final int WORLDHEIGHT = 640;
	
	private final Stage stage;
	private final Table table;
	
	public StagedScene(){
		//create a stage with stretch viewport
		stage = new Stage(new StretchViewport(WORLDWIDTH,WORLDHEIGHT));
		Gdx.input.setInputProcessor(stage);
		
		//create table to locate the buttons
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
	}
	
	/**
	 * Update the stage
	 */
	@Override
	public void update() {
	    stage.act(Gdx.graphics.getDeltaTime());
	}
	
	/**
	 * Draw the stage
	 */
	@Override
	public void draw() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}
	
	/**
	 * dispose the stage
	 */
	@Override
	public void dispose() {
		stage.dispose();
	}
	
	/**
	 * resize the stage 
	 * @param width changed screen width;
	 * @param height changed screen height;
	 */
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
	}
	
	@Override
	public void resume() {}
	
	@Override
	public void pause() {}
	
	/**
	 * @return stage return the stage 
	 */
	public Stage getStage(){
		return stage;
	}
	
	/**
	 * @return table return table that is current used for the stage
	 * 
	 */
	public Table getTable(){
		return table;
	}
	
	
}
