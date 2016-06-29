#!/bin/sh

DIR=$(dirname $0)

java -cp $HOME/.ivy2/cache/com.badlogicgames.gdx/gdx-tools/jars/gdx-tools-1.6.5.jar:$HOME/.ivy2/cache/com.badlogicgames.gdx/gdx/jars/gdx-1.6.5.jar \
  com.badlogic.gdx.tools.texturepacker.TexturePacker \
  $DIR/src/main/resources/src/images \
  $DIR/src/main/resources/pack \
  images.pack.atlas