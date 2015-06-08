package com.dropTheBox.game;

import com.efo.game.actor.Player;
import com.efo.game.actor.Platform;




public class FloorDef {
	//The distance of gap between platforms is diagonal distance of player's shape plus the height of Platform
	public final float DEFAULT_PLATFORM_GAP   = (float) Math.sqrt(Player.WIDTH * Player.WIDTH + Player.HEIGHT * Player.HEIGHT) + Platform.HEIGHT; 
	public final float DEFAULT_CREATE_BUFFER  = 45f; //The buffer distance, platforms will be created at worldHeight + this value
	public final float DEFAULT_DESTROY_BUFFER = 45f; //Platforms will be destroyed at negative of this value
	public final float DEFAULT_WIDTH_BUFFER   = 20f; //the size of invisible area of buffer; left and right
	
	private float platformGap =   DEFAULT_PLATFORM_GAP; 
	private float createBuffer =  DEFAULT_CREATE_BUFFER;
	private float destroyBuffer = DEFAULT_DESTROY_BUFFER;
	private float widthBuffer =   DEFAULT_WIDTH_BUFFER;
	
	public FloorDef(){}
	public FloorDef(float rs){
	}
	
	
	public float getPlatformGap() {
		return platformGap;
	}
	public void setPlatformGap(float platformGap) {
		this.platformGap = platformGap;
	}
	
	public float getDestroyBuffer() {
		return destroyBuffer;
	}
	public void setDestroyBuffer(float destroyBuffer) {
		this.destroyBuffer = destroyBuffer;
	}
	
	public float getCreateBuffer() {
		return createBuffer;
	}
	public void setCreateBuffer(float createBuffer) {
		this.createBuffer = createBuffer;
	}
	
	public float getWidthBuffer() {
		return widthBuffer;
	}
	public void setWidthBuffer(float widthBuffer) {
		this.widthBuffer = widthBuffer;
	}
	

	
	
	
}
