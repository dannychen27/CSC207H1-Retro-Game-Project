package com.project0607.game.object;

import com.project0607.activity.game.Game;

public class GameOverDisplay extends GameObject {

    /**
     * Create a new statistics display.
     *
     * @param x The horizontal coordinate of this statistics display.
     * @param y The vertical coordinate of this statistics display.
     */
    public GameOverDisplay(int x, int y) {
        super(x, y, 0, 0);
    }

    /**
     * Render the statistics display on screen.
     *
     * @param game The renderer.
     */
    @Override
    public void render(Game game) {
        game.getView().renderText(x, y, "GAME OVER");
    }
}
