package com.shatteredpixel.shatteredpixeldungeon.items.changer;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Belongings;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Transmuting;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.AR_T6;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.AssassinsSpear;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.BeamSaber;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.ChainFlail;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.ChainWhip;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.DualGreatSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.ForceGlove;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.GL_T6;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.HG_T6;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.HolySword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.HugeSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.Lance;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.LanceNShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.MeisterHammer;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.ObsidianShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.RL_T6;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.SR_T6;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.SharpKatana;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.SpearNShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.TacticalShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.TrueRunicBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.UnformedBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.UnholyBible;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class BluePrint extends Item {

    private static final String AC_USE		= "USE";

    {
        image = ItemSpriteSheet.BLUEPRINT;
        defaultAction = AC_USE;
        stackable = false;
        levelKnown = true;

        unique = true;
        bones = false;
    }

    private MeleeWeapon newWeapon;

    public BluePrint(MeleeWeapon wep) {
        this.newWeapon = wep;
    }

    public BluePrint() {}

    private static final String NEW_WEAPON	= "newWeapon";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( NEW_WEAPON, newWeapon );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        newWeapon = (MeleeWeapon) bundle.get( NEW_WEAPON );
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_USE);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals(AC_USE)) {
            GameScene.selectItem( itemSelector );
        }
    }

    private Item changeItem( Item item ){
        if (item instanceof MeleeWeapon) {
            return changeWeapon((MeleeWeapon) item);
        } else {
            return null;
        }
    }

    private MeleeWeapon changeWeapon(MeleeWeapon wep) {
        MeleeWeapon result = this.newWeapon;

        result.level(0);
        result.quantity(1);
        int level = wep.trueLevel();
        if (level > 0) {
            result.upgrade( level );
        } else if (level < 0) {
            result.degrade( -level );
        }

        if (wep instanceof Gun && result instanceof Gun) {  //재료와 결과물 모두 총기일 경우 총기 개조 효과가 유지됨
            ((Gun) result).barrelMod = ((Gun) wep).barrelMod;
            ((Gun) result).magazineMod = ((Gun) wep).magazineMod;
            ((Gun) result).bulletMod = ((Gun) wep).bulletMod;
            ((Gun) result).weightMod = ((Gun) wep).weightMod;
            ((Gun) result).attachMod = ((Gun) wep).attachMod;
            ((Gun) result).enchantMod = ((Gun) wep).enchantMod;
            ((Gun) result).inscribeMod = ((Gun) wep).inscribeMod;
        }

        result.enchantment = wep.enchantment;
        result.curseInfusionBonus = wep.curseInfusionBonus;
        result.masteryPotionBonus = wep.masteryPotionBonus;
        result.levelKnown = wep.levelKnown;
        result.cursedKnown = wep.cursedKnown;
        result.cursed = wep.cursed;
        result.augment = wep.augment;
        result.enchantHardened = wep.enchantHardened;

        return result;

    }
    
    private String inventoryTitle(){
        return Messages.get(this, "inv_title");
    }

    @Override
    public String desc() {
        String desc = super.desc();
        if (this.newWeapon != null) {
            desc += "\n\n" + Messages.get(this, "item_desc",
                    newWeapon.tier,
                    newWeapon.trueName(),
                    Math.min(100, 100-20*(newWeapon.tier-1)+10*this.level()),
                    Math.min(100, 100-20*(newWeapon.tier-2)+10*this.level()),
                    Math.min(100, 100-20*(newWeapon.tier-3)+10*this.level()),
                    Math.min(100, 100-20*(newWeapon.tier-4)+10*this.level()),
                    Math.min(100, 100-20*(newWeapon.tier-5)+10*this.level()));
        }

        return desc;
    }

    protected void onItemSelected(Item item) {
        Item result = null;

        float chance = 1-0.2f*(((MeleeWeapon)changeItem(item)).tier-((MeleeWeapon)item).tier)+0.1f*this.level();

        if (Random.Float() < chance || DeviceCompat.isDebug()) {
            result = changeItem(item);
        }

        if (result == null){
            //This shouldn't ever trigger
            GLog.n( Messages.get(this, "nothing") );
        } else {
            if (result != item) {
                int slot = Dungeon.quickslot.getSlot(item);
                if (item.isEquipped(Dungeon.hero)) {
                    item.cursed = false; //to allow it to be unequipped
                    if (Dungeon.hero.belongings.secondWep() == item){
                        ((EquipableItem) item).doUnequip(Dungeon.hero, false);
                        ((KindOfWeapon) result).equipSecondary(Dungeon.hero);
                    } else {
                        ((EquipableItem) item).doUnequip(Dungeon.hero, false);
                        ((EquipableItem) result).doEquip(Dungeon.hero);
                    }
                    Dungeon.hero.spend(-Dungeon.hero.cooldown()); //cancel equip/unequip time
                } else {
                    item.detach(Dungeon.hero.belongings.backpack);
                    if (!result.collect()) {
                        Dungeon.level.drop(result, curUser.pos).sprite.drop();
                    }
                }
                if (slot != -1
                        && result.defaultAction() != null
                        && !Dungeon.quickslot.isNonePlaceholder(slot)
                        && Dungeon.hero.belongings.contains(result)){
                    Dungeon.quickslot.setSlot(slot, result);
                }
            }
            if (result.isIdentified()){
                Catalog.setSeen(result.getClass());
            }
            Sample.INSTANCE.play(Assets.Sounds.READ);
            Dungeon.hero.spendAndNext(Actor.TICK);
            Transmuting.show(curUser, item, result);
            curUser.sprite.emitter().start(Speck.factory(Speck.CHANGE), 0.2f, 10);
            GLog.p( Messages.get(this, "morph") );
        }
        detach(Dungeon.hero.belongings.backpack);
    }

    @Override
    public boolean isUpgradable() {
        return true;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public int value() {
        return -1;
    }

    protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

        @Override
        public String textPrompt() {
            return inventoryTitle();
        }

        @Override
        public Class<? extends Bag> preferredBag() {
            return Belongings.Backpack.class;
        }

        @Override
        public boolean itemSelectable(Item item) {
            return item instanceof MeleeWeapon;
        }

        @Override
        public void onSelect( Item item ) {

            //FIXME this safety check shouldn't be necessary
            //it would be better to eliminate the curItem static variable.
            if (!(curItem instanceof BluePrint)){
                return;
            }

            if (item != null && itemSelectable(item)) {
                onItemSelected(item);
            }
        }
    };

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe {

        public static final ArrayList<ArrayList<Class<?extends Item>>> validIngredients = new ArrayList<>(); //연금술 무기들의 wepaonRecipe() 배열을 이곳에 저장한다.
        static {
            validIngredients.add( new TrueRunicBlade().weaponRecipe() );
            validIngredients.add( new Lance().weaponRecipe() );
            validIngredients.add( new ObsidianShield().weaponRecipe() );
            validIngredients.add( new ChainWhip().weaponRecipe() );
            validIngredients.add( new LanceNShield().weaponRecipe() );
            validIngredients.add( new ChainFlail().weaponRecipe() );
            validIngredients.add( new UnformedBlade().weaponRecipe() );
            validIngredients.add( new AR_T6().weaponRecipe() );
            validIngredients.add( new SR_T6().weaponRecipe() );
            validIngredients.add( new HG_T6().weaponRecipe() );
            validIngredients.add( new SpearNShield().weaponRecipe() );
            validIngredients.add( new TacticalShield().weaponRecipe() );
            validIngredients.add( new AssassinsSpear().weaponRecipe() );
            validIngredients.add( new ForceGlove().weaponRecipe() );
            validIngredients.add( new UnholyBible().weaponRecipe() );
            validIngredients.add( new HugeSword().weaponRecipe() );
            validIngredients.add( new MeisterHammer().weaponRecipe() );
            validIngredients.add( new BeamSaber().weaponRecipe() );
            validIngredients.add( new HolySword().weaponRecipe() );
            validIngredients.add( new DualGreatSword().weaponRecipe() );
            validIngredients.add( new SharpKatana().weaponRecipe() );
            validIngredients.add( new GL_T6().weaponRecipe() );
            validIngredients.add( new RL_T6().weaponRecipe() );
        }

        public static final LinkedHashMap<Integer, Class<?extends MeleeWeapon>> indexToOutput = new LinkedHashMap<>(); //validIngredients 배열의 인덱스를 넣으면 근접 무기를 반환한다.
        static {
            indexToOutput.put( 0, TrueRunicBlade.class );
            indexToOutput.put( 1, Lance.class );
            indexToOutput.put( 2, ObsidianShield.class );
            indexToOutput.put( 3, ChainWhip.class );
            indexToOutput.put( 4, LanceNShield.class );
            indexToOutput.put( 5, ChainFlail.class );
            indexToOutput.put( 6, UnformedBlade.class );
            indexToOutput.put( 7, AR_T6.class );
            indexToOutput.put( 8, SR_T6.class );
            indexToOutput.put( 9, HG_T6.class );
            indexToOutput.put( 10, SpearNShield.class );
            indexToOutput.put( 11, TacticalShield.class);
            indexToOutput.put( 12, AssassinsSpear.class);
            indexToOutput.put( 13, ForceGlove.class );
            indexToOutput.put( 14, UnholyBible.class );
            indexToOutput.put( 15, HugeSword.class );
            indexToOutput.put( 16, MeisterHammer.class );
            indexToOutput.put( 17, BeamSaber.class );
            indexToOutput.put( 18, HolySword.class );
            indexToOutput.put( 19, DualGreatSword.class );
            indexToOutput.put( 20, SharpKatana.class );
            indexToOutput.put( 21, GL_T6.class );
            indexToOutput.put( 22, RL_T6.class );
        }

        public static final LinkedHashMap<Integer, Integer> costs = new LinkedHashMap<>(); //validIngredients 배열의 인덱스를 넣으면 연금술 에너지 필요량을 반환한다.
        static {
            costs.put( 0, 0 );
            costs.put( 1, 0 );
            costs.put( 2, 0 );
            costs.put( 3, 0 );
            costs.put( 4, 5 );
            costs.put( 5, 5 );
            costs.put( 6, 0 );
            costs.put( 7, 0 );
            costs.put( 8, 0 );
            costs.put( 9, 0 );
            costs.put( 10, 5 );
            costs.put( 11, 5 );
            costs.put( 12, 5 );
            costs.put( 13, 5 );
            costs.put( 14, 0 );
            costs.put( 15, 0 );
            costs.put( 16, 0 );
            costs.put( 17, 0 );
            costs.put( 18, 5 );
            costs.put( 19, 5 );
            costs.put( 20, 0 );
            costs.put( 21, 0 );
            costs.put( 22, 0 );
        }

        public ArrayList<Class<?extends Item>> ingredientToArray(ArrayList<Item> ingredients) { //연금술 솥에 넣은 아이템들의 '클래스'를 배열로 만든다.

            ArrayList<Class<?extends Item>> ingredientsClassList = new ArrayList<>();

            for (Item i : ingredients) {
                ingredientsClassList.add(i.getClass());
            }

            return ingredientsClassList;
        }

        @Override
        public boolean testIngredients(ArrayList<Item> ingredients) {
            boolean valid = false;

            ArrayList<Class<?extends Item>> ingredientsClassList = ingredientToArray(ingredients);

            for (ArrayList<Class<?extends Item>> a : validIngredients) {
                if (ingredientsClassList.containsAll(a) && a.containsAll(ingredientsClassList)) { //위에서 만든 배열에 포함된 클래스를 모두 포함하고 있는 validIngredients가 존재할 경우 연금 가능
                    valid = true;
                }
            }

            return valid;
        }

        @Override
        public int cost(ArrayList<Item> ingredients) {
            int cost = 0;

            ArrayList<Class<?extends Item>> ingredientsClassList = ingredientToArray(ingredients);

            for (ArrayList<Class<?extends Item>> a : validIngredients) {
                if (ingredientsClassList.containsAll(a) && a.containsAll(ingredientsClassList)) { //validIngredients에 포함된 어떤 배열이 위에서 만든 배열의 요소를 모두 포함할 경우 그 인덱스를 가져와 costs의 키로 이용한다.
                    int index = validIngredients.indexOf(a);
                    cost = costs.get(index);
                }
            }

            return cost;
        }

        @Override
        public Item brew(ArrayList<Item> ingredients) {
            Item result = null;

            ArrayList<Class<?extends Item>> ingredientsClassList = ingredientToArray(ingredients);

            for (ArrayList<Class<?extends Item>> a : validIngredients) {
                if (ingredientsClassList.containsAll(a) && a.containsAll(ingredientsClassList)) { //validIngredients에 포함된 어떤 배열이 위에서 만든 배열의 요소를 모두 포함할 경우 그 인덱스를 가져와 indexToOutput의 키로 이용한다.
                    int index = validIngredients.indexOf(a);
                    result = new BluePrint(Reflection.newInstance(indexToOutput.get(index)));
                }
            }

            for (Item i : ingredients) {
                i.quantity(i.quantity()-1);
            }

            return result;
        }

        @Override
        public Item sampleOutput(ArrayList<Item> ingredients) {
            Item result = null;

            ArrayList<Class<?extends Item>> ingredientsClassList = ingredientToArray(ingredients);

            for (ArrayList<Class<?extends Item>> a : validIngredients) {
                if (ingredientsClassList.containsAll(a) && a.containsAll(ingredientsClassList)) { //validIngredients에 포함된 어떤 배열이 위에서 만든 배열의 요소를 모두 포함할 경우 그 인덱스를 가져와 indexToOutput의 키로 이용한다.
                    int index = validIngredients.indexOf(a);
                    result = new BluePrint(Reflection.newInstance(indexToOutput.get(index)));
                }
            }

            return result;
        }
    }

}
