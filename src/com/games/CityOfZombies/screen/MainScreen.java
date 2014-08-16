package com.games.CityOfZombies.screen;

import com.badlogic.gdx.Gdx;
import com.shellGDX.screen.GameScreen;

public class MainScreen extends GameScreen
{  
  public MainScreen()
  {
    super();
  }

  @Override
  public void show()
  {
    Gdx.input.setCatchBackKey(true);
    Gdx.input.setCatchMenuKey(true);
    setClearColor(1.0f, 1.0f, 1.0f, 1);
  }
}
