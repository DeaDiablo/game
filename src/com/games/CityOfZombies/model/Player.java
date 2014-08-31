package com.games.CityOfZombies.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.shellGDX.controller.PhysicsWorld2D;
import com.shellGDX.model2D.CompositeObject2D;

public class Player extends CompositeObject2D
{
  public Player(TextureRegion textureRegion)
  {
    super(textureRegion, true, false);
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

    body.setFixedRotation(true);
    attachModel(this, 0, 0);

    return body;
  }
}
