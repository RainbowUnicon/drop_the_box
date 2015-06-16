package com.dropTheBox.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Pools;

public class ShapeTransformer {
	
	private ShapeTransformer(){}
	
	public static void setSize(Shape shape, float... length){
		if(shape instanceof CircleShape)
			shape.setRadius(length[0]  / 2f);
		else if(shape instanceof PolygonShape)
			resizePolygon((PolygonShape) shape, length[0], length[1]);
	}
	
	public static void setScale(Shape shape, float... scale){
		if(shape instanceof CircleShape)
			shape.setRadius(shape.getRadius() / scale[0]);
		else if(shape instanceof PolygonShape)
			scalePolygon((PolygonShape) shape, scale[0], scale[1]);
	}
	
	public static void setPosition(Shape shape, float xPos, float yPos){
		if(shape instanceof CircleShape)
			((CircleShape) shape).setPosition(new Vector2(xPos , yPos ));
		else if(shape instanceof PolygonShape)
			translatePolygon((PolygonShape) shape, xPos, yPos);
	}

	private static void resizePolygon(PolygonShape shape, float w, float h){
		float minX = Float.MAX_VALUE;
		float minY = Float.MAX_VALUE;
		float maxX = 0;
		float maxY = 0;
		int numV = shape.getVertexCount();
		Vector2 vertex = Pools.obtain(Vector2.class);
		for(int i=0; i < numV; i++){			
			shape.getVertex(i, vertex);
			minX = (vertex.x < minX) ? vertex.x : minX;
			minY = (vertex.y < minY) ? vertex.y : minY;
			maxX = (vertex.x > maxX) ? vertex.x : maxX;
			maxY = (vertex.y > maxY) ? vertex.y : maxY;
		}
		scalePolygon(shape,w / (maxX - minX) , h / (maxY - minY));
	}

	private static void scalePolygon(PolygonShape shape, float scaleX , float scaleY){
		int numV = shape.getVertexCount();
		float[] vertices = new float[numV * 2];
		Vector2 vertex = Pools.obtain(Vector2.class);
		for(int i=0; i < numV; i++){
			shape.getVertex(i, vertex);
			vertices[i * 2] = vertex.x * scaleX;
			vertices[i * 2 + 1] = vertex.y * scaleY;
		}
		shape.set(vertices);
	}
	
	private static void translatePolygon(PolygonShape shape, float xPos, float yPos){
		int numV = shape.getVertexCount();
		float[] vertices = new float[numV *2];
		Vector2 vertex = Pools.obtain(Vector2.class);
		for(int i=0; i < numV; i++){
			shape.getVertex(i, vertex);
			vertices[i * 2] = vertex.x + xPos;
			vertices[i * 2 + 1] = vertex.y + yPos;
		}
		shape.set(vertices);
	}
	

}
