package com.dropTheBox.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.dropTheBox.game.actor.entity.EntityManager;
import com.dropTheBox.game.actor.item.ItemManager;
import com.dropTheBox.game.actor.platform.Platform;
import com.dropTheBox.game.actor.platform.PlatformManager;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.scene.GameScene;

public class FloorManager implements Disposable{
	public static final float PLATFORMGAP = 85; 
	public static final float WIDTHBUFFER = 60;
	public static final float DESTRUCTIONBUFFER = 100;
	public static final float CREATIONBUFFER = 100;
	
	private final ActorLayer layer;
	private final Camera camera;
	
	private final EntityManager entityManager;
	private final PlatformManager platformManager;
	private final ItemManager itemManager;
	

	boolean goNext = false;
	int yPos = 300;
	
	
	public FloorManager(ActorLayer _layer){
		layer = _layer;
		camera = layer.getCamera();
		
		layer.getCamera().setYVelocity(-50); //TODO remove magic number
		
		entityManager = new EntityManager(layer);
		platformManager = new PlatformManager(layer);
		itemManager = new ItemManager(layer);
		
		
		
		pData = new Array<Float[]>(); //TODO remove thiss
		
		next();
		this.update(0);
	}
	
//	public void init(){
//		for(int numOfFloor = 7; numOfFloor > 0; numOfFloor-- ){
//			createFloor( -creationBuffer + numOfFloor * (platformGap));
//		}
//	}
	
	public void update(float dt){

		if(getStartPoint() > getCreationPoint()){
			while(pData.size >0){
				Float[] w = pData.removeIndex(0);
				platformManager.generate("normal", w[0], w[1], w[2]);
			}
			next();
		}
		
		
		float desPoint = this.getDestructionPoint();
		entityManager.destroyAbove(desPoint);
		platformManager.destroyAbove(desPoint);
		itemManager.destroyAbove(desPoint);
	}
	
	
	
	
	private float getDestructionPoint(){
		return camera.getY() + layer.getHeight() + DESTRUCTIONBUFFER;
	}


	private float getCreationPoint(){
		return camera.getY() - CREATIONBUFFER;
	}

	@Override
	public void dispose() {
		entityManager.dispose();
		platformManager.dispose();
		itemManager.dispose();
	}
	
	

	
	public void next(){
		yPos -= PLATFORMGAP;
		createPlatforms();
	}
	
	public float getStartPoint(){
		return yPos;
	}
	
	Array<Float[]> pData;
	
	public void createPlatforms(){
		float yPos = this.yPos;
		int num = MathUtils.random(15);
		if(num == 0) //create floor with two hole
		{ 
			//create two hole position 
			int firstHolePos = MathUtils.random(0,((int) (layer.getWidth() - 60)));
			int secondHolePos = MathUtils.random(0,((int) (layer.getWidth() - 60 * 3)));

			//If the first hole position is lower than the the second hole position, add double gap to make sure there is space between two holes
			if(secondHolePos > firstHolePos){
				secondHolePos = (int) (secondHolePos + 60 * 2);
			}
			else{ //if The first hole position is higher than the second hole position, swap the first and second hole position
				int temp =firstHolePos;
				firstHolePos =secondHolePos;
				secondHolePos = temp;
				//after switch the coordinate of two holes, make sure the center platform to have at least platfromGap width
				if(secondHolePos - firstHolePos <60 *2){
					secondHolePos = (int) (firstHolePos + 60 *2);
				}
			}

			int[] holeLoc = {firstHolePos, secondHolePos};
			pData.add(new Float[]{-WIDTHBUFFER, (float) yPos, WIDTHBUFFER +  firstHolePos});
			pData.add(new Float[]{ firstHolePos + PLATFORMGAP, (float)yPos, secondHolePos - firstHolePos -PLATFORMGAP});
			pData.add(new Float[]{secondHolePos + PLATFORMGAP, (float) yPos,  layer.getWidth() + WIDTHBUFFER - (secondHolePos + PLATFORMGAP)});

		}
		else //create floor with one hole
		{
			int holePos = MathUtils.random(0,((int) (layer.getWidth() - PLATFORMGAP))); //decide the position of the hole
			pData.add(new Float[]{ -WIDTHBUFFER, (float) yPos, WIDTHBUFFER +  holePos});
			pData.add(new Float[]{holePos + PLATFORMGAP , (float)yPos, layer.getWidth() + WIDTHBUFFER - (holePos + PLATFORMGAP)});
		}
	}
}
