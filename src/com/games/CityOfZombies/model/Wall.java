package com.games.CityOfZombies.model;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.games.CityOfZombies.light.ShadowFilter;
import com.shellGDX.controller.PhysicsWorld2D;
import com.shellGDX.model3D.Scene3D;

public class Wall extends ModelPlate
{
  public Wall(Model model)
  {
    super(model);
  }

  public boolean initPhysicsObject(World world)
  {
    super.initPhysicsObject(world);

    PolygonShape box = new PolygonShape();
    box.setAsBox(100 * PhysicsWorld2D.WORLD_TO_BOX, 12.5f * PhysicsWorld2D.WORLD_TO_BOX);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = box;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = 0.0f;
    Fixture fixture = body.createFixture(fixtureDef);
    fixture.setFilterData(new ShadowFilter());

    box.dispose();
    return true;
  }
  
  @Override
  protected void setScene(Scene3D scene)
  {
    super.setScene(scene);
    if (PhysicsWorld2D.instance != null)
      initPhysicsObject(PhysicsWorld2D.instance);
  }
  
  @Override
  public boolean remove()
  {
    if (body != null && PhysicsWorld2D.instance != null)
    {
      PhysicsWorld2D.instance.destroyBody(body);
      body = null;
    }
    return super.remove();
  }

}
