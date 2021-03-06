package priv.util

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.AfterAction
import com.badlogic.gdx.scenes.scene2d.ui.{HorizontalGroup, VerticalGroup, WidgetGroup}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.{Action, Actor, InputEvent}

import scala.concurrent.Promise
import scala.util.Success


object GuiUtils {

  def row(actors : Actor*) = {
    group(new HorizontalGroup(), actors : _*)
  }

  def column(actors : Actor*)= {
    group(new VerticalGroup(), actors : _*)
  }

  def getCoord(actor : Actor) = actor.localToStageCoordinates(new Vector2)

  case class UpdateAction[A <: Actor](f : A => Unit) extends Action {

    def act(delta : Float) = {
      f(target.asInstanceOf[A])
      true
    }
  }

  object BasicAction {
    def apply(f : => Unit) = new BasicAction(f)
  }
  class BasicAction(f : => Unit) extends Action {
    def act(delta : Float) = {
      f
      true
    }
  }

  def promAction(actor : Actor) : Promise[Unit] = {
    val prom = new PromiseAction
    val afterAction = new AfterAction()
    actor addAction afterAction
    afterAction.setAction(prom)
    val promise = prom.prom
    promise
  }

  class PromiseAction extends Action {
    val prom = Promise[Unit]()

    def act(delta : Float) = {
      prom.complete(Success(()))
      true
    }
  }

  private def group[G <: WidgetGroup](g : G, actors : Actor*) = {
    actors foreach g.addActor
    g.pack()
    g
  }

  def onClick(f : => Unit) = new ClickListener() {
    override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
      f
    }
  }
}
