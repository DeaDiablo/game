package com.games.CityOfZombies.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Player extends Image
{
  public Player(TextureRegion region, float x, float y, float angle)
  {
    super(region);
    setPosition(x, y);
    setRotation(angle);
  }
}
