import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Group
import com.mygdx.game.effects.{BlackMass, Lightning}
import com.mygdx.game.gui.ButtonPanel
import priv.util.Dot
import priv.util.GuiUtils._

class SandboxBoard(resources : ScreenResources) {
  import resources._
  val buttons = new ButtonPanel(skin)
  val panel = column(buttons.panel)

  buttons.lightningButton.addListener(onClick {
    val source = dot(200, 500, "source")
    val target = dot(400, 400, "target")
    val (entity, prom) = Lightning(resources, source, target)
    resources.engine addEntity entity
  })

  buttons.blackMassButton.addListener(onClick {
    val source = dot(400, 500, "source")
    val target = dot(200, 400, "target")
    val (entity, prom) = BlackMass(resources, source, target, new Vector2(500, 100))
    resources.engine addEntity entity
  })

  panel.fill()
  panel.setFillParent(true)
  panel.pack()

  def dot(x : Int, y : Int, label : String) : Vector2 = {
    val pos = new Vector2(x, y)
    resources.stage.addActor(new Dot(pos, label, resources))
    pos
  }
}