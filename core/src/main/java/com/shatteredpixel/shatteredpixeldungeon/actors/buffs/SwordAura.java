package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.Sheath;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SwordAura extends Buff implements ActionIndicator.Action {
    {
        type = buffType.NEUTRAL;
        announced = false;
    }

    private int damage = 0;

    public int damageUse() {
        int use = this.damage;
        use = Math.round(use/(float)(1+hero.pointsInTalent(Talent.DIVIDED_POWER)));
        return use;
    }

    private float storeMulti() {
        float multi = 0.2f;
        multi *= 1+hero.pointsInTalent(Talent.MIND_FOCUSING)/3f;
        return multi;
    }

    private int maxDamage() {
        int maxDamage = 60;
        maxDamage += 30 * hero.pointsInTalent(Talent.STORED_POWER);
        return maxDamage;
    }

    public void hit(int damage) {
        this.damage += Math.round(damage * storeMulti());
        this.damage = Math.min(this.damage, maxDamage());
        ActionIndicator.setAction(this);
        if (this.damage <= 0) detach();
    }

    public void use() {
        this.use(0);
    }

    public void use(int recoverAmt) {
        this.damage -= damageUse() - recoverAmt;
        if (damage <= 0) {
            detach();
            ActionIndicator.clearAction();
        }
    }

    @Override
    public void detach() {
        super.detach();
        ActionIndicator.clearAction();
    }

    private static final String DAMAGE = "damage";
    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put( DAMAGE, damage );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        ActionIndicator.setAction(this);
        damage = bundle.getInt( DAMAGE );
    }

    @Override
    public int icon() {
        return BuffIndicator.AURA;
    }

    @Override
    public float iconFadePercent(){
        return Math.max(0, (maxDamage()-damage)/(float)maxDamage());
    }

    @Override
    public String iconTextDisplay(){
        return Integer.toString(damage);
    }

    @Override
    public String name() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc(){
        return Messages.get(this, "desc", damage, maxDamage(), damageUse());
    }

    @Override
    public String actionName() {
        return Messages.get(this, "action");
    }

    @Override
    public int actionIcon() {
        return HeroIcon.SWORD_AURA;
    }

    @Override
    public int indicatorColor() {
        return 0xFF2A00;
    }

    @Override
    public void doAction() {
        GameScene.selectCell(shooter);
    }

    public Aura knockAura(){
        return new Aura();
    }

    public class Aura extends MissileWeapon {

        {
            image = ItemSpriteSheet.SWORD_AURA;
            hitSound = Assets.Sounds.HIT_SLASH;
        }

        @Override
        public float accuracyFactor(Char owner, Char target) {
            float accFactor = super.accuracyFactor(owner, target);

            accFactor *= 1.5f;

            return accFactor;
        }

        @Override
        public int max() {
            return SwordAura.this.damageUse();
        }

        @Override
        public int damageRoll(Char owner) {
            int damage = SwordAura.this.damageUse();
            if (hero.hasTalent(Talent.WIND_BLAST)) {
                damage += 5*hero.pointsInTalent(Talent.WIND_BLAST);
            }
            return damage;
        }

        @Override
        public int STRReq(int lvl) {
            return hero.STR();
        } //no exceed str damage bonus

        @Override
        public int proc(Char attacker, Char defender, int damage) {
            int dmg = super.proc(attacker, defender, damage);
            if (Random.Float() < hero.pointsInTalent(Talent.ARCANE_POWER)/3f && hero.belongings.weapon != null) {
                dmg = hero.belongings.weapon.proc(attacker, defender, dmg);
            }
            return dmg;
        }

        @Override
        protected void onThrow( int cell ) {
            int hitChar = 0;
            if (cell != hero.pos) {
                Ballistica aim = new Ballistica(hero.pos, cell, Ballistica.DASH);
                ArrayList<Char> chars = new ArrayList<>();
                for (int c : aim.subPath(1, aim.dist)) {
                    Char ch;
                    if ((ch = Actor.findChar( c )) != null) {
                        chars.add( ch );
                    }
                }
                for (Char ch : chars) {
                    if (curUser.shoot(ch, this)) {
                        hitChar++;
                    }
                }
            }
            if (hero.hasTalent(Talent.ENERGY_COLLECT)) {
                SwordAura.this.use(hitChar * Aura.this.damageRoll(hero)/(10-2*hero.pointsInTalent(Talent.ENERGY_COLLECT)));
            } else {
                SwordAura.this.use();
            }

            Invisibility.dispel();
            if (hero.buff(Sheath.Sheathing.class) != null) {
                hero.buff(Sheath.Sheathing.class).detach();
            }
        }

        @Override
        public void throwSound() {
            Sample.INSTANCE.play( Assets.Sounds.MISS );
        }

        @Override
        public void cast(final Hero user, final int dst) {
            super.cast(user, dst);
        }
    }

    private CellSelector.Listener shooter = new CellSelector.Listener() {
        @Override
        public void onSelect( Integer target ) {
            if (target != null) {
                if (target == hero.pos) {
                    GLog.w(Messages.get(this, "cannot_hero"));
                } else {
                    knockAura().cast(hero, target);
                }
            }
        }
        @Override
        public String prompt() {
            return Messages.get(SpiritBow.class, "prompt");
        }
    };
}
