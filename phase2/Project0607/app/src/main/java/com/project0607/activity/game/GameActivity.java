package com.project0607.activity.game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.project0607.GlobalSession;
import com.project0607.ProjectApplication;
import com.project0607.R;
import com.project0607.activity.MainActivity;
import com.project0607.game.GameFactory;

import java.util.Timer;
import java.util.TimerTask;

/**
 * GameActivity keeps track of the view and game objects on screen
 * for a particular game.
 */
public class GameActivity extends AppCompatActivity implements View.OnTouchListener, GameView {

  /** An array that maps digits to sprites. */
  private static final int[] digitsToSprites =
      new int[] {
        R.drawable.sp_num_zero, R.drawable.sp_num_one,
        R.drawable.sp_num_two, R.drawable.sp_num_three,
        R.drawable.sp_num_four, R.drawable.sp_num_five,
        R.drawable.sp_num_six, R.drawable.sp_num_seven,
        R.drawable.sp_num_eight, R.drawable.sp_num_nine
      };

  /** Objects that are visible on the Android screen. */
  private ConstraintLayout viewObjects;

  /** The current game session. */
  private Game game;

  /** The game's clock. */
  private Timer updateOnIntervalTimer;

  /** Whether the game is paused or not. */
  private boolean isPaused;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_basic_game);
    setView();
    setViewObjects();
  }

  @Override
  protected void onStart() {
    super.onStart();
    viewObjects.post(
        () -> {
          game = chooseGame();
          updateGameObjects();
        });
  }

  @Override
  public int getWidth() {
    return viewObjects.getWidth();
  }

  @Override
  public int getHeight() {
    return viewObjects.getHeight();
  }

  @Override
  public int getDrawableWidth(int id) {
    //noinspection ConstantConditions
    return ContextCompat.getDrawable(this, id).getIntrinsicWidth();
  }

  @Override
  public int getDrawableHeight(int id) {
    //noinspection ConstantConditions
    return ContextCompat.getDrawable(this, id).getIntrinsicHeight();
  }

  @Override
  public void renderDrawable(int x, int y, int id) {
    ImageView imageView = new ImageView(this);
    viewObjects.addView(imageView);
    Drawable drawable = ContextCompat.getDrawable(this, id);
    imageView.setImageDrawable(drawable);
    imageView.setX(x);
    imageView.setY(y);
  }

  /**
   * Display a digit on screen.
   *
   * @param x The horizontal position of this text sprite.
   * @param y The vertical position of this text sprite.
   * @param d The digit to render.
   *
   * Precondition: d is a digit between 0 and 9.
   */
  public void renderDigit(int x, int y, int d) {
    renderDrawable(x, y, digitsToSprites[d]);
  }

  @Override
  public void renderText(int x, int y, String text) {
    TextView textView = new TextView(this);
    textView.setTextColor(Color.WHITE);
    viewObjects.addView(textView);
    textView.setText(text);
    textView.setX(x);
    textView.setY(y);
  }

  /**
   * Update this player's position based on touch location.
   *
   * @param view the View that was touched.
   * @param event the MotionEvent used for touch location.
   * @return true for continuous tracking. (and false for on click tracking)
   */
  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouch(View view, MotionEvent event) {
    int x = (int) event.getX();
    int y = (int) event.getY();
    game.onTouch(x, y);
    return true;
  }

  @Override
  protected void onPause() {
    super.onPause();
    game.saveStatistics();
    ((ProjectApplication) getApplicationContext()).onTerminate();
    updateOnIntervalTimer.cancel();
    updateOnIntervalTimer = null;
  }

  /**
   * Pause or resume the game.
   *
   * @param view The view.
   */
  public void pauseGame(View view) {
    if (!isPaused) {
      isPaused = true;
      updateOnIntervalTimer.cancel();
      updateOnIntervalTimer = null;
    } else {
      isPaused = false;
      updateOnIntervalTimer = new Timer();
      updateOnIntervalTimer.scheduleAtFixedRate(
          new TimerTask() {
            public void run() {
              updateGame();
              updateView();
            }
          },
          500,
          10);
    }
  }

  /** Initialize the game's view objects, and then adds them to ContentView to make them visible. */
  private void setView() {
    View controlPane = findViewById(R.id.view);
    controlPane.setOnTouchListener(this);
  }

  /** Initialize view objects for a particular game, and create a black background. */
  private void setViewObjects() {
    viewObjects = new ConstraintLayout(this);
    viewObjects.setBackgroundColor(0xFF000000);
    ConstraintLayout.LayoutParams constraintLayoutParams =
        new ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
    addContentView(viewObjects, constraintLayoutParams);
  }

  /** @return The game that the player would like to play. */
  private Game chooseGame() {
    GlobalSession session = ProjectApplication.getGlobalSession(this);
    Intent intent = getIntent();
    GameFactory.GameType gameChoice =
        (GameFactory.GameType) intent.getSerializableExtra(MainActivity.GAME_CHOICE);
    assert gameChoice != null;
    return new GameFactory().createGame(gameChoice, session, this);
  }

  /** Schedule updating all gameObjects within this game. */
  private void updateGameObjects() {
    updateOnIntervalTimer = new Timer();
    updateOnIntervalTimer.scheduleAtFixedRate(
        new TimerTask() {
          public void run() {
            updateGame();
            updateView();
          }
        },
        500,
        10);
  }

  /** Update all game objects in this game session. */
  private void updateGame() {
    game.update();
  }

  /** Update all view objects in this game session. */
  private void updateView() {
    runOnUiThread(
        () -> {
          viewObjects.removeAllViews();
          game.render();
        });
  }
}
