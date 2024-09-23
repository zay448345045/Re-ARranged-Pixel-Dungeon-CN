package com.shatteredpixel.shatteredpixeldungeon.items;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AscensionChallenge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.HorseRiding;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Saddle extends Item {
    {
        image = ItemSpriteSheet.SADDLE;
        levelKnown = true;

        bones = false;
        unique = true;
    }

    @Override
    public int level() {
        int level = Dungeon.hero == null ? 0 : Dungeon.hero.lvl/5;
        return level;
    }

    @Override
    public int buffedLvl() {
        //level isn't affected by buffs/debuffs
        return level();
    }

    public static void kill(Mob enemy) {
        Hero hero = Dungeon.hero;
        Saddle saddle = hero.belongings.getItem(Saddle.class);
        HorseRiding.HorseAlly horse = null;
        for (Char ch : Actor.chars()) {
            if (ch instanceof HorseRiding.HorseAlly) {
                horse = (HorseRiding.HorseAlly)ch;
            }
        }
        if (saddle != null) {
            if (hero.buff(HorseRiding.class) != null) {
                hero.buff(HorseRiding.class).healHorse(2 * (2+saddle.buffedLvl()));
            } else if (horse != null) {
                horse.heal(2 * (2+saddle.buffedLvl()));
            } else if (hero.buff(HorseRiding.RidingCooldown.class) != null && (Dungeon.hero.lvl <= enemy.maxLvl + 2 || Dungeon.hero.buff(AscensionChallenge.class) != null)) {
                hero.buff(HorseRiding.RidingCooldown.class).kill();
            }
        }
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", 2 * (2+this.buffedLvl()));
    }
}
