package com.dropTheBox.utils;


/**
 * This class contains general setting for this app.
 * Clients can read and write data by using getters & setters interface
 * @author younglee
 *
 */
public class GenSettings extends DataManager {
	private final boolean DEFAULT_USEACCEL = false;
	private final boolean DEFAULT_USEMUSIC = false;
	private final boolean DEFAULT_USESOUND = false;
	private final int DEFAULT_ACCELSEN = 50;
	private final int DEFAULT_MUSICVOL = 100;
	private final int DEFAULT_SOUNDVOL = 100;
	
	public GenSettings(){
		super("dtb_general_setting");
	}
	
	public boolean getUseAccel(){
		return prefs.getBoolean("useAccel", DEFAULT_USEACCEL);
	}
	
	public void setUseAccel(boolean use){
		prefs.putBoolean("useAccel", use);
		prefs.flush();
	}

	public int getAccelSen(){
		return prefs.getInteger("accelSen", DEFAULT_ACCELSEN);
	}
	
	public void setAccelSen(int sen){
		prefs.putInteger("accelSen", sen);
		prefs.flush();
	}
	
	public boolean getUseMusic(){
		return prefs.getBoolean("useMusic", DEFAULT_USEMUSIC);
	}
	
	public void setUseMusic(boolean use){
		prefs.putBoolean("useMusic", use);
		prefs.flush();
	}
	
	public int getMusicVol(){
		return prefs.getInteger("musicVol", DEFAULT_MUSICVOL);
	}
	
	public void setMusicVol(int vol){
		prefs.putInteger("musicVol", vol);
		prefs.flush();
	}
	
	public boolean getUseSound(){
		return prefs.getBoolean("useSound", DEFAULT_USESOUND);
	}
	
	public void setUseSound(boolean use){
		prefs.putBoolean("useSound", use);
		prefs.flush();
	}
	
	public int getSoundVol(){
		return prefs.getInteger("soundVol", DEFAULT_SOUNDVOL);
	}
	
	public void setSoundVol(int vol){
		prefs.putInteger("soundVol", vol);
		prefs.flush();
	}
	
	@Override
	public void setDefault() {
		prefs.putBoolean("useAccel", DEFAULT_USEACCEL);
		prefs.putBoolean("useMusic", DEFAULT_USEMUSIC);
		prefs.putBoolean("useSound", DEFAULT_USESOUND);
		prefs.putInteger("accelSen", DEFAULT_ACCELSEN);
		prefs.putInteger("musicVol", DEFAULT_MUSICVOL);
		prefs.putInteger("soundVol", DEFAULT_SOUNDVOL);
		
	}
}
