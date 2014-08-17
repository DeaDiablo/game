package com.games.CityOfZombies.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.games.CityOfZombies.CityOfZombies;
import com.games.CityOfZombies.model.Player;
import com.shellGDX.manager.ResourceManager;
import com.shellGDX.screen.GameScreen;

public class TestScreen extends GameScreen
{
  public Stage mainStage = null;
  public PerspectiveCamera cam;
  public CameraInputController camController;
  public ModelBatch modelBatch;
  public Array<ModelInstance> instances = new Array<ModelInstance>();
  public Environment environment;
   
  @Override
  public void show()
  {
    Gdx.input.setCatchBackKey(true);
    Gdx.input.setCatchMenuKey(true);
    setClearColor(1.0f, 1.0f, 1.0f, 1);
    Player player = new Player(ResourceManager.instance.getTextureRegion("player_pistol.png"), 500, 500, 0);
    mainStage = new Stage(new ScalingViewport(Scaling.fit, 1920, 1080));
    mainStage.addActor(player);
    CityOfZombies.contoller.addStage(mainStage);
    
    modelBatch = new ModelBatch();
    environment = new Environment();
    environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
    environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
     
    cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    cam.position.set(1f, 1f, 1f);
    cam.lookAt(0,0,0);
    cam.near = 1f;
    cam.far = 300f;
    cam.update();

    camController = new CameraInputController(cam);
    Gdx.input.setInputProcessor(camController);

    Model ship = ResourceManager.instance.getModel("data/models/testmodel.obj");
    ModelInstance shipInstance = new ModelInstance(ship);
    instances.add(shipInstance);
  }
   
  @Override
  public void render(float deltaTime)
  {
    super.render(deltaTime);

    camController.update();
     
    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.gl.glClear(GL30.GL_DEPTH_BUFFER_BIT);

    modelBatch.begin(cam);
    modelBatch.render(instances, environment);
    modelBatch.end();
  }
   
  @Override
  public void dispose()
  {
    modelBatch.dispose();
    instances.clear();
  }

  public void resume()
  {
  }

  public void resize(int width, int height)
  {
  }

  public void pause()
  {
  }
}
