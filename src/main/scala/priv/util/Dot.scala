package priv.util

import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.{Color, GL20, Mesh, VertexAttribute}
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.mygdx.game.GraphicResourceBase

class Dot(pos : Vector2, label : String, resources : GraphicResourceBase) extends Actor {
  val dot = createDot()
  val l = new Label(label, resources.skin)
  l.setPosition(pos.x + 10 , pos.y + 10)

  override def draw(batch : Batch, parentAlpha : Float): Unit = {
    l.draw(batch, parentAlpha)
    batch.end()
    batch.begin()
    val shader =  resources.effectResources.flat
    import shader.program

    batch.disableBlending()
    program.begin()
    program.setUniformMatrix("u_projTrans", batch.getProjectionMatrix)
    dot.render(program, GL20.GL_LINES, 0, 4)
    program.end()
    batch.enableBlending()
    batch.end()
    batch.begin()
  }

  private def createDot() = {
    import pos._
    val verts = Array[Float](
      x-5 , y   , Color.toFloatBits(255, 255, 255, 255),
      x+5 , y   , Color.toFloatBits(255, 255, 255, 255),
      x    , y-5   , Color.toFloatBits(255, 255, 255, 255),
      x    , y+5 , Color.toFloatBits(255, 255, 255, 255))

    val mesh = new Mesh( true, 4, 4,  // static mesh with 6 vertices and no indices
      new VertexAttribute( Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE ),
      new VertexAttribute( Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE ))

    mesh.setVertices( verts )
    mesh.setIndices(Array[Short]( 0, 1, 2, 3 ))
    mesh
  }
}
