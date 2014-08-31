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
import com.badlogic.gdx.utils.Array;
import com.shellGDX.controller.PhysicsWorld;
import com.shellGDX.model2D.CompositeModel;
import com.shellGDX.model2D.Model2D;

public class Player extends CompositeModel
{
  public Player(TextureRegion textureRegion)
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

    CircleShape circle = new CircleShape();
    circle.setRadius(actor.getWidth() * 0.25f * PhysicsWorld.WORLD_TO_BOX);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = circle;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = 1.0f;
    Fixture fixture = newBody.createFixture(fixtureDef);
    fixture.setUserData(this);
    circle.dispose();
    
    body.setBody(newBody);

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
