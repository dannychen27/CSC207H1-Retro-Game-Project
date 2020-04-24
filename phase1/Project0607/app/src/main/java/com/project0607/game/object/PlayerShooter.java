package com.project0607.game.object;

import com.project0607.R;
import com.project0607.game.Game;
import com.project0607.game.action.update.AddObjectUpdateAction;
import com.project0607.game.action.update.UpdateAction;

import java.util.ArrayList;
import java.util.List;

public class PlayerShooter extends Player {

  /** The number of ticks elapsed. */
  private int shootBullet = 0;

  /** Whether or not to fire a shot. */
  private boolean shoot = false;

  /**
   * Create a new PlayerShooter object.
   *
   * @param x This player's horizontal coordinate.
   * @param y This player's vertical coordinate.
   * @param vx This player's horizontal velocity.
   */
  public PlayerShooter(int x, int y, int vx) {
    super(x, y, vx);
  }

  /**
   * Update the player shooter based on the game's current state.
   *
   * @param game The game this player shooter is in.
   * @return Actions to take after this player shooter is updated.
   */
  @Override
  public List<? extends UpdateAction> update(Game game) {
    // The player shooter shoots one bullet every 5 ticks.
    List<UpdateAction> bulletsToUpdate = new ArrayList<>(super.update(game));
    if (shootBullet == 5 && shoot) {
      int middle = (game.getRenderer().getDrawableWidth(R.drawable.sp_player) + 2 * x) / 2;
      bulletsToUpdate.add(new AddObjectUpdateAction(new Bullet(middle, y - 100, 0, -25)));
    }

    // Although you could just increment shootBullet continuously, and then
    // shoot when shootBullet % 5 == 0, if the player plays the game long enough,
    // shootBullet will eventually integer overflow.
    // Hence we reset shootBullet to 0 every 5 bullet shots to prevent this.
    shootBullet++;
    if (shootBullet > 5) {
      shootBullet = 0;
    }
    return bulletsToUpdate;
  }

  /**
   * Start shooting on touch.
   *
   * @param game The game session this object is in.
   * @param newX The x-coordinate of the touch.
   * @param newY The y-coordinate of the touch.
   */
  @Override
  public void onTouch(Game game, int newX, int newY) {
    super.onTouch(game, newX, newY);
    shoot = true;
  }

  /**
   * Stop shooting on touch.
   *
   * @param game The game session this object is in.
   * @param newX The x-coordinate of the touch.
   * @param newY The y-coordinate of the touch.
   */
  @Override
  public void onTouchRelease(Game game, int newX, int newY) {
    super.onTouchRelease(game, newX, newY);
    shoot = false;
  }
}
