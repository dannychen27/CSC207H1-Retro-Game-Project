package com.project0607.activity;

import android.os.Bundle;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.project0607.ProjectionApplication;
import com.project0607.R;
import com.project0607.game.Game;
import com.project0607.util.TypedKey;
import com.project0607.util.TypedMap;

public class ConfigurationActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_configuration);
    TypedMap config = ProjectionApplication.getGlobalSession(this).getConfiguration();

    CheckBox fastPaddle = findViewById(R.id.fast_paddle);
    fastPaddle.setChecked(config.getOrDefault(Game.FAST_PADDLE, false));
    fastPaddle.setOnCheckedChangeListener(
      (compoundButton, b) -> setBooleanConfiguration(Game.FAST_PADDLE, b));

    CheckBox fastBall = findViewById(R.id.fast_ball);
    fastBall.setChecked(config.getOrDefault(Game.FAST_BALL, false));
    fastBall.setOnCheckedChangeListener(
      (compoundButton, b) -> setBooleanConfiguration(Game.FAST_BALL, b));

    CheckBox moreEnemies = findViewById(R.id.more_enemies);
    moreEnemies.setChecked(config.getOrDefault(Game.MORE_ENEMIES, false));
    moreEnemies.setOnCheckedChangeListener(
      (compoundButton, b) -> setBooleanConfiguration(Game.MORE_ENEMIES, b));
  }

  /**
   * Set the configure for a particular setting on or off.
   *
   * @param key The setting that we're interested in.
   * @param b Whether the setting is on or off.
   */
  private void setBooleanConfiguration(TypedKey<Boolean> key, boolean b) {
    ProjectionApplication.getGlobalSession(this).getConfiguration().put(key, b);
  }
}
