package com.project0607.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.project0607.GlobalSession;
import com.project0607.ProjectionApplication;
import com.project0607.R;
import com.project0607.game.Game;
import com.project0607.game.GameFactory;
import com.project0607.render.AndroidViewRenderer;
import com.project0607.render.Renderer;
import java.util.Timer;
import java.util.TimerTask;

public class BasicGameActivity extends AppCompatActivity implements View.OnTouchListener {

  /** Objects that are visible on the Android screen. */
  private ConstraintLayout viewObjects;

  /** The current game session. */
  private Game game;

  /** The game's clock. */
  private Timer updateOnIntervalTimer;

  private boolean isPause = false;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_basic_game);

    // sets the view (the big thing covering the entire screen from the xml) as our listener.
    // it will call onTouch whenever touched.
    View controlPane = findViewById(R.id.view);
    controlPane.setOnTouchListener(this);

    // initialize our viewObjects, add them to ContentView so they can be seen
    // creates a black background.
    this.viewObjects = new ConstraintLayout(this);
    viewObjects.setBackgroundColor(0xFF000000);
    ConstraintLayout.LayoutParams constraintLayoutParams =
        new ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
    addContentView(viewObjects, constraintLayoutParams);

    Button b = new Button(this);






  }

  @Override
  protected void onStart() {
    super.onStart();

    // Find what game we should play.
    game = this.chooseGame();

    // scheduling updating all gameObjects within this.game
    updateOnIntervalTimer = new Timer();
    updateOnIntervalTimer.scheduleAtFixedRate(
        new TimerTask() {
          public void run() {
            updateGame();
            updateView();
          }
        },
        // How long before to start calling the TimerTask (in milliseconds)
        500,
        // Amount of time between each execution (in milliseconds)
        10
    );
  }

  public void pauseGame(View view) {
    if (isPause == false) {
      isPause = true;
      updateOnIntervalTimer.cancel();
      updateOnIntervalTimer = null;
      } else {

      isPause = false;
      updateOnIntervalTimer = new Timer();
      updateOnIntervalTimer.scheduleAtFixedRate(
              new TimerTask() {
                public void run() {
                  updateGame();
                  updateView();
                }
              },
              // How long before to start calling the TimerTask (in milliseconds)
              500,
              // Amount of time between each execution (in milliseconds)
              10
      );
      }
    }

  /**
   * @return The game that the player would like to play.
   */
  private Game chooseGame() {
    GlobalSession session = ProjectionApplication.getGlobalSession(this);
    ProjectionApplication.setFileContext(this);
    Renderer renderer = new AndroidViewRenderer(this, viewObjects);
    Intent intent = getIntent();
    GameFactory.GameType gameChoice =
      (GameFactory.GameType) intent.getSerializableExtra(MainActivity.GAME_CHOICE);
    return new GameFactory().createGame(gameChoice, session, renderer);
  }

  @Override
  protected void onPause() {
    super.onPause();
    game.saveStatistics();
    updateOnIntervalTimer.cancel();
    updateOnIntervalTimer = null;
  }

  /** Update all view objects in this game session. */
  private void updateView() {
    runOnUiThread(
      () -> {
        viewObjects.removeAllViews();
        game.renderGameObjects();
      }
    );
  }

  /** Update all game objects in this game session. */
  private void updateGame() {
    game.updateGameObjects();
  }

  /**
   * Update this player's position based on touch location.
   *
   * @param view the View that was touched.
   * @param event the MotionEvent used for touch location.
   * @return true for continuous tracking.
   */
  @Override
  public boolean onTouch(View view, MotionEvent event) {
    // finding the coordinates of our touch event
    int x = (int) event.getX();
    int y = (int) event.getY();

    // Call the relevant method in Game
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      game.onTouch(x, y);
    } else if (event.getAction() == MotionEvent.ACTION_UP) {
      game.onTouchRelease(x, y);
    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
      game.onTouchMove(x, y);
    }

    // true allows continuous tracking, false would be only on click
    return true;
  }
}
