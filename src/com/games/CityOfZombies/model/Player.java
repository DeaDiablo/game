package com.games.CityOfZombies.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.shellGDX.model.PhysicsModel;

public class Player extends PhysicsModel
{
  public Player(TextureRegion region, float x, float y)
  {
    super(region);
    setPosition(x - getWidth() / 2, y - getHeight() / 2);
    setOrigin(getWidth() / 2, getHeight() / 2);
    fixedRotation = true;
    layout();
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
    circle.setRadius(getImageWidth() * 0.5f * WORLD_TO_BOX);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = circle;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = 1.0f;
    fixture = body.createFixture(fixtureDef);
    fixture.setUserData(this);

    circle.dispose();
    return true;
  }
}
