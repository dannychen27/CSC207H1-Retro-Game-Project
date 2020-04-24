package com.project0607.game.action.update;

import com.project0607.game.Game;
import com.project0607.game.object.GameObject;

public class AddObjectUpdateAction implements UpdateAction {

  /** The game object of interest. */
  private final GameObject gameObject;

  /**
   * Construct an action that adds a new game object.
   *
   * @param gameObject The game object to add.
   */
  public AddObjectUpdateAction(GameObject gameObject) {
    this.gameObject = gameObject;
  }

  @Override
  public void updateGame(Game game) {
    game.getGameObjects().add(this.gameObject);
  }
}
