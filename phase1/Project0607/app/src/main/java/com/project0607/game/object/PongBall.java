package com.project0607.game.object;

import com.project0607.game.Game;
import com.project0607.game.GamePong;
import com.project0607.util.TypedKey;

public class PongBall extends Ball {
  /**
   * Construct a new ball object for Pong.
   *
   * @param x This Pong ball's horizontal coordinate.
   * @param y This Pong ball's vertical coordinate.
   * @param vx This Pong ball's horizontal velocity.
   * @param vy This Pong ball's vertical velocity.
   */
  public PongBall(int x, int y, int vx, int vy) {
    super(x, y, vx, vy);
  }

  /**
   * Handle a vertical wall collision.
   *
   * @param game The game the object is in.
   * @param top Whether or not the collision is on the top wall.
   */
  @Override
  public void collideVertical(Game game, boolean top) {
    super.collideVertical(game, top);
    TypedKey<Integer> whoScores = top ? GamePong.KEY_PLAYER_SCORE : GamePong.KEY_COMPUTER_SCORE;
    game.getStatistics().upsert(whoScores, n -> n + 1, 1);
    reset(game);
  }

  /**
   * Update the statistics counting collisions.
   *
   * @param game The game session this Pong ball belongs to.
   */
  @Override
  public void updateCollisionStats(Game game) {
    game.getStatistics().upsert(GamePong.KEY_TOTAL_TOUCHES, n -> n + 1, 1);
  }
}
