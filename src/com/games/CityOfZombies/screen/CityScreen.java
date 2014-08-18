package com.games.CityOfZombies.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.games.CityOfZombies.model.Player;
import com.shellGDX.GameInstance;
import com.shellGDX.GameLog;
import com.shellGDX.controller.PhysicsWorld;
import com.shellGDX.manager.ResourceManager;
import com.shellGDX.screen.GameScreen;

public class CityScreen extends GameScreen implements InputProcessor
{
  //2d objects
  protected Stage mainStage = null;
  protected Camera camera2D = null;
  protected float width = 0.0f, height = 0.0f;
  protected Player player = null; 

  //3d objects
  public PerspectiveCamera    camera3D = null;
  public ModelBatch           batch3D  = null;
  public Array<ModelInstance> models   = new Array<ModelInstance>();
  
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
    PhysicsWorld.init(new Vector2(0, 0), false);
    Gdx.input.setCatchBackKey(true);
    Gdx.input.setCatchMenuKey(true);
    setClearColor(1.0f, 1.0f, 1.0f, 1.0f);

    //2d objects
    player = new Player(ResourceManager.instance.getTextureRegion("player_pistol.png"), width * 0.5f, height * 0.5f);

    mainStage = new Stage(new ScalingViewport(Scaling.fit, width, height));
    camera2D = mainStage.getCamera();
    mainStage.addActor(player);
    GameInstance.contoller.addStage(mainStage);

    //3d objects
    batch3D = new ModelBatch();

    camera3D = new PerspectiveCamera(90.0f * height / width, width, height);
    camera3D.position.set(0, 0, 50);
    camera3D.lookAt(0,0,0);
    camera3D.near = 1.0f;
    camera3D.far  = 50.0f;

    Model ship = ResourceManager.instance.getModel("data/models/testmodel.obj");
    ModelInstance shipInstance = new ModelInstance(ship);
    models.add(shipInstance);

    GameInstance.contoller.addProcessor(this);
  }
  
  protected float fpsTime = 1.0f;
  protected Vector2 speed = new Vector2();
  
  @Override
  public void update(float deltaTime)
  {
    super.update(deltaTime);
    
    fpsTime -= deltaTime;
    if (fpsTime <= 0.0f)
    {
      GameLog.instance.writeFPS();
      fpsTime = 1.0f;
    }

    speed.set(moveVec);
    speed.scl(PhysicsWorld.WORLD_TO_BOX * 20);
    player.getBody().setLinearVelocity(speed);
    mainStage.getCamera().position.x = player.getX();
    mainStage.getCamera().position.y = player.getY();

    camera3D.position.x = player.getX() - width * 0.5f;
    //camera3D.position.y = player.getY() - height * 0.5f;
    camera3D.update();
  }
  
  @Override
  public void render(float deltaTime)
  {    
    super.render(deltaTime);
     
    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.gl.glClear(GL30.GL_DEPTH_BUFFER_BIT);

    batch3D.begin(camera3D);
    batch3D.render(models);
    batch3D.end();
  }
   
  @Override
  public void dispose()
  {
    batch3D.dispose();
    models.clear();
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
  
  //control
  
  protected Vector2 moveVec    = new Vector2(0, 0);
  protected Vector2 mousePoint = new Vector2(0, 0);

  @Override
  public boolean keyDown(int keycode)
  {
    switch (keycode)
    {
      case Input.Keys.A:
      case Input.Keys.LEFT:
        moveVec.x -= 1.0f;
        break;

      case Input.Keys.D:
      case Input.Keys.RIGHT:
        moveVec.x += 1.0f;
        break;

      case Input.Keys.W:
      case Input.Keys.UP:
        moveVec.y += 1.0f;
        break;

      case Input.Keys.S:
      case Input.Keys.DOWN:
        moveVec.y -= 1.0f;
        break;
        
      default:
        return false;
    }
    
    return true;
  }

  @Override
  public boolean keyUp(int keycode)
  {
    switch (keycode)
    {
      case Input.Keys.A:
      case Input.Keys.LEFT:
        moveVec.x += 1.0f;
        break;

      case Input.Keys.D:
      case Input.Keys.RIGHT:
        moveVec.x -= 1.0f;
        break;

      case Input.Keys.W:
      case Input.Keys.UP:
        moveVec.y -= 1.0f;
        break;

      case Input.Keys.S:
      case Input.Keys.DOWN:
        moveVec.y += 1.0f;
        break;
        
      default:
        return false;
    }
    return true;
  }

  @Override
  public boolean keyTyped(char character)
  {
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button)
  {
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button)
  {
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer)
  {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY)
  {
    mousePoint.set(screenX, Gdx.graphics.getHeight() -  screenY);
    mainStage.screenToStageCoordinates(mousePoint);
    mousePoint.sub(width * 0.5f, height * 0.5f);
    player.setRotation(-MathUtils.atan2(mousePoint.y, mousePoint.x) * MathUtils.radiansToDegrees - 90.0f);
    return true;
  }

  @Override
  public boolean scrolled(int amount)
  {
    return false;
  }
}
