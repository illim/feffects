import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.{Game, Gdx, ScreenAdapter}
import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.mygdx.game.component._
import com.mygdx.game._
import com.mygdx.game.effects.Shaders
import com.mygdx.game.gui.ButtonPanel
import com.typesafe.config.ConfigFactory
import priv.util.GuiUtils._
import priv.util.Repere

import scala.util.control.NonFatal


object Sandbox extends App {
  val config: LwjglApplicationConfiguration = new LwjglApplicationConfiguration
  config.width = 832
  config.height = 768
  config.title = "sandbox"
  new LwjglApplication(new Sandbox, config)
}

class Sandbox extends Game {

  override def create(): Unit = {
    setScreen(new Screen)
  }

}

class Screen extends ScreenAdapter {

  val screenResources = new ScreenResources
  val repere          = new Repere(screenResources)
  val board           = new SandboxBoard(screenResources)
  var lastE           = Option.empty[Throwable]

  import screenResources._

  board.buttons.clearButton.addListener(onClick {
    stage.clear()
    engine.removeAllEntities()
    init()
  })

  init()

  override def render (delta : Float): Unit = {
    try {
      stage act delta

      Gdx.gl glClear GL20.GL_COLOR_BUFFER_BIT
      stage.draw()
      engine update delta
      lastE = None
    } catch {
      case NonFatal(e) =>
        if (! lastE.contains(e) ) {
          lastE = Some(e)
          e.printStackTrace()
        }
    }
  }

  override def resize (width : Int, height : Int) {
    stage.getViewport.update(width, height, true)
  }

  def init(): Unit = {
    stage addActor repere
    stage addActor board.panel
  }
}


class ScreenResources extends GraphicResourceBase {
  val stage  = new Stage()
  val atlas  = new TextureAtlas(Gdx.files.internal("pack/images.pack.atlas"))
  val shapes = new ShapeRenderer()
  val config = ConfigFactory.load()
  val batch  = stage.getBatch
  var skin   = loadSkin("font")

  val engine = new Engine()
  val renderSystem   = new RenderSystem(batch, stage.getCamera)
  val scriptSystem   = new ScriptSystem()
  val particleSystem = new ParticleSystem(engine)
  val timedSystem    = new TimedSystem(engine)

  engine addSystem scriptSystem
  engine addSystem timedSystem
  engine addSystem particleSystem
  engine addSystem renderSystem

  Gdx.input.setInputProcessor(stage)

  var effectResources = new EffectResources(new Shaders, this)
  def loadSkin(fontKey : String) = {
    val font = generateFont(fontKey)
    val skin = new Skin()
    skin addRegions new TextureAtlas(Gdx.files.internal("data/uiskin.atlas"))
    skin.add("default-font", font)
    skin load Gdx.files.internal("data/uiskin.json")
    skin
  }

  def generateFont(fontKey : String) = {
    val c = config getConfig fontKey
    val generator = new FreeTypeFontGenerator(Gdx.files.internal(c getString "name"))
    val parameter = new FreeTypeFontParameter()
    parameter.size          = c getInt "size"
    parameter.shadowOffsetX = c getInt "shadowOffsetX"
    parameter.shadowOffsetY = c getInt "shadowOffsetY"
    val font = generator generateFont parameter
    generator.dispose()
    font
  }
}