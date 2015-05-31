package com.dropTheBox.game.actor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Shape.Type;

public class SafeFixture{
	private Fixture fixture;
	
	public SafeFixture(Fixture f) {
		fixture = f;
	}
	
	public Type getType() {
		return fixture.getType();
	}

	
	public Shape getShape() {
		return fixture.getShape();
	}

	
	public void setSensor(boolean sensor) {
		fixture.setSensor(sensor);
	}

	
	public boolean isSensor() {
		return fixture.isSensor();
	}

	
	public void setFilterData(Filter filter) {
		fixture.setFilterData(filter);
	}

	
	public Filter getFilterData() {
		return fixture.getFilterData();
	}

	
	public void refilter() {
		fixture.refilter();
	}
	
	public boolean testPoint(Vector2 p) {
		return fixture.testPoint(p);
	}

	
	public boolean testPoint(float x, float y) {
		return fixture.testPoint(x, y);
	}

	
	public void setDensity(float density) {
		fixture.setDensity(density);
	}

	
	public float getDensity() {
		return fixture.getDensity();
	}

	
	public float getFriction() {
		return fixture.getFriction();
	}

	
	public void setFriction(float friction) {
		fixture.setFriction(friction);
	}

	
	public float getRestitution() {
		return fixture.getRestitution();
	}

	
	public void setRestitution(float restitution) {
		fixture.setRestitution(restitution);
	}
	
	public Object getUserData() {
		return fixture.getUserData();
	}

	
	public int hashCode() {
		return fixture.hashCode();
	}
	
	public boolean equals(Object obj) {
		return fixture.equals(obj);
	}
	
	public String toString() {
		return fixture.toString();
	}
}
