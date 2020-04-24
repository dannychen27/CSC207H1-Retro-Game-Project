package com.project0607.game;

import com.project0607.GlobalSession;
import com.project0607.activity.game.Game;
import com.project0607.activity.game.GameView;

/** A factory for creating games. */
public class GameFactory {

  /**
   * Create a game based on the specified game type.
   *
   * @param gameType The game type.
   * @param session The global session.
   * @param view The view to use.
   * @return The game.
   */
  public Game createGame(GameType gameType, GlobalSession session, GameView view) {
    switch (gameType) {
      case PONG:
        return new GamePong(session, view);
      case BREAKOUT:
        return new GameBreakout(session, view);
      case SHMUP:
        return new GameShootEmUp(session, view);
      default:
        throw new IllegalArgumentException("unknown game type");
    }
  }

  /** Game types. */
  public enum GameType {
    PONG,
    BREAKOUT,
    SHMUP
  }
}
