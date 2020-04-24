package com.project0607.game.ui;

import com.project0607.game.Game;
import com.project0607.util.TypedKey;

public class StatisticsDisplay extends UIElement {

    /** The statistic to be displayed. */
    private final TypedKey<Integer> statsKey;

    /**
     * Construct a new statistics display.
     *
     * @param x The horizontal coordinate of this life display.
     * @param y The vertical coordinate of this life display.
     * @param statsKey The statistic to be displayed.
     */
    public StatisticsDisplay(int x, int y, TypedKey<Integer> statsKey) {
        super(x, y);
        this.statsKey = statsKey;
    }

    /**
     * Render the statistics display on screen.
     *
     * @param game The renderer.
     */
    @Override
    public void render(Game game) {
        int stats = game.getStatistics().getOrDefault(statsKey, 0);
        renderNumbers(game, stats);
    }

    /**
     * Render the number of lives on screen, in the form
     *
     *      HEART_SPRITE x <lives>.
     *
     * @param game The game this score display is part of.
     * @param stats The statistic to display.
     */
    private void renderNumbers(Game game, int stats) {
        game.getRenderer().renderDigit(x - 100, y, (stats / 100) % 10);
        game.getRenderer().renderDigit(x - 50, y, (stats / 10) % 10);
        game.getRenderer().renderDigit(x, y, stats % 10);
    }
}
