package com.games.CityOfZombies.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.games.CityOfZombies.CityOfZombies;
import com.games.CityOfZombies.model.Player;
import com.shellGDX.manager.ResourceManager;
import com.shellGDX.screen.GameScreen;

public class CityScreen extends GameScreen
{
  protected Stage mainStage = null;
  
  public CityScreen()
  {
    super();
  }

  @Override
  public void show()
  {
    Gdx.input.setCatchBackKey(true);
    Gdx.input.setCatchMenuKey(true);
    setClearColor(1.0f, 1.0f, 1.0f, 1);

    Player player = new Player(ResourceManager.instance.getTextureRegion("player_pistol"), 500, 500, 0);
    mainStage = new Stage(new ScalingViewport(Scaling.fit, 1920, 1080));
    mainStage.addActor(player);
    CityOfZombies.contoller.addStage(mainStage);
  }
}
