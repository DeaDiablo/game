package com.games.CityOfZombies.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector2;
import com.games.CityOfZombies.light.Light2D3D;
import com.games.CityOfZombies.light.LightFilter;
import com.games.CityOfZombies.model.Car;
import com.games.CityOfZombies.model.ModelLayer;
import com.games.CityOfZombies.model.ModelLevel;
import com.games.CityOfZombies.model.Player;
import com.shellGDX.GameInstance;
import com.shellGDX.GameLog;
import com.shellGDX.box2dLight.Light2D;
import com.shellGDX.box2dLight.LightWorld2D;
import com.shellGDX.controller.PhysicsWorld2D;
import com.shellGDX.manager.ResourceManager;
import com.shellGDX.model2D.EffectObject2D;
import com.shellGDX.model2D.Scene2D;
import com.shellGDX.model3D.Scene3D;
import com.shellGDX.model3D.light.LightWorld3D;
import com.shellGDX.screen.GameScreen;
import com.shellGDX.utils.DayNightCycle;
import com.shellGDX.utils.gleed.Layer;
import com.shellGDX.utils.gleed.Level;

public class CityScreen extends GameScreen
{
  //parametrs
  protected float width = 0.0f,
                  height = 0.0f;
  
  //2d objects
  protected Scene2D            scene2D   = null;
  protected OrthographicCamera camera2D  = null;
  protected Player             player    = null;
  protected Car                car       = null;
  protected EffectObject2D     rain      = null;

  //3d objects
  protected Scene3D            scene3D   = null;
  protected PerspectiveCamera  camera3D  = null;
  
  protected boolean            clearWeather = true;
  protected DayNightCycle      dayNight     = new DayNightCycle(22, 0.5f / 48.0f, clearWeather);
  
  public CityScreen(float width, float height)
  {
    super();
    this.width = width;
    this.height = height;
  }

  @Override
  public void show()
  {
    //settings
    PhysicsWorld2D.init(new Vector2(0, 0), true);
    Gdx.input.setCatchBackKey(true);
    Gdx.input.setCatchMenuKey(true);
    setClearColor(0.0f, 0.0f, 0.0f, 1.0f);

    //2d objects
    scene2D = new Scene2D(width, height);
    camera2D = (OrthographicCamera)scene2D.getCamera();
    LightWorld2D.init(camera2D);
    Light2D.setContactFilter(new LightFilter());
    
    car = new Car(ResourceManager.instance.getTextureRegion("taxi.png"));
    car.setPosition(-500, 0);
    
    player = new Player(ResourceManager.instance.getTextureRegion("player_pistol.png"));
    Level level = ResourceManager.instance.getGleed2DMap("testLevel1.xml");
    scene2D.addActor(level);
    scene2D.addActor(player);
    scene2D.addActor(car);
    GameInstance.contoller.addScene2D(scene2D);

    //3d objects
    LightWorld3D.instance.setAmbientColor(dayNight.getDayColor());
    
    camera3D = new PerspectiveCamera(67f, 1920, 1080);
    camera3D.position.set(0, 0, 816);
    camera3D.lookAt(0, 0, 0);
    camera3D.near = 1.0f;
    camera3D.far  = camera3D.position.z;

    scene3D = new Scene3D(width, height, camera3D);

    ModelLevel modelLevel = new ModelLevel();
    for(Layer layer : level.getLayers())
    {
      ModelLayer modelLayer = new ModelLayer();
      modelLayer.parseLayer(layer);
      modelLevel.addModel3D(modelLayer);
    }
    
    scene3D.addModel3D(modelLevel);
    GameInstance.contoller.addScene3D(scene3D);

    GameInstance.contoller.addProcessor(player);
    
    rain = new EffectObject2D(ResourceManager.instance.getEffect("true_rain_test2.p"));
    rain.setZIndex(1000);
    scene2D.addActor(rain);
  }
  
  @Override
  public void update(float deltaTime)
  {
    dayNight.update(deltaTime, clearWeather);
    super.update(deltaTime);
    
    camera2D.position.x = player.getOriginX() + player.getX();
    camera2D.position.y = player.getOriginY() + player.getY();
    camera2D.update();

    camera3D.position.x = camera2D.position.x;
    camera3D.position.y = camera2D.position.y;
    camera3D.update();

    rain.setPosition(camera2D.position.x, camera2D.position.y);
    
    for(Light2D3D light : Light2D3D.lights2D3D)
      light.update(deltaTime);

    LightWorld3D.instance.update();
    scene3D.setShader(LightWorld3D.instance.getActiveShader());
    GameLog.instance.writeFPS();
  }

  public void resume()
  {
  }

  public void pause()
  {
  }

  @Override
  public void draw(float deltaTime)
  {
    scene2D.draw();
    Gdx.gl.glClear(GL30.GL_DEPTH_BUFFER_BIT);
    view.drawLightWorld();
    scene3D.draw();
    //view.drawPhysicsDebug(scene2D.getCamera());
  }
}
