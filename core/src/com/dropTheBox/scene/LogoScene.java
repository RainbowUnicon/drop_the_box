package com.dropTheBox.scene;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.dropTheBox.DropTheBox;
import com.dropTheBox.utils.CompFactory;

public class LogoScene extends StagedScene {
	private final DropTheBox dtb;
	
	public LogoScene(DropTheBox _dtb){
		dtb = _dtb;
		Table table = getTable();
		
		
		table.add(CompFactory.createImage("logo.png"));	
	}

}
