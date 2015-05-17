package com.dropTheBox.utils;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Every class that is related to both reading and writing files should extends this class
 * This class provides basic sets of methods that data class should contain 
 * @author younglee
 *
 */
public abstract class DataManager {
	protected final Preferences prefs;
	
	public DataManager(String dataName){
		prefs = Gdx.app.getPreferences(dataName);
	}
	
	/**
	 * Flush the written data.
	 * In other words, write the actual file
	 * Always call this method after change value to save the change
	 */
	public final void flush(){
		prefs.flush();
	}
	
	/**
	 * return keys and data stored in the object
	 * @return Map<String, ?> return map that contains key and data 
	 * @see Preferences#get()
	 */
	public Map<String, ?> get(){
		return prefs.get();
	}
	
	/**
	 * Rewrite the data into default setting.
	 */
	public abstract void setDefault();
}
