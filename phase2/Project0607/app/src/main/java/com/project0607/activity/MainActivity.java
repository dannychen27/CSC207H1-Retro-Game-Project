package com.project0607.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.project0607.R;
import com.project0607.activity.game.GameActivity;
import com.project0607.game.GameFactory;

/**
 * MainActivity displays buttons that allow the player to either:
 * - play one of three games: Pong, BrickBreaker, or Shoot 'Em Up.
 * - view game statistics for all games
 * - adjust game settings for each game.
 */
public class MainActivity extends AppCompatActivity {

  /** The game that this player has selected. */
  public static final String GAME_CHOICE = "com.project0607.GAME_CHOICE";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  // All the "enter" methods you see below in this class must contain exactly one
  // parameter (the view parameter) in order to work properly when used in
  // activity_main.xml.

  /**
   * Open Pong.
   *
   * @param view The view.
   */
  public void enterPong(View view) {
    enter(view, GameFactory.GameType.PONG);
  }

  /**
   * Open Breakout.
   *
   * @param view The view.
   */
  public void enterBreakout(View view) {
    enter(view, GameFactory.GameType.BREAKOUT);
  }

  /**
   * Open Shmup.
   *
   * @param view The view.
   */
  public void enterShmup(View view) {
    enter(view, GameFactory.GameType.SHMUP);
  }

  /**
   * Open Game Statistics.
   *
   * @param view The view.
   */
  public void enterGameStatistics(View view) {
    enter(view, StatisticsActivity.class);
  }

  /**
   * Open Configuration.
   *
   * @param view The view.
   */
  public void enterConfiguration(View view) {
    enter(view, ConfigurationActivity.class);
  }

  /**
   * Open the specified game activity.
   *
   * @param view The view.
   * @param gameType The game the player wants to play.
   *
   * Precondition: gameType != StatisticsActivity or ConfigurationActivity.
   */
  private void enter(View view, GameFactory.GameType gameType) {
    Intent intent = new Intent(this, GameActivity.class);
    intent.putExtra(GAME_CHOICE, gameType);
    startActivity(intent);
  }

  /**
   * Open a generic non-game activity.
   *
   * @param view The view.
   * @param nonGameActivity The new activity you want to run.
   *
   * Precondition: nonGameActivity != GameActivity.
   */
  private void enter(View view, Class<? extends AppCompatActivity> nonGameActivity) {
    Intent intent = new Intent(this, nonGameActivity);
    startActivity(intent);
  }
}
