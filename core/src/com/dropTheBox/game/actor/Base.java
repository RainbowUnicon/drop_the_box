package com.dropTheBox.game.actor;

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
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.utils.ShapeTransformer;

public abstract class Base extends com.badlogic.gdx.scenes.scene2d.Actor implements Poolable, ContactListener, Disposable{
	public static final short CATEGORY_PLAYER = 0x0001;
	public static final short CATEGORY_PLATFORM = 0x0002;
	public static final short CATEGORY_ITEM = 0x0004;
	public static final short CATEGORY_ENTITY = 0x0008;
	public static final short CATEGORY_WHATEVER = 0x0010;
	public static final short CATEGORY_WHEEL = (short)0x8000;
	
	public static final float WORLDSCALE = 100f; 

	private final ActorLayer layer;
	private final Body body;
	private TextureRegion image;


	public Base(ActorLayer _layer){
		layer = _layer;
		this.setName("Base");
		body = layer.world.createBody(new BodyDef());
		body.setActive(false);
	}

	protected void init(float xPos, float yPos, float width, float height){
		getLayer().stage.addActor(this);
		body.setActive(true);
		this.setSize(width, height);
		this.setPosition(xPos,yPos);
	}

	@Override
	public void reset() {
		super.remove();
		super.setColor(Color.WHITE);
		super.setSize(0, 0);
		super.setPosition(0, 0);
		super.setRotation(0);
		super.setVisible(true);
		super.setTouchable(null);
		super.setZIndex(0);
		super.setUserObject(null);
		super.clear();
		this.setImage(null);
		body.setActive(false);
	}

	@Override
	public void act(float dt){
		if(!isAwake()) return;
		super.setPosition(body.getPosition().x * WORLDSCALE - getWidth()/2 , 
				body.getPosition().y * WORLDSCALE - getHeight()/2);
		super.setRotation((float)Math.toDegrees(body.getAngle())); 

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

	@Override
	public void setPosition(float x, float y){
		super.setPosition(x, y);
		body.setTransform((x + getWidth()/2) / WORLDSCALE, (y + getHeight()/2) /  WORLDSCALE , body.getAngle());
	}

	@Override
	public void moveBy(float x, float y){
		super.moveBy(x, y);
		Vector2 v = body.getPosition();
		body.setTransform(v.x + x / WORLDSCALE, v.y + y / WORLDSCALE, body.getAngle());
	}

	@Override
	public void setX(float x){
		super.setX(x);
		body.setTransform((x + getWidth()/2) / WORLDSCALE, getCenterY() /  WORLDSCALE , body.getAngle());
	}

	@Override
	public void setY(float y){
		super.setY(y);
		body.setTransform(getCenterX() / WORLDSCALE, (y + getHeight()/2) /  WORLDSCALE , body.getAngle());
	}

	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	@Override
	public void rotateBy(float d){
		super.rotateBy(d);
		body.setTransform(body.getPosition() , body.getAngle() + (float) Math.toRadians(d));
	}

	@Override
	public void setRotation(float d){
		super.setRotation(d);
		body.setTransform(body.getPosition() , (float) Math.toRadians(d));
	}

	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	@Override
	public void sizeBy(float x, float y){
		this.setSize(getWidth() + x, getHeight() + y);
	}

	@Override 
	public void setSize(float w, float h){
		super.setSize(w, h);
		super.setOrigin(w/2, h/2);
		for(Fixture fixture : body.getFixtureList())
			ShapeTransformer.resize(fixture.getShape(), w / WORLDSCALE, h / WORLDSCALE);
	}

	@Override
	public void setWidth(float width){
		this.setSize(width, getHeight());
	}

	@Override
	public void setHeight(float height){
		setSize(getWidth(), height);
	}

	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	@Override
	public void scaleBy(float scale){
		setScale(getScaleX() + scale, getScaleY() + scale);
	}

	@Override
	public void scaleBy(float scaleX, float scaleY){
		setScale(getScaleX() + scaleX, getScaleY() + scaleY);
	}

	@Override
	public void setScale(float scaleXY) {
		setScale(scaleXY, scaleXY);
	}

	@Override
	public void setScale(float scaleX, float scaleY) {
		for(Fixture fixture : body.getFixtureList())
			ShapeTransformer.scale(fixture.getShape(), scaleX / getScaleX(), scaleY /getScaleY());
		super.setScale(scaleX, scaleY);
	}
	
	@Override
	public void setScaleX(float scaleX){
		setScale(scaleX, getScaleY());
	}
	
	@Override
	public void setScaleY(float scaleY){
		setScale(getScaleX(), scaleY);
	}



	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	@Override
	public boolean remove(){
		boolean success = super.remove();
		layer.world.destroyBody(body);
		return success;
	}

	@Override
	public void setBounds(float x, float y, float w, float h){
		this.setPosition(x,y);
		this.setSize(w,h);
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

	public ActorLayer getLayer(){
		return layer;
	}

	public static float convert(float n){
		return n / WORLDSCALE;
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
}
