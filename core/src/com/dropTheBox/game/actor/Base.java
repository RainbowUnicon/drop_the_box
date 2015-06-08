package com.dropTheBox.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dropTheBox.game.layer.ActorLayer;
import com.dropTheBox.utils.ShapeTransformer;

public abstract class Base extends com.badlogic.gdx.scenes.scene2d.Actor implements Poolable, ContactListener{
	public static final float WORLDSCALE = 20f; 
	
	private boolean initialized = false;
	private final ActorLayer layer;

	
	private TextureRegion region;
	
	private SafeBody sBody;
	
	protected Body body;
	protected Fixture fixture;
	
	private Base(){
		layer = null;
	}
	public Base(ActorLayer _layer){
		layer = _layer;
	}
	
	protected void init(float x, float y, float w, float h){
		layer.getStage().addActor(this);
		super.setWidth(w);
		super.setHeight(h);
		region = new TextureRegion(layer.getAssets().get("game/base.png",Texture.class));
		
		if(initialized){
			body.setActive(true);
			setSize(w / WORLDSCALE, h / WORLDSCALE);
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
			
			sBody = new SafeBody(body);
		}
		setPosition(x,y);
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
		batch.draw(region, xPos, yPos,  getWidth() / 2f, getHeight() / 2f, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}

	@Override
	public void moveBy(float x, float y){
		setPosition(getX() + x, getY() + y);
	}

	@Override
	public boolean remove(){
		boolean success = super.remove();
		getLayer().world.destroyBody(body);
		body = null;
		this.dispose();
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
		setPosition(x,y);
		setSize(w,h);
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
		body.setTransform(body.getPosition().x * WORLDSCALE, body.getPosition().y * WORLDSCALE,(float) Math.toRadians(d));
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
	
	public Fixture getFixture(){
		return fixture;
	}
	
	public SafeBody getBody(){
		return sBody;
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
	
	
	
	
	public void setImage(TextureRegion _region){
		region = _region;
	}
	
	public TextureRegion getImage(){
		return region;
	}
	
	public ActorLayer getLayer(){
		return layer;
	}
	
	protected boolean isInitialized(){
		return initialized;
	}

	
	@Override
	public void reset() {
		body.setActive(false);
		body.setLinearVelocity(0,0);
		body.setAngularVelocity(0);
		setSize(0,0);
		setPosition(0,0);
		setRotation(0);
		initialized = true;
		this.remove();
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

	//This method will be called, when this object is disposed
	public void dispose(){}
}
