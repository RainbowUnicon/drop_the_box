package com.dropTheBox.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.utils.ShapeTransformer;

public abstract class Base extends com.badlogic.gdx.scenes.scene2d.Actor implements Poolable, ContactListener, Disposable{
	public static final float WORLDSCALE = 20f; 
	
	private final ActorLayer layer;
	private boolean initialized;
	
	private Body body;
	private Fixture fixture;
	private TextureRegion image;
	
	
	@SuppressWarnings("unused")
	private Base(){
		layer = null;
	}
	
	public Base(ActorLayer _layer){
		layer = _layer;
		initialized = false;
	}
	
	protected void init(float xPos, float yPos, float width, float height){
		layer.stage.addActor(this);
		super.setSize(width, height);
		
		image = new TextureRegion(layer.getAssets().get("game/base.png",Texture.class));
		
		if(initialized){
			body.setActive(true);
			setSize(width / WORLDSCALE, height / WORLDSCALE);
		} else{
			World world = layer.world;
		
			body = world.createBody(createBodyDef());

			//create the default shape of the object 
			Shape shapeToBeDisposed = createShape();
			
			//create the fixture 
			fixture = body.createFixture(createFixtureDef(shapeToBeDisposed));
			fixture.setUserData(this);

			//dispose the shape
			shapeToBeDisposed.dispose();
		}
		this.setPosition(xPos,yPos);
		this.setOrigin(width / 2, height / 2);
	}
	
	@Override
	public void act(float dt){
		super.setX(body.getPosition().x * WORLDSCALE - getWidth()/2);
		super.setY(body.getPosition().y * WORLDSCALE - getHeight()/2);
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
	public void moveBy(float x, float y){
		this.setPosition(getX() + x, getY() + y);
	}

	@Override
	public boolean remove(){
		boolean success = super.remove();
		layer.world.destroyBody(body);
		return success;
	}
	
	@Override
	public void rotateBy(float d){
		this.setRotation(getRotation() + d);
	}
	
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
		super.setScale(scaleX, scaleY);
		ShapeTransformer.scale(fixture.getShape(), scaleX, scaleY);
	}

	@Override
	public void setBounds(float x, float y, float w, float h){
		this.setPosition(x,y);
		this.setSize(w,h);
	}
	
	@Override
	public void setHeight(float height){
		super.setHeight(height);
		ShapeTransformer.resize(fixture.getShape(), getWidth(), height);
	}
	
	@Override
	public void setPosition(float x, float y){
		super.setPosition(x, y);
		body.setTransform((x + getWidth()/2) / WORLDSCALE, (y + getHeight()/2) /  WORLDSCALE , body.getAngle());
	}
	
	@Override
	public void setRotation(float d){
		super.setRotation(d);
		body.setTransform(getCenterX() / WORLDSCALE, getCenterY() /  WORLDSCALE ,(float) Math.toRadians(d));
	}
	
	@Override 
	public void setSize(float w, float h){
		super.setSize(w, h);
		ShapeTransformer.resize(fixture.getShape(), w, h);
	}
	
	@Override
	public void setWidth(float width){
		super.setWidth(width);
		ShapeTransformer.resize(fixture.getShape(), width, getHeight());
	}
	
	@Override
	public void sizeBy(float x, float y){
		setSize(getWidth() + x, getHeight() + y);
	}
	
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
	}
	
	
	
	public float getCenterX(){
		return getX() + getWidth()/2f;
	}
	
	public float getCenterY(){
		return getY() + getHeight()/ 2f;
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
	
	protected boolean isInitialized(){
		return initialized;
	}
	
	@Override
	public void reset() {
		super.remove();
		body.setActive(false);
		initialized = true;
	}
	
	protected abstract FixtureDef createFixtureDef(Shape shape);
	
	protected abstract Shape createShape();
	
	protected abstract BodyDef createBodyDef();

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
