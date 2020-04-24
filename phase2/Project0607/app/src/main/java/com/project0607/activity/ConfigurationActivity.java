package com.project0607.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.project0607.ProjectApplication;
import com.project0607.R;
import com.project0607.activity.game.Game;
import com.project0607.util.TypedKey;
import com.project0607.util.TypedMap;

import java.util.HashMap;
import java.util.Map;

/**
 * ConfigurationActivity displays checkboxes for various settings that the player can
 * enable/disable, such as having a fast ball or a fast paddle.
 */
public class ConfigurationActivity extends AppCompatActivity {

  @SuppressLint("UseSparseArrays")
  private static Map<Integer, TypedKey<Boolean>> settingsToFlags = new HashMap<>();

  // Above is a list of configuration settings for all of our games.
  static {
    settingsToFlags.put(R.id.fast_paddle, Game.FAST_PADDLE);
    settingsToFlags.put(R.id.fast_ball, Game.FAST_BALL);
    settingsToFlags.put(R.id.more_enemies, Game.MORE_ENEMIES);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_configuration);
    TypedMap config = ProjectApplication.getGlobalSession(this).getConfiguration();
    displayCheckboxes(config);
  }

  /**
   * Display checkboxes for all of our settings.
   *
   * @param config The configuration.
   */
  private void displayCheckboxes(TypedMap config) {
    for (Integer setting : settingsToFlags.keySet()) {
      displayCheckBox(config, setting, settingsToFlags.get(setting));
    }
  }

  /**
   * Set the configuration status for a particular setting to be either on or off.
   *
   * @param configStatus The desired setting's configuration status (on or off).
   * @param isOn Whether the setting is set to on or off.
   */
  private void setConfigurationStatus(TypedKey<Boolean> configStatus, boolean isOn) {
    ProjectApplication.getGlobalSession(this).getConfiguration().put(configStatus, isOn);
  }

  /**
   * Display a checkbox for the following setting, which are all by default unchecked.
   *
   * @param config The set of configurations settings.
   * @param thisSetting The desired setting.
   * @param configStatus The desired setting's configuration status (on or off).
   */
  private void displayCheckBox(TypedMap config, int thisSetting, TypedKey<Boolean> configStatus) {
    CheckBox checkbox = findViewById(thisSetting);
    checkbox.setChecked(config.getOrDefault(configStatus, false));
    checkbox.setOnCheckedChangeListener(
        (compoundButton, onOrOff) -> setConfigurationStatus(configStatus, onOrOff));
  }
}
