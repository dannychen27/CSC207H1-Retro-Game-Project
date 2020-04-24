package com.project0607.game.object;

import com.project0607.R;
import com.project0607.game.Game;
import com.project0607.render.Renderer;

public class CrabEnemy extends Enemy {
    /**
     * Create a new crab enemy object.
     *
     * @param x This crab's horizontal coordinate.
     * @param y This crab's vertical coordinate.
     */
    public CrabEnemy(int x, int y) {
        super(x, y, 3, 4);
        hard = true;
        destructible = true;
        life = 2;
    }

    /** @return The crab's sprite. */
    @Override
    int getSprite() {
        return state ? R.drawable.sp_enemy_crab_1 : R.drawable.sp_enemy_crab_0;
    }
}
