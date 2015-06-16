package com.dropTheBox.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool.Poolable;

public abstract class Base extends MyActor implements Poolable, ContactListener, Disposable{
	public static final float WORLDSCALE = 100f; 

	private final Body body;
	private TextureRegion image;
	private float prevScaleX = 1, prevScaleY = 1;

	public Base(World world){
		this.setName("Base");
		body = world.createBody(new BodyDef());
		this.setActive(false);
	}

	protected void init(float xPos, float yPos, float width, float height){
		this.setSize(width, height);
		this.setPosition(xPos, yPos);
		this.setActive(true);
	}

	@Override
	public void reset() {
		this.remove();
		this.setRotation(0);
		this.setColor(Color.WHITE);
		this.setUserObject(null);
		this.setTouchable(null);
		this.clear();
		this.setActive(false);
	}

	@Override
	public void act(float dt){
		if(!isAwake()) return;
		this.setPosition(body.getPosition().x * WORLDSCALE - getWidth()/2 , body.getPosition().y * WORLDSCALE - getHeight()/2);
		this.setRotation((float)Math.toDegrees(body.getAngle()));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		drawImageAt(batch, getX(), getY());
	}

	protected void drawImageAt(Batch batch, float xPos, float yPos){
		batch.draw(image, xPos, yPos,  getOriginX(),getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	
	@Override
	protected void positionChanged(){
		body.setTransform(getCenterX() / WORLDSCALE, getCenterY() /  WORLDSCALE , body.getAngle());
	}

	@Override
	protected void rotationChanged(){
		body.setTransform(body.getPosition() ,(float) Math.toRadians(getRotation()));
	}
	
	@Override
	protected void sizeChanged(){
		super.setOrigin(getWidth() / 2f, getHeight() / 2f);
		body.setTransform(getCenterX() / WORLDSCALE, getCenterY() /  WORLDSCALE , body.getAngle());
		for(Fixture fixture : body.getFixtureList())
			ShapeTransformer.setSize(fixture.getShape(), getWidth()/ WORLDSCALE, getHeight() / WORLDSCALE);
		body.resetMassData();
	}

	@Override
	protected void scaleChanged(){
		for(Fixture fixture : body.getFixtureList())
			ShapeTransformer.setScale(fixture.getShape(), getScaleX() / prevScaleX, getScaleY() / prevScaleY);
		body.resetMassData();
	}

	@Override
	public void setBounds(float x, float y, float w, float h){
		this.setSize(w,h);
		this.setPosition(x,y);
	}

	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	public void setLinearVelocity(float vX, float vY) {
		body.setLinearVelocity(vX / WORLDSCALE, vY / WORLDSCALE);
	}

	public float getXVelocity() {
		return body.getLinearVelocity().x * WORLDSCALE;
	}

	public float getYVelocity() {
		return body.getLinearVelocity().y * WORLDSCALE;
	}

	public void setAngularVelocity(float omega) {
		body.setAngularVelocity(omega);
	}

	public float getAngularVelocity() {
		return body.getAngularVelocity();
	}

	public void applyAngularImpulse(float impulse, boolean wake){
		body.applyAngularImpulse(impulse, wake);
	}

	public void applyForce(float forceX, float forceY, float pointX, float pointY, boolean wake){
		body.applyForce(forceX, forceY, pointX, pointY, wake);
	}

	public void applyForce(Vector2 force, Vector2 point, boolean wake){
		body.applyForce(force, point, wake);
	}

	public void applyForceToCenter(float forceX, float forceY, boolean wake){
		body.applyForceToCenter(forceX, forceY, wake);
	}

	public void applyForceToCenter(Vector2 force, boolean wake){
		body.applyForceToCenter(force, wake);
	}

	public void applyLinearImpulse(float impulseX, float impulseY, float pointX, float pointY, boolean wake){
		body.applyLinearImpulse(impulseX, impulseY, pointX, pointY, wake);
	}

	public void applyLinearImpulse(Vector2 impulse, Vector2 point, boolean wake){
		body.applyLinearImpulse(impulse, point, wake);
	}

	public void applyTorque(float torque, boolean wake){
		body.applyTorque(torque, wake);
	}

	public boolean isActive(){
		return body.isActive();
	}

	public void setActive(boolean active){
		body.setActive(active);
		super.setVisible(active);
	}

	public boolean isAwake(){
		return body.isAwake();
	}

	public void setAwake(boolean awake){
		body.setAwake(awake);
	}
	
	public void setSleepingAllowed(boolean flag){
		body.setSleepingAllowed(flag);
	}

	public void setType(BodyType type){
		body.setType(type);
	}
	
	public BodyType getType(){
		return body.getType();
	}
	
	public void setRotationAllowed(boolean flag){
		body.setFixedRotation(flag);
	}

	public Fixture createFixture(FixtureDef def){
		return body.createFixture(def);
	}

	public void destroyFixture(Fixture fixture){
		body.destroyFixture(fixture);
	}

	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@



	public float getCenterX(){
		return getX() + getWidth()/2f;
	}

	public float getCenterY(){
		return getY() + getHeight()/ 2f;
	}

	public float getScaledWidth(){
		return getWidth() * getScaleX();
	}

	public float getScaledHeight(){
		return getHeight() + getScaleY();
	}

	public float getScaledX(){
		return getX() - getWidth() * (getScaleX() - 1f) / 2f;
	}

	public float getScaledY(){
		return getY() - getHeight() * (getScaleY() - 1f) / 2f;
	}

	public void setImage(TextureRegion _region){
		image = _region;
	}

	public TextureRegion getImage(){
		return image;
	}



	@Override
	public void beginContact (Contact contact){};

	@Override
	public void endContact (Contact contact){};

	@Override
	public void preSolve (Contact contact, Manifold oldManifold){};

	@Override
	public void postSolve (Contact contact, ContactImpulse impulse){};

	@Override
	public void dispose(){
		this.remove();
	}
	
	public static float convert(float n){
		return n / WORLDSCALE;
	}
}
