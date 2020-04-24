package com.project0607.game.object;

import com.project0607.R;
import com.project0607.activity.game.Game;

/** A pause button to pause a game. */
public class Pause extends GameObject {

  /**
   * Create a new Pause object.
   *
   * @param x This pause object's horizontal coordinate.
   * @param y This brick object's vertical coordinate.
   */
  public Pause(int x, int y) {
    super(x, y, 0, 0);
    solid = false;
  }

  @Override
  public void render(Game game) {
    renderBySpriteId(game, R.drawable.sp_pause);
  }
}
