package com.project0607.game;

import com.project0607.GlobalSession;
import com.project0607.render.Renderer;

/**
 * A factory for creating games.
 */
public class GameFactory {
  /**
   * Creates a game based on the type.
   *
   * @param gameType The game type.
   * @param session  The global session.
   * @param renderer The renderer to use.
   * @return The game.
   */
  public Game createGame(GameType gameType, GlobalSession session, Renderer renderer) {
    switch (gameType) {
      case PONG:
        return new GamePong(session, renderer);
      case BREAKOUT:
        return new GameBreakout(session, renderer);
      case SHMUP:
        return new GameShmup(session, renderer);
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
