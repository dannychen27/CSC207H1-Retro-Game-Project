package com.project0607.game;

import com.project0607.GlobalSession;
import com.project0607.game.object.Ball;
import com.project0607.game.object.ComputerPaddle;
import com.project0607.game.object.Pause;
import com.project0607.game.object.Player;
import com.project0607.game.object.PongBall;
import com.project0607.game.ui.StatisticsDisplay;
import com.project0607.render.Renderer;
import com.project0607.util.TypedKey;
import com.project0607.util.TypedMap;

public class GamePong extends Game {

  /** Various local statistics for Pong. */
  public static final TypedKey<Integer> KEY_TOTAL_TOUCHES = new TypedKey<>("pong.total_hits");
  public static final TypedKey<Integer> KEY_PLAYER_SCORE = new TypedKey<>("pong.player_score");
  public static final TypedKey<Integer> KEY_COMPUTER_SCORE = new TypedKey<>("pong.computer_score");

  /** Create a new GameOne object. */
  GamePong(GlobalSession session, Renderer renderer) {
    super(session, renderer);

    boolean isFast = session.getConfiguration().getOrDefault(Game.FAST_BALL, false);
    int speed = isFast ? 6 : 3;
    this.setUpPongGame(speed);
  }

  private void setUpPongGame(int speed) {
    Ball ball = new PongBall(500, 400, -speed, -speed);
    gameObjects.add(ball);
    gameObjects.add(new ComputerPaddle(200, 200, 2, ball));
    gameObjects.add(new Player(300, 1600, 0));
    gameObjects.add(new Pause(100, 100));
    renderables.add(new StatisticsDisplay(330, 100, GamePong.KEY_PLAYER_SCORE));
    renderables.add(new StatisticsDisplay(830, 100, GamePong.KEY_COMPUTER_SCORE));
  }

  @Override
  public void saveStatistics() {
    TypedMap globalStats = session.getStatistics();

    int totalTouches = statistics.getOrDefault(KEY_TOTAL_TOUCHES, 0);
    globalStats.upsert(KEY_TOTAL_TOUCHES, x -> x + totalTouches, totalTouches);

    int playerScore = statistics.getOrDefault(KEY_PLAYER_SCORE, 0);
    globalStats.upsert(KEY_PLAYER_SCORE, x -> x + playerScore, playerScore);

    int computerScore = statistics.getOrDefault(KEY_COMPUTER_SCORE,0);
    globalStats.upsert(KEY_COMPUTER_SCORE, x -> x + computerScore, computerScore);
  }
}
