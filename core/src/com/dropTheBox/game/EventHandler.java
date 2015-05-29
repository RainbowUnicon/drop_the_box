package com.dropTheBox.game;

import com.badlogic.gdx.utils.Array;
import com.dropTheBox.game.event.GameEvent;

public class EventHandler {
	private Array<GameEvent> queue;
	
	public void add(GameEvent event){
		if(event == null)
			throw new NullPointerException("Event Handler can't accept null");
		queue.add(event);
	}
	
	public void handle(){
		while(isEmpty())
			queue.removeIndex(0).resolve();
	}
	
	public int size(){
		return queue.size;
	}
	
	public boolean isEmpty(){
		return (queue.size <= 0);
	}
	
	public void clear(){
		queue.clear();
	}
}
