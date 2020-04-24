package com.project0607.game.ui;

import com.project0607.R;
import com.project0607.game.Game;
import com.project0607.util.TypedKey;

public class LifeDisplay extends StatisticsDisplay {

    /**
     * Construct a new life display.
     *
     * @param x The horizontal coordinate of this life display.
     * @param y The vertical coordinate of this life display.
     */
    public LifeDisplay(int x, int y, TypedKey<Integer> livesKey) {
        super(x, y, livesKey);
    }

    /**
     * Render the number of lives on screen, in the form
     *
     *      HEART_SPRITE x <lives>.
     *
     * @param game The game this score display is part of.
     */
    @Override
    public void render(Game game) {
        game.getRenderer().renderDrawable(x - 200, y, R.drawable.sp_heart);
        game.getRenderer().renderText(x - 140, y, "x");
        super.render(game);
    }
}