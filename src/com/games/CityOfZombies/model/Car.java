package com.games.CityOfZombies.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.shellGDX.box2dLight.ConeLight;
import com.shellGDX.box2dLight.RayHandler;
import com.shellGDX.controller.PhysicsWorld;
import com.shellGDX.model2D.CompositeModel;
import com.shellGDX.model2D.Model2D;

public class Car extends CompositeModel
{
  public Car(TextureRegion textureRegion)
  {
    init(textureRegion, new Array<String>());
  }
  
  @Override
  public boolean initPhysics(World world)
  {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.DynamicBody;
    bodyDef.linearDamping = 20.0f;
    bodyDef.angularDamping = 20.0f;
    bodyDef.fixedRotation = false;
    bodyDef.position.set(getX(), getY());
    bodyDef.position.scl(PhysicsWorld.WORLD_TO_BOX);
    bodyDef.angle = MathUtils.degreesToRadians * getRotation();

    Body newBody = world.createBody(bodyDef);

    PolygonShape polygon = new PolygonShape();
    polygon.setAsBox(actor.getWidth() * 0.5f * PhysicsWorld.WORLD_TO_BOX, actor.getHeight() * 0.5f * PhysicsWorld.WORLD_TO_BOX);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = polygon;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = 1.0f;
    Fixture fixture = newBody.createFixture(fixtureDef);
    fixture.setUserData(this);
    polygon.dispose();
    
    body.setBody(newBody);

    return true;
  }
  
  @Override
  public boolean initLigts(RayHandler rayHandler)
  {
    ConeLight left_headlight = new ConeLight(rayHandler, 256, new Color(0.5f, 0.5f, 0.5f, 0.5f), 1500, 0, 0, getRotation(), 30);
    left_headlight.attachToBody(body.getBody(), 300, -80);
    left_headlight.setActive(true);
    left_headlight.setStaticLight(false);
    light.addLight("left_headlight", left_headlight.hashCode());
    
    ConeLight right_headlight = new ConeLight(rayHandler, 256, new Color(0.5f, 0.5f, 0.5f, 0.5f), 1500, 0, 0, getRotation(), 30);
    right_headlight.attachToBody(body.getBody(), 300, 80);
    right_headlight.setActive(true);
    right_headlight.setStaticLight(false);
    light.addLight("right_headlight", right_headlight.hashCode());
  
    return true;
  }
  
  @Override
  public boolean initActor(TextureRegion textureRegion)
  {
    Model2D model = new Model2D(textureRegion);
    setActor(model);
    
    return true;
  }
}
