package com.dropTheBox.scene;

import com.dropTheBox.DropTheBox;

public class StoreScene extends StagedScene {
	private final DropTheBox dtb;
	
	public StoreScene(DropTheBox _dtb){
		dtb = _dtb;
		dtb.hashCode(); //TODO remove this line
	}
}
