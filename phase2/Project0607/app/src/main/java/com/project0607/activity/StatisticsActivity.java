package com.project0607.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project0607.ProjectApplication;
import com.project0607.R;
import com.project0607.game.GameBreakout;
import com.project0607.game.GamePong;
import com.project0607.game.GameShootEmUp;
import com.project0607.util.TypedMap;

/**
 * StatisticsActivity does two things:
 * - keeps track of both local statistics for each game as it plays, AND
 * - stores these local statistics to the global statistics at the end of a game session
 * (either because the player stops playing or the player receives a "Game Over").
 */
public class StatisticsActivity extends AppCompatActivity {

  /** A list of games whose statistics should be displayed. */
  private int[] gameStats = {R.string.stats_pong, R.string.stats_breakout, R.string.stats_shmup};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_statistics);
  }

  @Override
  protected void onStart() {
    super.onStart();
    TextView statsView = findViewById(R.id.statsView);
    StringBuilder statsString = new StringBuilder();
    Resources resources = getResources();
    displayAllGameStats(statsView, statsString, resources);
  }

  /**
   * Display all game statistics.
   *
   * @param statsView The statistics view.
   * @param statsString A string representation of the statistics.
   * @param resources The resources.
   */
  private void displayAllGameStats(
      TextView statsView, StringBuilder statsString, Resources resources) {
    for (int gameStat : gameStats) {
      displayGameStats(gameStat, statsString, resources);
    }
    statsView.setText(statsString);
  }

  /**
   * Display game statistics for this particular game.
   *
   * @param statID The id of the statistic begin displayed.
   * @param statsString A string representation of the statistics.
   * @param resources The resources.
   */
  private void displayGameStats(int statID, StringBuilder statsString, Resources resources) {
    statsString.append(resources.getString(statID));
    statsString.append("\n\n");
    appendGameStats(statID, statsString);
    statsString.append("\n\n\n\n");
  }

  /**
   * Append every game's global statistics to the statistics string.
   *
   * @param statID The id of the statistic begin displayed.
   * @param statsString A string representation of the statistics.
   */
  private void appendGameStats(int statID, StringBuilder statsString) {
    switch (statID) {
      case R.string.stats_pong:
        appendPongStats(statsString);
        break;
      case R.string.stats_breakout:
        appendBreakoutStats(statsString);
        break;
      case R.string.stats_shmup:
        appendShmupStats(statsString);
        break;
      default:
        throw new IllegalArgumentException("Game does not exist!");
    }
  }

  /**
   * Append global statistics for Pong to the statistics string.
   *
   * @param statsString The statistics string to append to.
   */
  private void appendPongStats(StringBuilder statsString) {
    TypedMap stats = getStatistics();

    int totalTouches = stats.getOrDefault(GamePong.KEY_TOTAL_TOUCHES, 0);
    appendStat(statsString, R.string.stat_pong_total_touches, totalTouches);

    int playerScore = stats.getOrDefault(GamePong.KEY_PLAYER_SCORE, 0);
    appendStat(statsString, R.string.stat_pong_player_score, playerScore);

    int computerScore = stats.getOrDefault(GamePong.KEY_COMPUTER_SCORE, 0);
    appendStat(statsString, R.string.stat_pong_computer_score, computerScore);
  }

  /**
   * Append global statistics for Breakout to the statistics string.
   *
   * @param statsString The statistics string to append to.
   */
  private void appendBreakoutStats(StringBuilder statsString) {
    TypedMap stats = getStatistics();

    int highestLevelReached = stats.getOrDefault(GameBreakout.KEY_HIGHEST_LEVEL_REACHED, 1);
    appendStat(statsString, R.string.stat_breakout_highest_level_reached, highestLevelReached);

    int bricksDestroyed = stats.getOrDefault(GameBreakout.KEY_BRICKS_DESTROYED, 0);
    appendStat(statsString, R.string.stat_breakout_bricks_destroyed, bricksDestroyed);
  }

  /**
   * Append global statistics for Shoot 'Em Up to the statistics string.
   *
   * @param statsString The statistics string to append to.
   */
  private void appendShmupStats(StringBuilder statsString) {
    TypedMap stats = getStatistics();

    int crabEnemiesKilled = stats.getOrDefault(GameShootEmUp.KEY_CRAB_ENEMIES_KILLED, 0);
    appendStat(statsString, R.string.stat_shmup_crab_enemies_killed, crabEnemiesKilled);

    int octopusEnemiesKilled = stats.getOrDefault(GameShootEmUp.KEY_OCTOPUS_ENEMIES_KILLED, 0);
    appendStat(statsString, R.string.stat_shmup_octopus_enemies_killed, octopusEnemiesKilled);

    int squidEnemiesKilled = stats.getOrDefault(GameShootEmUp.KEY_SQUID_ENEMIES_KILLED, 0);
    appendStat(statsString, R.string.stat_shmup_squid_enemies_killed, squidEnemiesKilled);

    int ufoEnemiesKilled = stats.getOrDefault(GameShootEmUp.KEY_UFO_ENEMIES_KILLED, 0);
    appendStat(statsString, R.string.stat_shmup_ufo_enemies_killed, ufoEnemiesKilled);

    int totalEnemiesKilled = stats.getOrDefault(GameShootEmUp.KEY_TOTAL_ENEMIES_KILLED, 0);
    appendStat(statsString, R.string.stat_shmup_total_enemies_killed, totalEnemiesKilled);

    int highestLevelReached = stats.getOrDefault(GameShootEmUp.KEY_HIGHEST_LEVEL_REACHED, 1);
    appendStat(statsString, R.string.stat_shmup_highest_level_reached, highestLevelReached);
  }

  /**
   * Append a global statistic for a particular game and its corresponding value to the statistics
   * string.
   *
   * @param statsString The statistics string to append to.
   * @param statID The id of the statistic begin displayed.
   * @param statValue The corresponding value of the statistic being displayed.
   */
  private void appendStat(StringBuilder statsString, int statID, Object statValue) {
    String statistic = getResources().getString(statID) + ": " + statValue + "\n";
    statsString.append(statistic);
  }

  /** @return A TypedMap of statistics to display in the statistics menu. */
  private TypedMap getStatistics() {
    return ProjectApplication.getGlobalSession(this).getStatistics();
  }
}
