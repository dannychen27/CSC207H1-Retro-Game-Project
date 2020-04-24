package com.project0607.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project0607.ProjectionApplication;
import com.project0607.R;
import com.project0607.game.GameBreakout;
import com.project0607.game.GamePong;
import com.project0607.game.GameShmup;
import com.project0607.util.TypedMap;

public class StatisticsActivity extends AppCompatActivity {

  /**
   * Set up a particular game when StatisticsActivity is instantiated.
   *
   * @param savedInstanceState The saved instance state.
   */
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_statistics);
  }

  @Override
  protected void onStart() {
    super.onStart();
    TextView statsView = findViewById(R.id.statsView);
    StringBuilder str = new StringBuilder();
    Resources resources = getResources();

    str.append(resources.getString(R.string.stats_pong));
    str.append("\n\n");
    appendPongStats(str);
    str.append("\n");

    str.append(resources.getString(R.string.stats_breakout));
    str.append("\n\n");
    appendBreakoutStats(str);
    str.append("\n");

    str.append(resources.getString(R.string.stats_shmup));
    str.append("\n\n");
    appendShmupStats(str);
    str.append("\n");

    statsView.setText(str);
  }

  /**
   * Append global statistics for Pong.
   *
   * @param str The string to append to.
   */
  private void appendPongStats(StringBuilder str) {
    TypedMap stats = getStatistics();

    int totalTouches = stats.getOrDefault(GamePong.KEY_TOTAL_TOUCHES, 0);
    appendStat(str, R.string.stat_pong_total_touches, totalTouches);

    int playerScore = stats.getOrDefault(GamePong.KEY_PLAYER_SCORE, 0);
    appendStat(str, R.string.stat_pong_player_score, playerScore);

    int computerScore = stats.getOrDefault(GamePong.KEY_COMPUTER_SCORE, 0);
    appendStat(str, R.string.stat_pong_computer_score, computerScore);

  }

  /**
   * Append global statistics for Breakout.
   *
   * @param str The string to append to.
   */
  private void appendBreakoutStats(StringBuilder str) {
    TypedMap stats = getStatistics();

    int bricksDestroyed = stats.getOrDefault(GameBreakout.KEY_BRICKS_DESTROYED, 0);
    appendStat(str, R.string.stat_breakout_bricks_destroyed, bricksDestroyed);
  }

  /**
   * Append global statistics for Shoot 'Em Up.
   *
   * @param str The string to append to.
   */
  private void appendShmupStats(StringBuilder str) {
    TypedMap stats = getStatistics();

    int enemiesKilled = stats.getOrDefault(GameShmup.KEY_ENEMIES_KILLED, 0);
    appendStat(str, R.string.stat_shmup_enemies_killed, enemiesKilled);
  }

  /**
   * Append a global statistic and its corresponding value.
   *
   * @param str The string to append to.
   * @param id The id of the statistic begin displayed.
   * @param value The corresponding value of the statistic being displayed.
   */
  private void appendStat(StringBuilder str, int id, Object value) {
    str.append(getResources().getString(id));
    str.append(": ");
    str.append(value);
    str.append("\n");
  }

  /** @return A TypedMap of statistics to display in the statistics menu. */
  private TypedMap getStatistics() {
    return ProjectionApplication.getGlobalSession(this).getStatistics();
  }
}
