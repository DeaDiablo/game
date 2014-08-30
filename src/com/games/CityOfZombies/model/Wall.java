package com.games.CityOfZombies.model;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Wall extends ModelPlate
{
  public Wall(Model model)
  {
    super(model);
  }

  @Override
  public boolean initPhysicsObject(World world)
  {
    super.initPhysicsObject(world);

    PolygonShape box = new PolygonShape();
    box.setAsBox(100 * WORLD_TO_BOX, 12.5f * WORLD_TO_BOX);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = box;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = 0.0f;
    fixture = body.createFixture(fixtureDef);

    box.dispose();
    return true;
  }
}
