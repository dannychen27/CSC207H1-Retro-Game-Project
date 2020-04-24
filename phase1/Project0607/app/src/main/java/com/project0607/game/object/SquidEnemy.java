package com.project0607.game.object;

import com.project0607.R;

public class SquidEnemy extends Enemy {
    /**
     * Create a new squid enemy object.
     *
     * @param x This squid's horizontal coordinate.
     * @param y This squid's vertical coordinate.
     */
    public SquidEnemy(int x, int y) {
        super(x, y, 4, 5);
        hard = true;
        destructible = true;
        life = 1;
    }

    /** @return The squid sprite. */
    @Override
    int getSprite() {
        return state ? R.drawable.sp_enemy_squid_1 : R.drawable.sp_enemy_squid_0;
    }
}
