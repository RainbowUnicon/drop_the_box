package com.dropTheBox.game.actor.platform;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;


public class PlatformGenerator {

	private final GameScene game;
	private final World world;
	private final int worldHeight, worldWidth;
	
	//constant 
	private final float platformGap; 
	private final float widthBuffer;
	
	private final Sprite sprite;
	
	public PlatformGenerator(GameScene _game, FloorDef fd){
		game = _game;
		world = game.getWorld();
		worldHeight = GameScene.WORLDHEIGHT;
		worldWidth = GameScene.WORLDWIDTH;
		
		platformGap =   fd.getPlatformGap();
		widthBuffer =   fd.getWidthBuffer();
		
		sprite = new Sprite(game.getAssetManager().get("platform.png",Texture.class));
	}
	
	//create platforms before the game starts, so the player doesn't start from nothing
	
	
	
	
	//this method will create new platform with holes
	//platform with 2 holes happens in 15%
	public void createPlatforms(Floor floor){
		float yPos = floor.getY() - platformGap;
		int num = MathUtils.random(15);
		if(num == 0) //create floor with two hole
		{ 
			//create two hole position 
			int firstHolePos = MathUtils.random(0,((int) (worldWidth - platformGap)));
			int secondHolePos = MathUtils.random(0,((int) (worldWidth - platformGap * 3)));
			
			//If the first hole position is lower than the the second hole position, add double gap to make sure there is space between two holes
			if(secondHolePos > firstHolePos){
				secondHolePos = (int) (secondHolePos + platformGap * 2);
			}
			else{ //if The first hole position is higher than the second hole position, swap the first and second hole position
				int temp =firstHolePos;
				firstHolePos =secondHolePos;
				secondHolePos = temp;
				//after switch the coordinate of two holes, make sure the center platform to have at least platfromGap width
				if(secondHolePos - firstHolePos < platformGap *2){
					secondHolePos = (int) (firstHolePos + platformGap *2);
				}
			}
			
			int[] holeLoc = {firstHolePos, secondHolePos};
			floor.addPlatform(new Platform(world, sprite,  -widthBuffer, yPos, widthBuffer +  firstHolePos, 5,floor));
			floor.addPlatform(new Platform(world, sprite,  firstHolePos + platformGap, yPos, secondHolePos - firstHolePos -platformGap, 5,floor));
			floor.addPlatform(new Platform(world, sprite,  secondHolePos + platformGap, yPos,  worldWidth + widthBuffer - (secondHolePos + platformGap), 5, floor));
			
		}
		else //create floor with one hole
		{
			int holePos = MathUtils.random(0,((int) (worldWidth - platformGap))); //decide the position of the hole
			floor.addPlatform(new Platform(world, sprite,  -widthBuffer, yPos, widthBuffer +  holePos, 5,floor));
			floor.addPlatform(new Platform(world, sprite,  holePos + platformGap , yPos, worldWidth + widthBuffer - (holePos + platformGap), 5,floor));
		}
	}
	
	//destroy the floor when its top left corner reaches the destroy Buffer + worldHeight
}


