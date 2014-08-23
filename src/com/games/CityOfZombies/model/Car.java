package com.games.CityOfZombies.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.shellGDX.box2dLight.ConeLight;
import com.shellGDX.box2dLight.PointLight;
import com.shellGDX.box2dLight.RayHandler;
import com.shellGDX.model2D.LightModel2D;

public class Car extends LightModel2D
{
  public Car(TextureRegion region, float x, float y)
  {
    super(region);
    setPosition(x, y);
    fixedRotation = false;
  }
  
  @Override
  public boolean initPhysicsObject(World world)
  {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.DynamicBody;
    bodyDef.linearDamping = 20.0f;
    bodyDef.angularDamping = 20.0f;
    bodyDef.fixedRotation = fixedRotation;
    bodyDef.position.set(getX(), getY());
    bodyDef.position.scl(WORLD_TO_BOX);
    bodyDef.angle = MathUtils.degreesToRadians * getRotation();

    body = world.createBody(bodyDef);

    PolygonShape polygon = new PolygonShape();
    polygon.setAsBox(getWidth() * 0.5f * WORLD_TO_BOX, getHeight() * 0.5f * WORLD_TO_BOX);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = polygon;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = 1.0f;
    fixture = body.createFixture(fixtureDef);
    fixture.setUserData(this);

    polygon.dispose();
    return true;
  }
  
  @Override
  public boolean initLightObject(RayHandler rayHandler)
  {
    ConeLight left_headlight = new ConeLight(rayHandler, 8, new Color(0.5f, 0.5f, 0.5f, 0.5f), 1500, 0, 0, getRotation(), 30);
    left_headlight.attachToBody(body, 50, -100);
    left_headlight.setActive(true);
    left_headlight.setStaticLight(false);
    lights.put("left_headlight", left_headlight.hashCode());
    
    ConeLight right_headlight = new ConeLight(rayHandler, 8, new Color(0.5f, 0.5f, 0.5f, 0.5f), 1500, 0, 0, getRotation(), 30);
    right_headlight.attachToBody(body, 50, 100);
    right_headlight.setActive(true);
    right_headlight.setStaticLight(false);
    lights.put("right_headlight", right_headlight.hashCode());
    return true;
  }
}
