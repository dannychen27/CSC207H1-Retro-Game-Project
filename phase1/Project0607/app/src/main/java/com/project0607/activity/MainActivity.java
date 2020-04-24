package com.project0607.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.project0607.R;
import com.project0607.game.GameFactory;

public class MainActivity extends AppCompatActivity {

  /** The game that this player has selected. */
  public static final String GAME_CHOICE = "com.project0607.GAME_CHOICE";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  /**
   * Open Pong.
   *
   * @param view The view.
   */
  public void enterPong(View view) {
    Intent intent = new Intent(this, BasicGameActivity.class);
    intent.putExtra(GAME_CHOICE, GameFactory.GameType.PONG);
    startActivity(intent);
  }

  /**
   * Open Breakout.
   *
   * @param view The view.
   */
  public void enterBreakout(View view) {
    Intent intent = new Intent(this, BasicGameActivity.class);
    intent.putExtra(GAME_CHOICE, GameFactory.GameType.BREAKOUT);
    startActivity(intent);
  }

  /**
   * Open Shmup.
   *
   * @param view The view.
   */
  public void enterShmup(View view) {
    Intent intent = new Intent(this, BasicGameActivity.class);
    intent.putExtra(GAME_CHOICE, GameFactory.GameType.SHMUP);
    startActivity(intent);
  }

  /**
   * Open Game Statistics.
   *
   * @param view The view.
   */
  public void enterGameStatistics(View view) {
    Intent intent = new Intent(this, StatisticsActivity.class);
    startActivity(intent);
  }

  /**
   * Open Configuration.
   *
   * @param view The view.
   */
  public void enterConfiguration(View view) {
    Intent intent = new Intent(this, ConfigurationActivity.class);
    startActivity(intent);
  }
}
