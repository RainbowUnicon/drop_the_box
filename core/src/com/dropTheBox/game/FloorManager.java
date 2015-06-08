package com.dropTheBox.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.dropTheBox.game.actor.item.ItemGenerator;
import com.dropTheBox.game.actor.platform.PlatformGenerator;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.scene.GameScene;

public class FloorManager{
	private final ActorLayer layer;
	
	private final PlatformGenerator platformGenerator;
	private final ItemGenerator itemGenerator;
	
	private final Array<Floor> floorList;
	private final Pool<Floor> floorPool;
	private int currentFloorNumber = 0;
	
	private final float platformGap; 
	private final float createBuffer;
	private final float destroyBuffer;
	private final float widthBuffer;
	
	public FloorManager(ActorLayer _layer, FloorDef fd){
		layer = _layer;
		
		platformGenerator = new PlatformGenerator(game,fd);
		itemGenerator = new ItemGenerator(game);
		floorList = new Array<Floor>(15);
		
		platformGap = fd.getPlatformGap();
		createBuffer = fd.getCreateBuffer();
		destroyBuffer = fd.getDestroyBuffer();
		widthBuffer = fd.getWidthBuffer();
		
		init();
	}
	
	public void init(){
		for(int numOfFloor = 7; numOfFloor > 0; numOfFloor-- ){
			createFloor( -createBuffer + numOfFloor * (platformGap));
		}
	}
	
	public void act(float dt){
		if(floorList.peek().getY() > worldYPos  -createBuffer + platformGap){
			createFloor(worldYPos -createBuffer);
		}
		if(floorList.first().getY() > worldHeight + worldYPos + destroyBuffer){
			destroyFloor();
		}
		
	}
	
	public void createFloor(float yPos){
		Floor floor = new Floor(++currentFloorNumber,yPos); 
		platformGenerator.createPlatforms(floor);
		itemGenerator.createItems(floor);
		floorList.add(floor);
	}
	
	//destroy the the highest platform, the first in floorlist
	public void destroyFloor(){
		floorList.first().destroy(world);
		floorList.removeIndex(0);
	}
	
	
	//GETTERs
	public Array<Floor> getFloorList(){
		return floorList;
	}
	public float getPlatformGap() {
		return platformGap;
	}

	public float getCreateBuffer() {
		return createBuffer;
	}

	public float getDestroyBuffer() {
		return destroyBuffer;
	}

	public float getWidthBuffer() {
		return widthBuffer;
	}
	
	public int getCurrentFloorNumber(){
		return currentFloorNumber;
	}
	
	public FloorDef getFloorDef(){
		FloorDef fd = new FloorDef();
		fd.setCreateBuffer(createBuffer);
		fd.setDestroyBuffer(destroyBuffer);
		fd.setPlatformGap(platformGap);
		fd.setWidthBuffer(widthBuffer);
		return fd;
	}
}
