package com.dropTheBox.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.dropTheBox.game.actor.Base;

public class ShapeTransformer {
	
	public static void resize(Shape shape, float w, float h){
		if(shape instanceof CircleShape){
			resizeCircle((CircleShape)shape, w / Base.WORLDSCALE, w / Base.WORLDSCALE);
		}
		else if(shape instanceof PolygonShape){
			resizePolygon((PolygonShape) shape, w / Base.WORLDSCALE, h / Base.WORLDSCALE);
		}
	}
	
	public static void resizeCircle(CircleShape shape, float r, float r1){
		shape.setRadius(r);
	}
	
	public static void resizePolygon(PolygonShape shape, float w, float h){
		scalePolygon(shape,w/ calcPolygonWidth(shape) ,h/ calcPolygonHeight(shape));
	}
	
	private static float calcPolygonWidth(PolygonShape shape){
		float min = Float.MAX_VALUE;
		float max = 0;
		int numV = shape.getVertexCount();
		for(int i=0; i < numV; i++){
			Vector2 vertex = new Vector2();
			shape.getVertex(i, vertex);
			if(vertex.x < min) min = vertex.x;
			if(vertex.x > max) max = vertex.x;
		}
		return max - min;
	}
	private static float calcPolygonHeight(PolygonShape shape){
		float min = Float.MAX_VALUE;
		float max = 0;
		int numV = shape.getVertexCount();
		for(int i=0; i < numV; i++){
			Vector2 vertex = new Vector2();
			shape.getVertex(i, vertex);
			if(vertex.y < min) min = vertex.y;
			if(vertex.y > max) max = vertex.y;
		}
		return max - min;
	}
	
	public static void scale(Shape shape, float scaleX, float  scaleY){
		if(shape instanceof CircleShape){
			scaleCircle((CircleShape)shape, scaleX, scaleY);
		}
		else if(shape instanceof PolygonShape){
			scalePolygon((PolygonShape) shape, scaleX, scaleY);
		}
	}
	
	
	public static void scaleCircle(CircleShape shape, float scaleX, float scaleY){
		shape.setRadius(shape.getRadius() / scaleX);
	}
	
	public static void scalePolygon(PolygonShape shape, float scaleX , float scaleY){
		int numV = shape.getVertexCount();
		float[] vertices = new float[numV *2];
		for(int i=0; i < numV; i++){
			Vector2 vertex = new Vector2();
			shape.getVertex(i, vertex);
			vertices[i * 2] = vertex.x * scaleX;
			vertices[i * 2 + 1] = vertex.y * scaleY;
		}
		shape.set(vertices);
	}
	
	public static void translate(Shape shape, float xPos, float yPos){
		if(shape instanceof CircleShape){
			translateCircle((CircleShape)shape, xPos, yPos);
		}
		else if(shape instanceof PolygonShape){
			translatePolygon((PolygonShape) shape, xPos, yPos);
		}
	}
	
	public static void translatePolygon(PolygonShape shape, float xPos, float yPos){
		int numV = shape.getVertexCount();
		float[] vertices = new float[numV *2];
		for(int i=0; i < numV; i++){
			Vector2 vertex = new Vector2();
			shape.getVertex(i, vertex);
			vertices[i * 2] = vertex.x + xPos / Base.WORLDSCALE;
			vertices[i * 2 + 1] = vertex.y + yPos / Base.WORLDSCALE;
		}
		shape.set(vertices);
	}
	
	public static void translateCircle(CircleShape shape, float xPos, float yPos){
		shape.setPosition(new Vector2(xPos / Base.WORLDSCALE,yPos / Base.WORLDSCALE));
	}
}
