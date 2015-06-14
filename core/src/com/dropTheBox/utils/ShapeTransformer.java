package com.dropTheBox.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Pools;

public class ShapeTransformer {
	
	public static void resize(Shape shape, float... length){
		if(shape instanceof CircleShape)
			shape.setRadius(length[0]  / 2f);
		else if(shape instanceof PolygonShape)
			resizePolygon((PolygonShape) shape, length[0], length[1]);
	}
	
	public static void scale(Shape shape, float... s){
		if(shape instanceof CircleShape)
			shape.setRadius(shape.getRadius() / s[0]);
		else if(shape instanceof PolygonShape)
			scalePolygon((PolygonShape) shape, s[0], s[1]);
	}
	
	public static void translate(Shape shape, float xPos, float yPos){
		if(shape instanceof CircleShape)
			((CircleShape) shape).setPosition(new Vector2(xPos , yPos ));
		else if(shape instanceof PolygonShape)
			translatePolygon((PolygonShape) shape, xPos, yPos);
	}

	public static void resizePolygon(PolygonShape shape, float w, float h){
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

	public static void scalePolygon(PolygonShape shape, float scaleX , float scaleY){
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
	
	public static void translatePolygon(PolygonShape shape, float xPos, float yPos){
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
