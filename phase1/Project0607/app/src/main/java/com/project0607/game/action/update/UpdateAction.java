package com.project0607.game.action.update;

import com.project0607.game.Game;

/** Represents an action to run to update the state of a game. */
public interface UpdateAction {

  /**
   * Update the state of this game.
   *
   * @param game The game session whose state has to be updated.
   */
  void updateGame(Game game);
}
