package com.project0607.game;

import com.project0607.GlobalSession;
import com.project0607.activity.game.Game;
import com.project0607.activity.game.GameView;
import com.project0607.game.object.ComputerPaddle;
import com.project0607.game.object.Pause;
import com.project0607.game.object.PlayerPaddle;
import com.project0607.game.object.PongBall;
import com.project0607.game.object.StatisticsDisplay;
import com.project0607.util.TypedKey;
import com.project0607.util.TypedMap;

/** A game of Pong. */
public class GamePong extends Game {

  // Various local statistics for Pong.
  public static final TypedKey<Integer> KEY_TOTAL_TOUCHES = new TypedKey<>("pong.total_hits");
  public static final TypedKey<Integer> KEY_PLAYER_SCORE = new TypedKey<>("pong.player_score");
  public static final TypedKey<Integer> KEY_COMPUTER_SCORE = new TypedKey<>("pong.computer_score");

  /** The current level in Pong. */
  private int currentLevel = 0;

  /** The ball used for Pong. */
  private PongBall ball;

  /** The player paddle used for Pong. */
  private PlayerPaddle player;

  /** The computer paddle used for Pong. */
  private ComputerPaddle computer;

  /** Create a new Pong object. */
  GamePong(GlobalSession session, GameView view) {
    super(session, view);
    startNewGame();
  }

  /** Store this game session's Pong statistics. */
  @Override
  public void saveStatistics() {
    TypedMap globalStats = session.getStatistics();

    int totalTouches = statistics.getOrDefault(KEY_TOTAL_TOUCHES, 0);
    globalStats.upsert(KEY_TOTAL_TOUCHES, x -> x + totalTouches, totalTouches);

    int playerScore = statistics.getOrDefault(KEY_PLAYER_SCORE, 0);
    globalStats.upsert(KEY_PLAYER_SCORE, x -> x + playerScore, playerScore);

    int computerScore = statistics.getOrDefault(KEY_COMPUTER_SCORE, 0);
    globalStats.upsert(KEY_COMPUTER_SCORE, x -> x + computerScore, computerScore);
  }

  @Override
  public void onLose() {
    getStatistics().upsert(KEY_COMPUTER_SCORE, n -> n + 1, 1);
    setUpLevel();
  }

  @Override
  public void onWin() {
    getStatistics().upsert(KEY_PLAYER_SCORE, n -> n + 1, 1);
    currentLevel++;
    setUpLevel();
  }

  /** Set up the first level. */
  private void startNewGame() {
    placeGameObjects();
    setUpLevel();
  }

  /** Place all necessary game objects on screen. */
  private void placeGameObjects() {
    setDisplays();
    setBall();
    setPaddles();
    setPauseButton();
  }

  /** Get the ball for Pong. */
  private void setBall() {
    boolean isFast = session.getConfiguration().getOrDefault(Game.FAST_BALL, false);
    int speed = isFast ? 6 : 3;
    ball = new PongBall(500, 400, -speed, -speed);
    addGameObject(ball);
  }

  /** Get the player and computer paddles for Pong. */
  private void setPaddles() {
    player = new PlayerPaddle(300, 1600, 0);
    addGameObject(player);

    computer = new ComputerPaddle(200, 200, 2, ball);
    addGameObject(computer);
  }

  /** Get the player and computer score displays for Pong. */
  private void setDisplays() {
    addGameObject(new StatisticsDisplay(330, 100, GamePong.KEY_PLAYER_SCORE));
    addGameObject(new StatisticsDisplay(830, 100, GamePong.KEY_COMPUTER_SCORE));
  }

  /** Set a pause display for Pong. */
  private void setPauseButton() {
    addGameObject(new Pause(50, 100));
  }

  /** Set up the ball and paddles for Pong.*/
  private void setUpLevel() {
    boolean isFast = session.getConfiguration().getOrDefault(Game.FAST_BALL, false);
    int speed = isFast ? 6 : 3;
    ball.setLocation(500, 400);
    ball.setVelocity(-speed - currentLevel, -speed - currentLevel);

    player.setLocation(300, 1600);
    player.setVelocity(2 + currentLevel, 0);
    player.onTouch(this, 300, 0);

    computer.setLocation(200, 200);
    computer.setVelocity(2 + currentLevel, 0);
  }
}
