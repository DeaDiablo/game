package com.games.CityOfZombies.screen;

import com.shellGDX.GameInstance;
import com.shellGDX.manager.ResourceManager;
import com.shellGDX.screen.GameScreen;

public class LoadingScreen extends GameScreen
{
  public LoadingScreen()
  {
    super();
  }

  @Override
  public void show()
  {
    ResourceManager.instance.loadFolder("data/sprites");
  }

  @Override
  public void render(float deltaTime)
  {
    super.render(deltaTime);

    if (ResourceManager.instance.update())
    {
      dispose();
      GameInstance.game.setScreen(new CityScreen());
    }
  }
}
