package com.games.CityOfZombies.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.games.CityOfZombies.model.Player;
import com.shellGDX.GameInstance;
import com.shellGDX.GameLog;
import com.shellGDX.controller.LightWorld;
import com.shellGDX.controller.PhysicsWorld;
import com.shellGDX.manager.ResourceManager;
import com.shellGDX.model2D.Scene2D;
import com.shellGDX.model3D.Model3D;
import com.shellGDX.model3D.Scene3D;
import com.shellGDX.screen.GameScreen;
import com.shellGDX.utils.gleed.Level;

public class CityScreen extends GameScreen implements InputProcessor
{
  //parametrs
  protected float width = 0.0f,
                  height = 0.0f;
  
  //2d objects
  protected Scene2D            scene2D   = null;
  protected OrthographicCamera camera2D  = null;
  protected Player             player    = null; 

  //3d objects
  protected Scene3D            scene3D   = null;
  protected PerspectiveCamera  camera3D  = null;
  
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
    PhysicsWorld.init(new Vector2(0, 0), true);
    LightWorld.init();
    Gdx.input.setCatchBackKey(true);
    Gdx.input.setCatchMenuKey(true);
    setClearColor(1.0f, 1.0f, 1.0f, 1.0f);

    //2d objects
    player = new Player(ResourceManager.instance.getTextureRegion("player_pistol.png"), 0, 0);
    Level level = ResourceManager.instance.getGleed2dMap("testLevel0.xml");

    scene2D = new Scene2D(width, height);
    camera2D = (OrthographicCamera) scene2D.getCamera();
    scene2D.addActor(level);
    scene2D.addActor(player);
    GameInstance.contoller.addScene2D(scene2D);

    //3d objects
    camera3D = new PerspectiveCamera(67f, 1920, 1080);
    camera3D.position.set(0, 0, 816);
    camera3D.lookAt(0, 0, 0);
    camera3D.near = 1.0f;
    camera3D.far  = camera3D.position.z;
    
    scene3D = new Scene3D(width, height, camera3D);

    Model3D model = new Model3D(ResourceManager.instance.getModel("testmodel.obj"));
    model.setScale(20, 20, 20);
    scene3D.addModel3D(model);
    model = new Model3D(ResourceManager.instance.getModel("testmodel.obj"), 500, 500, 0);
    model.setScale(20, 20, 20);
    scene3D.addModel3D(model);
    GameInstance.contoller.addScene3D(scene3D);

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
    speed.scl(PhysicsWorld.WORLD_TO_BOX * 300);
    player.getBody().setLinearVelocity(speed);
    
    camera2D.position.x = player.getOriginX() + player.getX();
    camera2D.position.y = player.getOriginY() + player.getY();
    camera2D.update();

    camera3D.position.x = camera2D.position.x;
    camera3D.position.y = camera2D.position.y;
    camera3D.update();
  }
  
  @Override
  public void render(float deltaTime)
  {
    super.render(deltaTime);
    LightWorld.instance.render();
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
    mousePoint.sub(Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.5f);
    player.setRotation(MathUtils.atan2(mousePoint.y, mousePoint.x) * MathUtils.radiansToDegrees - 90.0f);
    return true;
  }

  @Override
  public boolean scrolled(int amount)
  {
    return false;
  }
}
