package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.SMG;

import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
public class SMG extends Gun {

    {
        max_round = 4;
        round = max_round;
        shotPerShoot = 3;
        shootingAccuracy = 1.2f;
    }

    @Override
    public int baseBulletMax(int lvl) {
        return 2 * (tier+1) +
                Math.round(0.5f * lvl * (tier+1)); //2강 당 2/3/4/5/6 증가
    }

    @Override
    public Bullet knockBullet(){
        return new SMGBullet();
    }

    public class SMGBullet extends Bullet {
        {
            image = ItemSpriteSheet.TRIPLE_BULLET;
        }
    }

}
