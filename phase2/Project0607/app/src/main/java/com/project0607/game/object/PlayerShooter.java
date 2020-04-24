package com.project0607.game.object;

import com.project0607.R;
import com.project0607.activity.game.Game;

/**
 * A player shooter that can only move horizontally based on player touches
 * and shoot bullets to destroy enemies in Shoot 'Em Up.
 */
public class PlayerShooter extends PlayerPaddle {

  /**
   * The number of ticks elapsed.
   *
   * Representation Invariant: 0 <= shootBullets <= 5.
   */
  private int shootBullet = 0;

  /**
   * Create a new player shooter object.
   *
   * @param x This player shooter's initial horizontal coordinate.
   * @param y This player shooter's initial vertical coordinate.
   * @param vx This player shooter's initial horizontal velocity.
   */
  public PlayerShooter(int x, int y, int vx) {
    super(x, y, vx);
  }

  @Override
  public void onTouch(Game game, int newX, int newY) {
    super.onTouch(game, newX, newY);
  }

  @Override
  public void update(Game game) {
    super.update(game);
    shootBullets(game);
  }

  /**
   * Make the player shooter shoot bullets once every 5 ticks.
   *
   * @param game The game this player shooter belongs to.
   */
  private void shootBullets(Game game) {
    if (shootBullet == 5) {
      int middle = (game.getView().getDrawableWidth(R.drawable.sp_player) + 2 * x) / 2;
      game.addGameObject(new Bullet(middle, y - 100, 0, -25));
    }

    // Although you could just increment shootBullet continuously, and then
    // shoot when shootBullet % 5 == 0, if the player plays the game long enough,
    // the value of shootBullet will eventually result in an integer overflow.
    // This code avoids integer overflow.
    shootBullet = (shootBullet >= 5) ? 0 : shootBullet + 1;
  }
}
