package com.project0607.game.object;

import com.project0607.R;

public class UfoEnemy extends Enemy {
    /**
     * Create a new UFO enemy object.
     *
     * @param x This UFO's horizontal coordinate.
     * @param y This UFO's vertical coordinate.
     */
    public UfoEnemy(int x, int y) {
        super(x, y, 1, 2);
        hard = true;
        destructible = true;
        life = 10;
    }

    /** @return The UFO sprite. */
    @Override
    int getSprite() {
        return R.drawable.sp_enemy_ufo;
    }
}
