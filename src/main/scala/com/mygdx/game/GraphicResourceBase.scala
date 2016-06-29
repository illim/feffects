package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.mygdx.game.effects.{MyParticleEffects, SelectedShader, Shaders}
import com.typesafe.config.{Config, ConfigFactory}


trait GraphicResourceBase {
  def stage : Stage
  def shapes : ShapeRenderer
  def config : Config
  def atlas : TextureAtlas
  def skin : Skin

  def effectResources : EffectResources
}


class EffectResources(val shaders: Shaders, resources: GraphicResourceBase) {

  //val ripple   = shaders.getOrElseUpdate("ripple", _ ⇒ new RippleShader)
  val grey     = shaders get "grey"
  //val test     = shaders get "test"
  val flat   = shaders get "flat"
  val selected = shaders.getOrElseUpdate("sel", _ ⇒ new SelectedShader("sel", resources.config.getConfig("card.selection")))
  val particles = new MyParticleEffects(resources.atlas)
}