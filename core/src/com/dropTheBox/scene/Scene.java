package com.dropTheBox.scene;

import com.badlogic.gdx.utils.Disposable;

/**
 * This interface is used to stimulate each scene of the game.
 * For instance, main menu, game. setting menu are the examples of the scene.
 * @author younglee
 */

public interface Scene extends Disposable{
	public int WIDTH = 360;
	public int HEIGHT = 660;
	
	/**
	 * Update the status of the scene.
	 * This method will called constantly as long as this scene is placed on Escape_From_Office object
	 */
	void update();
	
	/**
	 * Draw objects on the screen
	 * This method will after update method.
	 * In other words, this method will be called constantly.
	 */
	void draw();
	
	/**
	 * This method will be called when the size of screen is changed.
	 * @param width change screen width
	 * @param height change height width
	 */
	void resize(int width, int height);
	
	/**
	 * This method will be called when the scene resumes
	 */
	void resume();
	
	/**
	 * This method will be called when the scene pauses
	 */
	void pause();
	
	/**
	 * This method will be called when the scene is disposed
	 */
	@Override
	void dispose();
}
