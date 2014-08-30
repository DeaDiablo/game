package com.games.CityOfZombies.light;

import com.badlogic.gdx.utils.Array;
import com.shellGDX.box2dLight.Light;
import com.shellGDX.model3D.light.Light3D;

public interface Light2D3D
{
  public static final Array<Light2D3D> lights2D3D = new Array<Light2D3D>();
  
  public Light3D getLight3D();
  public boolean isActive();
  public void update(float deltaTime);
  public Light asLight();
  public ConusLight2D3D asConusLight2D3D();
  public PointLight2D3D asPointLight2D3D();
}
