package com.project0607.game.object;

import com.project0607.R;
import com.project0607.activity.game.Game;
import com.project0607.game.GameShootEmUp;

/** A UFO enemy in Shoot 'Em Up. */
public class UfoEnemy extends Enemy {

  /**
   * Create a new UFO enemy object.
   *
   * @param x This UFO's horizontal coordinate.
   * @param y This UFO's vertical coordinate.
   */
  public UfoEnemy(int x, int y) {
    super(x, y, 1, 2, 10);
  }

  @Override
  public void render(Game game) {
    renderBySpriteId(game, R.drawable.sp_enemy_ufo);
  }

  @Override
  protected void onDeath(Game game) {
    game.getStatistics().upsert(GameShootEmUp.KEY_UFO_ENEMIES_KILLED, n -> n + 1, 1);
  }
}
