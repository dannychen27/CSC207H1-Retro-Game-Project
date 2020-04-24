package com.project0607.game.object;

import com.project0607.R;
import com.project0607.activity.game.Game;
import com.project0607.game.GameShootEmUp;

/** A squid enemy in Shoot 'Em Up. */
public class SquidEnemy extends Enemy {

  /**
   * Create a new squid enemy object.
   *
   * @param x This squid's initial horizontal coordinate.
   * @param y This squid's initial vertical coordinate.
   */
  public SquidEnemy(int x, int y) {
    super(x, y, 4, 5, 1);
  }

  @Override
  public void render(Game game) {
    int id = state ? R.drawable.sp_enemy_squid_0 : R.drawable.sp_enemy_squid_1;
    renderBySpriteId(game, id);
  }

  @Override
  protected void onDeath(Game game) {
    game.getStatistics().upsert(GameShootEmUp.KEY_SQUID_ENEMIES_KILLED, n -> n + 1, 1);
  }
}
