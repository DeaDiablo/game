package com.games.CityOfZombies.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.SnapshotArray;
import com.games.CityOfZombies.light.PointLight2D3D;
import com.shellGDX.box2dLight.Light2D;
import com.shellGDX.box2dLight.RayHandler;
import com.shellGDX.controller.PhysicsWorld2D;
import com.shellGDX.model2D.CompositeObject2D;

public class Player extends CompositeObject2D
{
  public Player(TextureRegion textureRegion)
  {
    super(textureRegion, true, true);
  }

  @Override
  protected Body initPhysic(World world)
  {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.DynamicBody;
    bodyDef.linearDamping = 20.0f;
    bodyDef.angularDamping = 20.0f;
    bodyDef.fixedRotation = true;
    bodyDef.position.set(getX(), getY());
    bodyDef.position.scl(PhysicsWorld2D.WORLD_TO_BOX);
    bodyDef.angle = MathUtils.degreesToRadians * getRotation();

    Body body = world.createBody(bodyDef);

    CircleShape circle = new CircleShape();
    circle.setRadius(modelObject.getWidth() * 0.25f * PhysicsWorld2D.WORLD_TO_BOX);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = circle;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = 1.0f;
    Fixture fixture = body.createFixture(fixtureDef);
    fixture.setUserData(this);
    circle.dispose();

    attachPhysicBody(0, 0);

    return body;
  }
  
  protected boolean initLights(RayHandler lightsWorld, final SnapshotArray<Light2D> lights)
  {
    PointLight2D3D pointLight = new PointLight2D3D(lightsWorld, 256, new Color(1, 1, 1, 1), 500, 0, 0);
    pointLight.attachToBody(getBody(), 0, 0);
    pointLight.setActive(true);
    pointLight.setStaticLight(false);
    lights.add(pointLight);
    
    return true;
  }
}
