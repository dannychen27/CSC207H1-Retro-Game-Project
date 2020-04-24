package com.project0607.game.object;

import com.project0607.R;
import com.project0607.activity.game.Game;
import com.project0607.game.GameShootEmUp;

/** A crab enemy in Shoot 'Em Up. */
public class CrabEnemy extends Enemy {

  /**
   * Create a new crab enemy object.
   *
   * @param x This crab's horizontal coordinate.
   * @param y This crab's vertical coordinate.
   */
  public CrabEnemy(int x, int y) {
    super(x, y, 3, 4, 2);
  }

  @Override
  public void render(Game game) {
    int id = state ? R.drawable.sp_enemy_crab_1 : R.drawable.sp_enemy_crab_0;
    renderBySpriteId(game, id);
  }

  @Override
  protected void onDeath(Game game) {
    game.getStatistics().upsert(GameShootEmUp.KEY_CRAB_ENEMIES_KILLED, n -> n + 1, 1);
  }
}
