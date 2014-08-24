package com.games.CityOfZombies.model;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.shellGDX.controller.PhysicsWorld;
import com.shellGDX.model3D.Model3D;

public class Wall extends Model3D
{
  public static final float WORLD_TO_BOX  = PhysicsWorld.WORLD_TO_BOX;
  public static final float BOX_TO_WORLD  = PhysicsWorld.BOX_TO_WORLD;

  protected BodyDef         bodyDef       = new BodyDef();
  protected FixtureDef      fixtureDef    = new FixtureDef();
  protected Body            body          = null;
  protected Fixture         fixture       = null;
  protected boolean         fixedRotation = false;

  public Wall(Model model)
  {
    super(model);
  }

  public boolean initPhysicsObject(World world)
  {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.StaticBody;
    bodyDef.linearDamping = 20.0f;
    bodyDef.angularDamping = 20.0f;
    bodyDef.fixedRotation = fixedRotation;
    bodyDef.position.set(getX(), getY());
    bodyDef.position.scl(WORLD_TO_BOX);
    bodyDef.angle = MathUtils.degreesToRadians * (getRoll() + 90.0f);

    body = world.createBody(bodyDef);

    PolygonShape box = new PolygonShape();
    box.setAsBox(100 * WORLD_TO_BOX, 12.5f * WORLD_TO_BOX);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = box;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = 0.0f;
    fixture = body.createFixture(fixtureDef);
    fixture.setUserData(this);

    box.dispose();
    return true;
  }

}
