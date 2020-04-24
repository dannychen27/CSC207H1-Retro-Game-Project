package com.project0607.game.action.update;

import com.project0607.game.Game;
import com.project0607.game.object.GameObject;

public class RemoveObjectUpdateAction implements UpdateAction {

  /** The game object of interest. */
  private final GameObject gameObject;

  /**
   * Construct an action that removes a new game object.
   *
   * @param gameObject The game object to remove.
   */
  public RemoveObjectUpdateAction(GameObject gameObject) {
    this.gameObject = gameObject;
  }

  @Override
  public void updateGame(Game game) {
    game.getGameObjects().remove(this.gameObject);
  }
}
