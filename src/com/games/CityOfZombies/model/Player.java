package com.games.CityOfZombies.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.games.CityOfZombies.light.PointLight2D3D;
import com.shellGDX.box2dLight.RayHandler;
import com.shellGDX.model2D.LightModel2D;

public class Player extends LightModel2D
{
  public Player(TextureRegion region, float x, float y)
  {
    super(region);
    setPosition(x, y);
    fixedRotation = true;
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

    CircleShape circle = new CircleShape();
    circle.setRadius(getWidth() * 0.25f * WORLD_TO_BOX);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = circle;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = 1.0f;
    fixture = body.createFixture(fixtureDef);
    fixture.setUserData(this);

    circle.dispose();
    return true;
  }

  @Override
  public boolean initLightObject(RayHandler rayHandler)
  {
    PointLight2D3D point = new PointLight2D3D(rayHandler, 256, new Color(0.85f, 0.75f, 0.65f, 1.0f), 500f, 0, 0);
    point.attachToBody(body, 0, 0);
    point.setActive(true);
    point.setStaticLight(false);

    return true;
  }
}
