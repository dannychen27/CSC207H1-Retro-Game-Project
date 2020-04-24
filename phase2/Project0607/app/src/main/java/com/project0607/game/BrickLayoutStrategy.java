package com.project0607.game;

import com.project0607.game.object.GameObject;

import java.util.List;

interface BrickLayoutStrategy {

  /**
   * Set the bricks for Breakout using the given brick layout strategy.
   *
   * @param gameObjects A list of game objects.
   */
  void setBricks(List<GameObject> gameObjects);
}
