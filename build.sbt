name := "feffects"

version := "1.0"

scalaVersion := "2.11.8"

organization := "priv.freespectrogdx"

val gdxVersion = "1.6.5"

val ashleyVersion = "1.6.0"

libraryDependencies ++= Seq(
  "com.badlogicgames.gdx" % "gdx" % gdxVersion,
  "com.badlogicgames.ashley" % "ashley" % ashleyVersion,
  "com.typesafe" % "config" % "1.3.0",
  "com.badlogicgames.gdx" % "gdx-backend-lwjgl" % gdxVersion % "test",
  "com.badlogicgames.gdx" % "gdx-tools" % gdxVersion % "test",
  "com.badlogicgames.gdx" % "gdx-platform" % gdxVersion % "test" classifier "natives-desktop",
  "com.badlogicgames.gdx" % "gdx-freetype" % gdxVersion % "test",
  "com.badlogicgames.gdx" % "gdx-freetype-platform" % gdxVersion % "test" classifier "natives-desktop"
)