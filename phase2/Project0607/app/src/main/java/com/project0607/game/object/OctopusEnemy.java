package com.project0607.game.object;

import com.project0607.R;
import com.project0607.activity.game.Game;
import com.project0607.game.GameShootEmUp;

/** An octopus enemy in Shoot 'Em Up. */
public class OctopusEnemy extends Enemy {

  /**
   * Create a new octopus enemy object.
   *
   * @param x This octopus's horizontal coordinate.
   * @param y This octopus's vertical coordinate.
   */
  public OctopusEnemy(int x, int y) {
    super(x, y, 2, 3, 5);
  }

  @Override
  public void render(Game game) {
    int id = state ? R.drawable.sp_enemy_octopus_0 : R.drawable.sp_enemy_octopus_1;
    renderBySpriteId(game, id);
  }

  @Override
  protected void onDeath(Game game) {
    game.getStatistics().upsert(GameShootEmUp.KEY_OCTOPUS_ENEMIES_KILLED, n -> n + 1, 1);
  }
}
